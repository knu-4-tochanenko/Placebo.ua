package ua.placebo.api.repository;

import com.github.database.rider.core.api.connection.ConnectionHolder;
import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.core.api.dataset.SeedStrategy;
import com.github.database.rider.spring.api.DBRider;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.sql.DataSource;

import ua.placebo.api.dto.DrugFilter;
import ua.placebo.api.entity.DrugEntity;
import ua.placebo.api.util.PostgresqlDbBaseTest;

import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.from;

@DBRider
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class DrugRepositoryIntegrationTest extends PostgresqlDbBaseTest {
	@Autowired
	protected DataSource dataSource;

	@SuppressWarnings("unused")
	public ConnectionHolder connectionHolder = () -> dataSource.getConnection();

	@Autowired
	private DrugRepository drugRepository;

	@Test
	@DataSet(executeScriptsBefore = "db-cleanup.sql",
			strategy = SeedStrategy.INSERT,
			value = "datasets/drugs.yml")
	public void shouldReturnAllDrugTypes() {
		// when
		List<String> actualDrugTypes = drugRepository.findAllTypes();

		// then
		Set<String> expectedDrugTypes = new HashSet<>(
				Arrays.asList("HEART", "MENTAL", "DIGESTIVE")
		);
		assertThat(new HashSet<>(actualDrugTypes)).isEqualTo(expectedDrugTypes);
	}

	@Test
	@DataSet(executeScriptsBefore = "db-cleanup.sql",
			strategy = SeedStrategy.INSERT,
			value = "datasets/drugs.yml")
	public void shouldReturnDrugsByName() {
		// given
		DrugFilter filter = new DrugFilter();
		filter.setName("Latuda");
		Pageable pageable = PageRequest.of(0, 10);

		// when
		Page<DrugEntity> drugs = drugRepository.findDrugs(filter, pageable);

		// then
		assertThat(drugs.getTotalElements()).isEqualTo(1);
		assertThat(drugs.getContent().get(0))
				.returns(1002L, from(DrugEntity::getId))
				.returns("Latuda", from(DrugEntity::getName))
				.returns("MENTAL", from(DrugEntity::getType))
				.returns("Drug that cures mental diseases", from(DrugEntity::getDescription))
				.returns(new BigDecimal("12.50"), from(DrugEntity::getPrice))
				.returns("https://www.northwestpharmacy.com/product/latuda", from(DrugEntity::getStoreUrl))
				.returns("https://www.consumeralertnow.com/images/Latuda.jpg", from(DrugEntity::getImageUrl));
	}

	@Test
	@DataSet(executeScriptsBefore = "db-cleanup.sql",
			strategy = SeedStrategy.INSERT,
			value = "datasets/drugs.yml")
	public void shouldReturnDrugsByType() {
		// given
		DrugFilter filter = new DrugFilter();
		filter.setType("HEART");
		Pageable pageable = PageRequest.of(0, 10);

		// when
		Page<DrugEntity> actualDrugs = drugRepository.findDrugs(filter, pageable);

		// then
		assertThat(actualDrugs.getTotalElements()).isEqualTo(2);
		Set<String> expectedDrugs = new HashSet<>(Arrays.asList("Corlanor", "Bystolic"));
		assertThat(new HashSet<>(actualDrugs.stream().map(DrugEntity::getName).collect(toList()))).isEqualTo(expectedDrugs);
	}

	@Test
	@DataSet(executeScriptsBefore = "db-cleanup.sql",
			strategy = SeedStrategy.INSERT,
			value = "datasets/drugs.yml")
	public void shouldReturnDrugsByPrice() {
		// given
		DrugFilter filter = new DrugFilter();
		filter.setMinPrice(new BigDecimal(11));
		filter.setMaxPrice(new BigDecimal(55));
		Pageable pageable = PageRequest.of(0, 10);

		// when
		Page<DrugEntity> actualDrugs = drugRepository.findDrugs(filter, pageable);

		// then
		assertThat(actualDrugs.getTotalElements()).isEqualTo(2);
		Set<String> expectedDrugs = new HashSet<>(Arrays.asList("Latuda", "Abilify"));
		assertThat(new HashSet<>(actualDrugs.stream().map(DrugEntity::getName).collect(toList()))).isEqualTo(expectedDrugs);
	}
}

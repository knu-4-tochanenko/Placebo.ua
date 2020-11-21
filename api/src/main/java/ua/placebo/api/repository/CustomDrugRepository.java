package ua.placebo.api.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import ua.placebo.api.dto.DrugFilter;
import ua.placebo.api.entity.DrugEntity;

public interface CustomDrugRepository {
  Page<DrugEntity> findDrugs(DrugFilter filter, Pageable pageable);
}

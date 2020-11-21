package ua.placebo.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ua.placebo.api.entity.DrugEntity;

public interface DrugRepository extends JpaRepository<DrugEntity, Long>, CustomDrugRepository {
}

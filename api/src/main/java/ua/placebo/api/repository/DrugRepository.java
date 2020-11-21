package ua.placebo.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

import ua.placebo.api.entity.DrugEntity;

public interface DrugRepository extends JpaRepository<DrugEntity, Long>, CustomDrugRepository {
  @Query("select distinct type from DrugEntity")
  List<String> findAllTypes();
}

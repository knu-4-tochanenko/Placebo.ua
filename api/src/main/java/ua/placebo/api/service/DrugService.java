package ua.placebo.api.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

import ua.placebo.api.dto.DrugDto;
import ua.placebo.api.dto.DrugFilter;

public interface DrugService {
  Page<DrugDto> searchDrugs(DrugFilter filter, Pageable pageable);

  List<String> getDrugTypes();
}

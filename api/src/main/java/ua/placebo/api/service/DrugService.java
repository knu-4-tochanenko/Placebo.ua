package ua.placebo.api.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import ua.placebo.api.dto.DrugDto;
import ua.placebo.api.dto.DrugFilter;

public interface DrugService {
  Page<DrugDto> searchDrugs(DrugFilter filter, Pageable pageable);
}

package ua.placebo.api.service.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import lombok.RequiredArgsConstructor;
import ua.placebo.api.dto.DrugDto;
import ua.placebo.api.dto.DrugFilter;
import ua.placebo.api.mapper.DrugMapper;
import ua.placebo.api.repository.DrugRepository;
import ua.placebo.api.service.DrugService;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DefaultDrugService implements DrugService {
  private final DrugRepository drugRepository;
  private final DrugMapper drugMapper;

  @Override
  public Page<DrugDto> searchDrugs(DrugFilter filter, Pageable pageable) {
    return drugRepository.findDrugs(filter, pageable)
        .map(drugMapper::toDto);
  }

  @Override
  public List<String> getDrugTypes() {
    return drugRepository.findAllTypes();
  }
}

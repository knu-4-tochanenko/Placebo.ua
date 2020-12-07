package ua.placebo.api.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.placebo.api.dto.DrugDto;
import ua.placebo.api.dto.DrugFilter;
import ua.placebo.api.exception.DrugNotFoundException;
import ua.placebo.api.mapper.DrugMapper;
import ua.placebo.api.repository.DrugRepository;
import ua.placebo.api.service.DrugService;

import java.util.List;

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

  @Override
  public DrugDto getDrugById(Long id) {
    return drugRepository.findById(id)
            .map(drugMapper::toDto)
            .orElseThrow(() -> new DrugNotFoundException(String.format("Drug with id %s is not found", id)));
  }
}

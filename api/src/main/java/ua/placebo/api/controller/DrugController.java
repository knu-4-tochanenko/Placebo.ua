package ua.placebo.api.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import lombok.RequiredArgsConstructor;
import ua.placebo.api.dto.DrugDto;
import ua.placebo.api.dto.DrugFilter;
import ua.placebo.api.service.DrugService;

@RestController
@RequestMapping("drugs")
@RequiredArgsConstructor
public class DrugController {
  private final DrugService drugService;

  @GetMapping
  public Page<DrugDto> searchDrugs(@RequestBody @Valid DrugFilter filter, Pageable pageable) {
    return drugService.searchDrugs(filter, pageable);
  }
}

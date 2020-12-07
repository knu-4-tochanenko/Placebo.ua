package ua.placebo.api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import ua.placebo.api.dto.DrugDto;
import ua.placebo.api.dto.DrugFilter;
import ua.placebo.api.service.DrugService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("drugs")
@RequiredArgsConstructor
public class DrugController {
  private final DrugService drugService;

  @PostMapping
  public Page<DrugDto> searchDrugs(@RequestBody @Valid DrugFilter filter, Pageable pageable) {
    return drugService.searchDrugs(filter, pageable);
  }

  @GetMapping("/types")
  public List<String> getDrugTypes() {
    return drugService.getDrugTypes();
  }

  @GetMapping("/{id}")
  public DrugDto getDrugById(@PathVariable("id") Long id) {
    return drugService.getDrugById(id);
  }
}

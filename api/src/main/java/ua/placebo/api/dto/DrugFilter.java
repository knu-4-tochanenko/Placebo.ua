package ua.placebo.api.dto;

import java.math.BigDecimal;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;

import lombok.Data;

@Data
public class DrugFilter {
  private String name;
  private String type;
  @DecimalMin("0")
  @Digits(integer=10, fraction=2)
  private BigDecimal minPrice;
  @DecimalMin("0")
  @Digits(integer=10, fraction=2)
  private BigDecimal maxPrice;
}

package ua.placebo.api.dto;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class DrugDto {
  private Long id;
  private String name;
  private String type;
  private String description;
  private BigDecimal price;
  private String storeUrl;
  private String imageUrl;
}

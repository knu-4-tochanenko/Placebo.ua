package ua.placebo.api.mapper;

import org.mapstruct.Mapper;

import ua.placebo.api.dto.DrugDto;
import ua.placebo.api.entity.DrugEntity;

@Mapper(componentModel = "spring")
public interface DrugMapper {
  DrugDto toDto(DrugEntity entity);
}

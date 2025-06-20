package org.example.demo2.utils.mapper.impl.response;

import java.util.List;

import org.example.demo2.dao.entity.ProfessoreEntity;
import org.example.demo2.dto.response.ProfessoreResponse;
import org.example.demo2.utils.mapper.general.GeneralRestMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProfessoreResponseMapper extends GeneralRestMapper<ProfessoreEntity, ProfessoreResponse> {

    @Override
    @Mapping(ignore = true, target = "classi")
    ProfessoreResponse fromEntityToRe(ProfessoreEntity entity);

    @Override
    @Mapping(ignore = true, target = "classi")
    ProfessoreEntity fromReToEntity(ProfessoreResponse re);

    @Override
    @Mapping(ignore = true, target = "classi")
    List<ProfessoreResponse> fromEntityListToReList(List<ProfessoreEntity> entityList);

    @Override
    @Mapping(ignore = true, target = "classi")
    List<ProfessoreEntity> fromReListToEntityList(List<ProfessoreResponse> re);

}

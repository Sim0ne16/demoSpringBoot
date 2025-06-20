package org.example.demo2.utils.mapper.impl.request;

import java.util.List;

import org.example.demo2.dao.entity.ProfessoreEntity;
import org.example.demo2.dto.request.ProfessoreRequest;
import org.example.demo2.utils.mapper.general.GeneralRestMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;



@Mapper(componentModel = "spring")
public interface ProfessoreRequestMapper extends GeneralRestMapper<ProfessoreEntity, ProfessoreRequest> {

    @Mapping(ignore = true, target = "classi")
    void updateEntityFromDto(ProfessoreRequest professoreRequest, @MappingTarget ProfessoreEntity professoreEntity);
    
    @Mapping(ignore = true, target = "classi")
    @Override
    ProfessoreEntity fromReToEntity(ProfessoreRequest re);

    @Mapping(ignore = true, target = "classi")
    @Override
    List<ProfessoreEntity> fromReListToEntityList(List<ProfessoreRequest> re);

    @Mapping(ignore = true, target = "specializzazione")
    @Override
    ProfessoreRequest fromEntityToRe(ProfessoreEntity entity);

    @Mapping(ignore = true, target = "classi")
    @Override
    List<ProfessoreRequest> fromEntityListToReList(List<ProfessoreEntity> entityList);


}

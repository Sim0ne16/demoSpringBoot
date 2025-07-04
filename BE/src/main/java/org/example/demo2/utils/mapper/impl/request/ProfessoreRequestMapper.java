package org.example.demo2.utils.mapper.impl.request;

import org.example.demo2.dao.entity.ProfessoreEntity;
import org.example.demo2.dto.request.ProfessoreRequest;
import org.example.demo2.utils.mapper.general.GeneralRestMapper;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ProfessoreRequestMapper extends GeneralRestMapper<ProfessoreEntity, ProfessoreRequest> {

    // Mapping base (Request <-> Entity)
    @Override
    ProfessoreRequest fromEntityToRe(ProfessoreEntity entity);

    @Override
    ProfessoreEntity fromReToEntity(ProfessoreRequest request);

    @Override
    List<ProfessoreRequest> fromEntityListToReList(List<ProfessoreEntity> entityList);

    @Override
    List<ProfessoreEntity> fromReListToEntityList(List<ProfessoreRequest> requestList);

    // Aggiorna entity esistente ignorando classi (ManyToMany)
    @Mapping(target = "classi", ignore = true)
    void updateEntityFromDto(ProfessoreRequest request, @MappingTarget ProfessoreEntity entity);
}

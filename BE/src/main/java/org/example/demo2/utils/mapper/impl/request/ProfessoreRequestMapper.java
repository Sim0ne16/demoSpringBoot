package org.example.demo2.utils.mapper.impl.request;

import org.example.demo2.dao.entity.ProfessoreEntity;
import org.example.demo2.dto.request.ProfessoreRequest;
import org.example.demo2.dto.response.ProfessoreResponse;
import org.example.demo2.utils.mapper.general.GeneralRestMapper;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;


@Mapper(componentModel = "spring")
public interface ProfessoreRequestMapper extends GeneralRestMapper<ProfessoreEntity, ProfessoreRequest> {

    @Named("professoreSemplice")
    ProfessoreResponse fromEntityToReSimple(ProfessoreEntity entity);

    @Named("professoreSemplice")
    @Mapping(target = "classi", ignore = true)
    ProfessoreEntity fromReToEntitySimple(ProfessoreResponse response);

    @IterableMapping(qualifiedByName = "professoreSemplice")
    List<ProfessoreResponse> fromEntityListToReListSimple(List<ProfessoreEntity> entityList);

    @IterableMapping(qualifiedByName = "professoreSemplice")
    List<ProfessoreEntity> fromReListToEntityListSimple(List<ProfessoreResponse> responseList);


}

package org.example.demo2.utils.mapper.impl.response;

import org.example.demo2.dao.entity.ProfessoreEntity;
import org.example.demo2.dto.response.ProfessoreResponse;
import org.example.demo2.utils.mapper.general.GeneralRestMapper;
import org.mapstruct.*;

import java.util.List;


@Mapper(
        componentModel = "spring",
        uses = ClasseResponseMapper.class,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface ProfessoreResponseMapper extends GeneralRestMapper<ProfessoreEntity, ProfessoreResponse> {

    // --------------------- MAPPING SEMPLICE --------------------- //

    @Named("professoreSemplice")
    @Mapping(target = "classi", ignore = true)
    ProfessoreResponse fromEntityToReSimple(ProfessoreEntity entity);

    @Named("professoreSemplice")
    @Mapping(target = "classi", ignore = true)
    ProfessoreEntity fromReToEntitySimple(ProfessoreResponse response);

    @IterableMapping(qualifiedByName = "professoreSemplice")
    List<ProfessoreResponse> fromEntityListToReListSimple(List<ProfessoreEntity> entityList);

    @IterableMapping(qualifiedByName = "professoreSemplice")
    List<ProfessoreEntity> fromReListToEntityListSimple(List<ProfessoreResponse> responseList);


    // --------------------- MAPPING COMPLETO --------------------- //

    @Named("professoreCompleto")
    @Mapping(target = "classi", qualifiedByName = "classeSemplice")
    ProfessoreResponse fromEntityToRe(ProfessoreEntity entity);

    @Named("professoreCompleto")
    @Mapping(target = "classi", qualifiedByName = "classeSemplice")
        // usa il mapping semplice delle classi
    ProfessoreEntity fromReToEntity(ProfessoreResponse response);

    @IterableMapping(qualifiedByName = "professoreCompleto")
    List<ProfessoreResponse> fromEntityListToReList(List<ProfessoreEntity> entityList);

    @IterableMapping(qualifiedByName = "professoreCompleto")
    List<ProfessoreEntity> fromReListToEntityList(List<ProfessoreResponse> responseList);
}

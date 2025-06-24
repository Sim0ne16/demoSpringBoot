package org.example.demo2.utils.mapper.impl.response;

import org.example.demo2.dao.entity.ClasseEntity;
import org.example.demo2.dto.response.ClasseResponse;
import org.mapstruct.*;

import java.util.ArrayList;
import java.util.List;


@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface ClasseResponseMapper {

    // --------------------- MAPPING SEMPLICE --------------------- //

    @Named("classeSemplice")
    @Mapping(target = "professori", ignore = true)
    @Mapping(target = "studenti", ignore = true)
    ClasseResponse fromEntityToReSimple(ClasseEntity entity);

    @Named("classeSemplice")
    @Mapping(target = "professori", ignore = true)
    @Mapping(target = "studenti", ignore = true)
    ClasseEntity fromReToEntitySimple(ClasseResponse response);

    @IterableMapping(qualifiedByName = "classeSemplice")
    List<ClasseResponse> fromEntityListToReListSimple(List<ClasseEntity> entityList);

    @IterableMapping(qualifiedByName = "classeSemplice")
    List<ClasseEntity> fromReListToEntityListSimple(List<ClasseResponse> responseList);

    // --------------------- MAPPING COMPLETO --------------------- //

    //Se ci sono studenti/professori, mappali uno a uno nel formato semplice e mettili nel DTO. Se non ci sono, lascia la lista vuota."
    @Named("classeCompleta")
    @Mapping(target = "professori", expression = "java(entity.getProfessori() != null ? entity.getProfessori().stream().map(professoreResponseMapper::fromEntityToReSimple).collect(java.util.stream.Collectors.toList()) : new java.util.ArrayList<>())")
    @Mapping(target = "studenti", expression = "java(entity.getStudenti() != null ? entity.getStudenti().stream().map(studenteResponseMapper::fromEntityToReSimple).collect(java.util.stream.Collectors.toList()) : new java.util.ArrayList<>())")
    ClasseResponse fromEntityToRe(ClasseEntity entity, StudenteResponseMapper studenteResponseMapper, ProfessoreResponseMapper professoreResponseMapper);

    @Named("classeCompleta")
    @Mapping(target = "professori", expression = "java(response.getProfessori() != null ? response.getProfessori().stream().map(professoreResponseMapper::fromReToEntitySimple).collect(java.util.stream.Collectors.toList()) : new java.util.ArrayList<>())")
    @Mapping(target = "studenti", expression = "java(response.getStudenti() != null ? response.getStudenti().stream().map(studenteResponseMapper::fromReToEntitySimple).collect(java.util.stream.Collectors.toList()) : new java.util.ArrayList<>())")
    ClasseEntity fromReToEntity(ClasseResponse response, StudenteResponseMapper studenteResponseMapper, ProfessoreResponseMapper professoreResponseMapper);


    default List<ClasseResponse> fromEntityListToReList(List<ClasseEntity> entityList, StudenteResponseMapper studenteResponseMapper, ProfessoreResponseMapper professoreResponseMapper) {
        List<ClasseResponse> responseList = new ArrayList<>();
        entityList.forEach(el -> responseList.add(fromEntityToRe(el, studenteResponseMapper, professoreResponseMapper)));
        return responseList;
    }

    default List<ClasseEntity> fromReListToEntityList(List<ClasseResponse> responseList, StudenteResponseMapper studenteResponseMapper, ProfessoreResponseMapper professoreResponseMapper) {
        List<ClasseEntity> entityList = new ArrayList<>();
        responseList.forEach(el -> entityList.add(fromReToEntity(el, studenteResponseMapper, professoreResponseMapper)));
        return entityList;
    }

}
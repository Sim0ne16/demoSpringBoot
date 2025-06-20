package org.example.demo2.utils.mapper.impl.response;

import org.example.demo2.dao.entity.StudenteEntity;
import org.example.demo2.dto.response.StudenteResponse;
import org.example.demo2.utils.mapper.general.GeneralRestMapper;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring")
public interface StudenteResponseMapper extends GeneralRestMapper<StudenteEntity, StudenteResponse> {

    @Override
    @Named("completo")
    @Mapping(target = "classeId", expression = "java(entity.getClasse() != null ? entity.getClasse().getId() : null)") //Perchè?
    @Mapping(ignore = true, target = "classe.studenti")
    StudenteResponse fromEntityToRe(StudenteEntity entity);

    @Override
    @Named("completo")
    @Mapping(ignore = true, target = "classe.studenti")
    StudenteEntity fromReToEntity(StudenteResponse re);

    @Override
    @IterableMapping(qualifiedByName = "completo")
    List<StudenteResponse> fromEntityListToReList(List<StudenteEntity> entityList);

    @Override
    @IterableMapping(qualifiedByName = "completo")
    List<StudenteEntity> fromReListToEntityList(List<StudenteResponse> re);


    @Named("semplice")
    @Mapping(ignore = true, target = "classe" )
    StudenteResponse fromEntityToReSimple(StudenteEntity entityList);

    @IterableMapping(qualifiedByName = "semplice")
    List<StudenteResponse> fromEntityListToReListSimple(List<StudenteEntity> entityList);


}

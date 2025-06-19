package org.example.demo2.utils.mapper.impl.request;

import org.example.demo2.dao.entity.StudenteEntity;
import org.example.demo2.dto.request.StudenteRequest;
import org.example.demo2.utils.mapper.general.GeneralRestMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface StudenteRequestMapper extends GeneralRestMapper<StudenteEntity, StudenteRequest> {

    @Mapping(ignore = true, target = "classe.studenti")
    //@MappingTarget indica a MapStruct che studenteEntity non è da creare, ma da aggiornare/modificare.
    void updateEntityFromDto(StudenteRequest studenteRequest, @MappingTarget StudenteEntity studenteEntity);

    @Mapping(ignore = true, target = "classe.studenti")
    @Override
    StudenteEntity fromReToEntity(StudenteRequest re);

    @Mapping(ignore = true, target = "classe.studenti")
    @Override
    List<StudenteEntity> fromReListToEntityList(List<StudenteRequest> re);

    @Mapping(ignore = true, target = "classe.studenti")
    @Override
    StudenteRequest fromEntityToRe(StudenteEntity entity);

    @Mapping(ignore = true, target = "classe.studenti")
    @Override
    List<StudenteRequest> fromEntityListToReList(List<StudenteEntity> entityList);
}


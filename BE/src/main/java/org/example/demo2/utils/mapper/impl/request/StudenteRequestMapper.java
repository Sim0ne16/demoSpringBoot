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

    @Mapping(target = "classe", ignore = true)
    void updateEntityFromDto(StudenteRequest studenteRequest, @MappingTarget StudenteEntity studenteEntity);

    @Mapping(target = "classe", ignore = true)
    @Override
    StudenteEntity fromReToEntity(StudenteRequest studenteRequest);

    
    @Override
    StudenteRequest fromEntityToRe(StudenteEntity studenteEntity);

    @Mapping(ignore = true, target = "classe")
    @Override
    List<StudenteRequest> fromEntityListToReList(List<StudenteEntity> entityList);

    @Mapping(ignore = true, target = "classe")
    @Override
    List<StudenteEntity> fromReListToEntityList(List<StudenteRequest> reList);


}

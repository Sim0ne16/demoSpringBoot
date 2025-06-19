package org.example.demo2.utils.mapper.impl.response;

import org.example.demo2.dao.entity.StudenteEntity;
import org.example.demo2.dto.response.StudenteResponse;
import org.example.demo2.utils.mapper.general.GeneralRestMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface StudenteResponseMapper extends GeneralRestMapper<StudenteEntity, StudenteResponse> {
    @Override
    @Mapping(ignore = true, target = "classe.studenti")
    StudenteResponse fromEntityToRe(StudenteEntity entity);

    @Override
    @Mapping(ignore = true, target = "classe.studenti")
    StudenteEntity fromReToEntity(StudenteResponse re);

    @Override
    @Mapping(ignore = true, target = "classe.studenti")
    List<StudenteResponse> fromEntityListToReList(List<StudenteEntity> entityList);

    @Override
    @Mapping(ignore = true, target = "classe.studenti")
    List<StudenteEntity> fromReListToEntityList(List<StudenteResponse> re);
}

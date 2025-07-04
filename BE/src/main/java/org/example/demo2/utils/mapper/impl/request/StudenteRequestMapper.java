package org.example.demo2.utils.mapper.impl.request;

import org.example.demo2.dao.entity.StudenteEntity;
import org.example.demo2.dto.request.StudenteRequest;
import org.example.demo2.utils.mapper.general.GeneralRestMapper;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface StudenteRequestMapper extends GeneralRestMapper<StudenteEntity, StudenteRequest> {

    // Mapping base (Request <-> Entity)
    @Override
    StudenteRequest fromEntityToRe(StudenteEntity entity);

    @Override
    StudenteEntity fromReToEntity(StudenteRequest request);

    @Override
    List<StudenteRequest> fromEntityListToReList(List<StudenteEntity> entityList);

    @Override
    List<StudenteEntity> fromReListToEntityList(List<StudenteRequest> requestList);

    // Aggiorna entity esistente ignorando la relazione Classe
    @Mapping(target = "classe", ignore = true)
    void updateEntityFromDto(StudenteRequest request, @MappingTarget StudenteEntity entity);
}

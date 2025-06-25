package org.example.demo2.utils.mapper.impl.request;

import org.example.demo2.dao.entity.StudenteEntity;
import org.example.demo2.dto.request.StudenteRequest;
import org.example.demo2.utils.mapper.general.GeneralRestMapper;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface StudenteRequestMapper extends GeneralRestMapper<StudenteEntity, StudenteRequest> {

    @Named("studenteSemplice")
    StudenteRequest fromEntityToReSimple(StudenteEntity entity);

    @Named("studenteSemplice")
    @Mapping(target = "classe", ignore = true)
    StudenteEntity fromReToEntitySimple(StudenteRequest request);

    @IterableMapping(qualifiedByName = "studenteSemplice")
    List<StudenteRequest> fromEntityListToReListSimple(List<StudenteEntity> entityList);

    @IterableMapping(qualifiedByName = "studenteSemplice")
    List<StudenteEntity> fromReListToEntityListSimple(List<StudenteRequest> requestList);

    //Aggiunto per aggiornare un'entità esistente
    @Mapping(target = "classe", ignore = true)
    void updateEntityFromDto(StudenteRequest request, @MappingTarget StudenteEntity entity);

}

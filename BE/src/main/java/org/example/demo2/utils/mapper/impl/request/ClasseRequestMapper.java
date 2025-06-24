package org.example.demo2.utils.mapper.impl.request;


import org.example.demo2.dao.entity.ClasseEntity;
import org.example.demo2.dto.request.ClasseRequest;
import org.example.demo2.utils.mapper.general.GeneralRestMapper;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ClasseRequestMapper extends GeneralRestMapper<ClasseEntity, ClasseRequest> {

    @Named("classeSemplice")
    ClasseRequest fromEntityToReSimple(ClasseEntity entity);

    @Named("classeSemplice")
    @Mapping(target = "professori", ignore = true)
    @Mapping(target = "studenti", ignore = true)
    ClasseEntity fromReToEntitySimple(ClasseRequest request);

    @IterableMapping(qualifiedByName = "classeSemplice")
    List<ClasseRequest> fromEntityListToReListSimple(List<ClasseEntity> entityList);

    @IterableMapping(qualifiedByName = "classeSemplice")
    List<ClasseEntity> fromReListToEntityListSimple(List<ClasseRequest> requestList);

}


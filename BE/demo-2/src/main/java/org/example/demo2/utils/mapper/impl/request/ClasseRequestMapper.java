package org.example.demo2.utils.mapper.impl.request;


import org.example.demo2.dao.entity.ClasseEntity;
import org.example.demo2.dto.request.ClasseRequest;
import org.example.demo2.dto.response.ClasseResponse;
import org.example.demo2.utils.mapper.general.GeneralRestMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ClasseRequestMapper extends GeneralRestMapper<ClasseEntity, ClasseResponse> {

    @Mapping(ignore = true, target = "professori")
    @Mapping(ignore = true, target = "studenti")
    void updateEntityFromDto(ClasseRequest classeRequest, @MappingTarget ClasseEntity classeEntity);

    @Mapping(ignore = true, target = "professori")
    @Mapping(ignore = true, target = "studenti")
    ClasseEntity fromReToEntity(ClasseRequest classeRequest);

    @Override
    @Mapping(ignore = true, target = "professori")
    @Mapping(ignore = true, target = "studenti")
    ClasseResponse fromEntityToRe(ClasseEntity request);

    @Mapping(ignore = true, target = "professori")
    @Mapping(ignore = true, target = "studenti")
    ClasseEntity fromReToEntity(Long id);
}


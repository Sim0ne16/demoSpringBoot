package org.example.demo2.utils.mapper.impl.response;

import org.example.demo2.dao.entity.ClasseEntity;
import org.example.demo2.dto.response.ClasseResponse;
import org.example.demo2.utils.mapper.general.GeneralRestMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper(componentModel = "spring")
public interface ClasseResponseMapper extends GeneralRestMapper<ClasseEntity, ClasseResponse> {

    @Override
    @Mapping(ignore = true, target = "studenti") // evita loop potenziali
    @Mapping(ignore = true, target = "professori")
    ClasseResponse fromEntityToRe(ClasseEntity entity);

    @Override
    @Mapping(ignore = true, target = "studenti")
    @Mapping(ignore = true, target = "professori")
    ClasseEntity fromReToEntity(ClasseResponse re);

}


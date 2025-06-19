package org.example.demo2.utils.mapper.impl.request;

import org.example.demo2.dao.entity.ClasseEntity;
import org.example.demo2.dto.request.ClasseRequest;
import org.example.demo2.dto.response.ClasseResponse;
import org.example.demo2.utils.mapper.general.GeneralRestMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ClasseRequestMapper extends GeneralRestMapper<ClasseEntity, ClasseResponse> {

    ClasseEntity fromReToEntity(ClasseRequest request);
    ClasseResponse fromEntityToRe(ClasseEntity request);

}

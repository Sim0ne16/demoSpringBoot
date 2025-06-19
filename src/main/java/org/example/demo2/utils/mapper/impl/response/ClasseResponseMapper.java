package org.example.demo2.utils.mapper.impl.response;

import org.example.demo2.dao.entity.ClasseEntity;
import org.example.demo2.dto.response.ClasseResponse;
import org.example.demo2.utils.mapper.general.GeneralRestMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ClasseResponseMapper extends GeneralRestMapper<ClasseEntity, ClasseResponse> {

}

package org.example.demo2.utils.mapper.impl.request;

import org.example.demo2.dao.entity.ProfessoreEntity;
import org.example.demo2.dto.request.ProfessoreRequest;
import org.example.demo2.utils.mapper.general.GeneralRestMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProfessoreRequestMapper extends GeneralRestMapper<ProfessoreEntity, ProfessoreRequest> {


}

package org.example.demo2.utils.mapper.impl.response;

import org.example.demo2.dao.entity.ProfessoreEntity;
import org.example.demo2.dto.response.ProfessoreResponse;
import org.example.demo2.utils.mapper.general.GeneralRestMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProfessoreResponseMapper extends GeneralRestMapper<ProfessoreEntity, ProfessoreResponse> {


}

package org.example.demo2.utils.mapper.impl.request;

import org.example.demo2.dao.entity.ClasseEntity;
import org.example.demo2.dto.request.ClasseRequest;
import org.example.demo2.utils.mapper.general.GeneralRestMapper;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ClasseRequestMapper extends GeneralRestMapper<ClasseEntity, ClasseRequest> {

    // Mapping base (Request <-> Entity)
    @Override
    ClasseRequest fromEntityToRe(ClasseEntity entity);

    @Override
    ClasseEntity fromReToEntity(ClasseRequest request);

    @Override
    List<ClasseRequest> fromEntityListToReList(List<ClasseEntity> entityList);

    @Override
    List<ClasseEntity> fromReListToEntityList(List<ClasseRequest> requestList);

    // Aggiorna entity esistente ignorando le relazioni cicliche
    @Mapping(target = "studenti", ignore = true)
    @Mapping(target = "professori", ignore = true)
    void updateEntityFromDto(ClasseRequest request, @MappingTarget ClasseEntity entity);
}

package org.example.demo2.utils.mapper.impl.response;

import org.example.demo2.dao.entity.StudenteEntity;
import org.example.demo2.dto.response.StudenteResponse;
import org.example.demo2.utils.mapper.general.GeneralRestMapper;
import org.mapstruct.*;

import java.util.List;

@Mapper(
        componentModel = "spring",
        uses = ClasseResponseMapper.class,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface StudenteResponseMapper extends GeneralRestMapper<StudenteEntity, StudenteResponse> {

    // --------------------- MAPPING SEMPLICE --------------------- //

    @Named("studenteSemplice")
    @Mapping(target = "classe", ignore = true)
    StudenteResponse fromEntityToReSimple(StudenteEntity entity);

    @Named("studenteSemplice")
    @Mapping(target = "classe", ignore = true)
    StudenteEntity fromReToEntitySimple(StudenteResponse response);

    @IterableMapping(qualifiedByName = "studenteSemplice")
    List<StudenteResponse> fromEntityListToReListSimple(List<StudenteEntity> entityList);

    @IterableMapping(qualifiedByName = "studenteSemplice")
    List<StudenteEntity> fromReListToEntityListSimple(List<StudenteResponse> responseList);

    // --------------------- MAPPING COMPLETO --------------------- //

    @Named("studenteCompleto")
    @Mapping(target = "classe", qualifiedByName = "classeSemplice")
    StudenteResponse fromEntityToRe(StudenteEntity entity);

    @Named("studenteCompleto")
    @Mapping(target = "classe", qualifiedByName = "classeSemplice")
    StudenteEntity fromReToEntity(StudenteResponse response);

    @IterableMapping(qualifiedByName = "studenteCompleto")
    List<StudenteResponse> fromEntityListToReList(List<StudenteEntity> entityList);

    @IterableMapping(qualifiedByName = "studenteCompleto")
    List<StudenteEntity> fromReListToEntityList(List<StudenteResponse> responseList);
}

package org.example.demo2.utils.mapper.impl.request;

import org.example.demo2.dao.entity.ClasseEntity;
import org.example.demo2.dao.entity.StudenteEntity;
import org.example.demo2.dto.request.StudenteRequest;
import org.example.demo2.utils.mapper.general.GeneralRestMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring", imports = org.example.demo2.dao.entity.ClasseEntity.class)
public interface StudenteRequestMapper extends GeneralRestMapper<StudenteEntity, StudenteRequest> {

    @Mapping(target = "classe", expression = "java(fromClasseId(studenteRequest.getClasseId()))")
    void updateEntityFromDto(StudenteRequest studenteRequest, @MappingTarget StudenteEntity studenteEntity);

    @Mapping(target = "classe", expression = "java(fromClasseId(re.getClasseId()))")
    @Override
    StudenteEntity fromReToEntity(StudenteRequest re);

    @Mapping(source = "classe.id", target = "classeId")
    @Override
    StudenteRequest fromEntityToRe(StudenteEntity entity);

    @Mapping(ignore = true, target = "classe")
    @Override
    List<StudenteRequest> fromEntityListToReList(List<StudenteEntity> entityList);

    @Mapping(ignore = true, target = "classe")
    @Override
    List<StudenteEntity> fromReListToEntityList(List<StudenteRequest> reList);

    // Metodo di supporto per assegnare l'oggetto ClasseEntity da classeId
    default ClasseEntity fromClasseId(Long classeId) {
        if (classeId == null) {
            return null;
        }
        ClasseEntity classe = new ClasseEntity();
        classe.setId(classeId);
        return classe;
    }
}

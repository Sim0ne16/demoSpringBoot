package org.example.demo2.service.general;

import java.util.List;

import org.example.demo2.dao.entity.ClasseEntity;
import org.example.demo2.dto.request.ClasseRequest;
import org.example.demo2.dto.response.ClasseResponse;
import org.example.demo2.utils.exception.NotFoundException;

public interface ClasseService extends CrudService<ClasseEntity> {
    ClasseResponse insert(ClasseRequest classeRequest);

    ClasseResponse getById(Long id) throws NotFoundException;

    ClasseResponse getStudentList(Long classeId) throws NotFoundException;

    ClasseResponse getProfessori(Long classeId) throws NotFoundException;

    ClasseResponse update1(ClasseRequest classeRequest) throws NotFoundException;

    List<ClasseResponse> getAll();

    void delete(Long id) throws NotFoundException;

}

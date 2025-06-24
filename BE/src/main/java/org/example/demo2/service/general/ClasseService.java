package org.example.demo2.service.general;

import org.example.demo2.dto.request.ClasseRequest;
import org.example.demo2.dto.response.ClasseResponse;
import org.example.demo2.utils.exception.NotFoundException;

import java.util.List;

public interface ClasseService {
    ClasseResponse insert(ClasseRequest classeRequest);

    ClasseResponse getById(Long id) throws NotFoundException;

    ClasseResponse getStudentList(Long classeId) throws NotFoundException;

    ClasseResponse getProfessori(Long classeId) throws NotFoundException;

    ClasseResponse update1(ClasseRequest classeRequest) throws NotFoundException;

    List<ClasseResponse> getAll(boolean includeStudenti, boolean includeProfessori);

    void delete(Long id) throws NotFoundException;

}

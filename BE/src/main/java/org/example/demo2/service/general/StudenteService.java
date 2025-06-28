package org.example.demo2.service.general;


import org.example.demo2.dto.request.StudenteRequest;
import org.example.demo2.dto.response.StudenteResponse;
import org.example.demo2.utils.exception.NotFoundException;

import java.util.List;

public interface StudenteService {


    StudenteResponse insert(StudenteRequest studenteRequest) throws NotFoundException;

    StudenteResponse getById(Long id, boolean includeClasse) throws NotFoundException;

    public StudenteResponse update(Long id, StudenteRequest studenteRequest) throws NotFoundException; 

    List<StudenteResponse> getAll(boolean includeClasse);

    List<StudenteResponse> getByNameAndLastName(String nome, String cognome) throws NotFoundException;

    void delete(Long id) throws NotFoundException;

    StudenteResponse assegnaClasse(Long studenteId, Long classeId) throws NotFoundException;

    //Aggiunto per rimuovere i due metodi che erano nel posto sbagliato in ClassController.
    List<StudenteResponse> getAllByClass(Long classeId) throws NotFoundException;

    
} 
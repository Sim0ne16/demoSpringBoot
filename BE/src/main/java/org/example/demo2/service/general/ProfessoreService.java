package org.example.demo2.service.general;

import java.util.List;

import org.example.demo2.dao.entity.ProfessoreEntity;
import org.example.demo2.dto.request.ProfessoreRequest;
import org.example.demo2.dto.response.ClasseResponse;
import org.example.demo2.dto.response.ProfessoreResponse;
import org.example.demo2.utils.exception.NotFoundException;

public interface ProfessoreService {

    ProfessoreResponse insert(ProfessoreRequest professoreRequest);
    ProfessoreResponse getById(Long id) throws NotFoundException;
    ProfessoreResponse getById2(Long id) throws NotFoundException;
    ProfessoreResponse update1(ProfessoreRequest professoreRequest) throws NotFoundException;
    ProfessoreResponse update2(ProfessoreRequest professoreRequest) throws NotFoundException;
    List<ProfessoreResponse> getAll();
    ProfessoreResponse getByNameAndLastName(String nome, String cognome) throws NotFoundException;
    void delete(Long id) throws NotFoundException;

    List<ClasseResponse> getClassiDelProfessore(Long professoreId) throws NotFoundException;
    ProfessoreResponse assegnaClasse(Long professoreId, Long classeId) throws NotFoundException;
    
    //Aggiunto per rimuovere i due metodi che erano nel posto sbagliato in ClassController.
    List<ProfessoreResponse> getAllByClasse(Long classeId) throws NotFoundException;
    
}

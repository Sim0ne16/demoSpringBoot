package org.example.demo2.service.general;

import org.example.demo2.dto.request.ProfessoreRequest;
import org.example.demo2.dto.response.ProfessoreResponse;
import org.example.demo2.utils.enums.Specializzazione;
import org.example.demo2.utils.exception.NotFoundException;

import java.time.LocalDate;
import java.util.List;

public interface ProfessoreService {

    ProfessoreResponse insert(ProfessoreRequest professoreRequest);

    ProfessoreResponse getById(Long id) throws NotFoundException;

    ProfessoreResponse getById2(Long id) throws NotFoundException;

    ProfessoreResponse update(Long id, ProfessoreRequest professoreRequest) throws NotFoundException;

    List<ProfessoreResponse> getAll(boolean includeClassi);

    ProfessoreResponse getByNameAndLastName(String nome, String cognome) throws NotFoundException;

    void delete(Long id) throws NotFoundException;

    ProfessoreResponse assegnaClasse(Long professoreId, Long classeId) throws NotFoundException;

    List<ProfessoreResponse> getAllByClasse(Long classeId) throws NotFoundException;

    List<ProfessoreResponse> getProfessoriByDataNascitaBetween(LocalDate dataInizio, LocalDate dataFine);

    List<ProfessoreResponse> getProfessoriBySpecializzazione(Specializzazione specializzazione);

}

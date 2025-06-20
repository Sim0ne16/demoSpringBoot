package org.example.demo2.service.impl;

import java.util.List;
import java.util.Optional;

import org.example.demo2.dao.entity.ClasseEntity;
import org.example.demo2.dao.entity.ProfessoreEntity;
import org.example.demo2.dao.repository.ClasseRepository;
import org.example.demo2.dao.repository.ProfessoreRepository;
import org.example.demo2.dto.request.ProfessoreRequest;
import org.example.demo2.dto.response.ClasseResponse;
import org.example.demo2.dto.response.ProfessoreResponse;
import org.example.demo2.service.general.ProfessoreService;
import org.example.demo2.utils.exception.NotFoundException;
import org.example.demo2.utils.mapper.impl.request.ProfessoreRequestMapper;
import org.example.demo2.utils.mapper.impl.response.ClasseResponseMapper;
import org.example.demo2.utils.mapper.impl.response.ProfessoreResponseMapper;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProfessoreServiceImpl implements ProfessoreService {


    private final ProfessoreRepository professoreRepository;
    private final ProfessoreRequestMapper professoreRequestMapper;
    private final ProfessoreResponseMapper professoreResponseMapper;
    private final ClasseRepository classeRepository;
    private final ClasseResponseMapper classeResponseMapper;

    @Override
    public ProfessoreResponse insert(ProfessoreRequest professoreRequest) {
        ProfessoreEntity professoreEntity = professoreRequestMapper.fromReToEntity(professoreRequest);
        professoreRepository.save(professoreEntity);
        return professoreResponseMapper.fromEntityToRe(professoreEntity);
    }

    @Override
    public ProfessoreResponse getById(Long id) throws NotFoundException {
        Optional<ProfessoreEntity> professoreEntityOptional = professoreRepository.findById(id);

        if (professoreEntityOptional.isPresent()) {
            return professoreResponseMapper.fromEntityToRe(professoreEntityOptional.get());
        } else {
            throw new NotFoundException("Professore non trovato");
        }
    }
    // Preferibile utilizzare questo per via del .orElseThrow
    @Override
    public ProfessoreResponse getById2(Long id) throws NotFoundException {
        ProfessoreEntity professoreEntity = professoreRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Professore non trovato"));
        return professoreResponseMapper.fromEntityToRe(professoreEntity);
    }

    @Override
    public ProfessoreResponse update1(ProfessoreRequest professoreRequest) throws NotFoundException {
       ProfessoreEntity professoreEntity = professoreRepository.findById(professoreRequest.getId())
                .orElseThrow(() -> new NotFoundException("Professore non esistente"));

        // updateEntityFromDto è un metodo del mapper che aggiorna solo i campi necessari.
        professoreRequestMapper.updateEntityFromDto(professoreRequest, professoreEntity);
        professoreRepository.save(professoreEntity);

        return professoreResponseMapper.fromEntityToRe(professoreEntity );
    }

    @Override
    public ProfessoreResponse update2(ProfessoreRequest professoreRequest) throws NotFoundException {
       if (!professoreRepository.existsById(professoreRequest.getId())) {
            throw new NotFoundException("Professore non esistente");
        }
        ProfessoreEntity entityUpdated = professoreRepository.save(professoreRequestMapper.fromReToEntity(professoreRequest));
        return professoreResponseMapper.fromEntityToRe(entityUpdated);
    }

    @Override
    public List<ProfessoreResponse> getAll() {
       List<ProfessoreEntity> professori = professoreRepository.findAll();
        return professoreResponseMapper.fromEntityListToReList(professori);
        }
    

    @Override
    public ProfessoreResponse getByNameAndLastName(String nome, String cognome) throws NotFoundException {
        Optional<ProfessoreEntity> professoreEntity = professoreRepository.findByNomeAndCognome(nome, cognome);

        if (!professoreEntity.isPresent()) {
            throw new NotFoundException("Professore non esistente");
        }

        return professoreResponseMapper.fromEntityToRe(professoreEntity.get());
    }
        
    
    @Override
    public void delete(Long id) throws NotFoundException {
       if (!professoreRepository.existsById(id)) {
            throw new NotFoundException("Professore non esistente");
        }
        professoreRepository.deleteById(id);
    }

    @Override
    public List<ClasseResponse> getClassiDelProfessore(Long professoreId) throws NotFoundException {
    ProfessoreEntity professore = professoreRepository.findById(professoreId)
            .orElseThrow(() -> new NotFoundException("Professore non trovato"));

    // Converte le classi associate in ClasseResponse
    return classeResponseMapper.fromEntityListToReList(professore.getClassi().stream().toList());
}

    
    @Override
public ProfessoreResponse assegnaClasse(Long professoreId, Long classeId) throws NotFoundException {
    // Recupera il professore
    ProfessoreEntity professore = professoreRepository.findById(professoreId)
            .orElseThrow(() -> new NotFoundException("Professore non trovato"));

    // Recupera la classe
    ClasseEntity classe = classeRepository.findById(classeId)
            .orElseThrow(() -> new NotFoundException("Classe non trovata"));

    // Aggiunge la classe al set, se non già presente
    professore.getClassi().add(classe);

    // Salva
    ProfessoreEntity updated = professoreRepository.save(professore);

    return professoreResponseMapper.fromEntityToRe(updated);
}
    }




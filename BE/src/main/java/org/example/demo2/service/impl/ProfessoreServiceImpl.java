package org.example.demo2.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.demo2.dao.entity.ClasseEntity;
import org.example.demo2.dao.entity.ProfessoreEntity;
import org.example.demo2.dao.repository.ClasseRepository;
import org.example.demo2.dao.repository.ProfessoreRepository;
import org.example.demo2.dto.request.ProfessoreRequest;
import org.example.demo2.dto.response.ProfessoreResponse;
import org.example.demo2.service.general.ProfessoreService;
import org.example.demo2.utils.exception.NotFoundException;
import org.example.demo2.utils.mapper.impl.request.ProfessoreRequestMapper;
import org.example.demo2.utils.mapper.impl.response.ProfessoreResponseMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProfessoreServiceImpl implements ProfessoreService {

    private final ProfessoreRepository professoreRepository;
    private final ProfessoreRequestMapper professoreRequestMapper;
    private final ProfessoreResponseMapper professoreResponseMapper;
    private final ClasseRepository classeRepository;

    @Override
    public ProfessoreResponse insert(ProfessoreRequest professoreRequest) {
        if (professoreRequest.getId() != null) {
            throw new IllegalArgumentException("L'id deve essere null per creare un nuovo professore");
        }

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

        // updateEntityFromDto è un metodo del mapper che aggiorna solo i campi
        // necessari.
        // Implementato updateEntityFromDto!
        professoreRequestMapper.updateEntityFromDto(professoreRequest, professoreEntity);
        professoreRepository.save(professoreEntity);

        return professoreResponseMapper.fromEntityToRe(professoreEntity);
    }

    @Override
    public ProfessoreResponse update2(ProfessoreRequest professoreRequest) throws NotFoundException {
        if (!professoreRepository.existsById(professoreRequest.getId())) {
            throw new NotFoundException("Professore non esistente");
        }
        ProfessoreEntity entityUpdated = professoreRepository
                .save(professoreRequestMapper.fromReToEntity(professoreRequest));
        return professoreResponseMapper.fromEntityToRe(entityUpdated);
    }

    @Override
    public List<ProfessoreResponse> getAll(boolean includeClasse) {
        List<ProfessoreEntity> professoriEntity = professoreRepository.findAll();
        return includeClasse
                ? professoreResponseMapper.fromEntityListToReList(professoriEntity)
                : professoreResponseMapper.fromEntityListToReListSimple(professoriEntity);
    }

    @Override
    public ProfessoreResponse getByNameAndLastName(String nome, String cognome) throws NotFoundException {
        Optional<ProfessoreEntity> professoreEntity = professoreRepository.findByNomeAndCognome(nome, cognome);

        if (!professoreEntity.isPresent()) {
            throw new NotFoundException("Professore non esistente");
        }

        return professoreResponseMapper.fromEntityToRe(professoreEntity.get());
    }

    /*
     * Modificato per evitare problemi nel caso volessimo eliminare un professore
     * che e asseganto a una classe
     * Chiedo se e la cosa giusta da fare perche mi sembrerebbe giusto anche non
     * lasciarlo fare per evitare
     * di cancellare professori che essendo già assegnati a una classe non
     * dovrebbero essere rimossi.
     * Oppure lasciarli entrambi e lasciare la scelta per evenienza
     */
    @Override
    public void delete(Long id) throws NotFoundException {
        ProfessoreEntity professore = professoreRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Professore non esistente"));

        // Rimuovi la relazione ManyToMany da tutte le classi
        for (ClasseEntity classe : professore.getClassi()) {
            classe.getProfessori().remove(professore);
        }

        professore.getClassi().clear(); // opzionale

        professoreRepository.delete(professore); 
    }
    // Nel dubbio lascio la vecchia versione standard qua
    /*
     * @Override
     * public void delete(Long id) throws NotFoundException {
     * if (!professoreRepository.existsById(id)) {
     * throw new NotFoundException("Professore non esistente");
     * }
     * professoreRepository.deleteById(id);
     * }
     */

    // Modificato qeusto metodo che mi salvata solo a lato passivo e non
    // proprietario ecco spiegato il [] come risposta
    @Override
    public ProfessoreResponse assegnaClasse(Long professoreId, Long classeId) throws NotFoundException {
        ProfessoreEntity professore = professoreRepository.findById(professoreId)
                .orElseThrow(() -> new NotFoundException("Professore non trovato"));

        ClasseEntity classe = classeRepository.findById(classeId)
                .orElseThrow(() -> new NotFoundException("Classe non trovata"));

        // Aggiorna entrambi i lati
        professore.getClassi().add(classe);
        classe.getProfessori().add(professore); // questo è il lato proprietario

        // Salva SOLO il lato proprietario
        classeRepository.save(classe);

        return professoreResponseMapper.fromEntityToRe(professore);
    }

    // Aggiunto un metodo per rimuovere un professore da una classe
    public ProfessoreResponse rimuoviClasse(Long professoreId, Long classeId) throws NotFoundException {
        ProfessoreEntity professore = professoreRepository.findById(professoreId)
                .orElseThrow(() -> new NotFoundException("Professore non trovato"));
        ClasseEntity classe = classeRepository.findById(classeId)
                .orElseThrow(() -> new NotFoundException("Classe non trovata"));

        // Rimuovi entrambi i lati della relazione
        professore.getClassi().remove(classe);
        classe.getProfessori().remove(professore);

        // Salva SOLO il lato proprietario
        classeRepository.save(classe);

        return professoreResponseMapper.fromEntityToRe(professore);
    }

    @Override
    public List<ProfessoreResponse> getAllByClasse(Long classeId) throws NotFoundException {
        if (!classeRepository.existsById(classeId)) {
            throw new NotFoundException("Classe non trovata con id " + classeId);
        }
        List<ProfessoreEntity> list = professoreRepository.findAllByClassi_Id(classeId);
        return professoreResponseMapper.fromEntityListToReList(list);
    }

}

package org.example.demo2.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.demo2.dao.entity.ClasseEntity;
import org.example.demo2.dao.entity.ProfessoreEntity;
import org.example.demo2.dao.repository.ClasseRepository;
import org.example.demo2.dao.repository.ProfessoreRepository;
import org.example.demo2.dto.request.ProfessoreRequest;
import org.example.demo2.dto.response.ProfessoreResponse;
import org.example.demo2.service.general.ProfessoreService;
import org.example.demo2.utils.enums.Specializzazione;
import org.example.demo2.utils.exception.NotFoundException;
import org.example.demo2.utils.mapper.impl.request.ProfessoreRequestMapper;
import org.example.demo2.utils.mapper.impl.response.ProfessoreResponseMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProfessoreServiceImpl implements ProfessoreService {

    private final ProfessoreRepository professoreRepository;
    private final ProfessoreRequestMapper professoreRequestMapper;
    private final ProfessoreResponseMapper professoreResponseMapper;
    private final ClasseRepository classeRepository;

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
    public ProfessoreResponse update(Long id, ProfessoreRequest professoreRequest) throws NotFoundException {
        ProfessoreEntity professoreEntity = professoreRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Professore non esistente"));

        professoreRequestMapper.updateEntityFromDto(professoreRequest, professoreEntity);
        professoreRepository.save(professoreEntity);

        return professoreResponseMapper.fromEntityToRe(professoreEntity);
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
     * @Override
     * public void delete(Long id) throws NotFoundException {
     * ProfessoreEntity professore = professoreRepository.findById(id)
     * .orElseThrow(() -> new NotFoundException("Professore non esistente"));
     * 
     * // Rimuovi la relazione ManyToMany da tutte le classi
     * for (ClasseEntity classe : professore.getClassi()) {
     * classe.getProfessori().remove(professore);
     * }
     * 
     * professore.getClassi().clear(); // opzionale
     * 
     * professoreRepository.delete(professore);
     * }
     */
    //Posso solo staccarli dalle classi prima di cancellare professore se c'e una relazione
    //Nella Many to Many la prassi e fare cosi mentre per una OneToMany
    // Hibernate cancella i “figli” grazie a orphanRemoval = true oppure CascadeType.REMOVE
    @Override
    public void delete(Long id) throws NotFoundException {
        ProfessoreEntity professore = professoreRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Professore non esistente"));

        // Stacca dalle classi
        for (ClasseEntity classe : professore.getClassi()) {
            classe.getProfessori().remove(professore);
        }
        professore.getClassi().clear();

        professoreRepository.delete(professore);
    }

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

    // Dato un range di date torna i provessori in quel range
    @Override
    public List<ProfessoreResponse> getProfessoriByDataNascitaBetween(LocalDate dataInizio, LocalDate dataFine) {
        List<ProfessoreEntity> professori = professoreRepository.findByDataNascitaBetween(dataInizio, dataFine);
        return professori.stream()
                .map(professoreResponseMapper::fromEntityToRe)
                .collect(Collectors.toList()); // -> raccoglie gli elementi del flusso e li mette dentro una List.
    }

    // Data la specializzazione torna tutti i professori che hanno quella
    // specializzazione
    @Override
    public List<ProfessoreResponse> getProfessoriBySpecializzazione(Specializzazione specializzazione) {
        List<ProfessoreEntity> professori = professoreRepository.findBySpecializzazione(specializzazione);
        return professori.stream()
                .map(professoreResponseMapper::fromEntityToRe)
                .collect(Collectors.toList());
    }

}

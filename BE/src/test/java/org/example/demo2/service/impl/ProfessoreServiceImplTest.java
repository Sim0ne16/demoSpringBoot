package org.example.demo2.service.impl;

import org.example.demo2.dao.entity.ClasseEntity;
import org.example.demo2.dao.entity.ProfessoreEntity;
import org.example.demo2.dao.repository.ClasseRepository;
import org.example.demo2.dao.repository.ProfessoreRepository;
import org.example.demo2.dto.request.ProfessoreRequest;
import org.example.demo2.dto.response.ProfessoreResponse;
import org.example.demo2.utils.enums.Specializzazione;
import org.example.demo2.utils.exception.NotFoundException;
import org.example.demo2.utils.mapper.impl.request.ProfessoreRequestMapper;
import org.example.demo2.utils.mapper.impl.response.ProfessoreResponseMapper;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * ProfessoreServiceImplTest — CRUD + relazioni + filtri.
 */
@ExtendWith(MockitoExtension.class)
class ProfessoreServiceImplTest {

    @Mock
    ProfessoreRepository professoreRepository;
    @Mock
    ClasseRepository classeRepository;
    @Mock
    ProfessoreRequestMapper professoreRequestMapper;
    @Mock
    ProfessoreResponseMapper professoreResponseMapper;

    @InjectMocks
    ProfessoreServiceImpl professoreService;

    @Test
    void testInsertProfessore() {
        ProfessoreRequest req = new ProfessoreRequest();
        ProfessoreEntity entity = new ProfessoreEntity();
        ProfessoreResponse res = new ProfessoreResponse();

        when(professoreRequestMapper.fromReToEntity(req)).thenReturn(entity);
        when(professoreRepository.save(entity)).thenReturn(entity);
        when(professoreResponseMapper.fromEntityToRe(entity)).thenReturn(res);

        assertNotNull(professoreService.insert(req));
    }

    @Test
    void testUpdateProfessore() throws NotFoundException {
        ProfessoreRequest req = new ProfessoreRequest();
        ProfessoreEntity entity = new ProfessoreEntity();
        ProfessoreResponse res = new ProfessoreResponse();

        when(professoreRepository.findById(1L)).thenReturn(Optional.of(entity));
        doNothing().when(professoreRequestMapper).updateEntityFromDto(req, entity);
        when(professoreRepository.save(entity)).thenReturn(entity);
        when(professoreResponseMapper.fromEntityToRe(entity)).thenReturn(res);

        assertNotNull(professoreService.update(1L, req));
    }

    @Test
    void testDeleteProfessore() throws NotFoundException {
        ProfessoreEntity p = new ProfessoreEntity();
        ClasseEntity c = new ClasseEntity();

        //  Usa liste modificabili per relazioni bidirezionali
        p.setClassi(new ArrayList<>(List.of(c)));
        c.setProfessori(new ArrayList<>(List.of(p)));

        when(professoreRepository.findById(1L)).thenReturn(Optional.of(p));
        professoreService.delete(1L);
        verify(professoreRepository).delete(p);
    }

    @Test
    void testAssegnaClasse() throws NotFoundException {
        ProfessoreEntity p = new ProfessoreEntity();
        ClasseEntity c = new ClasseEntity();

        //  Inizializza lista classi del professore
        p.setClassi(new ArrayList<>());
        //  Inizializza lista professori della classe!
        c.setProfessori(new ArrayList<>());

        when(professoreRepository.findById(1L)).thenReturn(Optional.of(p));
        when(classeRepository.findById(2L)).thenReturn(Optional.of(c));
        when(professoreResponseMapper.fromEntityToRe(p)).thenReturn(new ProfessoreResponse());

        ProfessoreResponse r = professoreService.assegnaClasse(1L, 2L);
        assertNotNull(r);
    }

    @Test
    void testRimuoviClasse() throws NotFoundException {
        ProfessoreEntity p = new ProfessoreEntity();
        ClasseEntity c = new ClasseEntity();

        //  Inizializza lista classi del professore con la classe
        p.setClassi(new ArrayList<>(List.of(c)));
        //  Inizializza lista professori della classe con il professore
        c.setProfessori(new ArrayList<>(List.of(p)));

        when(professoreRepository.findById(1L)).thenReturn(Optional.of(p));
        when(classeRepository.findById(2L)).thenReturn(Optional.of(c));
        when(professoreResponseMapper.fromEntityToRe(p)).thenReturn(new ProfessoreResponse());

        ProfessoreResponse r = professoreService.rimuoviClasse(1L, 2L);
        assertNotNull(r);
    }

    @Test
    void testGetProfessoriBySpecializzazione() {
        when(professoreRepository.findBySpecializzazione(Specializzazione.MATEMATICA))
                .thenReturn(List.of(new ProfessoreEntity()));
        when(professoreResponseMapper.fromEntityToRe(any())).thenReturn(new ProfessoreResponse());

        List<ProfessoreResponse> res = professoreService.getProfessoriBySpecializzazione(Specializzazione.MATEMATICA);
        assertFalse(res.isEmpty());
    }

    @Test
    void testGetProfessoriByDataNascitaBetween() {
        when(professoreRepository.findByDataNascitaBetween(any(), any()))
                .thenReturn(List.of(new ProfessoreEntity()));
        when(professoreResponseMapper.fromEntityToRe(any())).thenReturn(new ProfessoreResponse());

        List<ProfessoreResponse> res = professoreService.getProfessoriByDataNascitaBetween(
                LocalDate.now(), LocalDate.now());
        assertFalse(res.isEmpty());
    }
}

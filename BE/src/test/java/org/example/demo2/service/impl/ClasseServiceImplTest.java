package org.example.demo2.service.impl;

import org.example.demo2.dao.entity.ClasseEntity;
import org.example.demo2.dao.entity.ProfessoreEntity;
import org.example.demo2.dao.repository.ClasseRepository;
import org.example.demo2.dao.repository.ProfessoreRepository;
import org.example.demo2.dto.request.ClasseRequest;
import org.example.demo2.dto.response.ClasseResponse;
import org.example.demo2.utils.exception.NotFoundException;
import org.example.demo2.utils.mapper.impl.request.ClasseRequestMapper;
import org.example.demo2.utils.mapper.impl.response.ClasseResponseMapper;
import org.example.demo2.utils.mapper.impl.response.ProfessoreResponseMapper;
import org.example.demo2.utils.mapper.impl.response.StudenteResponseMapper;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

/**
 * ClasseServiceImplTest — copre CRUD + relazioni.
 */
@ExtendWith(MockitoExtension.class)
class ClasseServiceImplTest {

    @Mock
    ClasseRepository classeRepository;
    @Mock
    ProfessoreRepository professoreRepository;
    @Mock
    ClasseRequestMapper classeRequestMapper;
    @Mock
    ClasseResponseMapper classeResponseMapper;
    @Mock
    StudenteResponseMapper studenteResponseMapper;
    @Mock
    ProfessoreResponseMapper professoreResponseMapper;

    @InjectMocks
    ClasseServiceImpl classeService;

    /**
     * Test insert classe
     */
    @Test
    void testInsertClasse() {
        ClasseRequest request = new ClasseRequest();
        request.setIdentificativo("A");

        ClasseEntity entity = new ClasseEntity();
        entity.setId(1L);

        ClasseResponse response = new ClasseResponse();
        response.setId(1L);

        when(classeRequestMapper.fromReToEntity(request)).thenReturn(entity);
        when(classeRepository.save(entity)).thenReturn(entity);
        when(classeResponseMapper.fromEntityToRe(entity, studenteResponseMapper, professoreResponseMapper))
                .thenReturn(response);

        ClasseResponse result = classeService.insert(request);

        assertNotNull(result);
        assertEquals(1L, result.getId());
    }

    /**
     * Test update classe
     */
    @Test
    void testUpdateClasse() throws NotFoundException {
        ClasseRequest request = new ClasseRequest();
        request.setIdentificativo("B");

        ClasseEntity entity = new ClasseEntity();
        entity.setId(1L);

        ClasseResponse response = new ClasseResponse();
        response.setId(1L);

        when(classeRepository.findById(1L)).thenReturn(Optional.of(entity));
        doNothing().when(classeRequestMapper).updateEntityFromDto(request, entity);
        when(classeRepository.save(entity)).thenReturn(entity);
        when(classeResponseMapper.fromEntityToRe(entity, studenteResponseMapper, professoreResponseMapper))
                .thenReturn(response);

        ClasseResponse result = classeService.update(1L, request);

        assertNotNull(result);
    }

    /**
     * Test delete classe OK
     */
    @Test
    void testDeleteClasse() throws NotFoundException {
        when(classeRepository.existsById(1L)).thenReturn(true);
        classeService.delete(1L);
        verify(classeRepository).deleteById(1L);
    }

    /**
     * Test delete classe NOT FOUND
     */
    @Test
    void testDeleteClasseNotFound() {
        when(classeRepository.existsById(1L)).thenReturn(false);
        assertThrows(NotFoundException.class, () -> classeService.delete(1L));
    }

    /**
     * Test getClasseAssegnataProfessore
     */
    @Test
    void testGetClasseAssegnataProfessore() throws NotFoundException {
        ProfessoreEntity professore = new ProfessoreEntity();
        ClasseEntity classe = new ClasseEntity();
        professore.setClassi(List.of(classe));

        when(professoreRepository.findById(1L)).thenReturn(Optional.of(professore));
        when(classeResponseMapper.fromEntityListToReList(any(), any(), any()))
                .thenReturn(List.of(new ClasseResponse()));

        List<ClasseResponse> result = classeService.getClasseAssegnataProfessore(1L);

        assertEquals(1, result.size());
    }
}

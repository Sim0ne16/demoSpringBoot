package org.example.demo2.service.impl;

import org.example.demo2.dao.entity.ClasseEntity;
import org.example.demo2.dao.entity.StudenteEntity;
import org.example.demo2.dao.repository.ClasseRepository;
import org.example.demo2.dao.repository.StudenteRepository;
import org.example.demo2.dto.request.StudenteRequest;
import org.example.demo2.dto.response.StudenteResponse;
import org.example.demo2.utils.exception.NotFoundException;
import org.example.demo2.utils.mapper.impl.request.StudenteRequestMapper;
import org.example.demo2.utils.mapper.impl.response.StudenteResponseMapper;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

/**
 * StudenteServiceImplTest — CRUD + assegnaClasse.
 */
@ExtendWith(MockitoExtension.class)
class StudenteServiceImplTest {

    @Mock
    StudenteRepository studenteRepository;
    @Mock
    ClasseRepository classeRepository;
    @Mock
    StudenteRequestMapper studenteRequestMapper;
    @Mock
    StudenteResponseMapper studenteResponseMapper;

    @InjectMocks
    StudenteServiceImpl studenteService;

    @Test
    void testInsertStudente() throws NotFoundException {
        StudenteRequest req = new StudenteRequest();
        StudenteEntity e = new StudenteEntity();
        StudenteResponse r = new StudenteResponse();

        when(studenteRequestMapper.fromReToEntity(req)).thenReturn(e);
        when(studenteRepository.save(e)).thenReturn(e);
        when(studenteResponseMapper.fromEntityToRe(e)).thenReturn(r);

        assertNotNull(studenteService.insert(req));
    }

    @Test
    void testUpdateStudente() throws NotFoundException {
        StudenteRequest req = new StudenteRequest();
        StudenteEntity e = new StudenteEntity();
        StudenteResponse r = new StudenteResponse();

        when(studenteRepository.findById(1L)).thenReturn(Optional.of(e));
        doNothing().when(studenteRequestMapper).updateEntityFromDto(req, e);
        when(studenteRepository.save(e)).thenReturn(e);
        when(studenteResponseMapper.fromEntityToRe(e)).thenReturn(r);

        assertNotNull(studenteService.update(1L, req));
    }

    @Test
    void testDeleteStudente() throws NotFoundException {
        when(studenteRepository.existsById(1L)).thenReturn(true);
        studenteService.delete(1L);
        verify(studenteRepository).deleteById(1L);
    }

    @Test
    void testAssegnaClasseStudente() throws NotFoundException {
        StudenteEntity s = new StudenteEntity();
        ClasseEntity c = new ClasseEntity();
        StudenteResponse r = new StudenteResponse();

        when(studenteRepository.findById(1L)).thenReturn(Optional.of(s));
        when(classeRepository.findById(2L)).thenReturn(Optional.of(c));
        when(studenteRepository.save(s)).thenReturn(s);
        when(studenteResponseMapper.fromEntityToRe(s)).thenReturn(r);

        assertNotNull(studenteService.assegnaClasse(1L, 2L));
    }
}

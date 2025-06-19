package org.example.demo2.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.demo2.dao.entity.StudenteEntity;
import org.example.demo2.dao.repository.StudenteRepository;
import org.example.demo2.dto.request.StudenteRequest;
import org.example.demo2.dto.response.StudenteResponse;
import org.example.demo2.service.general.StudenteService;
import org.example.demo2.utils.exception.NotFoundException;
import org.example.demo2.utils.mapper.impl.request.StudenteRequestMapper;
import org.example.demo2.utils.mapper.impl.response.StudenteResponseMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StudenteServiceImpl implements StudenteService {

    private final StudenteRepository studenteRepository;
    private final StudenteRequestMapper studenteRequestMapper;
    private final StudenteResponseMapper studenteResponseMapper;

    @Override
    public StudenteResponse insert(StudenteRequest studenteRequest) {
        StudenteEntity studenteEntity = studenteRequestMapper.fromReToEntity(studenteRequest);
        studenteRepository.save(studenteEntity);
        return studenteResponseMapper.fromEntityToRe(studenteEntity);
    }

    // Due esempi di getById
    @Override
    public StudenteResponse getById(Long id) throws NotFoundException {
        Optional<StudenteEntity> studenteEntityOptional = studenteRepository.findById(id);

        if (studenteEntityOptional.isPresent()) {
            return studenteResponseMapper.fromEntityToRe(studenteEntityOptional.get());
        } else {
            throw new NotFoundException("Studente non trovato");
        }
    }

    // Preferibile utilizzare questo per via del .orElseThrow
    @Override
    public StudenteResponse getById2(Long id) throws NotFoundException {
        StudenteEntity studenteEntity = studenteRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Studente non trovato"));
        return studenteResponseMapper.fromEntityToRe(studenteEntity);
    }

    @Override
    public StudenteResponse update1(StudenteRequest studenteRequest) throws NotFoundException {
        StudenteEntity studenteEntity = studenteRepository.findById(studenteRequest.getId())
                .orElseThrow(() -> new NotFoundException("Studente non esistente"));

        // updateEntityFromDto è un metodo del mapper che aggiorna solo i campi necessari.
        studenteRequestMapper.updateEntityFromDto(studenteRequest, studenteEntity);
        studenteRepository.save(studenteEntity);

        return studenteResponseMapper.fromEntityToRe(studenteEntity );
    }

    @Override
    public StudenteResponse update2(StudenteRequest studenteRequest) throws NotFoundException {
        if (!studenteRepository.existsById(studenteRequest.getId())) {
            throw new NotFoundException("Studente non esistente");
        }
        StudenteEntity entityUpdated = studenteRepository.save(studenteRequestMapper.fromReToEntity(studenteRequest));
        return studenteResponseMapper.fromEntityToRe(entityUpdated);
    }

    @Override
    public void delete(Long id) throws NotFoundException {
        if (!studenteRepository.existsById(id)) {
            throw new NotFoundException("Studente non esistente");
        }
        studenteRepository.deleteById(id);
    }

    @Override
    public List<StudenteResponse> getAll() {
        List<StudenteEntity> studenti = studenteRepository.findAll();
        return studenteResponseMapper.fromEntityListToReList(studenti);
    }

    @Override
    public StudenteResponse getByNameAndLastName(String nome, String cognome) throws NotFoundException {

        Optional<StudenteEntity> studenteEntity = studenteRepository.findByNomeAndCognome(nome, cognome);

        if (!studenteEntity.isPresent()) {
            throw new NotFoundException("Studente non esistente");
        }

        return studenteResponseMapper.fromEntityToRe(studenteEntity.get());
    }
}

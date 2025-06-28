package org.example.demo2.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.demo2.dao.entity.ClasseEntity;
import org.example.demo2.dao.entity.StudenteEntity;
import org.example.demo2.dao.repository.ClasseRepository;
import org.example.demo2.dao.repository.StudenteRepository;
import org.example.demo2.dto.request.StudenteRequest;
import org.example.demo2.dto.response.StudenteResponse;
import org.example.demo2.service.general.StudenteService;
import org.example.demo2.utils.exception.NotFoundException;
import org.example.demo2.utils.mapper.impl.request.StudenteRequestMapper;
import org.example.demo2.utils.mapper.impl.response.StudenteResponseMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StudenteServiceImpl implements StudenteService {

    private final StudenteRepository studenteRepository;
    private final StudenteRequestMapper studenteRequestMapper;
    private final StudenteResponseMapper studenteResponseMapper;
    private final ClasseRepository classeRepository;

    @Override
    public StudenteResponse insert(StudenteRequest studenteRequest) throws NotFoundException {
        StudenteEntity studenteEntity = studenteRequestMapper.fromReToEntity(studenteRequest);

        // Se classeId è presente, recupera e assegna la classe
        if (studenteRequest.getClasseId() != null) {
            ClasseEntity classe = classeRepository.findById(studenteRequest.getClasseId())
                    .orElseThrow(
                            () -> new NotFoundException("Classe non trovata con id: " + studenteRequest.getClasseId()));
            studenteEntity.setClasse(classe);
        }
        // Salva lo studente
        StudenteEntity savedEntity = studenteRepository.save(studenteEntity);
        return studenteResponseMapper.fromEntityToRe(savedEntity);
    }

    // Preferibile utilizzare questo per via del .orElseThrow
    @Override
    public StudenteResponse getById(Long id, boolean includeClasse) throws NotFoundException {
        StudenteEntity studenteEntity = studenteRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Studente non trovato con id " + id));
        return includeClasse
                ? studenteResponseMapper.fromEntityToRe(studenteEntity)
                : studenteResponseMapper.fromEntityToReSimple(studenteEntity);
    }

    @Override
    public StudenteResponse update(Long id, StudenteRequest studenteRequest) throws NotFoundException {
        StudenteEntity studenteEntity = studenteRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Studente non trovato con id " + id));

        studenteRequestMapper.updateEntityFromDto(studenteRequest, studenteEntity);
        studenteRepository.save(studenteEntity);

        return studenteResponseMapper.fromEntityToRe(studenteEntity);
    }

    @Override
    public void delete(Long id) throws NotFoundException {
        if (!studenteRepository.existsById(id)) {
            throw new NotFoundException("Studente non trovato con id " + id);
        }
        studenteRepository.deleteById(id);
    }

    @Override
    public List<StudenteResponse> getAll(boolean includeClasse) {
        List<StudenteEntity> studentiEntity = studenteRepository.findAll();

        return includeClasse
                ? studenteResponseMapper.fromEntityListToReList(studentiEntity)
                : studenteResponseMapper.fromEntityListToReListSimple(studentiEntity);
    }

    @Override
    public List<StudenteResponse> getByNameAndLastName(String nome, String cognome) throws NotFoundException {
        List<StudenteEntity> studenti = studenteRepository.findByNomeAndCognome(nome, cognome);

        if (studenti.isEmpty()) {
            throw new NotFoundException("Nessuno studente trovato con nome " + nome + " e cognome " + cognome);
        }

        return studenti.stream()
                .map(studenteResponseMapper::fromEntityToRe)
                .collect(Collectors.toList());
    }

    @Override
    public StudenteResponse assegnaClasse(Long studenteId, Long classeId) throws NotFoundException {
        // Recupera lo studente
        StudenteEntity studente = studenteRepository.findById(studenteId)
                .orElseThrow(() -> new NotFoundException("Studente non trovato"));

        // Recupera la classe
        ClasseEntity classe = classeRepository.findById(classeId)
                .orElseThrow(() -> new NotFoundException("Classe non trovata"));

        // Assegna la classe allo studente
        studente.setClasse(classe);

        // Salva
        StudenteEntity updated = studenteRepository.save(studente);

        return studenteResponseMapper.fromEntityToRe(updated);
    }

    @Override
    public List<StudenteResponse> getAllByClass(Long classeId) throws NotFoundException {
        if (!classeRepository.existsById(classeId)) {
            throw new NotFoundException("Classe non trovata con id " + classeId);
        }
        List<StudenteEntity> list = studenteRepository.findAllByClasseId(classeId);
        return studenteResponseMapper.fromEntityListToReListSimple(list);
    }
}
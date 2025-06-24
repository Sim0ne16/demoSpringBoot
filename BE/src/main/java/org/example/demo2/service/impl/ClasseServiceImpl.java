package org.example.demo2.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.demo2.dao.entity.ClasseEntity;
import org.example.demo2.dao.repository.ClasseRepository;
import org.example.demo2.dto.request.ClasseRequest;
import org.example.demo2.dto.response.ClasseResponse;
import org.example.demo2.service.general.ClasseService;
import org.example.demo2.utils.exception.NotFoundException;
import org.example.demo2.utils.mapper.impl.request.ClasseRequestMapper;
import org.example.demo2.utils.mapper.impl.response.ClasseResponseMapper;
import org.example.demo2.utils.mapper.impl.response.ProfessoreResponseMapper;
import org.example.demo2.utils.mapper.impl.response.StudenteResponseMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClasseServiceImpl implements ClasseService {

    private final ClasseRepository classeRepository;
    private final ClasseRequestMapper classeRequestMapper;
    private final ClasseResponseMapper classeResponseMapper;
    private final StudenteResponseMapper studenteResponseMapper;
    private final ProfessoreResponseMapper professoreResponseMapper;

    @Override
    public ClasseResponse insert(ClasseRequest classeRequest) {
        ClasseEntity classeEntity = classeRequestMapper.fromReToEntity(classeRequest);
        classeRepository.save(classeEntity);
        return classeResponseMapper.fromEntityToRe(classeEntity, studenteResponseMapper, professoreResponseMapper);
    }

    @Override
    public ClasseResponse update1(ClasseRequest classeRequest) throws NotFoundException {
        ClasseEntity classeEntity = classeRepository.findById(classeRequest.getId())
                .orElseThrow(() -> new NotFoundException("Classe non esistente"));

        //classeRequestMapper.updateEntityFromDto(classeRequest, classeEntity);
        classeRepository.save(classeEntity);

        return classeResponseMapper.fromEntityToRe(classeEntity, studenteResponseMapper, professoreResponseMapper);
    }

    @Override
    public List<ClasseResponse> getAll(boolean includeStudenti, boolean includeProfessori) {
        List<ClasseEntity> classi = classeRepository.findAll();
        List<ClasseResponse> response = classeResponseMapper.fromEntityListToReList(classi, studenteResponseMapper, professoreResponseMapper);

        if(!includeStudenti)
            response.forEach(c -> c.setStudenti(null));

        if(!includeProfessori)
            response.forEach(c -> c.setProfessori(null));

        return response;
    }

    @Override
    public void delete(Long id) throws NotFoundException {
        if (!classeRepository.existsById(id)) {
            throw new NotFoundException("Classe non esistente");
        }
        classeRepository.deleteById(id);
    }

    @Override
    public ClasseEntity insert(ClasseEntity dto) {
        return classeRepository.save(dto);
    }

    @Override
    public ClasseEntity update(ClasseEntity dto) throws NotFoundException {
        if (!classeRepository.existsById(dto.getId())) {
            throw new NotFoundException("Classe non esistente");
        }
        return classeRepository.save(dto);
    }

    @Override
    public ClasseResponse getStudentList(Long classeId) throws NotFoundException {
        ClasseEntity classe = classeRepository.findByIdWithStudenti(classeId)
                .orElseThrow(() -> new NotFoundException("Classe non trovata"));

        ClasseResponse response = classeResponseMapper.fromEntityToRe(classe, studenteResponseMapper, professoreResponseMapper);
        response.setStudenti(classe.getStudenti().stream()
                .map(studenteResponseMapper::fromEntityToRe)
                .toList());

        return response;
    }

    @Override
    public ClasseResponse getProfessori(Long classeId) throws NotFoundException {
        ClasseEntity classe = classeRepository.findByIdWithProfessori(classeId)
                .orElseThrow(() -> new NotFoundException("Classe non trovata"));

        ClasseResponse response = classeResponseMapper.fromEntityToRe(classe, studenteResponseMapper, professoreResponseMapper);
        if (classe.getProfessori() != null) {
            response.setProfessori(
                    classe.getProfessori().stream()
                            .map(professoreResponseMapper::fromEntityToRe)
                            .toList());
        } else {
            response.setProfessori(List.of());
        }
        return response;
    }

    @Override
    public ClasseResponse getById(Long id) throws NotFoundException {
        ClasseEntity classe = classeRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Classe non trovata"));
        return classeResponseMapper.fromEntityToRe(classe, studenteResponseMapper, professoreResponseMapper);
    }

}

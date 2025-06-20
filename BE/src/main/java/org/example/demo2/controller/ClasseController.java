package org.example.demo2.controller;

import lombok.RequiredArgsConstructor;
import org.example.demo2.dto.request.ClasseRequest;
import org.example.demo2.dto.response.ClasseResponse;
import org.example.demo2.service.general.ClasseService;
import org.example.demo2.utils.exception.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/classe")
@RequiredArgsConstructor
public class ClasseController {

    private final ClasseService classeService;

    // Crea nuova classe
    @PostMapping
    public ResponseEntity<ClasseResponse> inserisciClasse(@RequestBody ClasseRequest request) {
        ClasseResponse response = classeService.insert(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    // Ottieni tutte le classi
    @GetMapping
    public ResponseEntity<List<ClasseResponse>> getAllClassi() {
        return ResponseEntity.ok(classeService.getAll());
    }

    // Ottieni classe per ID
    @GetMapping("/{id}")
    public ResponseEntity<ClasseResponse> getClasseById(@PathVariable Long id) throws NotFoundException {
        return ResponseEntity.ok(classeService.getById(id));
    }

    // Aggiorna una classe
    @PutMapping
    public ResponseEntity<ClasseResponse> aggiornaClasse(@RequestBody ClasseRequest request) throws NotFoundException {
        return ResponseEntity.ok(classeService.update1(request));
    }

    // Elimina una classe
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClasse(@PathVariable Long id) throws NotFoundException {
        classeService.delete(id);
        return ResponseEntity.noContent().build();
    }

    // Ottieni la classe con lista studenti
    @GetMapping("/{id}/studenti")
    public ResponseEntity<ClasseResponse> getStudentiClasse(@PathVariable Long id) throws NotFoundException {
        return ResponseEntity.ok(classeService.getStudentList(id));
    }

    // Ottieni la classe con lista professori
    @GetMapping("/{id}/professori")
    public ResponseEntity<ClasseResponse> getProfessoriClasse(@PathVariable Long id) throws NotFoundException {
        return ResponseEntity.ok(classeService.getProfessori(id));
    }
}
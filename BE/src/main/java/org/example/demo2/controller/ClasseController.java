package org.example.demo2.controller;

import lombok.RequiredArgsConstructor;

import org.example.demo2.dto.request.ClasseRequest;
import org.example.demo2.dto.response.ClasseResponse;
import org.example.demo2.service.general.ClasseService;
import org.example.demo2.utils.exception.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/classe")
@RequiredArgsConstructor
public class ClasseController {

    private final ClasseService classeService;

    // Restituisce tutte le classi
    @GetMapping
    public ResponseEntity<List<ClasseResponse>> getAllClassi(
            @RequestParam(defaultValue = "false") boolean includeStudenti,
            @RequestParam(defaultValue = "false") boolean includeProfessori) {
        return ResponseEntity.ok(classeService.getAll(includeStudenti, includeProfessori));
    }

    // Restituisce classe per ID
    @GetMapping("/{id}")
    public ResponseEntity<ClasseResponse> getClasseById(@PathVariable Long id) throws NotFoundException {
        return ResponseEntity.ok(classeService.getById(id));
    }

    //Ottenere professori assegnati a una classe
    @GetMapping("/assegnate-professore/{professoreId}")
    public ResponseEntity<List<ClasseResponse>> getClasseAssegnataProfessore(@PathVariable Long professoreId)
            throws NotFoundException {
        List<ClasseResponse> classi = classeService.getClasseAssegnataProfessore(professoreId);
        return ResponseEntity.ok(classi);
    }

    // Crea nuova classe
    @PostMapping
    public ResponseEntity<ClasseResponse> inserisciClasse(@RequestBody ClasseRequest request) {
        ClasseResponse response = classeService.insert(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    // Aggiorna una classe  MODIFICATO
    @PutMapping("/{id}")
    public ResponseEntity<ClasseResponse> aggiornaClasse(
            @PathVariable Long id,
            @Valid @RequestBody ClasseRequest request) throws NotFoundException {
        ClasseResponse response = classeService.update(id, request);
        return ResponseEntity.ok(response);
    }

    // Elimina una classe
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClasse(@PathVariable Long id) throws NotFoundException {
        classeService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
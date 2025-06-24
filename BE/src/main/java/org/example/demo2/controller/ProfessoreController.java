package org.example.demo2.controller;

import java.util.List;

import org.example.demo2.dto.request.ProfessoreRequest;
import org.example.demo2.dto.response.ClasseResponse;
import org.example.demo2.dto.response.ProfessoreResponse;
import org.example.demo2.service.general.ProfessoreService;
import org.example.demo2.utils.exception.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/professore")
public class ProfessoreController {

    private final ProfessoreService professoreService;

    // Con il verbo Get indichiamo la ricezione di dati dal Database
    @GetMapping
    private ResponseEntity<List<ProfessoreResponse>> getAllProfessori() {
        List<ProfessoreResponse> professoreResponse = professoreService.getAll();
        return new ResponseEntity<>(professoreResponse, HttpStatus.OK);
    }
    //Prende un proffessore basandosi sull'ID
    @GetMapping(path = "/{id}")
    private ResponseEntity<ProfessoreResponse> getProfessoreById(@PathVariable Long id) {
        try {
            ProfessoreResponse professore = professoreService.getById(id);
            return new ResponseEntity<>(professore, HttpStatus.OK);
        } catch (NotFoundException e) {
            // e' buona pratica loggare il perche' dell'errore etc
            // logger.info("Chiamata esplosa perche'...)
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    //Crea Un Professore
    @PostMapping
    public ResponseEntity<ProfessoreResponse> creaProfessore(@RequestBody ProfessoreRequest request) {
        ProfessoreResponse response = professoreService.insert(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    // Assegna una classe a un professore
     @PutMapping("/{professoreId}/classi/{classeId}")
    public ResponseEntity<ProfessoreResponse> assegnaClasseAProfessore(@PathVariable Long professoreId,
                                                                       @PathVariable Long classeId) throws NotFoundException {
        return ResponseEntity.ok(professoreService.assegnaClasse(professoreId, classeId));
    }

    // Vedi classi assegnate al rpofessore
    @GetMapping("/{id}/classi")
    public ResponseEntity<List<ClasseResponse>> getClassiDelProfessore(@PathVariable Long id) throws NotFoundException {
        List<ClasseResponse> classi = professoreService.getClassiDelProfessore(id);
        return ResponseEntity.ok(classi);

    }

    /**
     * GET /professori/classe/{classeId}
     * → restituisce la lista di ProfessoreResponse per la classe richiesta
     * 
     * Metodo che era nel posto sbagliato
     */
    
    @GetMapping("/classe/{classeId}")
    public ResponseEntity<List<ProfessoreResponse>> getByClass(
            @PathVariable Long classeId) throws NotFoundException {
        List<ProfessoreResponse> list = professoreService.getAllByClasse(classeId);
        return ResponseEntity.ok(list);
    }
}

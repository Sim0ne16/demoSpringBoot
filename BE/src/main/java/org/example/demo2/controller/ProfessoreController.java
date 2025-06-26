package org.example.demo2.controller;

import lombok.RequiredArgsConstructor;
import org.example.demo2.dto.request.ProfessoreRequest;
import org.example.demo2.dto.response.ProfessoreResponse;
import org.example.demo2.service.general.ProfessoreService;
import org.example.demo2.utils.exception.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/professore")
public class ProfessoreController {
    private final ProfessoreService professoreService;

    // Restituisci tutti i professori
    @GetMapping
    private ResponseEntity<List<ProfessoreResponse>> getAllProfessori(
            @RequestParam(defaultValue = "false") boolean includeClassi) {
        List<ProfessoreResponse> list = professoreService.getAll(includeClassi);
        return ResponseEntity.ok(list);
    }

    // Restituisce i professori in base all'ID
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

    // In base all'ID della classe restituisce i professori che le appartengono
    @GetMapping("/classe/{classeId}")
    public ResponseEntity<List<ProfessoreResponse>> getByClass(
            @PathVariable Long classeId) throws NotFoundException {
        List<ProfessoreResponse> list = professoreService.getAllByClasse(classeId);
        return ResponseEntity.ok(list);
    }

    // Crea Un Professore
    @PostMapping
    public ResponseEntity<ProfessoreResponse> creaProfessore(@RequestBody ProfessoreRequest request) {
        ProfessoreResponse response = professoreService.insert(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    // Assegna una classe a un professore
    @PutMapping("/professori/{professoreId}/classi/aggiungi/{classeId}")
    public ResponseEntity<ProfessoreResponse> assegnaClasseAProfessore(@PathVariable Long professoreId,
            @PathVariable Long classeId) throws NotFoundException {
        return ResponseEntity.ok(professoreService.assegnaClasse(professoreId, classeId));
    }

    // Modifica un professore già esistente
    @PutMapping
    public ResponseEntity<ProfessoreResponse> aggiornaProfessore(@RequestBody ProfessoreRequest request)
            throws NotFoundException {
        ProfessoreResponse response = professoreService.update1(request);
        return ResponseEntity.ok(response);
    }

    //  No
    @DeleteMapping("/professori/{professoreId}/classi/rimuovi/{classeId}")
    public ResponseEntity<ProfessoreResponse> rimuoviClasseDaProfessore(
            @PathVariable Long professoreId,
            @PathVariable Long classeId) throws NotFoundException {
        return ResponseEntity.ok(professoreService.rimuoviClasse(professoreId, classeId));
    }
    /*
    //Delete semplice -> Guarda in ProfessoreServiceImpl per info a riguardo.  
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProfessore(@PathVariable Long id) throws NotFoundException {
        professoreService.delete(id);
        return ResponseEntity.noContent().build(); // 204 No Content
    }
    */
    
}

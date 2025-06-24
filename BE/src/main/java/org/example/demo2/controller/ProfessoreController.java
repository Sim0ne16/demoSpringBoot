package org.example.demo2.controller;

import lombok.RequiredArgsConstructor;
import org.example.demo2.dto.request.ProfessoreRequest;
import org.example.demo2.dto.response.ClasseResponse;
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

    @GetMapping
    private ResponseEntity<List<ProfessoreResponse>> getAllProfessori(@RequestParam(defaultValue = "false") boolean includeClassi) {
        List<ProfessoreResponse> list = professoreService.getAll(includeClassi);
        return ResponseEntity.ok(list);
    }

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

    // Vedi classi assegnate al rpofessore, VEDI I COMMENTI NEL SERVICE
    /*
    @GetMapping("/{id}/classi")
    public ResponseEntity<List<ClasseResponse>> getClassiDelProfessore(@PathVariable Long id) throws NotFoundException {
        List<ClasseResponse> classi = professoreService.getClassiDelProfessore(id);
        return ResponseEntity.ok(classi);

    }
     */

    /**
     * GET /professori/classe/{classeId}
     * → restituisce la lista di ProfessoreResponse per la classe richiesta
     * <p>
     * Metodo che era nel posto sbagliato
     */

    @GetMapping("/classe/{classeId}")
    public ResponseEntity<List<ProfessoreResponse>> getByClass(
            @PathVariable Long classeId) throws NotFoundException {
        List<ProfessoreResponse> list = professoreService.getAllByClasse(classeId);
        return ResponseEntity.ok(list);
    }

}

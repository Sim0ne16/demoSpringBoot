package org.example.demo2.controller;

import lombok.RequiredArgsConstructor;
import org.example.demo2.dto.request.StudenteRequest;
import org.example.demo2.dto.response.StudenteResponse;
import org.example.demo2.service.general.StudenteService;
import org.example.demo2.utils.exception.NotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import jakarta.validation.Valid;

import java.net.URI;
import java.util.List;

//Omologo tutto in italiano per adesso!
@RestController // Combinazione di @Controller e @ResponseBody
@RequiredArgsConstructor
@RequestMapping("/students") // A quale indirizzo e' mappata la nostra API
public class StudenteController {

    /*
     * Quando usare PathVariable e quando RequestParam :
     *
     * ID risorsa (es. id, uuid) @PathVariable
     * Filtri, query, pagine, sort @RequestParam
     * Risorse annidate @PathVariable
     * Parametri opzionali @RequestParam
     */

    private final StudenteService studenteService;

    // Con il verbo Get indichiamo la ricezione di dati dal Database
    @GetMapping
    private ResponseEntity<List<StudenteResponse>> getAllStudenti(
            @RequestParam(defaultValue = "false") boolean includeClasse) {
        List<StudenteResponse> list = studenteService.getAll(includeClasse);
        return ResponseEntity.ok(list);
    }

    /*
     * Andando a specificare un path con le {} indichiamo la presenza di un
     * pathvariable, che possiamo passare poi come
     * parametro nel metodo --> in questo caso {id}
     */
    @GetMapping(path = "/{id}")
    private ResponseEntity<StudenteResponse> getStudenteById(
            @PathVariable Long id,
            @RequestParam(required = false, defaultValue = "false") boolean includeClass) throws NotFoundException {
        StudenteResponse studenteResponse = studenteService.getById(id, includeClass);
        return ResponseEntity.ok(studenteResponse);
    }

    // esempio di sintassi di chiamata -> http://localhost:8080/studente/Mario/Rossi
    /*
     * @GetMapping(path = "/{nome}/{cognome}")
     * private ResponseEntity<StudenteResponse>
     * getStudenteByNomeCognome(@PathVariable("nome") String name,
     * 
     * @PathVariable("cognome") String lastName) {
     * try {
     * StudenteResponse studenteResponse =
     * studenteService.getByNameAndLastName(name, lastName);
     * return ResponseEntity.ok(studenteResponse);
     * } catch (NotFoundException e) {
     * e.printStackTrace();
     * return ResponseEntity.notFound().build();
     * }
     * }
     */
    
    /*
     * Stesso metodo precedente ma con utilizzo di QueryParam invece che
     * PathVariable, il query Param a differenza del
     * path variable prevede coding ed encoding, quindi può prevedere degli spazi a
     * differenza del path variable
     * esempio di sintassi di chiamata ->
     * http://localhost:8080/studente/fullname?name=Mario&lastName=Rossi
     */
    
     // Restituisce studenti per nome e cognome
    @GetMapping("/nome-completo")
    public ResponseEntity<List<StudenteResponse>> getNomeCompleto(
            @RequestParam("nome") String name,
            @RequestParam("cognome") String lastName) throws NotFoundException {
        List<StudenteResponse> studenti = studenteService.getByNameAndLastName(name, lastName);
        return ResponseEntity.ok(studenti);
    }

    // In base all'ID della classe restituisce gli studenti che le appartengono
    @GetMapping("/classe/{classeId}")
    public ResponseEntity<List<StudenteResponse>> getStudentiByClasse(
            @PathVariable Long classeId) throws NotFoundException {
        List<StudenteResponse> list = studenteService.getAllByClass(classeId);
        return ResponseEntity.ok(list);
    }

    /*
     * Il vero Post indica un inserimento di dati nel database, dobbiamo fornire un
     * Body nella richiesta e l'annotations
     *
     * @RequestBody server per convertire il Json che arriva in una classe
     * comprensibile a Java, i campi nel Json devono
     * corrispondere ai campi che abbiamo definito nella classe java (in questo caso
     * la classe AthleteRest)
     */

    // Modificato per L'ID
    @PostMapping
    private ResponseEntity<StudenteResponse> creaStudente(@Valid @RequestBody StudenteRequest studenteRequest)
            throws NotFoundException {

        StudenteResponse studenteResponse = studenteService.insert(studenteRequest);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(studenteResponse.getId())
                .toUri();

        return ResponseEntity.created(location).body(studenteResponse);
    }
    /*
     * Perché tornare l'URI?
     * Standard HTTP:
     * La specifica HTTP dice che, dopo aver creato una risorsa (ad esempio con un
     * POST), il server dovrebbe restituire lo status 201 Created
     * e includere nel campo header Location l'URI della nuova risorsa.
     *
     * Comunicazione chiara al client:
     * Il client sa esattamente dove può trovare o accedere a quella risorsa creata.
     * Ad esempio, se crei un nuovo utente, l'URI potrebbe essere qualcosa come
     * /users/123.
     * Tornare quell’URI aiuta il client a fare subito operazioni future su
     * quell’utente (GET, PUT, DELETE).
     *
     * Best practice RESTful:
     * In un’API RESTful ben progettata, risorse e URI sono fondamentali per la
     * navigazione e manipolazione.
     * Restituire l’URI della risorsa è una buona prassi per favorire
     * l’auto-documentazione dell’API e la facilità d’uso.
     */

    /*
     * Il verbo Put indica un update dell'entità nel database, a differenza della
     * Post dobbiamo gestire l'eccezzione
     * che abbiamo specificato nel service
     */
    @PutMapping("/{id}")
    public ResponseEntity<StudenteResponse> aggiornaStudente(
            @PathVariable Long id,
            @Valid @RequestBody StudenteRequest request) throws NotFoundException {
        StudenteResponse response = studenteService.update(id, request);
        return ResponseEntity.ok(response);
    }

    // Assegna una classe a uno studente
    @PutMapping("/{studenteId}/classe/{classeId}")
    public ResponseEntity<StudenteResponse> assegnaClasseStudente(@PathVariable Long studenteId,
            @PathVariable Long classeId) throws NotFoundException {
        StudenteResponse updated = studenteService.assegnaClasse(studenteId, classeId);
        return ResponseEntity.ok(updated);
    }

    // Elimina uno studente
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminaStudente(@PathVariable Long id) throws NotFoundException {
        studenteService.delete(id);
        return ResponseEntity.noContent().build();
    }
}



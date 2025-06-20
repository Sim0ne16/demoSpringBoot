package org.example.demo2.controller;

import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.example.demo2.dto.request.StudenteRequest;
import org.example.demo2.dto.response.StudenteResponse;
import org.example.demo2.service.general.StudenteService;
import org.example.demo2.utils.exception.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController // Combinazione di @Controller e @ResponseBody
@RequiredArgsConstructor
@RequestMapping("/studente") // A quale indirizzo e' mappata la nostra API
public class StudenteController {

    /*
    Quando usare PathVariable e quando RequestParam :

    ID risorsa (es. id, uuid)	    @PathVariable
    Filtri, query, pagine, sort	    @RequestParam
    Risorse annidate	            @PathVariable
    Parametri opzionali	            @RequestParam
    */

    private final StudenteService studenteService;

    // Con il verbo Get indichiamo la ricezione di dati dal Database
    @GetMapping
    private ResponseEntity<List<StudenteResponse>> getAllStudenti(@RequestParam(required = false, defaultValue = "false") boolean includeClasse) {
        List<StudenteResponse> studentiResponse = studenteService.getAll(includeClasse);
        return ResponseEntity.ok(studentiResponse);
    }

    /*
     * Andando a specificare un path con le {} indichiamo la presenza di un
     * pathvariable, che possiamo passare poi come
     * parametro nel metodo --> in questo caso {id}
     */
    @GetMapping(path = "/{id}")
    private ResponseEntity<StudenteResponse> getStudentById(
            @PathVariable Long id,
            @RequestParam(required = false, defaultValue = "false") boolean includeClasse
    ) throws NotFoundException {
        StudenteResponse studenteResponse = studenteService.getById(id, includeClasse);
        return ResponseEntity.ok(studenteResponse);
    }

    // esempio di sintassi di chiamata -> http://localhost:8080/studente/Mario/Rossi
    @GetMapping(path = "/{name}/{lastName}")
    private ResponseEntity<StudenteResponse> getByNameAndLastName(@PathVariable("name") String name,
                                                                  @PathVariable("lastName") String lastName) {
        try {
            StudenteResponse studenteResponse = studenteService.getByNameAndLastName(name, lastName);
            return ResponseEntity.ok(studenteResponse);
        } catch (NotFoundException e) {
            e.printStackTrace();
            return ResponseEntity.notFound().build();
        }
    }

    /*
     * Stesso metodo precedente ma con utilizzo di QueryParam invece che
     * PathVariable, il query Param a differenza del
     * path variable prevede coding ed encoding, quindi può prevedere degli spazi a
     * differenza del path variable
     * esempio di sintassi di chiamata ->
     * http://localhost:8080/studente/fullname?name=Mario&lastName=Rossi
     */
    @GetMapping(path = "/fullname")
    private ResponseEntity<StudenteResponse> getByFullName(
            @RequestParam("name") String name,
            @RequestParam("lastName") String lastName) {
        try {
            StudenteResponse studenteResponse = studenteService.getByNameAndLastName(name, lastName);
            return new ResponseEntity<>(studenteResponse, HttpStatus.OK);
        } catch (NotFoundException e) {
            e.printStackTrace();
            return ResponseEntity.notFound().build();
        }
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
    @PostMapping
    private ResponseEntity<StudenteResponse> postStudente(@RequestBody StudenteRequest studenteRequest) {
        //StudenteValidator.validate(studenteRequest)
        StudenteResponse studenteResponse = studenteService.insert(studenteRequest);

        /*
        Perché tornare l'URI?
        Standard HTTP:
        La specifica HTTP dice che, dopo aver creato una risorsa (ad esempio con un POST), il server dovrebbe restituire lo status 201 Created
        e includere nel campo header Location l'URI della nuova risorsa.

        Comunicazione chiara al client:
        Il client sa esattamente dove può trovare o accedere a quella risorsa creata.
        Ad esempio, se crei un nuovo utente, l'URI potrebbe essere qualcosa come /users/123.
        Tornare quell’URI aiuta il client a fare subito operazioni future su quell’utente (GET, PUT, DELETE).

        Best practice RESTful:
        In un’API RESTful ben progettata, risorse e URI sono fondamentali per la navigazione e manipolazione.
        Restituire l’URI della risorsa è una buona prassi per favorire l’auto-documentazione dell’API e la facilità d’uso.
        */

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(studenteResponse.getId())
                .toUri();


        return ResponseEntity.created(location).build();
    }



    /*
     * Il verbo Put indica un update dell'entità nel database, a differenza della
     * Post dobbiamo gestire l'eccezzione
     * che abbiamo specificato nel service
     */
    @PutMapping
    private ResponseEntity<StudenteResponse> putStudente(@RequestBody StudenteRequest studenteRequest) {
        try {
            //StudenteValidator.validate(studenteRequest)
            StudenteResponse studenteResponse = studenteService.update1(studenteRequest);
            return new ResponseEntity<>(studenteResponse, HttpStatus.OK);
        } catch (NotFoundException e) {
            e.printStackTrace();
            return ResponseEntity.notFound().build();
        }
    }

    // Con il verbo Delete indichiamo l'eliminazione di un'entità nel database
    @DeleteMapping(path = "/{id}")
    private ResponseEntity<Void> deleteStudente(@PathVariable Long id) {
        try {
            studenteService.delete(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (NotFoundException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    //Assegna una classe a uno studente
    @PutMapping("/{studenteId}/classe/{classeId}")
    public ResponseEntity<StudenteResponse> assegnaClasseAStudente(@PathVariable Long studenteId,
                                                                   @PathVariable Long classeId) throws NotFoundException {
        StudenteResponse updated = studenteService.assegnaClasse(studenteId, classeId);
        return ResponseEntity.ok(updated);
    }

}

package org.example.demo2.controller;

import lombok.RequiredArgsConstructor;
import org.example.demo2.dto.request.StudenteRequest;
import org.example.demo2.dto.response.StudenteResponse;
import org.example.demo2.service.general.StudenteService;
import org.example.demo2.utils.exception.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController // Combinazione di @Controller e @ResponseBody
@RequiredArgsConstructor
@RequestMapping("/studente") // A quale indirizzo e' mappata la nostra API
public class StudenteController {

    private final StudenteService studenteService;

    // Con il verbo Get indichiamo la ricezione di dati dal Database
    @GetMapping
    private ResponseEntity<List<StudenteResponse>> getAllStudenti() {
        List<StudenteResponse> studentiResponse = studenteService.getAll();
        return new ResponseEntity<>(studentiResponse, HttpStatus.OK);
    }

    /*
     * Andando a specificare un path con le {} indichiamo la presenza di un
     * pathvariable, che possiamo passare poi come
     * parametro nel metodo --> in questo caso {id}
     */
    @GetMapping(path = "/{id}")
    private ResponseEntity<StudenteResponse> getStudentById(@PathVariable Long id) {
        try {
            StudenteResponse athlete = studenteService.getById(id);
            return new ResponseEntity<>(athlete, HttpStatus.OK);
        } catch (NotFoundException e) {
            //e' buona pratica loggare il perche' dell'errore etc
            // logger.info("Chiamata esplosa perche'...)
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // esempio di sintassi di chiamata -> http://localhost:8080/studente/Mario/Rossi
    @GetMapping(path = "/{name}/{lastName}")
    private ResponseEntity<StudenteResponse> getByNameAndLastName(@PathVariable("name") String name,
            @PathVariable("lastName") String lastName) {
        try {
            StudenteResponse studenteResponse = studenteService.getByNameAndLastName(name, lastName);
            return new ResponseEntity<>(studenteResponse, HttpStatus.OK);
        } catch (NotFoundException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
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
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
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
        return new ResponseEntity<>(studenteResponse, HttpStatus.CREATED);
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
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
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

}

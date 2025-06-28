package org.example.demo2.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;



@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudenteResponse {

    // Se vogliamo ignorare un campo usiamo @JsonBackReference
    private Long id;
    private String nome;
    private String cognome;
    private LocalDate dataNascita;
    private ClasseResponse classe;

}

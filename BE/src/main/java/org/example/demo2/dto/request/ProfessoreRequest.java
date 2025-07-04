package org.example.demo2.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.example.demo2.utils.enums.Specializzazione;
import org.example.demo2.validator.IniziaConMaiuscola;
import org.example.demo2.validator.Maggiorenne;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProfessoreRequest {

    // private Long id;
    @NotNull(message = "Il nome e' obbligatorio")
    @Size(min = 2, max = 50, message = "Il nome deve avere tra 2 e 50 caratteri")
    @IniziaConMaiuscola
    private String nome;

    @NotBlank(message = "Il cognome non puo' essere vuoto")
    @Size(min = 2, max = 50, message = "Il congome deve avere tra 2 e 50 caratteri")
    @IniziaConMaiuscola
    private String cognome;
    
    @Maggiorenne
    private LocalDate dataNascita; // -> Passato a LocalDate
    
    private Specializzazione specializzazione;

}

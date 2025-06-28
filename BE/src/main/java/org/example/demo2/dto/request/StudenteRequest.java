package org.example.demo2.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudenteRequest {

        
    /* 
    Esempio di validazione del dto, a nostra discrezione utilizzare jakarta oppure affidarci a un Validator custom
    Altre annotazioni utili non usate :
        @NotEmpty(message = "")
        @Email(message = "")
        @Min(value = 18, message = "L'eta' minima e' 18 anni")
        @Max(value = 100, message = "L'eta' massima e' 100 anni")
        @Pattern(regexp = "[A-Z]{6}[0-9]{2}[A-Z][0-9]{2}[A-Z][0-9]{3}[A-Z]", message = "Codice fiscale non valido")
        @Positive(message = "Il numero deve essere positivo")
        @PositiveOrZero(message = "Il saldo non può essere negativo")
        @AssertTrue(message = "")
        @Past(message = "La data di nascita deve essere nel passato")
        @Future(message = "La scadenza deve essere nel futuro")
        @Valid -> Per i nestedDTO

    Per attivare la validazione nel controller servira' il @Valid 
    */

    //private Long id;

    @NotNull(message = "Il nome e' obbligatorio")
    @Size(min = 2, max = 50, message = "Il nome deve avere tra 2 e 50 caratteri")
    private String nome;

    @NotBlank(message = "Il cognome non puo' essere vuoto")
    @Size(min = 2, max = 50, message = "Il congome deve avere tra 2 e 50 caratteri")
    private String cognome;

    private LocalDate dataNascita; //-> PAssato a Local Date 

    // @NotNull(message = "La classe è obbligatoria") -> NON più obbligatoria
    private Long classeId;


}

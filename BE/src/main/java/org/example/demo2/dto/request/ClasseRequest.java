package org.example.demo2.dto.request;

import org.example.demo2.validator.GradoValido;
import org.example.demo2.validator.IdentificativoValido;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClasseRequest {

    // private Long id;  -> Tolto per non specificare l'id quando facciamo insert di classi 
    //Rendiamo obbligatori gli altri due 
    @NotNull(message = "L'identificativo è obbligatorio")
    @Size(min = 1, max = 1, message = "L'identificativo deve essere una sola lettera")
    @IdentificativoValido
    private String identificativo;//-> Cambiato a String cosi posso controllare se l’utente manda un carattere “vuoto” " ".

    @NotBlank(message = "Il grado è obbligatorio")
    @Size(max = 10, message = "Il grado non può superare 10 caratteri")
    @GradoValido
    private String grado;

}

package org.example.demo2.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
    private char identificativo;
    @NotBlank(message = "Il grado è obbligatorio")
    private String grado;

}

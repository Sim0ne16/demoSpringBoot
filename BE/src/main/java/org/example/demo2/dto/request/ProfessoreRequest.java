package org.example.demo2.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.demo2.utils.enums.Specializzazione;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProfessoreRequest {

    private Long id;
    private String nome;
    private String cognome;
    private LocalDateTime dataNascita;
    private Specializzazione specializzazione;

}

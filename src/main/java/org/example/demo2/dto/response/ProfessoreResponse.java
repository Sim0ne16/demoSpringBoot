package org.example.demo2.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.demo2.utils.enums.Specializzazione;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProfessoreResponse {

    private Long id;
    private String nome;
    private String cognome;
    private int eta;
    private LocalDateTime dataNascita;
    private Specializzazione specializzazione;
    private List<ClasseResponse> classi;

}

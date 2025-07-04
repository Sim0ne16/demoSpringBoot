package org.example.demo2.dto.response;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
//@JsonInclude(JsonInclude.Include.NON_NULL) //Non voglio tornare i campi null posso fare così o configuro JacksonConfig
public class ClasseResponse {

    private Long id;
    private String identificativo;
    private String grado;
    private List<StudenteResponse> studenti;
    private List<ProfessoreResponse> professori;

}

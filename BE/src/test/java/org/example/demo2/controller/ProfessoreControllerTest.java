package org.example.demo2.controller;

import org.example.demo2.dto.request.ProfessoreRequest;
import org.example.demo2.dto.response.ProfessoreResponse;
import org.example.demo2.service.general.ProfessoreService;
import org.example.demo2.utils.enums.Specializzazione;
import org.example.demo2.utils.exception.GlobalExceptionHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class ProfessoreControllerTest {

    private MockMvc mockMvc;

    @Mock
    private ProfessoreService professoreService;

    @InjectMocks
    private ProfessoreController professoreController;

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders
            .standaloneSetup(professoreController)
            .setControllerAdvice(new GlobalExceptionHandler())
            .build();
    }

    @Test
    void testGetAllProfessori() throws Exception {
        var p = new ProfessoreResponse();
        p.setId(10L);
        p.setNome("Anna");

        when(professoreService.getAll(false)).thenReturn(List.of(p));

        mockMvc.perform(get("/professore")
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].nome").value("Anna"));
    }

    @Test
    void testGetProfessoreById() throws Exception {
        var p = new ProfessoreResponse();
        p.setId(11L);
        p.setNome("Luigi");

        when(professoreService.getById(11L)).thenReturn(p);

        mockMvc.perform(get("/professore/11"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.nome").value("Luigi"));
    }

    @Test
    void testGetAllByClass() throws Exception {
        var p = new ProfessoreResponse();
        p.setId(12L);

        when(professoreService.getAllByClasse(2L)).thenReturn(List.of(p));

        mockMvc.perform(get("/professore/classe/2"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].id").value(12));
    }

    @Test
    void testGetProfessoriByDataNascitaRange() throws Exception {
        var p = new ProfessoreResponse();
        p.setId(13L);

        LocalDate start = LocalDate.of(1970,1,1);
        LocalDate end   = LocalDate.of(1980,12,31);

        when(professoreService.getProfessoriByDataNascitaBetween(start, end))
            .thenReturn(List.of(p));

        mockMvc.perform(get("/professore/by-data-nascita")
                .param("dataInizio","1970-01-01")
                .param("dataFine","1980-12-31"))
            .andExpect(status().isOk());
    }

    @Test
    void testGetProfessoriBySpecializzazione() throws Exception {
        var p = new ProfessoreResponse();
        p.setId(14L);
        p.setSpecializzazione(Specializzazione.MATEMATICA);

        when(professoreService.getProfessoriBySpecializzazione(Specializzazione.MATEMATICA))
            .thenReturn(List.of(p));

        mockMvc.perform(get("/professore/by-specializzazione")
                .param("specializzazione","MATEMATICA"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].specializzazione").value("MATEMATICA"));
    }

    @Test
    void testCreaProfessore() throws Exception {
        var p = new ProfessoreResponse();
        p.setId(15L);
        p.setNome("Maria");

        when(professoreService.insert(any(ProfessoreRequest.class))).thenReturn(p);

        mockMvc.perform(post("/professore")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"nome\":\"Maria\",\"cognome\":\"Bianchi\",\"dataNascita\":\"1975-03-20\"}"))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.nome").value("Maria"));
    }

    @Test
    void testAggiornaProfessore() throws Exception {
        var p = new ProfessoreResponse();
        p.setId(16L);
        p.setNome("Carlo");

        when(professoreService.update(eq(16L), any(ProfessoreRequest.class))).thenReturn(p);

        mockMvc.perform(put("/professore/16")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"nome\":\"Carlo\",\"cognome\":\"Neri\",\"dataNascita\":\"1985-07-11\"}"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(16));
    }

    @Test
    void testAssegnaClasseAProfessore() throws Exception {
        var p = new ProfessoreResponse();
        p.setId(17L);

        when(professoreService.assegnaClasse(17L, 5L)).thenReturn(p);

        mockMvc.perform(put("/professore/professori/17/classi/aggiungi/5"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(17));
    }

    @Test
    void testDeleteProfessore() throws Exception {
        mockMvc.perform(delete("/professore/20"))
            .andExpect(status().isNoContent());

        verify(professoreService).delete(20L);
    }
}

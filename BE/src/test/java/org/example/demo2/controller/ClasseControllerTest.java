package org.example.demo2.controller;

import org.example.demo2.dto.request.ClasseRequest;
import org.example.demo2.dto.response.ClasseResponse;
import org.example.demo2.service.general.ClasseService;
import org.example.demo2.utils.exception.GlobalExceptionHandler;
import org.example.demo2.utils.exception.NotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class ClasseControllerTest {

    private MockMvc mockMvc;

    @Mock
    private ClasseService classeService;

    @InjectMocks
    private ClasseController classeController;

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders
            .standaloneSetup(classeController)
            .setControllerAdvice(new GlobalExceptionHandler())
            .build();
    }

    @Test
    void testGetAllClassi() throws Exception {
        ClasseResponse c = new ClasseResponse();
        c.setId(1L);
        c.setIdentificativo("A");
        c.setGrado("Prima");

        when(classeService.getAll(false, false)).thenReturn(List.of(c));

        mockMvc.perform(get("/classe")
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].identificativo").value("A"));
    }

    @Test
    void testGetClasseById() throws Exception {
        ClasseResponse c = new ClasseResponse();
        c.setId(2L);
        c.setIdentificativo("B");

        when(classeService.getById(2L)).thenReturn(c);

        mockMvc.perform(get("/classe/2"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.identificativo").value("B"));
    }

    @Test
    void testGetClasseById_NotFound() throws Exception {
        when(classeService.getById(99L))
            .thenThrow(new NotFoundException("Classe non trovata"));

        mockMvc.perform(get("/classe/99"))
            .andExpect(status().isNotFound())
            .andExpect(jsonPath("$.status").value(404))
            .andExpect(jsonPath("$.message").value("Classe non trovata"));
    }

    @Test
    void testGetClasseAssegnataProfessore() throws Exception {
        ClasseResponse c = new ClasseResponse();
        c.setId(3L);
        c.setIdentificativo("C");

        when(classeService.getClasseAssegnataProfessore(5L))
            .thenReturn(List.of(c));

        mockMvc.perform(get("/classe/assegnate-professore/5"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].id").value(3));
    }

    @Test
    void testInserisciClasse() throws Exception {
        when(classeService.insert(any(ClasseRequest.class)))
            .thenReturn(new ClasseResponse(1L, "X", "Terza", List.of(), List.of()));

        mockMvc.perform(post("/classe")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"identificativo\":\"X\",\"grado\":\"Terza\"}"))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.identificativo").value("X"));
    }

    @Test
    void testAggiornaClasse() throws Exception {
        when(classeService.update(eq(7L), any(ClasseRequest.class)))
            .thenReturn(new ClasseResponse(7L, "Y", "Quarta", List.of(), List.of()));

        mockMvc.perform(put("/classe/7")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"identificativo\":\"Y\",\"grado\":\"Quarta\"}"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.grado").value("Quarta"));
    }

    @Test
    void testDeleteClasse() throws Exception {
        mockMvc.perform(delete("/classe/4"))
            .andExpect(status().isNoContent());

        verify(classeService).delete(4L);
    }
}

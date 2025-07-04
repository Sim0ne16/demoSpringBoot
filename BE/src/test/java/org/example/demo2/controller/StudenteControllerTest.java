package org.example.demo2.controller;

import org.example.demo2.dto.request.StudenteRequest;
import org.example.demo2.dto.response.StudenteResponse;
import org.example.demo2.service.general.StudenteService;
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
class StudenteControllerTest {

    private MockMvc mockMvc;

    @Mock
    private StudenteService studenteService;

    @InjectMocks
    private StudenteController studenteController;

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders
            .standaloneSetup(studenteController)
            .setControllerAdvice(new GlobalExceptionHandler())
            .build();
    }

    @Test
    void testGetAllStudenti() throws Exception {
        var s = new StudenteResponse();
        s.setId(101L);
        s.setNome("Luca");

        when(studenteService.getAll(false)).thenReturn(List.of(s));

        mockMvc.perform(get("/students")
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].nome").value("Luca"));
    }

    @Test
    void testGetStudenteById() throws Exception {
        var s = new StudenteResponse();
        s.setId(102L);
        s.setCognome("Verdi");

        when(studenteService.getById(102L, false)).thenReturn(s);

        mockMvc.perform(get("/students/102"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.cognome").value("Verdi"));
    }

    @Test
    void testGetStudenteById_NotFound() throws Exception {
        when(studenteService.getById(999L, false))
            .thenThrow(new NotFoundException("Studente non trovato"));

        mockMvc.perform(get("/students/999"))
            .andExpect(status().isNotFound())
            .andExpect(jsonPath("$.message").value("Studente non trovato"));
    }

    @Test
    void testGetNomeCompleto() throws Exception {
        var s = new StudenteResponse();
        s.setId(103L);

        when(studenteService.getByNameAndLastName("Mario","Rossi"))
            .thenReturn(List.of(s));

        mockMvc.perform(get("/students/nome-completo")
                .param("nome","Mario")
                .param("cognome","Rossi"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].id").value(103));
    }

    @Test
    void testGetStudentiByClasse() throws Exception {
        var s = new StudenteResponse();
        s.setId(104L);

        when(studenteService.getAllByClass(7L)).thenReturn(List.of(s));

        mockMvc.perform(get("/students/classe/7"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].id").value(104));
    }

    @Test
    void testCreaStudente() throws Exception {
        var s = new StudenteResponse();
        s.setId(105L);

        when(studenteService.insert(any(StudenteRequest.class))).thenReturn(s);

        mockMvc.perform(post("/students")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"nome\":\"Giulia\",\"cognome\":\"Neri\",\"dataNascita\":\"2001-02-02\"}"))
            .andExpect(status().isCreated())
            .andExpect(header().string("Location", org.hamcrest.Matchers.containsString("/students/105")))
            .andExpect(jsonPath("$.id").value(105));
    }

    @Test
    void testAggiornaStudente() throws Exception {
        var s = new StudenteResponse();
        s.setId(106L);

        when(studenteService.update(eq(106L), any(StudenteRequest.class))).thenReturn(s);

        mockMvc.perform(put("/students/106")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"nome\":\"Paolo\",\"cognome\":\"Bianchi\",\"dataNascita\":\"2002-03-03\"}"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(106));
    }

    @Test
    void testAssegnaClasseStudente() throws Exception {
        var s = new StudenteResponse();
        s.setId(107L);

        when(studenteService.assegnaClasse(107L, 8L)).thenReturn(s);

        mockMvc.perform(put("/students/107/classe/8"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(107));
    }

    @Test
    void testEliminaStudente() throws Exception {
        mockMvc.perform(delete("/students/110"))
            .andExpect(status().isNoContent());

        verify(studenteService).delete(110L);
    }
}

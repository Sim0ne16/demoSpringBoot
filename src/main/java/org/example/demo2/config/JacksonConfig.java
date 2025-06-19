package org.example.demo2.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


//Configurazione per la quale decidiamo di non serializzare in risposta i campi NULL!
//Possiamo fare così o specificare @JsonInclude(JsonInclude.Include.NON_NULL) SUL DTO

@Configuration
public class JacksonConfig {

    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        mapper.registerModule(new JavaTimeModule()); // <--- Supporto per LocalDateTime
        return mapper;
    }
}
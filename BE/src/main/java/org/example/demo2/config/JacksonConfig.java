package org.example.demo2.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


//Configurazione per la quale decidiamo di non serializzare in risposta i campi NULL!
//Possiamo fare così o specificare @JsonInclude(JsonInclude.Include.NON_NULL) SUL DTO

/*Scelto di modificare questo file di configurazione 
per gestire in modo univoco le date senza dover mettere la annotation ovunque servisse
 ma lasciando tutto qua anche in caso non fuzioni come dovesse
*/

@Configuration
public class JacksonConfig {

    private static final String DATE_FORMAT = "yyyy-MM-dd";

    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        //NON include i campi null
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

        //Modulo per JAVA Date/Time
        JavaTimeModule module = new JavaTimeModule();

        //Serializer per LocalDateTime nel formato yyy-MM-dd
        module.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(DateTimeFormatter.ofPattern(DATE_FORMAT)));

        //Registra il modulo(usa questo modulo aggiuntivo per gestire nuovi tipi di dati o comportamenti personalizzati.)
        mapper.registerModule(module);

        //Disabilita i timestamp per le date
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        return mapper;
    }
}
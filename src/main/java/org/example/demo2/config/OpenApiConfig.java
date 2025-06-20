package org.example.demo2.config;


import io.swagger.v3.oas.models.OpenAPI;


import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API APP Scuola")
                        .version("1.0")
                        .description("Documentazione delle API per la gestione delle classi e studenti di una scuola")
                        .contact(new Contact()
                                .name("Tuo Nome")
                                .email("test@test.com")));
    }
}
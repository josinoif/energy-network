package com.sinapsis.backend.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;

@Configuration
@EnableWebMvc
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Energy Network API")
                        .version("1.0.0")
                        .description("API para gerenciamento de RedesMT e Subestações")
                        .contact(new Contact()
                                .name("Josino")
                                .email("josinon@gmail.com")
                                .url("https://sinapsis-network.com")));
    }
}
package com.service.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI myOpenAPI() {
        Contact contact = new Contact();
        contact.setEmail("sivajiganesh@91social.com");
        contact.setName("SivajiGanesh Nangireddy");
        contact.setUrl("https://sivajiganesh.dev");

        Info info = new Info()
            .title("Service Request")
            .version("1.0")
            .contact(contact)
            .description(
                "API specs for service attribute definition and capture service attribute details");
        return new OpenAPI().info(info);
    }
}

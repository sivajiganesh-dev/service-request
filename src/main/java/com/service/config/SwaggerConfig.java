package com.service.config;

import java.util.Collections;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
            .apiInfo(
                new ApiInfo("Service Request",
                    "API specs for service attribute definition and capture service attribute details",
                    "1.0",
                    "",
                    new Contact("SivajiGanesh Nangireddy", "https://sivajiganesh.dev", "sivajiganesh@91social.com"),
                    "",
                    "",
                    Collections.emptyList()))
            .select()
            .apis(RequestHandlerSelectors.basePackage("com.service"))
            .paths(PathSelectors.any())
            .build();
    }
}

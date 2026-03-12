package com.ecommerce.auth.config;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@SecurityScheme(
        name = "Authentication",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        scheme = "bearer"
)
public class SwaggerConfig {

    @Value("${spring.application.name}")
    private String appName;

    @Value("${spring.application.version}")
    private String appVersion;

    @Value("${spring.application.doc.url}")
    private String docUrl;

    @Value("${spring.application.doc.description}")
    private String description;

    @Bean
    public OpenAPI springDoc() {
        return new OpenAPI()
                .info(new Info()
                        .title(appName)
                        .version(appVersion))
                .externalDocs(new ExternalDocumentation()
                        .description(description)
                        .url(docUrl))

                ;
    }
}

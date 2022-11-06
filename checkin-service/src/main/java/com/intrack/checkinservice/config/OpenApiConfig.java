package com.intrack.checkinservice.config;//package com.intrack.checkinservice.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.annotations.servers.ServerVariable;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        servers = {
                @Server(url = "http://localhost:{port}/{basePath}", description = "API localhost", variables = {
                        @ServerVariable(name = "port", defaultValue = "8080"),
                        @ServerVariable(name = "basePath", defaultValue = "event-service-backend")
                })
        })
public class OpenApiConfig {

    @Value("${info.build.version}")
    private String appVersion;

    @Bean
    public OpenAPI api() {
        return new OpenAPI()
                .info(metadata());
    }

    private Info metadata() {
        return new Info()
                .title("Event service backend")
                .version(appVersion)
                .description("This is an API reference for developers to the TicketFlip Event service backend REST API.")
                .termsOfService("http://swagger.io/terms/")
                .license(new License().name("Apache 2.0").url("http://springdoc.org"));
    }
}

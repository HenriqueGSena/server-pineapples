package com.server.pineapples.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI defaultOpenAPI() {
        Server server = new Server();
        server.setUrl("http://localhost:8080");
        server.setDescription("Pineapples Service By Avantio");

        Contact contact = new Contact();
        contact.setName("Henrique Sena");
        contact.setUrl("https://henriquegsena.github.io/");

        Info info = new Info()
                .title("Pineapples Service By Avantio Documentation")
                .version("1.0.0")
                .contact(contact);
        return  new OpenAPI().info(info).servers(List.of(server));
    }
}

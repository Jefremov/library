package com.accenture.library.config;

import io.swagger.v3.oas.models.*;
import io.swagger.v3.oas.models.info.*;
import io.swagger.v3.oas.models.servers.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.context.annotation.*;

import java.util.*;


public class OpenAPIConfig {

    @Value("${dev-url}")
    private String devUrl;

    @Bean
    public OpenAPI myOpenAPI() {
        Server devServer = new Server();
        devServer.setUrl(devUrl);
        devServer.setDescription("Server URL in Development environment");

        Info info = new Info()
                .title("Library API")
                .version("1.0");

        return new OpenAPI().info(info).servers(List.of(devServer));
    }
}
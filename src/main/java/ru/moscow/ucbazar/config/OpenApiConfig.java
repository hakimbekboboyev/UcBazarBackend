package ru.moscow.ucbazar.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@OpenAPIDefinition(
        info = @Info(
                title = "UcBazar API documentation",
                version = "v1",
                description = "Doing CRUD operations",
                termsOfService = "Terms of service",
                contact = @Contact(
                        name = "Uc Bazar"
                ),
                license = @License(
                        name = "License by Mango Evolution"
                )
        ),
        servers = {
                @Server(
                        description = "Dev api",
                        url = "https://ucbazarbackend.onrender.com/"
                ),
                @Server(
                        description = "Dev(test)",
                        url = "http://localhost:8081"
                )
        }


)
public class OpenApiConfig {
    @Bean
    public WebMvcConfigurer webMvcConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addResourceHandlers(ResourceHandlerRegistry registry) {
                registry.addResourceHandler("swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/");
                registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
            }
        };
    }
}

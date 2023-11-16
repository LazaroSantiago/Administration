package com.example.administration;

import entity.Administrator;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import service.AdministratorService;

@SpringBootApplication
@ComponentScan({ "controller", "entity", "repository", "service", "com.example.administration"})
@EnableJpaRepositories("repository")
@EnableMongoRepositories("repository")
@OpenAPIDefinition
@EntityScan("entity")
public class AdministrationApplication {

    public static void main(String[] args) {
        SpringApplication.run(AdministrationApplication.class, args);
    }

    @Autowired
    private AdministratorService administratorService;

    @PostConstruct
    public void cargaData() throws Exception {
        administratorService.save(new Administrator(2L, "John Doe"));
        administratorService.save(new Administrator(3L, "Juilio"));
    }

//    http://localhost:8080/swagger-ui/index.html

}

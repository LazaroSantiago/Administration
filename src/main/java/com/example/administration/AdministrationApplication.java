package com.example.administration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

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

}

package com.example.enotes_api_service;

import com.example.enotes_api_service.Dto.CategoryDto;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
public class EnotesApiServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(EnotesApiServiceApplication.class, args);

    }

}

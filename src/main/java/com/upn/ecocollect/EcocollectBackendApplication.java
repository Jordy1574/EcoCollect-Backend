package com.upn.ecocollect;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.upn.ecocollect.repository")
public class EcocollectBackendApplication {
    public static void main(String[] args) {
        SpringApplication.run(EcocollectBackendApplication.class, args);
    }
}


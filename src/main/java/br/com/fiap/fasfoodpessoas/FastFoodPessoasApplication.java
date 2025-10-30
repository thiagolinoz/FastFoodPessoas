package br.com.fiap.fasfoodpessoas;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {
        "br.com.fiap.fasfoodpessoas",
        "br.com.fiap.fasfoodpessoas.infrastructure.web.api",
        "br.com.fiap.fasfoodpessoas.infrastructure.persistence.jpa"
})
public class FastFoodPessoasApplication {

    public static void main(String[] args) {
        SpringApplication.run(FastFoodPessoasApplication.class, args);
    }
}

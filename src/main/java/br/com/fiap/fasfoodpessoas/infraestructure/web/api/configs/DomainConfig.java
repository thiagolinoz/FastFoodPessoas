package br.com.fiap.fasfoodpessoas.infraestructure.web.api.configs;

import br.com.fiap.fasfoodpessoas.domain.ports.out.PessoaRepositoryPort;
import br.com.fiap.fasfoodpessoas.domain.services.PessoaService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DomainConfig {

    @Bean
    public PessoaService pessoaService(PessoaRepositoryPort pessoaRepository) {
        return new PessoaService(pessoaRepository);
    }
}

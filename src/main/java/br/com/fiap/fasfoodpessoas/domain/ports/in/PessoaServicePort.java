package br.com.fiap.fasfoodpessoas.domain.ports.in;

import br.com.fiap.fasfoodpessoas.domain.models.PessoaModel;

import java.util.Optional;

public interface PessoaServicePort {
    PessoaModel cadastraPessoa(PessoaModel pessoa);
    Optional<PessoaModel> buscaPessoaPorCpf(String cdDocPessoa);
}

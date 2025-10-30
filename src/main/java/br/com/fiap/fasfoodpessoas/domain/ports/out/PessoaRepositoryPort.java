package br.com.fiap.fasfoodpessoas.domain.ports.out;

import br.com.fiap.fasfoodpessoas.domain.models.PessoaModel;

import java.util.Optional;

public interface PessoaRepositoryPort {

    PessoaModel cadastrarPessoa(PessoaModel pessoaModel);
    Optional<PessoaModel> buscarPessoaPorCpf(String cdDocPessoa);
}

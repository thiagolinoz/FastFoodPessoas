package br.com.fiap.fasfoodpessoas.domain.services;

import br.com.fiap.fasfoodpessoas.domain.models.PessoaModel;
import br.com.fiap.fasfoodpessoas.domain.ports.in.PessoaServicePort;
import br.com.fiap.fasfoodpessoas.domain.ports.out.PessoaRepositoryPort;
import br.com.fiap.fasfoodpessoas.infraestructure.web.api.exceptions.CpfCadastradoException;

import java.util.Optional;

public class PessoaService implements PessoaServicePort {

    private final PessoaRepositoryPort pessoaRepositoryPort;

    public PessoaService(PessoaRepositoryPort pessoaRepositoryPort) {
        this.pessoaRepositoryPort = pessoaRepositoryPort;
    }

    @Override
    public PessoaModel cadastraPessoa(PessoaModel pessoaModel) {
        Optional<PessoaModel> pessoaModelEncontrada = this.buscaPessoaPorCpf(pessoaModel.getCdDocPessoa());
        if (pessoaModelEncontrada.isPresent()) {
            throw new CpfCadastradoException("Cliente ja cadastrado com o CPF informado");
        }
        return pessoaRepositoryPort.cadastrarPessoa(pessoaModel);
    }

    @Override
    public Optional<PessoaModel> buscaPessoaPorCpf(String cdDocPessoa) {
        return pessoaRepositoryPort.buscarPessoaPorCpf(cdDocPessoa);
    }


}

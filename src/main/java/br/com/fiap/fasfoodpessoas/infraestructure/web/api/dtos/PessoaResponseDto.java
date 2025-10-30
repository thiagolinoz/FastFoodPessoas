package br.com.fiap.fasfoodpessoas.infraestructure.web.api.dtos;

import br.com.fiap.fasfoodpessoas.domain.enums.TipoPessoaEnum;
import br.com.fiap.fasfoodpessoas.domain.models.PessoaModel;

public record PessoaResponseDto(
        String cdDocPessoa,
        String nmPessoa,
        TipoPessoaEnum tpPessoa,
        String dsEmail
) {
    public PessoaResponseDto(PessoaModel pessoaModel) {
        this(pessoaModel.getCdDocPessoa(),
                pessoaModel.getNmPessoa(),
                pessoaModel.getTpPessoa(),
                pessoaModel.getDsEmail());
    }
}

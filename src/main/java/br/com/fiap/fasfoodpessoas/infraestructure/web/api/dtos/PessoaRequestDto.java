package br.com.fiap.fasfoodpessoas.infraestructure.web.api.dtos;


import br.com.fiap.fasfoodpessoas.domain.enums.TipoPessoaEnum;
import br.com.fiap.fasfoodpessoas.domain.models.PessoaModel;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.br.CPF;

public record PessoaRequestDto(
        @Valid
        @NotNull(message = "O atributo cdDocPessoa é obrigatório.")
        @CPF(message = "O CPF informado no atributo cdDocPessoa é inválido.")
        String cdDocPessoa,
        @Valid
        @NotNull(message = "O atributo nmPessoa é obrigatório.")
        @Size(min = 3, max = 200, message = "O atributo nmPessoa deve ter entre 3 e 200 caracteres.")
        String nmPessoa,
        @Valid
        @NotNull(message = "O atributo tpPessoa é obrigatório.")
        TipoPessoaEnum tpPessoa,
        @Valid
        @NotNull(message = "O atributo dsEmail é obrigatório.")
        @Email(message = "O valor informado para dsEmail não é um e-mail válido")
        String dsEmail
) {
    public PessoaRequestDto(PessoaModel pessoaModel) {
        this(pessoaModel.getCdDocPessoa(), pessoaModel.getNmPessoa(), pessoaModel.getTpPessoa(), pessoaModel.getDsEmail());
    }
}

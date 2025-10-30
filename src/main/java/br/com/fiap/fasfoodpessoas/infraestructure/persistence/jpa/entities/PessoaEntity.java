package br.com.fiap.fasfoodpessoas.infraestructure.persistence.jpa.entities;

import br.com.fiap.fasfoodpessoas.domain.enums.TipoPessoaEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tb_pessoas")
public class PessoaEntity {

    @Id
    @Column(name = "cd_doc_pessoa", nullable = false, unique = true)
    private String cdDocPessoa;
    @Column(name = "nm_pessoa", nullable = false)
    private String nmPessoa;
    @Enumerated(EnumType.STRING)
    @Column(name = "tp_pessoa", nullable = false)
    private TipoPessoaEnum tpPessoa;
    @Column(name = "ds_email", nullable = false)
    private String dsEmail;

    public String getCdDocPessoa() {
        return cdDocPessoa;
    }

    public String getNmPessoa() {
        return nmPessoa;
    }

    public TipoPessoaEnum getTpPessoa() {
        return tpPessoa;
    }

    public String getDsEmail() {
        return dsEmail;
    }

    public void setCdDocPessoa(String cdDocPessoa) {
        this.cdDocPessoa = cdDocPessoa;
    }

    public void setNmPessoa(String nmPessoa) {
        this.nmPessoa = nmPessoa;
    }

    public void setTpPessoa(TipoPessoaEnum tpPessoa) {
        this.tpPessoa = tpPessoa;
    }

    public void setDsEmail(String dsEmail) {
        this.dsEmail = dsEmail;
    }
}

package br.com.fiap.fasfoodpessoas.infraestructure.persistence.entities;

import br.com.fiap.fasfoodpessoas.domain.enums.TipoPessoaEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "pessoas")
public class PessoaEntity {

    @Id
    private String id;
    private String cdDocPessoa;
    private String nmPessoa;
    private TipoPessoaEnum tpPessoa;
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

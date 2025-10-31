package br.com.fiap.fasfoodpessoas.infraestructure.commons.mappers;

import br.com.fiap.fasfoodpessoas.domain.models.PessoaModel;
import br.com.fiap.fasfoodpessoas.infraestructure.persistence.entities.PessoaEntity;

public class PessoaMapper {

    public static PessoaModel toModel(PessoaEntity pessoaEntity) {
        PessoaModel pessoaModel = new PessoaModel();
        pessoaModel.setCdDocPessoa(pessoaEntity.getCdDocPessoa());
        pessoaModel.setNmPessoa(pessoaEntity.getNmPessoa());
        pessoaModel.setTpPessoa(pessoaEntity.getTpPessoa());
        pessoaModel.setDsEmail(pessoaEntity.getDsEmail());
        return pessoaModel;
    }

    public static PessoaEntity toEntity(PessoaModel pessoaModel) {
        PessoaEntity pessoaEntity = new PessoaEntity();
        pessoaEntity.setCdDocPessoa(pessoaModel.getCdDocPessoa());
        pessoaEntity.setNmPessoa(pessoaModel.getNmPessoa());
        pessoaEntity.setTpPessoa(pessoaModel.getTpPessoa());
        pessoaEntity.setDsEmail(pessoaModel.getDsEmail());
        return pessoaEntity;
    }
}

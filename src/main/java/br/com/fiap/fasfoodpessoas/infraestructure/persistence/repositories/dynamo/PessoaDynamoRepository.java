package br.com.fiap.fasfoodpessoas.infraestructure.persistence.repositories.dynamo;

import br.com.fiap.fasfoodpessoas.infraestructure.persistence.entities.PessoaEntity;
import org.springframework.stereotype.Repository;

@Repository
public interface PessoaDynamoRepository {
    PessoaEntity findByCdDocPessoa(String cdDocPessoa);
    void salvar(PessoaEntity pessoaEntity);
}

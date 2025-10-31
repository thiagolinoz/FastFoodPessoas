package br.com.fiap.fasfoodpessoas.infraestructure.persistence.repositories.mongo;

import br.com.fiap.fasfoodpessoas.infraestructure.persistence.entities.PessoaEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PessoaMongoDBRepository extends MongoRepository<PessoaEntity, String> {
    PessoaEntity findByCdDocPessoa(String cdDocPessoa);
}

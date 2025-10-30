package br.com.fiap.fasfoodpessoas.infraestructure.persistence.jpa.repositories;

import br.com.fiap.fasfoodpessoas.domain.models.PessoaModel;
import br.com.fiap.fasfoodpessoas.domain.ports.out.PessoaRepositoryPort;
import br.com.fiap.fasfoodpessoas.infraestructure.commons.mappers.PessoaMapper;
import br.com.fiap.fasfoodpessoas.infraestructure.persistence.jpa.entities.PessoaEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public class PessoaRepository implements PessoaRepositoryPort {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public PessoaModel cadastrarPessoa(PessoaModel pessoaModel) {
        PessoaEntity pessoaEntity = PessoaMapper.toEntity(pessoaModel);
        entityManager.merge(pessoaEntity);
        return PessoaMapper.toModel(pessoaEntity);
    }

    @Override
    public Optional<PessoaModel> buscarPessoaPorCpf(String cdDocPessoa) {
        PessoaEntity pessoaEntity = entityManager.find(PessoaEntity.class, cdDocPessoa);
        return Optional.ofNullable(pessoaEntity).map(PessoaMapper::toModel);
    }
}

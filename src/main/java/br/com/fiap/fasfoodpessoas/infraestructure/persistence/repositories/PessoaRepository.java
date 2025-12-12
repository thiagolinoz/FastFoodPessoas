package br.com.fiap.fasfoodpessoas.infraestructure.persistence.repositories;

import br.com.fiap.fasfoodpessoas.domain.models.PessoaModel;
import br.com.fiap.fasfoodpessoas.domain.ports.out.PessoaRepositoryPort;
import br.com.fiap.fasfoodpessoas.infraestructure.commons.mappers.PessoaMapper;
import br.com.fiap.fasfoodpessoas.infraestructure.persistence.entities.PessoaEntity;
import br.com.fiap.fasfoodpessoas.infraestructure.persistence.repositories.mongo.PessoaMongoDBRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class PessoaRepository implements PessoaRepositoryPort {

    private final PessoaMongoDBRepository pessoaMongoDBRepository;


    public PessoaRepository(PessoaMongoDBRepository pessoaMongoDBRepository) {
        this.pessoaMongoDBRepository = pessoaMongoDBRepository;
    }

    @Override
    public PessoaModel cadastrarPessoa(PessoaModel pessoaModel) {
        PessoaEntity pessoaEntity = PessoaMapper.toEntity(pessoaModel);
        pessoaMongoDBRepository.save(pessoaEntity);
        return PessoaMapper.toModel(pessoaEntity);
    }

    @Override
    public Optional<PessoaModel> buscarPessoaPorCpf(String cdDocPessoa) {
        PessoaEntity pessoaEntity = pessoaMongoDBRepository.findByCdDocPessoa(cdDocPessoa);
        return Optional.ofNullable(pessoaEntity).map(PessoaMapper::toModel);
    }
}

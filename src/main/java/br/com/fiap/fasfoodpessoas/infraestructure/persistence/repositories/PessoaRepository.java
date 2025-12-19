package br.com.fiap.fasfoodpessoas.infraestructure.persistence.repositories;

import br.com.fiap.fasfoodpessoas.domain.models.PessoaModel;
import br.com.fiap.fasfoodpessoas.domain.ports.out.PessoaRepositoryPort;
import br.com.fiap.fasfoodpessoas.infraestructure.commons.mappers.PessoaMapper;
import br.com.fiap.fasfoodpessoas.infraestructure.persistence.entities.PessoaEntity;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.Key;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;

import java.util.Optional;

@Component
public class PessoaRepository implements PessoaRepositoryPort {

    private final DynamoDbTable<PessoaEntity> tabelaPessoa;


    public PessoaRepository(DynamoDbEnhancedClient enhancedClient) {
        TableSchema<PessoaEntity> schema = TableSchema.fromBean(PessoaEntity.class);
        this.tabelaPessoa = enhancedClient.table("Pessoas", schema);
    }

    @Override
    public PessoaModel cadastrarPessoa(PessoaModel pessoaModel) {
        PessoaEntity pessoaEntity = PessoaMapper.toEntity(pessoaModel);
        tabelaPessoa.putItem(pessoaEntity);
        return PessoaMapper.toModel(pessoaEntity);
    }

    @Override
    public Optional<PessoaModel> buscarPessoaPorCpf(String cdDocPessoa) {
        PessoaEntity pessoaEntity = tabelaPessoa.getItem(Key.builder().partitionValue(cdDocPessoa).build());
        return Optional.ofNullable(pessoaEntity).map(PessoaMapper::toModel);
    }
}

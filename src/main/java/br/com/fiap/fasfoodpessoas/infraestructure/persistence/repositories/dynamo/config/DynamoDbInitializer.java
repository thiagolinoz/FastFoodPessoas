package br.com.fiap.fasfoodpessoas.infraestructure.persistence.repositories.dynamo.config;

import br.com.fiap.fasfoodpessoas.infraestructure.persistence.entities.PessoaEntity;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;

@Configuration
@Profile("local")
public class DynamoDbInitializer {

    @EventListener(ApplicationReadyEvent.class)
    public void setupTables(DynamoDbEnhancedClient enhancedClient) {
        System.out.println(">>>> TENTANDO CRIAR TABELAS NO DYNAMO LOCAL <<<<");
        DynamoDbTable<PessoaEntity> table = enhancedClient.table("Pessoas", TableSchema.fromBean(PessoaEntity.class));

        try {
            table.createTable();
            System.out.println("Infraestrutura local do DynamoDB pronta.");
            System.out.println(">>>> TABELA PESSOAS CRIADA COM SUCESSO <<<<");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}

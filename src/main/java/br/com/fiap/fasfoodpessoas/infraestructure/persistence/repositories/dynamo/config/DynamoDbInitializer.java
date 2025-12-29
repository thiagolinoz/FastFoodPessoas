package br.com.fiap.fasfoodpessoas.infraestructure.persistence.repositories.dynamo.config;

import br.com.fiap.fasfoodpessoas.infraestructure.persistence.entities.PessoaEntity;
import org.springframework.beans.factory.annotation.Value;
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

    @Value("${aws.dynamodb.create-tables:false}")
    private boolean shouldCreate;

    private final DynamoDbEnhancedClient enhancedClient;

    public DynamoDbInitializer(DynamoDbEnhancedClient enhancedClient) {
        this.enhancedClient = enhancedClient;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void setupTables() {
        if (!shouldCreate) return;

        DynamoDbTable<PessoaEntity> table = enhancedClient.table("Pessoas", TableSchema.fromBean(PessoaEntity.class));

        table.createTable();
    }
}

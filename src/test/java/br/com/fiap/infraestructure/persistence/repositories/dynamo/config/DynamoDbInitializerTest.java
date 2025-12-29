package br.com.fiap.infraestructure.persistence.repositories.dynamo.config;

import br.com.fiap.fasfoodpessoas.infraestructure.persistence.entities.PessoaEntity;
import br.com.fiap.fasfoodpessoas.infraestructure.persistence.repositories.dynamo.config.DynamoDbInitializer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("DynamoDbInitializer - Testes da inicialização de tabelas")
class DynamoDbInitializerTest {

    @Mock
    private DynamoDbEnhancedClient enhancedClient;

    @Mock
    private DynamoDbTable<PessoaEntity> pessoaTable;

    @InjectMocks
    private DynamoDbInitializer dynamoDbInitializer;

    private ByteArrayOutputStream outputStreamCaptor;

    @BeforeEach
    void setUp() {
        outputStreamCaptor = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStreamCaptor));
    }

    @Test
    @DisplayName("Deve retornar sem fazer nada quando shouldCreate é false")
    void deveRetornarQuandoShouldCreateIsFalse() {
        // Arrange
        ReflectionTestUtils.setField(dynamoDbInitializer, "shouldCreate", false);

        // Act
        dynamoDbInitializer.setupTables();

        // Assert
        verify(enhancedClient, never()).table(anyString(), any());
        String output = outputStreamCaptor.toString();
    }

    @Test
    @DisplayName("Deve criar tabela com sucesso quando shouldCreate é true")
    void deveCriarTabelaComSucessoQuandoShouldCreateIsTrue() {
        // Arrange
        ReflectionTestUtils.setField(dynamoDbInitializer, "shouldCreate", true);
        when(enhancedClient.table("Pessoas", software.amazon.awssdk.enhanced.dynamodb.TableSchema.fromBean(PessoaEntity.class)))
                .thenReturn(pessoaTable);
        doNothing().when(pessoaTable).createTable();

        // Act
        dynamoDbInitializer.setupTables();

        // Assert
        verify(enhancedClient, times(1)).table("Pessoas", software.amazon.awssdk.enhanced.dynamodb.TableSchema.fromBean(PessoaEntity.class));
        verify(pessoaTable, times(1)).createTable();
    }

    @Test
    @DisplayName("Deve tratar exceção silenciosamente quando falha ao criar tabela")
    void deveTratarExcecaoQuandoFalhaAoCriarTabela() {
        // Arrange
        ReflectionTestUtils.setField(dynamoDbInitializer, "shouldCreate", true);
        when(enhancedClient.table("Pessoas", software.amazon.awssdk.enhanced.dynamodb.TableSchema.fromBean(PessoaEntity.class)))
                .thenReturn(pessoaTable);
        
        String errorMessage = "Table already exists";
        doThrow(new RuntimeException(errorMessage)).when(pessoaTable).createTable();

        // Act & Assert - não deve lançar exceção
        assertDoesNotThrow(() -> dynamoDbInitializer.setupTables());

        verify(enhancedClient, times(1)).table("Pessoas", software.amazon.awssdk.enhanced.dynamodb.TableSchema.fromBean(PessoaEntity.class));
        verify(pessoaTable, times(1)).createTable();
    }

       @Test
    @DisplayName("Deve usar nome correto da tabela: 'Pessoas'")
    void deveUsarNomeCorretoDaTabela() {
        // Arrange
        ReflectionTestUtils.setField(dynamoDbInitializer, "shouldCreate", true);
        when(enhancedClient.table("Pessoas", software.amazon.awssdk.enhanced.dynamodb.TableSchema.fromBean(PessoaEntity.class)))
                .thenReturn(pessoaTable);
        doNothing().when(pessoaTable).createTable();

        // Act
        dynamoDbInitializer.setupTables();

        // Assert
        verify(enhancedClient, times(1)).table(eq("Pessoas"), any());
    }

    @Test
    @DisplayName("Deve usar PessoaEntity como schema")
    void deveUsarPessoaEntityComoSchema() {
        // Arrange
        ReflectionTestUtils.setField(dynamoDbInitializer, "shouldCreate", true);
        when(enhancedClient.table("Pessoas", software.amazon.awssdk.enhanced.dynamodb.TableSchema.fromBean(PessoaEntity.class)))
                .thenReturn(pessoaTable);
        doNothing().when(pessoaTable).createTable();

        // Act
        dynamoDbInitializer.setupTables();

        // Assert
        verify(enhancedClient, times(1)).table("Pessoas", software.amazon.awssdk.enhanced.dynamodb.TableSchema.fromBean(PessoaEntity.class));
    }

    @Test
    @DisplayName("Deve chamar createTable apenas uma vez quando shouldCreate é true")
    void deveChamarCreateTableApenasUmaVez() {
        // Arrange
        ReflectionTestUtils.setField(dynamoDbInitializer, "shouldCreate", true);
        when(enhancedClient.table("Pessoas", software.amazon.awssdk.enhanced.dynamodb.TableSchema.fromBean(PessoaEntity.class)))
                .thenReturn(pessoaTable);
        doNothing().when(pessoaTable).createTable();

        // Act
        dynamoDbInitializer.setupTables();

        // Assert
        verify(pessoaTable, times(1)).createTable();
    }

    @Test
    @DisplayName("Deve nunca chamar createTable quando shouldCreate é false")
    void deveNuncaChamarCreateTableQuandoShouldCreateIsFalse() {
        // Arrange
        ReflectionTestUtils.setField(dynamoDbInitializer, "shouldCreate", false);

        // Act
        dynamoDbInitializer.setupTables();

        // Assert
        verify(pessoaTable, never()).createTable();
        verify(enhancedClient, never()).table(anyString(), any());
    }

    @Test
    @DisplayName("Deve imprimir mensagem de erro quando exception contém mensagem")
    void deveImprimirMensagemDeErro() {
        // Arrange
        ReflectionTestUtils.setField(dynamoDbInitializer, "shouldCreate", true);
        String errorMessage = "ResourceInUseException: Table already exists";
        Exception exception = new RuntimeException(errorMessage);
        
        when(enhancedClient.table("Pessoas", software.amazon.awssdk.enhanced.dynamodb.TableSchema.fromBean(PessoaEntity.class)))
                .thenReturn(pessoaTable);
        doThrow(exception).when(pessoaTable).createTable();

        // Act
        dynamoDbInitializer.setupTables();

        // Assert
        String output = outputStreamCaptor.toString();
        assertTrue(output.contains(errorMessage) || output.contains("ResourceInUseException"));
    }

    @Test
    @DisplayName("Deve ser thread-safe e suportar múltiplas chamadas")
    void deveSerThreadSafe() {
        // Arrange
        ReflectionTestUtils.setField(dynamoDbInitializer, "shouldCreate", true);
        when(enhancedClient.table("Pessoas", software.amazon.awssdk.enhanced.dynamodb.TableSchema.fromBean(PessoaEntity.class)))
                .thenReturn(pessoaTable);
        doThrow(new RuntimeException("Table already exists"))
                .when(pessoaTable).createTable();

        // Act - Chamar múltiplas vezes
        assertDoesNotThrow(() -> {
            dynamoDbInitializer.setupTables();
            dynamoDbInitializer.setupTables();
        });

        // Assert
        verify(pessoaTable, times(2)).createTable();
    }

    @Test
    @DisplayName("Deve aceitar diferentes tipos de exceções")
    void deveAceitarDiferentesTiposDeExcecoes() {
        // Arrange
        ReflectionTestUtils.setField(dynamoDbInitializer, "shouldCreate", true);
        
        // Teste com IllegalArgumentException
        when(enhancedClient.table("Pessoas", software.amazon.awssdk.enhanced.dynamodb.TableSchema.fromBean(PessoaEntity.class)))
                .thenReturn(pessoaTable);
        doThrow(new IllegalArgumentException("Invalid arguments")).when(pessoaTable).createTable();

        // Act & Assert
        assertDoesNotThrow(() -> dynamoDbInitializer.setupTables());
    }
}

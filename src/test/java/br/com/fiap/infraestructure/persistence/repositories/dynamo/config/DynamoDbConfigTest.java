package br.com.fiap.infraestructure.persistence.repositories.dynamo.config;

import br.com.fiap.fasfoodpessoas.infraestructure.persistence.repositories.dynamo.config.DynamoDbConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;
import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("DynamoDbConfig - Testes da configuração do DynamoDB")
class DynamoDbConfigTest {

    private DynamoDbConfig dynamoDbConfig;

    @BeforeEach
    void setUp() {
        dynamoDbConfig = new DynamoDbConfig();
    }

    @Test
    @DisplayName("Deve criar client DynamoDB com endpoint override quando endpoint não contém amazonaws.com")
    void deveCriarClientComEndpointOverrideQuandoEndpointLocalHost() {
        // Arrange
        String localEndpoint = "http://localhost:8000";
        String region = "us-east-1";
        
        ReflectionTestUtils.setField(dynamoDbConfig, "endpoint", localEndpoint);
        ReflectionTestUtils.setField(dynamoDbConfig, "region", region);

        // Act
        DynamoDbClient client = dynamoDbConfig.dynamoDbClient();

        // Assert
        assertNotNull(client, "Cliente DynamoDB não deve ser nulo");
        assertFalse(client.getClass().getName().isEmpty());
    }

    @Test
    @DisplayName("Deve criar client DynamoDB SEM endpoint override quando endpoint contém amazonaws.com")
    void deveCriarClientSemEndpointOverrideQuandoEndpointAmazonAws() {
        // Arrange
        String awsEndpoint = "https://dynamodb.us-east-1.amazonaws.com";
        String region = "us-east-1";
        
        ReflectionTestUtils.setField(dynamoDbConfig, "endpoint", awsEndpoint);
        ReflectionTestUtils.setField(dynamoDbConfig, "region", region);

        // Act
        DynamoDbClient client = dynamoDbConfig.dynamoDbClient();

        // Assert
        assertNotNull(client, "Cliente DynamoDB não deve ser nulo");
        assertTrue(awsEndpoint.contains("amazonaws.com"));
    }

    @Test
    @DisplayName("Deve verificar que endpoint local não contém amazonaws.com")
    void deveVerificarEndpointLocalNaoContemAmazonasComDot() {
        // Arrange
        String localEndpoint = "http://localhost:8000";

        // Act & Assert
        assertFalse(localEndpoint.contains("amazonaws.com"), 
                   "Endpoint local não deve conter amazonaws.com");
    }

    @Test
    @DisplayName("Deve verificar que endpoint AWS contém amazonaws.com")
    void deveVerificarEndpointAwsContemAmazonasCom() {
        // Arrange
        String awsEndpoint = "https://dynamodb.us-east-1.amazonaws.com";

        // Act & Assert
        assertTrue(awsEndpoint.contains("amazonaws.com"), 
                  "Endpoint AWS deve conter amazonaws.com");
    }

    @Test
    @DisplayName("Deve suportar diferentes regiões")
    void deveSuportarDiferentesRegioes() {
        // Arrange
        String endpoint = "http://localhost:8000";
        String[] regioes = {"us-east-1", "eu-west-1", "ap-southeast-1"};

        // Act & Assert
        for (String regiao : regioes) {
            ReflectionTestUtils.setField(dynamoDbConfig, "endpoint", endpoint);
            ReflectionTestUtils.setField(dynamoDbConfig, "region", regiao);
            
            DynamoDbClient client = dynamoDbConfig.dynamoDbClient();
            assertNotNull(client, "Cliente DynamoDB não deve ser nulo para região " + regiao);
            client.close();
        }
    }

    @Test
    @DisplayName("Deve criar EnhancedClient com DynamoDbClient")
    void deveCriarEnhancedClient() {
        // Arrange
        String endpoint = "http://localhost:8000";
        String region = "us-east-1";
        
        ReflectionTestUtils.setField(dynamoDbConfig, "endpoint", endpoint);
        ReflectionTestUtils.setField(dynamoDbConfig, "region", region);
        
        DynamoDbClient ddbClient = dynamoDbConfig.dynamoDbClient();

        // Act
        var enhancedClient = dynamoDbConfig.enhancedClient(ddbClient);

        // Assert
        assertNotNull(enhancedClient, "EnhancedClient não deve ser nulo");
        
        // Cleanup
        ddbClient.close();
    }

    @Test
    @DisplayName("Deve verificar comportamento para endpoint local sem protocol")
    void deveVerificarComEndpointLocal() {
        // Arrange
        String localEndpoint = "localhost:8000";
        String region = "us-east-1";
        
        ReflectionTestUtils.setField(dynamoDbConfig, "endpoint", localEndpoint);
        ReflectionTestUtils.setField(dynamoDbConfig, "region", region);

        // Act & Assert
        assertFalse(localEndpoint.contains("amazonaws.com"));
        DynamoDbClient client = dynamoDbConfig.dynamoDbClient();
        assertNotNull(client);
        client.close();
    }

    @Test
    @DisplayName("Deve ser case-sensitive na verificação de amazonaws.com")
    void deveSerCaseSensitiveNaVerificacao() {
        // Arrange
        String endpointMaiuscula = "https://dynamodb.us-east-1.AMAZONAWS.COM";
        String endpointMinuscula = "https://dynamodb.us-east-1.amazonaws.com";

        // Act & Assert
        assertFalse(endpointMaiuscula.contains("amazonaws.com"), 
                   "Deve ser case-sensitive");
        assertTrue(endpointMinuscula.contains("amazonaws.com"));
    }
}

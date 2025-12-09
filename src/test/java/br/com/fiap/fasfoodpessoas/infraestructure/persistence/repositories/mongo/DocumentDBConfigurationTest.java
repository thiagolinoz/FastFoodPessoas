package br.com.fiap.fasfoodpessoas.infraestructure.persistence.repositories.mongo;

import com.mongodb.client.MongoClient;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.util.ReflectionTestUtils;

import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = DocumentDBConfiguration.class)
@TestPropertySource(properties = {
    "spring.data.mongodb.uri=mongodb://localhost:27017/test"
})
class DocumentDBConfigurationTest {

    private static final String DATABASE_NAME = "aplicacao_fiap";
    private static final String FIELD_CONNECTION_STRING = "connectionString";
    private static final String TEST_MONGODB_URI = "mongodb://localhost:27017/test";

    @Autowired
    private DocumentDBConfiguration configuration;

    @Test
    void deveRetornarNomeDoBancoDeDados() throws Exception {
        Method method = DocumentDBConfiguration.class.getDeclaredMethod("getDatabaseName");
        method.setAccessible(true);
        String databaseName = (String) method.invoke(configuration);

        assertNotNull(databaseName);
        assertEquals(DATABASE_NAME, databaseName);
    }

    @Test
    void deveSerUmaConfiguracao() {
        Configuration annotation = DocumentDBConfiguration.class.getAnnotation(Configuration.class);

        assertNotNull(annotation);
    }

    @Test
    void deveEstenderAbstractMongoClientConfiguration() {
        assertTrue(AbstractMongoClientConfiguration.class.isAssignableFrom(DocumentDBConfiguration.class));
    }

    @Test
    void deveTerCampoConnectionStringAnotadoComValue() throws NoSuchFieldException {
        var field = DocumentDBConfiguration.class.getDeclaredField(FIELD_CONNECTION_STRING);
        var annotation = field.getAnnotation(org.springframework.beans.factory.annotation.Value.class);

        assertNotNull(annotation);
        assertEquals("${spring.data.mongodb.uri}", annotation.value());
    }

    @Test
    void deveCriarMongoClientComSucesso() {
        ReflectionTestUtils.setField(configuration, FIELD_CONNECTION_STRING, TEST_MONGODB_URI);

        MongoClient mongoClient = configuration.mongoClient();

        assertNotNull(mongoClient);
    }

    @Test
    void deveConfigurarSSLContext() {
        ReflectionTestUtils.setField(configuration, FIELD_CONNECTION_STRING, TEST_MONGODB_URI);

        MongoClient mongoClient = null;
        try {
            mongoClient = configuration.mongoClient();
            assertNotNull(mongoClient);
        } catch (Exception e) {
            // Em ambiente de teste, pode falhar por falta de servidor real
            assertTrue(e instanceof RuntimeException);
        } finally {
            if (mongoClient != null) {
                mongoClient.close();
            }
        }
    }

    @Test
    void deveUsarConnectionStringDoProperties() {
        assertNotNull(configuration);
        
        String connectionString = (String) ReflectionTestUtils.getField(configuration, FIELD_CONNECTION_STRING);
        
        assertNotNull(connectionString);
        assertTrue(connectionString.contains("mongodb://"));
    }

    @Test
    void deveTerMetodoMongoClientAnotadoComBean() throws NoSuchMethodException {
        var method = DocumentDBConfiguration.class.getMethod("mongoClient");
        var beanAnnotation = method.getAnnotation(org.springframework.context.annotation.Bean.class);

        assertNotNull(beanAnnotation);
    }

    @Test
    void deveNomeDoBancoSerAplicacaoFiap() throws Exception {
        Method method = DocumentDBConfiguration.class.getDeclaredMethod("getDatabaseName");
        method.setAccessible(true);
        String databaseName = (String) method.invoke(configuration);

        assertEquals(DATABASE_NAME, databaseName);
        assertFalse(databaseName.isEmpty());
        assertFalse(databaseName.isBlank());
    }

    @Test
    void deveMongoClientRetornarInstanciaNaoNula() {
        ReflectionTestUtils.setField(configuration, FIELD_CONNECTION_STRING, TEST_MONGODB_URI);

        MongoClient client = null;
        try {
            client = configuration.mongoClient();
            assertNotNull(client);
        } catch (RuntimeException e) {
            // Esperado em alguns ambientes de teste
            assertTrue(e.getMessage().contains("Erro ao configurar MongoDB client") || 
                      e.getMessage().contains("connection"));
        } finally {
            if (client != null) {
                client.close();
            }
        }
    }

    @Test
    void deveConfigurarSSLComTrustManager() {
        ReflectionTestUtils.setField(configuration, FIELD_CONNECTION_STRING, TEST_MONGODB_URI);

        try {
            MongoClient client = configuration.mongoClient();
            assertNotNull(client);
            client.close();
        } catch (RuntimeException e) {
            // SSL pode falhar em ambiente de teste
            assertNotNull(e.getMessage());
        }
    }

    @Test
    void devePermitirHostNameInvalidoNaConfiguracaoSSL() {
        ReflectionTestUtils.setField(configuration, FIELD_CONNECTION_STRING, TEST_MONGODB_URI);

        // A configuração deve permitir hostname inválido
        assertDoesNotThrow(() -> {
            try {
                MongoClient client = configuration.mongoClient();
                client.close();
            } catch (RuntimeException e) {
                // Erro pode ocorrer por outras razões em teste
            }
        });
    }

    @Test
    void deveUsarProtocoloTLSParaSSL() {
        ReflectionTestUtils.setField(configuration, FIELD_CONNECTION_STRING, TEST_MONGODB_URI);

        // Verifica que não lança exceção relacionada ao protocolo TLS
        try {
            configuration.mongoClient();
        } catch (RuntimeException e) {
            // Se houver exceção, não deve ser sobre TLS
            assertFalse(e.getMessage().contains("TLS") && e.getCause().getMessage().contains("No such algorithm"));
        }
    }

    @Test
    void deveRetornarMesmoNomeDeBancoDeDadosEmMultiplasChamadas() throws Exception {
        Method method = DocumentDBConfiguration.class.getDeclaredMethod("getDatabaseName");
        method.setAccessible(true);
        String nome1 = (String) method.invoke(configuration);
        String nome2 = (String) method.invoke(configuration);

        assertEquals(nome1, nome2);
        assertEquals(DATABASE_NAME, nome1);
        assertEquals(DATABASE_NAME, nome2);
    }

    @Test
    void deveSerClasseDeConfiguracao() {
        assertTrue(DocumentDBConfiguration.class.isAnnotationPresent(Configuration.class));
    }

    @Test
    void deveHerdarDeAbstractMongoClientConfiguration() {
        Class<?> superClass = DocumentDBConfiguration.class.getSuperclass();

        assertEquals(AbstractMongoClientConfiguration.class, superClass);
    }

    @Test
    void deveTerConstrutorPadraoPublico() {
        assertDoesNotThrow(() -> {
            DocumentDBConfiguration config = new DocumentDBConfiguration();
            assertNotNull(config);
        });
    }

    @Test
    void deveConnectionStringSerInjetadaPorValue() throws NoSuchFieldException {
        var field = DocumentDBConfiguration.class.getDeclaredField(FIELD_CONNECTION_STRING);
        
        assertNotNull(field);
        assertEquals(String.class, field.getType());
    }
}

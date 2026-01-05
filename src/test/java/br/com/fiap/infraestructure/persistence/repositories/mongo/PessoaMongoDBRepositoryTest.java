package br.com.fiap.infraestructure.persistence.repositories.mongo;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * NOTA: Este teste foi desabilitado porque o projeto agora usa DynamoDB em vez de MongoDB.
 * Os testes de repositório estão em PessoaRepositoryTest.
 */
class PessoaMongoDBRepositoryTest {

    @Test
    void testeDesabilitado() {
        // Este teste existe apenas para evitar erro de "classe sem testes"
        assertTrue(true, "Repositório MongoDB não é mais usado - projeto migrou para DynamoDB");
    }
}

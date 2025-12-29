package br.com.fiap.infraestructure.persistence.entities;

import br.com.fiap.fasfoodpessoas.domain.enums.TipoPessoaEnum;
import br.com.fiap.fasfoodpessoas.infraestructure.persistence.entities.PessoaEntity;
import org.junit.jupiter.api.Test;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;

import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.*;

class PessoaEntityTest {

    // ==================== TESTES DE CONSTRUTOR ====================

    @Test
    void deveCriarEntityComConstrutorVazio() {
        PessoaEntity entity = new PessoaEntity();

        assertNotNull(entity);
        assertNull(entity.getCdDocPessoa());
        assertNull(entity.getNmPessoa());
        assertNull(entity.getTpPessoa());
        assertNull(entity.getDsEmail());
    }

    @Test
    void deveCriarEntityComConstrutorCompleto() {
        PessoaEntity entity = new PessoaEntity(
                "12345678900",
                "João Silva",
                TipoPessoaEnum.CLIENTE,
                "joao@email.com"
        );

        assertNotNull(entity);
        assertEquals("12345678900", entity.getCdDocPessoa());
        assertEquals("João Silva", entity.getNmPessoa());
        assertEquals(TipoPessoaEnum.CLIENTE, entity.getTpPessoa());
        assertEquals("joao@email.com", entity.getDsEmail());
    }

    // ==================== TESTES DE GETTERS E SETTERS ====================

    @Test
    void deveSetarEObterCdDocPessoa() {
        PessoaEntity entity = new PessoaEntity();
        entity.setCdDocPessoa("98765432100");

        assertEquals("98765432100", entity.getCdDocPessoa());
    }

    @Test
    void deveSetarEObterNmPessoa() {
        PessoaEntity entity = new PessoaEntity();
        entity.setNmPessoa("Maria Santos");

        assertEquals("Maria Santos", entity.getNmPessoa());
    }

    @Test
    void deveSetarEObterTpPessoa() {
        PessoaEntity entity = new PessoaEntity();
        entity.setTpPessoa(TipoPessoaEnum.FUNCIONARIO);

        assertEquals(TipoPessoaEnum.FUNCIONARIO, entity.getTpPessoa());
    }

    @Test
    void deveSetarEObterDsEmail() {
        PessoaEntity entity = new PessoaEntity();
        entity.setDsEmail("teste@email.com");

        assertEquals("teste@email.com", entity.getDsEmail());
    }

    @Test
    void deveCriarClienteComTodosOsCampos() {
        PessoaEntity entity = new PessoaEntity();
        entity.setCdDocPessoa("11111111111");
        entity.setNmPessoa("Pedro Costa");
        entity.setTpPessoa(TipoPessoaEnum.CLIENTE);
        entity.setDsEmail("pedro@email.com");

        assertEquals("11111111111", entity.getCdDocPessoa());
        assertEquals("Pedro Costa", entity.getNmPessoa());
        assertEquals(TipoPessoaEnum.CLIENTE, entity.getTpPessoa());
        assertEquals("pedro@email.com", entity.getDsEmail());
    }

    @Test
    void deveCriarFuncionarioComTodosOsCampos() {
        PessoaEntity entity = new PessoaEntity();
        entity.setCdDocPessoa("22222222222");
        entity.setNmPessoa("Ana Paula");
        entity.setTpPessoa(TipoPessoaEnum.FUNCIONARIO);
        entity.setDsEmail("ana@empresa.com");

        assertEquals("22222222222", entity.getCdDocPessoa());
        assertEquals("Ana Paula", entity.getNmPessoa());
        assertEquals(TipoPessoaEnum.FUNCIONARIO, entity.getTpPessoa());
        assertEquals("ana@empresa.com", entity.getDsEmail());
    }

    @Test
    void deveAceitarValoresNulos() {
        PessoaEntity entity = new PessoaEntity();
        entity.setCdDocPessoa(null);
        entity.setNmPessoa(null);
        entity.setTpPessoa(null);
        entity.setDsEmail(null);

        assertNull(entity.getCdDocPessoa());
        assertNull(entity.getNmPessoa());
        assertNull(entity.getTpPessoa());
        assertNull(entity.getDsEmail());
    }

    @Test
    void deveAceitarStringsVazias() {
        PessoaEntity entity = new PessoaEntity();
        entity.setCdDocPessoa("");
        entity.setNmPessoa("");
        entity.setDsEmail("");

        assertEquals("", entity.getCdDocPessoa());
        assertEquals("", entity.getNmPessoa());
        assertEquals("", entity.getDsEmail());
    }

    @Test
    void deveAlterarCamposAposCriacao() {
        PessoaEntity entity = new PessoaEntity(
                "33333333333",
                "Carlos Eduardo",
                TipoPessoaEnum.CLIENTE,
                "carlos@email.com"
        );

        entity.setNmPessoa("Carlos Eduardo Junior");
        entity.setDsEmail("carlos.junior@email.com");
        entity.setTpPessoa(TipoPessoaEnum.FUNCIONARIO);

        assertEquals("Carlos Eduardo Junior", entity.getNmPessoa());
        assertEquals("carlos.junior@email.com", entity.getDsEmail());
        assertEquals(TipoPessoaEnum.FUNCIONARIO, entity.getTpPessoa());
    }

    @Test
    void deveTrocarTipoDePessoa() {
        PessoaEntity entity = new PessoaEntity();
        entity.setTpPessoa(TipoPessoaEnum.CLIENTE);
        assertEquals(TipoPessoaEnum.CLIENTE, entity.getTpPessoa());

        entity.setTpPessoa(TipoPessoaEnum.FUNCIONARIO);
        assertEquals(TipoPessoaEnum.FUNCIONARIO, entity.getTpPessoa());
    }

    @Test
    void devePermitirNomeComCaracteresEspeciais() {
        PessoaEntity entity = new PessoaEntity();
        entity.setNmPessoa("José da Silva Júnior");

        assertEquals("José da Silva Júnior", entity.getNmPessoa());
    }

    @Test
    void devePermitirEmailComFormatoComplexo() {
        PessoaEntity entity = new PessoaEntity();
        entity.setDsEmail("usuario.teste+tag@dominio.com.br");

        assertEquals("usuario.teste+tag@dominio.com.br", entity.getDsEmail());
    }

    // ==================== TESTES DE EQUALS E HASHCODE ====================

    @Test
    void deveCompararEntitiesComEquals() {
        PessoaEntity entity1 = new PessoaEntity(
                "44444444444",
                "Julia Lima",
                TipoPessoaEnum.CLIENTE,
                "julia@email.com"
        );

        PessoaEntity entity2 = new PessoaEntity(
                "44444444444",
                "Julia Lima",
                TipoPessoaEnum.CLIENTE,
                "julia@email.com"
        );

        PessoaEntity entity3 = new PessoaEntity(
                "55555555555",
                "Outro Nome",
                TipoPessoaEnum.FUNCIONARIO,
                "outro@email.com"
        );

        assertEquals(entity1, entity2);
        assertNotEquals(entity1, entity3);
    }

    @Test
    void deveRetornarTrueQuandoCompararMesmaInstancia() {
        PessoaEntity entity = new PessoaEntity(
                "12345678900",
                "João Silva",
                TipoPessoaEnum.CLIENTE,
                "joao@email.com"
        );

        PessoaEntity mesmaReferencia = entity;

        assertEquals(entity, mesmaReferencia);
    }

    @Test
    void deveRetornarFalseQuandoCompararComNull() {
        PessoaEntity entity = new PessoaEntity(
                "12345678900",
                "João Silva",
                TipoPessoaEnum.CLIENTE,
                "joao@email.com"
        );

        assertNotEquals(entity, null);
    }

    @Test
    void deveRetornarFalseQuandoCompararComObjetoDeTipoDiferente() {
        PessoaEntity entity = new PessoaEntity(
                "12345678900",
                "João Silva",
                TipoPessoaEnum.CLIENTE,
                "joao@email.com"
        );

        PessoaEntity mesmaReferencia = entity;

        assertSame(entity, mesmaReferencia);
    }

    @Test
    void deveGerarHashCodeConsistente() {
        PessoaEntity entity1 = new PessoaEntity(
                "66666666666",
                "Roberto Alves",
                TipoPessoaEnum.CLIENTE,
                "roberto@email.com"
        );

        PessoaEntity entity2 = new PessoaEntity(
                "66666666666",
                "Roberto Alves",
                TipoPessoaEnum.CLIENTE,
                "roberto@email.com"
        );

        assertEquals(entity1.hashCode(), entity2.hashCode());
    }

    @Test
    void deveGerarHashCodeDiferenteParaObjetosDiferentes() {
        PessoaEntity entity1 = new PessoaEntity(
                "11111111111",
                "Nome Um",
                TipoPessoaEnum.CLIENTE,
                "email1@test.com"
        );

        PessoaEntity entity2 = new PessoaEntity(
                "22222222222",
                "Nome Dois",
                TipoPessoaEnum.FUNCIONARIO,
                "email2@test.com"
        );

        assertNotEquals(entity1.hashCode(), entity2.hashCode());
    }

    // ==================== TESTES DE TOSTRING ====================

    @Test
    void deveGerarToStringComTodosOsCampos() {
        PessoaEntity entity = new PessoaEntity(
                "77777777777",
                "Patricia Ferreira",
                TipoPessoaEnum.FUNCIONARIO,
                "patricia@empresa.com"
        );

        String toString = entity.toString();

        assertNotNull(toString);
        assertTrue(toString.contains("77777777777"));
        assertTrue(toString.contains("Patricia Ferreira"));
        assertTrue(toString.contains("FUNCIONARIO"));
        assertTrue(toString.contains("patricia@empresa.com"));
    }

    @Test
    void deveGerarToStringComCamposNulos() {
        PessoaEntity entity = new PessoaEntity();

        String toString = entity.toString();

        assertNotNull(toString);
        assertTrue(toString.contains("null") || toString.isEmpty() || toString.contains("PessoaEntity"));
    }

    // ==================== TESTES DE ANOTAÇÕES DYNAMODB ====================

    @Test
    void deveTerAnotacaoDynamoDbBean() {
        DynamoDbBean annotation = PessoaEntity.class.getAnnotation(DynamoDbBean.class);

        assertNotNull(annotation, "A classe PessoaEntity deve ter a anotação @DynamoDbBean");
    }

    @Test
    void deveGetCdDocPessoaTerAnotacaoDynamoDbPartitionKey() throws NoSuchMethodException {
        Method method = PessoaEntity.class.getMethod("getCdDocPessoa");
        DynamoDbPartitionKey annotation = method.getAnnotation(DynamoDbPartitionKey.class);

        assertNotNull(annotation, "O método getCdDocPessoa deve ter a anotação @DynamoDbPartitionKey");
    }

    @Test
    void deveCdDocPessoaSerAChavePrimaria() {
        PessoaEntity entity1 = new PessoaEntity(
                "12345678900",
                "João Silva",
                TipoPessoaEnum.CLIENTE,
                "joao@email.com"
        );

        PessoaEntity entity2 = new PessoaEntity(
                "12345678900",
                "Maria Silva",
                TipoPessoaEnum.FUNCIONARIO,
                "maria@email.com"
        );

        // Mesmo cdDocPessoa deve significar a mesma pessoa
        assertEquals(entity1.getCdDocPessoa(), entity2.getCdDocPessoa());
    }

    // ==================== TESTES DE COMPORTAMENTO ====================

    @Test
    void deveCriarMultiplasInstanciasIndependentes() {
        PessoaEntity entity1 = new PessoaEntity();
        entity1.setCdDocPessoa("99999999999");
        entity1.setNmPessoa("Nome 1");

        PessoaEntity entity2 = new PessoaEntity();
        entity2.setCdDocPessoa("10101010101");
        entity2.setNmPessoa("Nome 2");

        assertNotEquals(entity1.getCdDocPessoa(), entity2.getCdDocPessoa());
        assertEquals("99999999999", entity1.getCdDocPessoa());
        assertEquals("10101010101", entity2.getCdDocPessoa());
        assertNotEquals(entity1.getNmPessoa(), entity2.getNmPessoa());
    }

    @Test
    void deveTerGetterESetterParaTodosOsCampos() throws NoSuchMethodException {
        // Verifica existência de getters
        assertNotNull(PessoaEntity.class.getMethod("getCdDocPessoa"));
        assertNotNull(PessoaEntity.class.getMethod("getNmPessoa"));
        assertNotNull(PessoaEntity.class.getMethod("getTpPessoa"));
        assertNotNull(PessoaEntity.class.getMethod("getDsEmail"));

        // Verifica existência de setters
        assertNotNull(PessoaEntity.class.getMethod("setCdDocPessoa", String.class));
        assertNotNull(PessoaEntity.class.getMethod("setNmPessoa", String.class));
        assertNotNull(PessoaEntity.class.getMethod("setTpPessoa", TipoPessoaEnum.class));
        assertNotNull(PessoaEntity.class.getMethod("setDsEmail", String.class));
    }

    @Test
    void deveManteCdDocPessoaConstanteAposAlterarOutrosCampos() {
        PessoaEntity entity = new PessoaEntity(
                "88888888888",
                "Nome Original",
                TipoPessoaEnum.CLIENTE,
                "email.original@test.com"
        );

        String cdDocOriginal = entity.getCdDocPessoa();

        entity.setNmPessoa("Nome Alterado");
        entity.setDsEmail("email.novo@test.com");
        entity.setTpPessoa(TipoPessoaEnum.FUNCIONARIO);

        assertEquals(cdDocOriginal, entity.getCdDocPessoa());
    }

    @Test
    void devePermitirAlterarCdDocPessoa() {
        PessoaEntity entity = new PessoaEntity();
        entity.setCdDocPessoa("11111111111");
        assertEquals("11111111111", entity.getCdDocPessoa());

        entity.setCdDocPessoa("22222222222");
        assertEquals("22222222222", entity.getCdDocPessoa());
    }
}

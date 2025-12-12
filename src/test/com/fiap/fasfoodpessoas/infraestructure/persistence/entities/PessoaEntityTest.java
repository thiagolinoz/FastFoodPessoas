package com.fiap.fasfoodpessoas.infraestructure.persistence.entities;

import br.com.fiap.fasfoodpessoas.domain.enums.TipoPessoaEnum;
import br.com.fiap.fasfoodpessoas.infraestructure.persistence.entities.PessoaEntity;
import org.junit.jupiter.api.Test;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;

class PessoaEntityTest {

    @Test
    void deveCriarEntityComConstrutorVazio() {
        PessoaEntity entity = new PessoaEntity();

        assertNotNull(entity);
        assertNull(entity.getId());
        assertNull(entity.getCdDocPessoa());
        assertNull(entity.getNmPessoa());
        assertNull(entity.getTpPessoa());
        assertNull(entity.getDsEmail());
    }

    @Test
    void deveCriarEntityComConstrutorCompleto() {
        PessoaEntity entity = new PessoaEntity(
                "123",
                "12345678900",
                "João Silva",
                TipoPessoaEnum.CLIENTE,
                "joao@email.com"
        );

        assertNotNull(entity);
        assertEquals("123", entity.getId());
        assertEquals("12345678900", entity.getCdDocPessoa());
        assertEquals("João Silva", entity.getNmPessoa());
        assertEquals(TipoPessoaEnum.CLIENTE, entity.getTpPessoa());
        assertEquals("joao@email.com", entity.getDsEmail());
    }

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
        entity.setId("456");
        entity.setCdDocPessoa("11111111111");
        entity.setNmPessoa("Pedro Costa");
        entity.setTpPessoa(TipoPessoaEnum.CLIENTE);
        entity.setDsEmail("pedro@email.com");

        assertEquals("456", entity.getId());
        assertEquals("11111111111", entity.getCdDocPessoa());
        assertEquals("Pedro Costa", entity.getNmPessoa());
        assertEquals(TipoPessoaEnum.CLIENTE, entity.getTpPessoa());
        assertEquals("pedro@email.com", entity.getDsEmail());
    }

    @Test
    void deveCriarFuncionarioComTodosOsCampos() {
        PessoaEntity entity = new PessoaEntity();
        entity.setId("789");
        entity.setCdDocPessoa("22222222222");
        entity.setNmPessoa("Ana Paula");
        entity.setTpPessoa(TipoPessoaEnum.FUNCIONARIO);
        entity.setDsEmail("ana@empresa.com");

        assertEquals("789", entity.getId());
        assertEquals("22222222222", entity.getCdDocPessoa());
        assertEquals("Ana Paula", entity.getNmPessoa());
        assertEquals(TipoPessoaEnum.FUNCIONARIO, entity.getTpPessoa());
        assertEquals("ana@empresa.com", entity.getDsEmail());
    }

    @Test
    void deveAceitarValoresNulos() {
        PessoaEntity entity = new PessoaEntity();
        entity.setId(null);
        entity.setCdDocPessoa(null);
        entity.setNmPessoa(null);
        entity.setTpPessoa(null);
        entity.setDsEmail(null);

        assertNull(entity.getId());
        assertNull(entity.getCdDocPessoa());
        assertNull(entity.getNmPessoa());
        assertNull(entity.getTpPessoa());
        assertNull(entity.getDsEmail());
    }

    @Test
    void deveAceitarStringsVazias() {
        PessoaEntity entity = new PessoaEntity();
        entity.setId("");
        entity.setCdDocPessoa("");
        entity.setNmPessoa("");
        entity.setDsEmail("");

        assertEquals("", entity.getId());
        assertEquals("", entity.getCdDocPessoa());
        assertEquals("", entity.getNmPessoa());
        assertEquals("", entity.getDsEmail());
    }

    @Test
    void deveAlterarCamposAposCriacao() {
        PessoaEntity entity = new PessoaEntity(
                "100",
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
    void deveCompararEntitiesComEquals() {
        PessoaEntity entity1 = new PessoaEntity(
                "200",
                "44444444444",
                "Julia Lima",
                TipoPessoaEnum.CLIENTE,
                "julia@email.com"
        );

        PessoaEntity entity2 = new PessoaEntity(
                "200",
                "44444444444",
                "Julia Lima",
                TipoPessoaEnum.CLIENTE,
                "julia@email.com"
        );

        PessoaEntity entity3 = new PessoaEntity(
                "201",
                "55555555555",
                "Outro Nome",
                TipoPessoaEnum.FUNCIONARIO,
                "outro@email.com"
        );

        assertEquals(entity1, entity2);
        assertNotEquals(entity1, entity3);
    }

    @Test
    void deveGerarHashCodeConsistente() {
        PessoaEntity entity1 = new PessoaEntity(
                "300",
                "66666666666",
                "Roberto Alves",
                TipoPessoaEnum.CLIENTE,
                "roberto@email.com"
        );

        PessoaEntity entity2 = new PessoaEntity(
                "300",
                "66666666666",
                "Roberto Alves",
                TipoPessoaEnum.CLIENTE,
                "roberto@email.com"
        );

        assertEquals(entity1.hashCode(), entity2.hashCode());
    }

    @Test
    void deveGerarToStringComTodosOsCampos() {
        PessoaEntity entity = new PessoaEntity(
                "400",
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
    void deveTerAnotacaoDocument() {
        Document annotation = PessoaEntity.class.getAnnotation(Document.class);

        assertNotNull(annotation);
        assertEquals("pessoas", annotation.collection());
    }

    @Test
    void deveTerCampoIdAnotadoComId() throws NoSuchFieldException {
        Field idField = PessoaEntity.class.getDeclaredField("id");
        Id annotation = idField.getAnnotation(Id.class);

        assertNotNull(annotation);
    }

    @Test
    void devePermitirSetarId() {
        PessoaEntity entity = new PessoaEntity();
        entity.setId("id-teste-123");

        assertEquals("id-teste-123", entity.getId());
    }

    @Test
    void deveManterIdAposAlterarOutrosCampos() {
        PessoaEntity entity = new PessoaEntity();
        entity.setId("id-fixo");
        entity.setCdDocPessoa("88888888888");
        entity.setNmPessoa("Nome Teste");

        assertEquals("id-fixo", entity.getId());
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
    void deveCriarMultiplasInstanciasIndependentes() {
        PessoaEntity entity1 = new PessoaEntity();
        entity1.setCdDocPessoa("99999999999");

        PessoaEntity entity2 = new PessoaEntity();
        entity2.setCdDocPessoa("10101010101");

        assertNotEquals(entity1.getCdDocPessoa(), entity2.getCdDocPessoa());
        assertEquals("99999999999", entity1.getCdDocPessoa());
        assertEquals("10101010101", entity2.getCdDocPessoa());
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

    @Test
    void deveSerMapeadaParaColecaoPessoas() {
        Document documentAnnotation = PessoaEntity.class.getAnnotation(Document.class);

        assertNotNull(documentAnnotation);
        assertEquals("pessoas", documentAnnotation.collection());
    }

    @Test
    void deveTerGetterESetterParaTodosOsCampos() throws NoSuchMethodException {
        assertNotNull(PessoaEntity.class.getMethod("getId"));
        assertNotNull(PessoaEntity.class.getMethod("getCdDocPessoa"));
        assertNotNull(PessoaEntity.class.getMethod("getNmPessoa"));
        assertNotNull(PessoaEntity.class.getMethod("getTpPessoa"));
        assertNotNull(PessoaEntity.class.getMethod("getDsEmail"));
        
        assertNotNull(PessoaEntity.class.getMethod("setId", String.class));
        assertNotNull(PessoaEntity.class.getMethod("setCdDocPessoa", String.class));
        assertNotNull(PessoaEntity.class.getMethod("setNmPessoa", String.class));
        assertNotNull(PessoaEntity.class.getMethod("setTpPessoa", TipoPessoaEnum.class));
        assertNotNull(PessoaEntity.class.getMethod("setDsEmail", String.class));
    }
}

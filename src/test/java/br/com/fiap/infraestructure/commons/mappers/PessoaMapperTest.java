package br.com.fiap.infraestructure.commons.mappers;

import br.com.fiap.fasfoodpessoas.domain.enums.TipoPessoaEnum;
import br.com.fiap.fasfoodpessoas.domain.models.PessoaModel;
import br.com.fiap.fasfoodpessoas.infraestructure.commons.mappers.PessoaMapper;
import br.com.fiap.fasfoodpessoas.infraestructure.persistence.entities.PessoaEntity;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PessoaMapperTest {

    @Test
    void deveConverterEntityParaModelComSucesso() {
        PessoaEntity entity = new PessoaEntity();
        entity.setCdDocPessoa("12345678900");
        entity.setNmPessoa("João Silva");
        entity.setTpPessoa(TipoPessoaEnum.CLIENTE);
        entity.setDsEmail("joao@email.com");

        PessoaModel model = PessoaMapper.toModel(entity);

        assertNotNull(model);
        assertEquals("12345678900", model.getCdDocPessoa());
        assertEquals("João Silva", model.getNmPessoa());
        assertEquals(TipoPessoaEnum.CLIENTE, model.getTpPessoa());
        assertEquals("joao@email.com", model.getDsEmail());
    }

    @Test
    void deveConverterModelParaEntityComSucesso() {
        PessoaModel model = new PessoaModel.Builder()
                .setCdDocPessoa("98765432100")
                .setNmPessoa("Maria Santos")
                .setTpPessoa(TipoPessoaEnum.FUNCIONARIO)
                .setDsEmail("maria@empresa.com")
                .build();

        PessoaEntity entity = PessoaMapper.toEntity(model);

        assertNotNull(entity);
        assertEquals("98765432100", entity.getCdDocPessoa());
        assertEquals("Maria Santos", entity.getNmPessoa());
        assertEquals(TipoPessoaEnum.FUNCIONARIO, entity.getTpPessoa());
        assertEquals("maria@empresa.com", entity.getDsEmail());
    }

    @Test
    void deveConverterClienteEntityParaModel() {
        PessoaEntity entity = new PessoaEntity();
        entity.setCdDocPessoa("11111111111");
        entity.setNmPessoa("Pedro Costa");
        entity.setTpPessoa(TipoPessoaEnum.CLIENTE);
        entity.setDsEmail("pedro@email.com");

        PessoaModel model = PessoaMapper.toModel(entity);

        assertNotNull(model);
        assertEquals(TipoPessoaEnum.CLIENTE, model.getTpPessoa());
        assertEquals("Pedro Costa", model.getNmPessoa());
    }

    @Test
    void deveConverterFuncionarioModelParaEntity() {
        PessoaModel model = new PessoaModel.Builder()
                .setCdDocPessoa("22222222222")
                .setNmPessoa("Ana Paula")
                .setTpPessoa(TipoPessoaEnum.FUNCIONARIO)
                .setDsEmail("ana@empresa.com")
                .build();

        PessoaEntity entity = PessoaMapper.toEntity(model);

        assertNotNull(entity);
        assertEquals(TipoPessoaEnum.FUNCIONARIO, entity.getTpPessoa());
        assertEquals("Ana Paula", entity.getNmPessoa());
    }

    @Test
    void deveManterTodosOsCamposAoConverterEntityParaModel() {
        PessoaEntity entity = new PessoaEntity();
        entity.setCdDocPessoa("33333333333");
        entity.setNmPessoa("Carlos Eduardo");
        entity.setTpPessoa(TipoPessoaEnum.CLIENTE);
        entity.setDsEmail("carlos@email.com");

        PessoaModel model = PessoaMapper.toModel(entity);

        assertEquals(entity.getCdDocPessoa(), model.getCdDocPessoa());
        assertEquals(entity.getNmPessoa(), model.getNmPessoa());
        assertEquals(entity.getTpPessoa(), model.getTpPessoa());
        assertEquals(entity.getDsEmail(), model.getDsEmail());
    }

    @Test
    void deveManterTodosOsCamposAoConverterModelParaEntity() {
        PessoaModel model = new PessoaModel.Builder()
                .setCdDocPessoa("44444444444")
                .setNmPessoa("Julia Lima")
                .setTpPessoa(TipoPessoaEnum.FUNCIONARIO)
                .setDsEmail("julia@empresa.com")
                .build();

        PessoaEntity entity = PessoaMapper.toEntity(model);

        assertEquals(model.getCdDocPessoa(), entity.getCdDocPessoa());
        assertEquals(model.getNmPessoa(), entity.getNmPessoa());
        assertEquals(model.getTpPessoa(), entity.getTpPessoa());
        assertEquals(model.getDsEmail(), entity.getDsEmail());
    }

    @Test
    void deveConverterEntityComCamposNulos() {
        PessoaEntity entity = new PessoaEntity();
        entity.setCdDocPessoa(null);
        entity.setNmPessoa(null);
        entity.setTpPessoa(null);
        entity.setDsEmail(null);

        PessoaModel model = PessoaMapper.toModel(entity);

        assertNotNull(model);
        assertNull(model.getCdDocPessoa());
        assertNull(model.getNmPessoa());
        assertNull(model.getTpPessoa());
        assertNull(model.getDsEmail());
    }

    @Test
    void deveConverterModelComCamposNulos() {
        PessoaModel model = new PessoaModel();
        model.setCdDocPessoa(null);
        model.setNmPessoa(null);
        model.setTpPessoa(null);
        model.setDsEmail(null);

        PessoaEntity entity = PessoaMapper.toEntity(model);

        assertNotNull(entity);
        assertNull(entity.getCdDocPessoa());
        assertNull(entity.getNmPessoa());
        assertNull(entity.getTpPessoa());
        assertNull(entity.getDsEmail());
    }

    @Test
    void deveConverterEntityComCamposVazios() {
        PessoaEntity entity = new PessoaEntity();
        entity.setCdDocPessoa("");
        entity.setNmPessoa("");
        entity.setTpPessoa(TipoPessoaEnum.CLIENTE);
        entity.setDsEmail("");

        PessoaModel model = PessoaMapper.toModel(entity);

        assertNotNull(model);
        assertEquals("", model.getCdDocPessoa());
        assertEquals("", model.getNmPessoa());
        assertEquals("", model.getDsEmail());
    }

    @Test
    void deveConverterModelComCamposVazios() {
        PessoaModel model = new PessoaModel.Builder()
                .setCdDocPessoa("")
                .setNmPessoa("")
                .setTpPessoa(TipoPessoaEnum.FUNCIONARIO)
                .setDsEmail("")
                .build();

        PessoaEntity entity = PessoaMapper.toEntity(model);

        assertNotNull(entity);
        assertEquals("", entity.getCdDocPessoa());
        assertEquals("", entity.getNmPessoa());
        assertEquals("", entity.getDsEmail());
    }

    @Test
    void deveRealizarConversaoIdempotente() {
        PessoaModel modelOriginal = new PessoaModel.Builder()
                .setCdDocPessoa("55555555555")
                .setNmPessoa("Roberto Alves")
                .setTpPessoa(TipoPessoaEnum.CLIENTE)
                .setDsEmail("roberto@email.com")
                .build();

        PessoaEntity entity = PessoaMapper.toEntity(modelOriginal);
        PessoaModel modelConvertido = PessoaMapper.toModel(entity);

        assertEquals(modelOriginal.getCdDocPessoa(), modelConvertido.getCdDocPessoa());
        assertEquals(modelOriginal.getNmPessoa(), modelConvertido.getNmPessoa());
        assertEquals(modelOriginal.getTpPessoa(), modelConvertido.getTpPessoa());
        assertEquals(modelOriginal.getDsEmail(), modelConvertido.getDsEmail());
    }

    @Test
    void deveRealizarConversaoInversaIdempotente() {
        PessoaEntity entityOriginal = new PessoaEntity();
        entityOriginal.setCdDocPessoa("66666666666");
        entityOriginal.setNmPessoa("Patricia Ferreira");
        entityOriginal.setTpPessoa(TipoPessoaEnum.FUNCIONARIO);
        entityOriginal.setDsEmail("patricia@empresa.com");

        PessoaModel model = PessoaMapper.toModel(entityOriginal);
        PessoaEntity entityConvertida = PessoaMapper.toEntity(model);

        assertEquals(entityOriginal.getCdDocPessoa(), entityConvertida.getCdDocPessoa());
        assertEquals(entityOriginal.getNmPessoa(), entityConvertida.getNmPessoa());
        assertEquals(entityOriginal.getTpPessoa(), entityConvertida.getTpPessoa());
        assertEquals(entityOriginal.getDsEmail(), entityConvertida.getDsEmail());
    }

    @Test
    void deveConverterMultiplasEntitiesParaModels() {
        PessoaEntity entity1 = new PessoaEntity();
        entity1.setCdDocPessoa("77777777777");
        entity1.setNmPessoa("Pessoa 1");
        entity1.setTpPessoa(TipoPessoaEnum.CLIENTE);
        entity1.setDsEmail("pessoa1@email.com");

        PessoaEntity entity2 = new PessoaEntity();
        entity2.setCdDocPessoa("88888888888");
        entity2.setNmPessoa("Pessoa 2");
        entity2.setTpPessoa(TipoPessoaEnum.FUNCIONARIO);
        entity2.setDsEmail("pessoa2@empresa.com");

        PessoaModel model1 = PessoaMapper.toModel(entity1);
        PessoaModel model2 = PessoaMapper.toModel(entity2);

        assertNotNull(model1);
        assertNotNull(model2);
        assertNotEquals(model1.getCdDocPessoa(), model2.getCdDocPessoa());
        assertEquals("77777777777", model1.getCdDocPessoa());
        assertEquals("88888888888", model2.getCdDocPessoa());
    }

    @Test
    void deveConverterMultiplasModelsParaEntities() {
        PessoaModel model1 = new PessoaModel.Builder()
                .setCdDocPessoa("99999999999")
                .setNmPessoa("Model 1")
                .setTpPessoa(TipoPessoaEnum.CLIENTE)
                .setDsEmail("model1@email.com")
                .build();

        PessoaModel model2 = new PessoaModel.Builder()
                .setCdDocPessoa("10101010101")
                .setNmPessoa("Model 2")
                .setTpPessoa(TipoPessoaEnum.FUNCIONARIO)
                .setDsEmail("model2@empresa.com")
                .build();

        PessoaEntity entity1 = PessoaMapper.toEntity(model1);
        PessoaEntity entity2 = PessoaMapper.toEntity(model2);

        assertNotNull(entity1);
        assertNotNull(entity2);
        assertNotEquals(entity1.getCdDocPessoa(), entity2.getCdDocPessoa());
        assertEquals("99999999999", entity1.getCdDocPessoa());
        assertEquals("10101010101", entity2.getCdDocPessoa());
    }

    @Test
    void devePreservarTipoEnumAoConverterEntityParaModel() {
        PessoaEntity entityCliente = new PessoaEntity();
        entityCliente.setTpPessoa(TipoPessoaEnum.CLIENTE);

        PessoaEntity entityFuncionario = new PessoaEntity();
        entityFuncionario.setTpPessoa(TipoPessoaEnum.FUNCIONARIO);

        PessoaModel modelCliente = PessoaMapper.toModel(entityCliente);
        PessoaModel modelFuncionario = PessoaMapper.toModel(entityFuncionario);

        assertEquals(TipoPessoaEnum.CLIENTE, modelCliente.getTpPessoa());
        assertEquals(TipoPessoaEnum.FUNCIONARIO, modelFuncionario.getTpPessoa());
    }

    @Test
    void devePreservarTipoEnumAoConverterModelParaEntity() {
        PessoaModel modelCliente = new PessoaModel();
        modelCliente.setTpPessoa(TipoPessoaEnum.CLIENTE);

        PessoaModel modelFuncionario = new PessoaModel();
        modelFuncionario.setTpPessoa(TipoPessoaEnum.FUNCIONARIO);

        PessoaEntity entityCliente = PessoaMapper.toEntity(modelCliente);
        PessoaEntity entityFuncionario = PessoaMapper.toEntity(modelFuncionario);

        assertEquals(TipoPessoaEnum.CLIENTE, entityCliente.getTpPessoa());
        assertEquals(TipoPessoaEnum.FUNCIONARIO, entityFuncionario.getTpPessoa());
    }

    @Test
    void deveConverterEntityComNomeComplexo() {
        PessoaEntity entity = new PessoaEntity();
        entity.setCdDocPessoa("12312312312");
        entity.setNmPessoa("José da Silva Santos Júnior");
        entity.setTpPessoa(TipoPessoaEnum.CLIENTE);
        entity.setDsEmail("jose.silva@email.com.br");

        PessoaModel model = PessoaMapper.toModel(entity);

        assertEquals("José da Silva Santos Júnior", model.getNmPessoa());
        assertEquals("jose.silva@email.com.br", model.getDsEmail());
    }

    @Test
    void deveConverterModelComEmailComplexo() {
        PessoaModel model = new PessoaModel.Builder()
                .setCdDocPessoa("45645645645")
                .setNmPessoa("Teste Email")
                .setTpPessoa(TipoPessoaEnum.FUNCIONARIO)
                .setDsEmail("usuario.teste+tag@dominio.com.br")
                .build();

        PessoaEntity entity = PessoaMapper.toEntity(model);

        assertEquals("usuario.teste+tag@dominio.com.br", entity.getDsEmail());
    }

    @Test
    void deveSerMetodoEstatico() throws NoSuchMethodException {
        var methodToModel = PessoaMapper.class.getMethod("toModel", PessoaEntity.class);
        var methodToEntity = PessoaMapper.class.getMethod("toEntity", PessoaModel.class);

        assertTrue(java.lang.reflect.Modifier.isStatic(methodToModel.getModifiers()));
        assertTrue(java.lang.reflect.Modifier.isStatic(methodToEntity.getModifiers()));
    }
}

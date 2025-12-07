package com.fiap.fasfoodpessoas.infraestructure.web.api.dtos;

import br.com.fiap.fasfoodpessoas.domain.enums.TipoPessoaEnum;
import br.com.fiap.fasfoodpessoas.domain.models.PessoaModel;
import br.com.fiap.fasfoodpessoas.infraestructure.web.api.dtos.PessoaRequestDto;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class PessoaRequestDtoTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void deveCriarPessoaRequestDtoComTodosOsCamposValidos() {
        PessoaRequestDto dto = new PessoaRequestDto(
                "12345678900",
                "João Silva",
                TipoPessoaEnum.CLIENTE,
                "joao@email.com"
        );

        assertNotNull(dto);
        assertEquals("12345678900", dto.cdDocPessoa());
        assertEquals("João Silva", dto.nmPessoa());
        assertEquals(TipoPessoaEnum.CLIENTE, dto.tpPessoa());
        assertEquals("joao@email.com", dto.dsEmail());
    }

    @Test
    void deveCriarPessoaRequestDtoAPartirDePessoaModel() {
        PessoaModel model = new PessoaModel.Builder()
                .setCdDocPessoa("98765432100")
                .setNmPessoa("Maria Santos")
                .setTpPessoa(TipoPessoaEnum.FUNCIONARIO)
                .setDsEmail("maria@empresa.com")
                .build();

        PessoaRequestDto dto = new PessoaRequestDto(model);

        assertNotNull(dto);
        assertEquals("98765432100", dto.cdDocPessoa());
        assertEquals("Maria Santos", dto.nmPessoa());
        assertEquals(TipoPessoaEnum.FUNCIONARIO, dto.tpPessoa());
        assertEquals("maria@empresa.com", dto.dsEmail());
    }

    @Test
    void deveValidarComSucessoQuandoTodosCamposValidos() {
        PessoaRequestDto dto = new PessoaRequestDto(
                "12345678900",
                "João Silva",
                TipoPessoaEnum.CLIENTE,
                "joao@email.com"
        );

        Set<ConstraintViolation<PessoaRequestDto>> violations = validator.validate(dto);

        assertTrue(violations.isEmpty());
    }

    @Test
    void deveFalharQuandoCpfNull() {
        PessoaRequestDto dto = new PessoaRequestDto(
                null,
                "João Silva",
                TipoPessoaEnum.CLIENTE,
                "joao@email.com"
        );

        Set<ConstraintViolation<PessoaRequestDto>> violations = validator.validate(dto);

        assertFalse(violations.isEmpty());
        assertTrue(violations.stream()
                .anyMatch(v -> v.getMessage().contains("cdDocPessoa é obrigatório")));
    }

    @Test
    void deveFalharQuandoNomeNull() {
        PessoaRequestDto dto = new PessoaRequestDto(
                "12345678900",
                null,
                TipoPessoaEnum.CLIENTE,
                "joao@email.com"
        );

        Set<ConstraintViolation<PessoaRequestDto>> violations = validator.validate(dto);

        assertFalse(violations.isEmpty());
        assertTrue(violations.stream()
                .anyMatch(v -> v.getMessage().contains("nmPessoa é obrigatório")));
    }

    @Test
    void deveFalharQuandoTipoPessoaNull() {
        PessoaRequestDto dto = new PessoaRequestDto(
                "12345678900",
                "João Silva",
                null,
                "joao@email.com"
        );

        Set<ConstraintViolation<PessoaRequestDto>> violations = validator.validate(dto);

        assertFalse(violations.isEmpty());
        assertTrue(violations.stream()
                .anyMatch(v -> v.getMessage().contains("tpPessoa é obrigatório")));
    }

    @Test
    void deveFalharQuandoEmailNull() {
        PessoaRequestDto dto = new PessoaRequestDto(
                "12345678900",
                "João Silva",
                TipoPessoaEnum.CLIENTE,
                null
        );

        Set<ConstraintViolation<PessoaRequestDto>> violations = validator.validate(dto);

        assertFalse(violations.isEmpty());
        assertTrue(violations.stream()
                .anyMatch(v -> v.getMessage().contains("dsEmail é obrigatório")));
    }

    @Test
    void deveFalharQuandoCpfInvalido() {
        PessoaRequestDto dto = new PessoaRequestDto(
                "123",
                "João Silva",
                TipoPessoaEnum.CLIENTE,
                "joao@email.com"
        );

        Set<ConstraintViolation<PessoaRequestDto>> violations = validator.validate(dto);

        assertFalse(violations.isEmpty());
        assertTrue(violations.stream()
                .anyMatch(v -> v.getMessage().contains("CPF informado") || 
                              v.getMessage().contains("inválido")));
    }

    @Test
    void deveFalharQuandoEmailInvalido() {
        PessoaRequestDto dto = new PessoaRequestDto(
                "12345678900",
                "João Silva",
                TipoPessoaEnum.CLIENTE,
                "email-invalido"
        );

        Set<ConstraintViolation<PessoaRequestDto>> violations = validator.validate(dto);

        assertFalse(violations.isEmpty());
        assertTrue(violations.stream()
                .anyMatch(v -> v.getMessage().contains("e-mail válido")));
    }

    @Test
    void deveFalharQuandoNomeMenorQue3Caracteres() {
        PessoaRequestDto dto = new PessoaRequestDto(
                "12345678900",
                "Jo",
                TipoPessoaEnum.CLIENTE,
                "joao@email.com"
        );

        Set<ConstraintViolation<PessoaRequestDto>> violations = validator.validate(dto);

        assertFalse(violations.isEmpty());
        assertTrue(violations.stream()
                .anyMatch(v -> v.getMessage().contains("entre 3 e 200 caracteres")));
    }

    @Test
    void deveFalharQuandoNomeMaiorQue200Caracteres() {
        String nomeLongo = "a".repeat(201);
        PessoaRequestDto dto = new PessoaRequestDto(
                "12345678900",
                nomeLongo,
                TipoPessoaEnum.CLIENTE,
                "joao@email.com"
        );

        Set<ConstraintViolation<PessoaRequestDto>> violations = validator.validate(dto);

        assertFalse(violations.isEmpty());
        assertTrue(violations.stream()
                .anyMatch(v -> v.getMessage().contains("entre 3 e 200 caracteres")));
    }

    @Test
    void deveValidarComSucessoNomeCom3Caracteres() {
        PessoaRequestDto dto = new PessoaRequestDto(
                "12345678900",
                "Ana",
                TipoPessoaEnum.CLIENTE,
                "ana@email.com"
        );

        Set<ConstraintViolation<PessoaRequestDto>> violations = validator.validate(dto);

        assertTrue(violations.isEmpty());
    }

    @Test
    void deveValidarComSucessoNomeCom200Caracteres() {
        String nome200 = "a".repeat(200);
        PessoaRequestDto dto = new PessoaRequestDto(
                "12345678900",
                nome200,
                TipoPessoaEnum.CLIENTE,
                "joao@email.com"
        );

        Set<ConstraintViolation<PessoaRequestDto>> violations = validator.validate(dto);

        assertTrue(violations.isEmpty());
    }

    @Test
    void deveCriarClienteComSucesso() {
        PessoaRequestDto dto = new PessoaRequestDto(
                "11111111111",
                "Pedro Costa",
                TipoPessoaEnum.CLIENTE,
                "pedro@email.com"
        );

        assertEquals(TipoPessoaEnum.CLIENTE, dto.tpPessoa());
        Set<ConstraintViolation<PessoaRequestDto>> violations = validator.validate(dto);
        assertTrue(violations.isEmpty());
    }

    @Test
    void deveCriarFuncionarioComSucesso() {
        PessoaRequestDto dto = new PessoaRequestDto(
                "22222222222",
                "Ana Paula",
                TipoPessoaEnum.FUNCIONARIO,
                "ana@empresa.com"
        );

        assertEquals(TipoPessoaEnum.FUNCIONARIO, dto.tpPessoa());
        Set<ConstraintViolation<PessoaRequestDto>> violations = validator.validate(dto);
        assertTrue(violations.isEmpty());
    }

    @Test
    void deveSerRecord() {
        assertTrue(PessoaRequestDto.class.isRecord());
    }

    @Test
    void deveTerQuatroCampos() {
        assertEquals(4, PessoaRequestDto.class.getRecordComponents().length);
    }

    @Test
    void deveCompararPessoaRequestDtosIguais() {
        PessoaRequestDto dto1 = new PessoaRequestDto(
                "12345678900",
                "João Silva",
                TipoPessoaEnum.CLIENTE,
                "joao@email.com"
        );

        PessoaRequestDto dto2 = new PessoaRequestDto(
                "12345678900",
                "João Silva",
                TipoPessoaEnum.CLIENTE,
                "joao@email.com"
        );

        assertEquals(dto1, dto2);
    }

    @Test
    void deveCompararPessoaRequestDtosDiferentes() {
        PessoaRequestDto dto1 = new PessoaRequestDto(
                "12345678900",
                "João Silva",
                TipoPessoaEnum.CLIENTE,
                "joao@email.com"
        );

        PessoaRequestDto dto2 = new PessoaRequestDto(
                "98765432100",
                "Maria Santos",
                TipoPessoaEnum.FUNCIONARIO,
                "maria@empresa.com"
        );

        assertNotEquals(dto1, dto2);
    }

    @Test
    void deveGerarHashCodeConsistente() {
        PessoaRequestDto dto1 = new PessoaRequestDto(
                "12345678900",
                "João Silva",
                TipoPessoaEnum.CLIENTE,
                "joao@email.com"
        );

        PessoaRequestDto dto2 = new PessoaRequestDto(
                "12345678900",
                "João Silva",
                TipoPessoaEnum.CLIENTE,
                "joao@email.com"
        );

        assertEquals(dto1.hashCode(), dto2.hashCode());
    }

    @Test
    void deveGerarToStringComTodosOsCampos() {
        PessoaRequestDto dto = new PessoaRequestDto(
                "12345678900",
                "João Silva",
                TipoPessoaEnum.CLIENTE,
                "joao@email.com"
        );

        String toString = dto.toString();

        assertNotNull(toString);
        assertTrue(toString.contains("12345678900"));
        assertTrue(toString.contains("João Silva"));
        assertTrue(toString.contains("CLIENTE"));
        assertTrue(toString.contains("joao@email.com"));
    }

    @Test
    void deveConverterModelComTodosCamposPreenchidos() {
        PessoaModel model = new PessoaModel.Builder()
                .setCdDocPessoa("33333333333")
                .setNmPessoa("Carlos Eduardo")
                .setTpPessoa(TipoPessoaEnum.CLIENTE)
                .setDsEmail("carlos@email.com")
                .build();

        PessoaRequestDto dto = new PessoaRequestDto(model);

        assertEquals(model.getCdDocPessoa(), dto.cdDocPessoa());
        assertEquals(model.getNmPessoa(), dto.nmPessoa());
        assertEquals(model.getTpPessoa(), dto.tpPessoa());
        assertEquals(model.getDsEmail(), dto.dsEmail());
    }

    @Test
    void deveValidarEmailComFormatoComplexo() {
        PessoaRequestDto dto = new PessoaRequestDto(
                "44444444444",
                "Roberto Alves",
                TipoPessoaEnum.FUNCIONARIO,
                "roberto.alves+tag@empresa.com.br"
        );

        Set<ConstraintViolation<PessoaRequestDto>> violations = validator.validate(dto);
        assertTrue(violations.isEmpty());
    }

    @Test
    void deveValidarNomeComCaracteresEspeciais() {
        PessoaRequestDto dto = new PessoaRequestDto(
                "55555555555",
                "José da Silva Júnior",
                TipoPessoaEnum.CLIENTE,
                "jose@email.com"
        );

        Set<ConstraintViolation<PessoaRequestDto>> violations = validator.validate(dto);
        assertTrue(violations.isEmpty());
    }

    @Test
    void deveFalharQuandoNomeVazio() {
        PessoaRequestDto dto = new PessoaRequestDto(
                "12345678900",
                "",
                TipoPessoaEnum.CLIENTE,
                "joao@email.com"
        );

        Set<ConstraintViolation<PessoaRequestDto>> violations = validator.validate(dto);

        assertFalse(violations.isEmpty());
    }

    @Test
    void deveFalharQuandoEmailVazio() {
        PessoaRequestDto dto = new PessoaRequestDto(
                "12345678900",
                "João Silva",
                TipoPessoaEnum.CLIENTE,
                ""
        );

        Set<ConstraintViolation<PessoaRequestDto>> violations = validator.validate(dto);

        assertFalse(violations.isEmpty());
    }

    @Test
    void deveTerConstrutorComQuatroParametros() {
        assertDoesNotThrow(() -> new PessoaRequestDto(
                "12345678900",
                "Teste",
                TipoPessoaEnum.CLIENTE,
                "teste@email.com"
        ));
    }

    @Test
    void deveTerConstrutorComPessoaModel() {
        PessoaModel model = new PessoaModel.Builder()
                .setCdDocPessoa("12345678900")
                .setNmPessoa("Teste")
                .setTpPessoa(TipoPessoaEnum.CLIENTE)
                .setDsEmail("teste@email.com")
                .build();

        assertDoesNotThrow(() -> new PessoaRequestDto(model));
    }

    @Test
    void deveSerImutavel() {
        PessoaRequestDto dto = new PessoaRequestDto(
                "12345678900",
                "João Silva",
                TipoPessoaEnum.CLIENTE,
                "joao@email.com"
        );

        // Records são imutáveis
        assertEquals("12345678900", dto.cdDocPessoa());
        assertEquals("João Silva", dto.nmPessoa());
        assertEquals(TipoPessoaEnum.CLIENTE, dto.tpPessoa());
        assertEquals("joao@email.com", dto.dsEmail());
    }

    @Test
    void deveTerAnotacaoNotNullEmTodosCampos() throws NoSuchFieldException {
        var cdDocPessoaAnnotations = PessoaRequestDto.class.getRecordComponents()[0].getAnnotations();
        var nmPessoaAnnotations = PessoaRequestDto.class.getRecordComponents()[1].getAnnotations();
        var tpPessoaAnnotations = PessoaRequestDto.class.getRecordComponents()[2].getAnnotations();
        var dsEmailAnnotations = PessoaRequestDto.class.getRecordComponents()[3].getAnnotations();

        assertTrue(cdDocPessoaAnnotations.length > 0);
        assertTrue(nmPessoaAnnotations.length > 0);
        assertTrue(tpPessoaAnnotations.length > 0);
        assertTrue(dsEmailAnnotations.length > 0);
    }

    @Test
    void deveFalharComMultiplasViolacoesQuandoTodosCamposInvalidos() {
        PessoaRequestDto dto = new PessoaRequestDto(
                null,
                null,
                null,
                null
        );

        Set<ConstraintViolation<PessoaRequestDto>> violations = validator.validate(dto);

        assertFalse(violations.isEmpty());
        assertTrue(violations.size() >= 4);
    }
}

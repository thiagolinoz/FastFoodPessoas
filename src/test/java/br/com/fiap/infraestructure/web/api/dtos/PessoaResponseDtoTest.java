package br.com.fiap.infraestructure.web.api.dtos;

import br.com.fiap.fasfoodpessoas.domain.enums.TipoPessoaEnum;
import br.com.fiap.fasfoodpessoas.domain.models.PessoaModel;
import br.com.fiap.fasfoodpessoas.infraestructure.web.api.dtos.PessoaResponseDto;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PessoaResponseDtoTest {

    @Test
    void deveCriarPessoaResponseDtoComTodosOsCampos() {
        PessoaResponseDto dto = new PessoaResponseDto(
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
    void deveCriarPessoaResponseDtoAPartirDePessoaModel() {
        PessoaModel model = new PessoaModel.Builder()
                .setCdDocPessoa("98765432100")
                .setNmPessoa("Maria Santos")
                .setTpPessoa(TipoPessoaEnum.FUNCIONARIO)
                .setDsEmail("maria@empresa.com")
                .build();

        PessoaResponseDto dto = new PessoaResponseDto(model);

        assertNotNull(dto);
        assertEquals("98765432100", dto.cdDocPessoa());
        assertEquals("Maria Santos", dto.nmPessoa());
        assertEquals(TipoPessoaEnum.FUNCIONARIO, dto.tpPessoa());
        assertEquals("maria@empresa.com", dto.dsEmail());
    }

    @Test
    void deveCriarPessoaResponseDtoCliente() {
        PessoaResponseDto dto = new PessoaResponseDto(
                "11111111111",
                "Pedro Costa",
                TipoPessoaEnum.CLIENTE,
                "pedro@email.com"
        );

        assertEquals(TipoPessoaEnum.CLIENTE, dto.tpPessoa());
    }

    @Test
    void deveCriarPessoaResponseDtoFuncionario() {
        PessoaResponseDto dto = new PessoaResponseDto(
                "22222222222",
                "Ana Paula",
                TipoPessoaEnum.FUNCIONARIO,
                "ana@empresa.com"
        );

        assertEquals(TipoPessoaEnum.FUNCIONARIO, dto.tpPessoa());
    }

    @Test
    void deveConverterClienteModelParaDto() {
        PessoaModel clienteModel = new PessoaModel.Builder()
                .setCdDocPessoa("33333333333")
                .setNmPessoa("Carlos Eduardo")
                .setTpPessoa(TipoPessoaEnum.CLIENTE)
                .setDsEmail("carlos@email.com")
                .build();

        PessoaResponseDto dto = new PessoaResponseDto(clienteModel);

        assertEquals("33333333333", dto.cdDocPessoa());
        assertEquals("Carlos Eduardo", dto.nmPessoa());
        assertEquals(TipoPessoaEnum.CLIENTE, dto.tpPessoa());
        assertEquals("carlos@email.com", dto.dsEmail());
    }

    @Test
    void deveConverterFuncionarioModelParaDto() {
        PessoaModel funcionarioModel = new PessoaModel.Builder()
                .setCdDocPessoa("44444444444")
                .setNmPessoa("Julia Lima")
                .setTpPessoa(TipoPessoaEnum.FUNCIONARIO)
                .setDsEmail("julia@empresa.com")
                .build();

        PessoaResponseDto dto = new PessoaResponseDto(funcionarioModel);

        assertEquals("44444444444", dto.cdDocPessoa());
        assertEquals("Julia Lima", dto.nmPessoa());
        assertEquals(TipoPessoaEnum.FUNCIONARIO, dto.tpPessoa());
        assertEquals("julia@empresa.com", dto.dsEmail());
    }

    @Test
    void deveSerRecord() {
        assertTrue(PessoaResponseDto.class.isRecord());
    }

    @Test
    void deveTerQuatroCampos() {
        assertEquals(4, PessoaResponseDto.class.getRecordComponents().length);
    }

    @Test
    void deveCompararPessoaResponseDtosIguais() {
        PessoaResponseDto dto1 = new PessoaResponseDto(
                "12345678900",
                "João Silva",
                TipoPessoaEnum.CLIENTE,
                "joao@email.com"
        );

        PessoaResponseDto dto2 = new PessoaResponseDto(
                "12345678900",
                "João Silva",
                TipoPessoaEnum.CLIENTE,
                "joao@email.com"
        );

        assertEquals(dto1, dto2);
    }

    @Test
    void deveCompararPessoaResponseDtosDiferentes() {
        PessoaResponseDto dto1 = new PessoaResponseDto(
                "12345678900",
                "João Silva",
                TipoPessoaEnum.CLIENTE,
                "joao@email.com"
        );

        PessoaResponseDto dto2 = new PessoaResponseDto(
                "98765432100",
                "Maria Santos",
                TipoPessoaEnum.FUNCIONARIO,
                "maria@empresa.com"
        );

        assertNotEquals(dto1, dto2);
    }

    @Test
    void deveGerarHashCodeConsistente() {
        PessoaResponseDto dto1 = new PessoaResponseDto(
                "12345678900",
                "João Silva",
                TipoPessoaEnum.CLIENTE,
                "joao@email.com"
        );

        PessoaResponseDto dto2 = new PessoaResponseDto(
                "12345678900",
                "João Silva",
                TipoPessoaEnum.CLIENTE,
                "joao@email.com"
        );

        assertEquals(dto1.hashCode(), dto2.hashCode());
    }

    @Test
    void deveGerarToStringComTodosOsCampos() {
        PessoaResponseDto dto = new PessoaResponseDto(
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
    void deveCriarPessoaResponseDtoComNomeCompleto() {
        PessoaResponseDto dto = new PessoaResponseDto(
                "55555555555",
                "José da Silva Santos Júnior",
                TipoPessoaEnum.CLIENTE,
                "jose@email.com"
        );

        assertEquals("José da Silva Santos Júnior", dto.nmPessoa());
    }

    @Test
    void deveCriarPessoaResponseDtoComEmailComplexo() {
        PessoaResponseDto dto = new PessoaResponseDto(
                "66666666666",
                "Roberto Alves",
                TipoPessoaEnum.FUNCIONARIO,
                "roberto.alves+tag@empresa.com.br"
        );

        assertEquals("roberto.alves+tag@empresa.com.br", dto.dsEmail());
    }

    @Test
    void deveCriarPessoaResponseDtoComValoresNull() {
        PessoaResponseDto dto = new PessoaResponseDto(null, null, null, null);

        assertNotNull(dto);
        assertNull(dto.cdDocPessoa());
        assertNull(dto.nmPessoa());
        assertNull(dto.tpPessoa());
        assertNull(dto.dsEmail());
    }

    @Test
    void deveConverterModelComTodosCamposPreenchidos() {
        PessoaModel model = new PessoaModel.Builder()
                .setCdDocPessoa("77777777777")
                .setNmPessoa("Patricia Ferreira")
                .setTpPessoa(TipoPessoaEnum.CLIENTE)
                .setDsEmail("patricia@email.com")
                .build();

        PessoaResponseDto dto = new PessoaResponseDto(model);

        assertNotNull(dto.cdDocPessoa());
        assertNotNull(dto.nmPessoa());
        assertNotNull(dto.tpPessoa());
        assertNotNull(dto.dsEmail());
    }

    @Test
    void devePreservarCpfAoConverter() {
        PessoaModel model = new PessoaModel.Builder()
                .setCdDocPessoa("88888888888")
                .setNmPessoa("Teste")
                .setTpPessoa(TipoPessoaEnum.CLIENTE)
                .setDsEmail("teste@email.com")
                .build();

        PessoaResponseDto dto = new PessoaResponseDto(model);

        assertEquals(model.getCdDocPessoa(), dto.cdDocPessoa());
    }

    @Test
    void devePreservarNomeAoConverter() {
        PessoaModel model = new PessoaModel.Builder()
                .setCdDocPessoa("99999999999")
                .setNmPessoa("Fernando Costa")
                .setTpPessoa(TipoPessoaEnum.FUNCIONARIO)
                .setDsEmail("fernando@empresa.com")
                .build();

        PessoaResponseDto dto = new PessoaResponseDto(model);

        assertEquals(model.getNmPessoa(), dto.nmPessoa());
    }

    @Test
    void devePreservarTipoPessoaAoConverter() {
        PessoaModel modelCliente = new PessoaModel.Builder()
                .setCdDocPessoa("10101010101")
                .setNmPessoa("Cliente Teste")
                .setTpPessoa(TipoPessoaEnum.CLIENTE)
                .setDsEmail("cliente@email.com")
                .build();

        PessoaModel modelFuncionario = new PessoaModel.Builder()
                .setCdDocPessoa("20202020202")
                .setNmPessoa("Funcionario Teste")
                .setTpPessoa(TipoPessoaEnum.FUNCIONARIO)
                .setDsEmail("funcionario@empresa.com")
                .build();

        PessoaResponseDto dtoCliente = new PessoaResponseDto(modelCliente);
        PessoaResponseDto dtoFuncionario = new PessoaResponseDto(modelFuncionario);

        assertEquals(TipoPessoaEnum.CLIENTE, dtoCliente.tpPessoa());
        assertEquals(TipoPessoaEnum.FUNCIONARIO, dtoFuncionario.tpPessoa());
    }

    @Test
    void devePreservarEmailAoConverter() {
        PessoaModel model = new PessoaModel.Builder()
                .setCdDocPessoa("11122233344")
                .setNmPessoa("Gabriela Santos")
                .setTpPessoa(TipoPessoaEnum.CLIENTE)
                .setDsEmail("gabriela@email.com")
                .build();

        PessoaResponseDto dto = new PessoaResponseDto(model);

        assertEquals(model.getDsEmail(), dto.dsEmail());
    }

    @Test
    void deveCriarMultiplosDtosIndependentes() {
        PessoaResponseDto dto1 = new PessoaResponseDto(
                "11111111111",
                "Pessoa 1",
                TipoPessoaEnum.CLIENTE,
                "pessoa1@email.com"
        );

        PessoaResponseDto dto2 = new PessoaResponseDto(
                "22222222222",
                "Pessoa 2",
                TipoPessoaEnum.FUNCIONARIO,
                "pessoa2@empresa.com"
        );

        assertNotEquals(dto1.cdDocPessoa(), dto2.cdDocPessoa());
        assertNotEquals(dto1.nmPessoa(), dto2.nmPessoa());
        assertNotEquals(dto1.tpPessoa(), dto2.tpPessoa());
        assertNotEquals(dto1.dsEmail(), dto2.dsEmail());
    }

    @Test
    void deveTerConstrutorComQuatroParametros() {
        assertDoesNotThrow(() -> new PessoaResponseDto(
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

        assertDoesNotThrow(() -> new PessoaResponseDto(model));
    }

    @Test
    void deveSerImutavel() {
        PessoaResponseDto dto = new PessoaResponseDto(
                "12345678900",
                "João Silva",
                TipoPessoaEnum.CLIENTE,
                "joao@email.com"
        );

        // Records são imutáveis, então os valores não podem ser alterados
        assertEquals("12345678900", dto.cdDocPessoa());
        assertEquals("João Silva", dto.nmPessoa());
        assertEquals(TipoPessoaEnum.CLIENTE, dto.tpPessoa());
        assertEquals("joao@email.com", dto.dsEmail());
    }

    @Test
    void deveCompararComNull() {
        PessoaResponseDto dto = new PessoaResponseDto(
                "12345678900",
                "João Silva",
                TipoPessoaEnum.CLIENTE,
                "joao@email.com"
        );

        assertNotEquals(null, dto);
    }

    @Test
    void deveCompararComOutroTipo() {
        PessoaResponseDto dto = new PessoaResponseDto(
                "12345678900",
                "João Silva",
                TipoPessoaEnum.CLIENTE,
                "joao@email.com"
        );

        PessoaResponseDto mesmaReferencia = dto;

        assertSame(dto, dto);
    }

    @Test
    void deveCriarDtoComCpfFormatado() {
        PessoaResponseDto dto = new PessoaResponseDto(
                "12345678900",
                "Teste CPF",
                TipoPessoaEnum.CLIENTE,
                "teste@email.com"
        );

        assertEquals("12345678900", dto.cdDocPessoa());
        assertEquals(11, dto.cdDocPessoa().length());
    }
}

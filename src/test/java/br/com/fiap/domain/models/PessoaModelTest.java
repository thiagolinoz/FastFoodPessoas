package br.com.fiap.domain.models;

import br.com.fiap.fasfoodpessoas.domain.enums.TipoPessoaEnum;
import br.com.fiap.fasfoodpessoas.domain.models.PessoaModel;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PessoaModelTest {

    @Test
    void deveCriarPessoaComConstrutorVazio() {
        PessoaModel pessoa = new PessoaModel();

        assertNotNull(pessoa);
        assertNull(pessoa.getCdDocPessoa());
        assertNull(pessoa.getNmPessoa());
        assertNull(pessoa.getTpPessoa());
        assertNull(pessoa.getDsEmail());
    }

    @Test
    void deveCriarPessoaComConstrutorCompleto() {
        PessoaModel pessoa = new PessoaModel(
                "12345678900",
                "João Silva",
                TipoPessoaEnum.CLIENTE,
                "joao@email.com"
        );

        assertNotNull(pessoa);
        assertEquals("12345678900", pessoa.getCdDocPessoa());
        assertEquals("João Silva", pessoa.getNmPessoa());
        assertEquals(TipoPessoaEnum.CLIENTE, pessoa.getTpPessoa());
        assertEquals("joao@email.com", pessoa.getDsEmail());
    }

    @Test
    void deveSetarCdDocPessoaComRetornoFluent() {
        PessoaModel pessoa = new PessoaModel();

        PessoaModel resultado = pessoa.setCdDocPessoa("98765432100");

        assertEquals("98765432100", pessoa.getCdDocPessoa());
        assertSame(pessoa, resultado);
    }

    @Test
    void deveSetarNmPessoaComRetornoFluent() {
        PessoaModel pessoa = new PessoaModel();

        PessoaModel resultado = pessoa.setNmPessoa("Maria Santos");

        assertEquals("Maria Santos", pessoa.getNmPessoa());
        assertSame(pessoa, resultado);
    }

    @Test
    void deveSetarTpPessoaComRetornoFluent() {
        PessoaModel pessoa = new PessoaModel();

        PessoaModel resultado = pessoa.setTpPessoa(TipoPessoaEnum.FUNCIONARIO);

        assertEquals(TipoPessoaEnum.FUNCIONARIO, pessoa.getTpPessoa());
        assertSame(pessoa, resultado);
    }

    @Test
    void deveSetarDsEmailComRetornoFluent() {
        PessoaModel pessoa = new PessoaModel();

        PessoaModel resultado = pessoa.setDsEmail("maria@email.com");

        assertEquals("maria@email.com", pessoa.getDsEmail());
        assertSame(pessoa, resultado);
    }

    @Test
    void devePermitirEncadeamentoDeSetters() {
        PessoaModel pessoa = new PessoaModel()
                .setCdDocPessoa("11122233344")
                .setNmPessoa("Pedro Costa")
                .setTpPessoa(TipoPessoaEnum.CLIENTE)
                .setDsEmail("pedro@email.com");

        assertEquals("11122233344", pessoa.getCdDocPessoa());
        assertEquals("Pedro Costa", pessoa.getNmPessoa());
        assertEquals(TipoPessoaEnum.CLIENTE, pessoa.getTpPessoa());
        assertEquals("pedro@email.com", pessoa.getDsEmail());
    }

    @Test
    void deveCriarPessoaClienteComBuilder() {
        PessoaModel pessoa = new PessoaModel.Builder()
                .setCdDocPessoa("12345678900")
                .setNmPessoa("Ana Paula")
                .setTpPessoa(TipoPessoaEnum.CLIENTE)
                .setDsEmail("ana@email.com")
                .build();

        assertNotNull(pessoa);
        assertEquals("12345678900", pessoa.getCdDocPessoa());
        assertEquals("Ana Paula", pessoa.getNmPessoa());
        assertEquals(TipoPessoaEnum.CLIENTE, pessoa.getTpPessoa());
        assertEquals("ana@email.com", pessoa.getDsEmail());
    }

    @Test
    void deveCriarPessoaFuncionarioComBuilder() {
        PessoaModel pessoa = new PessoaModel.Builder()
                .setCdDocPessoa("98765432100")
                .setNmPessoa("Carlos Eduardo")
                .setTpPessoa(TipoPessoaEnum.FUNCIONARIO)
                .setDsEmail("carlos@empresa.com")
                .build();

        assertNotNull(pessoa);
        assertEquals("98765432100", pessoa.getCdDocPessoa());
        assertEquals("Carlos Eduardo", pessoa.getNmPessoa());
        assertEquals(TipoPessoaEnum.FUNCIONARIO, pessoa.getTpPessoa());
        assertEquals("carlos@empresa.com", pessoa.getDsEmail());
    }

    @Test
    void deveCriarPessoaComBuilderSemDefinirTodosOsCampos() {
        PessoaModel pessoa = new PessoaModel.Builder()
                .setNmPessoa("Fernanda Lima")
                .build();

        assertNotNull(pessoa);
        assertNull(pessoa.getCdDocPessoa());
        assertEquals("Fernanda Lima", pessoa.getNmPessoa());
        assertNull(pessoa.getTpPessoa());
        assertNull(pessoa.getDsEmail());
    }

    @Test
    void devePermitirAlterarCamposAposCriacao() {
        PessoaModel pessoa = new PessoaModel.Builder()
                .setCdDocPessoa("11111111111")
                .setNmPessoa("Roberto Alves")
                .setTpPessoa(TipoPessoaEnum.CLIENTE)
                .setDsEmail("roberto@email.com")
                .build();

        pessoa.setNmPessoa("Roberto Alves Junior");
        pessoa.setDsEmail("roberto.junior@email.com");

        assertEquals("11111111111", pessoa.getCdDocPessoa());
        assertEquals("Roberto Alves Junior", pessoa.getNmPessoa());
        assertEquals(TipoPessoaEnum.CLIENTE, pessoa.getTpPessoa());
        assertEquals("roberto.junior@email.com", pessoa.getDsEmail());
    }

    @Test
    void deveAceitarValoresNulosNosSetters() {
        PessoaModel pessoa = new PessoaModel("123", "Teste", TipoPessoaEnum.CLIENTE, "teste@email.com");

        pessoa.setCdDocPessoa(null);
        pessoa.setNmPessoa(null);
        pessoa.setTpPessoa(null);
        pessoa.setDsEmail(null);

        assertNull(pessoa.getCdDocPessoa());
        assertNull(pessoa.getNmPessoa());
        assertNull(pessoa.getTpPessoa());
        assertNull(pessoa.getDsEmail());
    }

    @Test
    void deveManterDadosAposCopiaDeReferencia() {
        PessoaModel pessoa1 = new PessoaModel.Builder()
                .setCdDocPessoa("12345678900")
                .setNmPessoa("Julia Santos")
                .setTpPessoa(TipoPessoaEnum.CLIENTE)
                .setDsEmail("julia@email.com")
                .build();

        PessoaModel pessoa2 = pessoa1;

        assertEquals(pessoa1.getCdDocPessoa(), pessoa2.getCdDocPessoa());
        assertEquals(pessoa1.getNmPessoa(), pessoa2.getNmPessoa());
        assertEquals(pessoa1.getTpPessoa(), pessoa2.getTpPessoa());
        assertEquals(pessoa1.getDsEmail(), pessoa2.getDsEmail());
        assertSame(pessoa1, pessoa2);
    }

    @Test
    void devePermitirTrocarTipoDePessoa() {
        PessoaModel pessoa = new PessoaModel.Builder()
                .setCdDocPessoa("55555555555")
                .setNmPessoa("Patricia Ferreira")
                .setTpPessoa(TipoPessoaEnum.CLIENTE)
                .setDsEmail("patricia@email.com")
                .build();

        assertEquals(TipoPessoaEnum.CLIENTE, pessoa.getTpPessoa());

        pessoa.setTpPessoa(TipoPessoaEnum.FUNCIONARIO);

        assertEquals(TipoPessoaEnum.FUNCIONARIO, pessoa.getTpPessoa());
    }

    @Test
    void deveCriarPessoaComDocumentoVazio() {
        PessoaModel pessoa = new PessoaModel.Builder()
                .setCdDocPessoa("")
                .setNmPessoa("Teste")
                .setTpPessoa(TipoPessoaEnum.CLIENTE)
                .setDsEmail("teste@email.com")
                .build();

        assertEquals("", pessoa.getCdDocPessoa());
    }

    @Test
    void deveCriarPessoaComEmailVazio() {
        PessoaModel pessoa = new PessoaModel.Builder()
                .setCdDocPessoa("12345678900")
                .setNmPessoa("Teste")
                .setTpPessoa(TipoPessoaEnum.CLIENTE)
                .setDsEmail("")
                .build();

        assertEquals("", pessoa.getDsEmail());
    }
}

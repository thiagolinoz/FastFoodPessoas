package br.com.fiap.domain.services;

import br.com.fiap.fasfoodpessoas.domain.enums.TipoPessoaEnum;
import br.com.fiap.fasfoodpessoas.domain.models.PessoaModel;
import br.com.fiap.fasfoodpessoas.domain.ports.out.PessoaRepositoryPort;
import br.com.fiap.fasfoodpessoas.domain.services.PessoaService;
import br.com.fiap.fasfoodpessoas.infraestructure.web.api.exceptions.CpfCadastradoException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PessoaServiceTest {

    @Mock
    private PessoaRepositoryPort pessoaRepositoryPort;

    @InjectMocks
    private PessoaService pessoaService;

    private PessoaModel pessoaCliente;
    private PessoaModel pessoaFuncionario;

    @BeforeEach
    void setUp() {
        pessoaCliente = new PessoaModel.Builder()
                .setCdDocPessoa("12345678900")
                .setNmPessoa("João Silva")
                .setTpPessoa(TipoPessoaEnum.CLIENTE)
                .setDsEmail("joao@email.com")
                .build();

        pessoaFuncionario = new PessoaModel.Builder()
                .setCdDocPessoa("98765432100")
                .setNmPessoa("Maria Santos")
                .setTpPessoa(TipoPessoaEnum.FUNCIONARIO)
                .setDsEmail("maria@empresa.com")
                .build();
    }

    @Test
    void deveCadastrarPessoaComSucesso() {
        when(pessoaRepositoryPort.buscarPessoaPorCpf(anyString())).thenReturn(Optional.empty());
        when(pessoaRepositoryPort.cadastrarPessoa(any(PessoaModel.class))).thenReturn(pessoaCliente);

        PessoaModel resultado = pessoaService.cadastraPessoa(pessoaCliente);

        assertNotNull(resultado);
        assertEquals("12345678900", resultado.getCdDocPessoa());
        assertEquals("João Silva", resultado.getNmPessoa());
        assertEquals(TipoPessoaEnum.CLIENTE, resultado.getTpPessoa());
        assertEquals("joao@email.com", resultado.getDsEmail());

        verify(pessoaRepositoryPort, times(1)).buscarPessoaPorCpf("12345678900");
        verify(pessoaRepositoryPort, times(1)).cadastrarPessoa(pessoaCliente);
    }

    @Test
    void deveCadastrarFuncionarioComSucesso() {
        when(pessoaRepositoryPort.buscarPessoaPorCpf(anyString())).thenReturn(Optional.empty());
        when(pessoaRepositoryPort.cadastrarPessoa(any(PessoaModel.class))).thenReturn(pessoaFuncionario);

        PessoaModel resultado = pessoaService.cadastraPessoa(pessoaFuncionario);

        assertNotNull(resultado);
        assertEquals("98765432100", resultado.getCdDocPessoa());
        assertEquals("Maria Santos", resultado.getNmPessoa());
        assertEquals(TipoPessoaEnum.FUNCIONARIO, resultado.getTpPessoa());

        verify(pessoaRepositoryPort, times(1)).buscarPessoaPorCpf("98765432100");
        verify(pessoaRepositoryPort, times(1)).cadastrarPessoa(pessoaFuncionario);
    }

    @Test
    void deveLancarExcecaoQuandoCpfJaCadastrado() {
        when(pessoaRepositoryPort.buscarPessoaPorCpf(anyString())).thenReturn(Optional.of(pessoaCliente));

        CpfCadastradoException exception = assertThrows(CpfCadastradoException.class, () -> {
            pessoaService.cadastraPessoa(pessoaCliente);
        });

        assertEquals("Cliente ja cadastrado com o CPF informado", exception.getMessage());
        verify(pessoaRepositoryPort, times(1)).buscarPessoaPorCpf("12345678900");
        verify(pessoaRepositoryPort, never()).cadastrarPessoa(any(PessoaModel.class));
    }

    @Test
    void naoDeveCadastrarQuandoCpfDuplicado() {
        PessoaModel pessoaExistente = new PessoaModel.Builder()
                .setCdDocPessoa("12345678900")
                .setNmPessoa("Pessoa Existente")
                .setTpPessoa(TipoPessoaEnum.CLIENTE)
                .setDsEmail("existente@email.com")
                .build();

        when(pessoaRepositoryPort.buscarPessoaPorCpf("12345678900")).thenReturn(Optional.of(pessoaExistente));

        assertThrows(CpfCadastradoException.class, () -> {
            pessoaService.cadastraPessoa(pessoaCliente);
        });

        verify(pessoaRepositoryPort, times(1)).buscarPessoaPorCpf("12345678900");
        verify(pessoaRepositoryPort, never()).cadastrarPessoa(any(PessoaModel.class));
    }

    @Test
    void deveBuscarPessoaPorCpfComSucesso() {
        when(pessoaRepositoryPort.buscarPessoaPorCpf("12345678900")).thenReturn(Optional.of(pessoaCliente));

        Optional<PessoaModel> resultado = pessoaService.buscaPessoaPorCpf("12345678900");

        assertTrue(resultado.isPresent());
        assertEquals("12345678900", resultado.get().getCdDocPessoa());
        assertEquals("João Silva", resultado.get().getNmPessoa());
        verify(pessoaRepositoryPort, times(1)).buscarPessoaPorCpf("12345678900");
    }

    @Test
    void deveRetornarOptionalVazioQuandoPessoaNaoEncontrada() {
        when(pessoaRepositoryPort.buscarPessoaPorCpf("00000000000")).thenReturn(Optional.empty());

        Optional<PessoaModel> resultado = pessoaService.buscaPessoaPorCpf("00000000000");

        assertFalse(resultado.isPresent());
        assertTrue(resultado.isEmpty());
        verify(pessoaRepositoryPort, times(1)).buscarPessoaPorCpf("00000000000");
    }

    @Test
    void deveBuscarFuncionarioPorCpf() {
        when(pessoaRepositoryPort.buscarPessoaPorCpf("98765432100")).thenReturn(Optional.of(pessoaFuncionario));

        Optional<PessoaModel> resultado = pessoaService.buscaPessoaPorCpf("98765432100");

        assertTrue(resultado.isPresent());
        assertEquals(TipoPessoaEnum.FUNCIONARIO, resultado.get().getTpPessoa());
        assertEquals("Maria Santos", resultado.get().getNmPessoa());
        verify(pessoaRepositoryPort, times(1)).buscarPessoaPorCpf("98765432100");
    }

    @Test
    void deveValidarFluxoCompletoDeCadastro() {
        String cpf = "11111111111";
        PessoaModel novaPessoa = new PessoaModel.Builder()
                .setCdDocPessoa(cpf)
                .setNmPessoa("Pedro Costa")
                .setTpPessoa(TipoPessoaEnum.CLIENTE)
                .setDsEmail("pedro@email.com")
                .build();

        when(pessoaRepositoryPort.buscarPessoaPorCpf(cpf)).thenReturn(Optional.empty());
        when(pessoaRepositoryPort.cadastrarPessoa(novaPessoa)).thenReturn(novaPessoa);

        PessoaModel resultado = pessoaService.cadastraPessoa(novaPessoa);

        assertNotNull(resultado);
        assertEquals(cpf, resultado.getCdDocPessoa());
        
        verify(pessoaRepositoryPort, times(1)).buscarPessoaPorCpf(cpf);
        verify(pessoaRepositoryPort, times(1)).cadastrarPessoa(novaPessoa);
        verifyNoMoreInteractions(pessoaRepositoryPort);
    }

    @Test
    void deveVerificarCpfAntesDeInserirNoBanco() {
        when(pessoaRepositoryPort.buscarPessoaPorCpf("12345678900")).thenReturn(Optional.of(pessoaCliente));

        assertThrows(CpfCadastradoException.class, () -> {
            pessoaService.cadastraPessoa(pessoaCliente);
        });

        verify(pessoaRepositoryPort, times(1)).buscarPessoaPorCpf("12345678900");
        verify(pessoaRepositoryPort, never()).cadastrarPessoa(any(PessoaModel.class));
    }

    @Test
    void deveCadastrarQuandoBuscaRetornaVazio() {
        when(pessoaRepositoryPort.buscarPessoaPorCpf(anyString())).thenReturn(Optional.empty());
        when(pessoaRepositoryPort.cadastrarPessoa(any(PessoaModel.class))).thenReturn(pessoaCliente);

        PessoaModel resultado = pessoaService.cadastraPessoa(pessoaCliente);

        assertNotNull(resultado);
        verify(pessoaRepositoryPort, times(1)).buscarPessoaPorCpf(anyString());
        verify(pessoaRepositoryPort, times(1)).cadastrarPessoa(any(PessoaModel.class));
    }

    @Test
    void devePermitirCadastrarDiferentesCpfs() {
        PessoaModel pessoa1 = new PessoaModel.Builder()
                .setCdDocPessoa("11111111111")
                .setNmPessoa("Pessoa 1")
                .setTpPessoa(TipoPessoaEnum.CLIENTE)
                .setDsEmail("pessoa1@email.com")
                .build();

        PessoaModel pessoa2 = new PessoaModel.Builder()
                .setCdDocPessoa("22222222222")
                .setNmPessoa("Pessoa 2")
                .setTpPessoa(TipoPessoaEnum.CLIENTE)
                .setDsEmail("pessoa2@email.com")
                .build();

        when(pessoaRepositoryPort.buscarPessoaPorCpf("11111111111")).thenReturn(Optional.empty());
        when(pessoaRepositoryPort.buscarPessoaPorCpf("22222222222")).thenReturn(Optional.empty());
        when(pessoaRepositoryPort.cadastrarPessoa(pessoa1)).thenReturn(pessoa1);
        when(pessoaRepositoryPort.cadastrarPessoa(pessoa2)).thenReturn(pessoa2);

        PessoaModel resultado1 = pessoaService.cadastraPessoa(pessoa1);
        PessoaModel resultado2 = pessoaService.cadastraPessoa(pessoa2);

        assertNotNull(resultado1);
        assertNotNull(resultado2);
        assertNotEquals(resultado1.getCdDocPessoa(), resultado2.getCdDocPessoa());
        
        verify(pessoaRepositoryPort, times(1)).cadastrarPessoa(pessoa1);
        verify(pessoaRepositoryPort, times(1)).cadastrarPessoa(pessoa2);
    }

    @Test
    void deveDelegarBuscaParaRepositorio() {
        String cpf = "33333333333";
        when(pessoaRepositoryPort.buscarPessoaPorCpf(cpf)).thenReturn(Optional.empty());

        Optional<PessoaModel> resultado = pessoaService.buscaPessoaPorCpf(cpf);

        assertFalse(resultado.isPresent());
        verify(pessoaRepositoryPort, times(1)).buscarPessoaPorCpf(cpf);
    }

    @Test
    void deveDelegarCadastroParaRepositorioAposValidacao() {
        when(pessoaRepositoryPort.buscarPessoaPorCpf(anyString())).thenReturn(Optional.empty());
        when(pessoaRepositoryPort.cadastrarPessoa(pessoaCliente)).thenReturn(pessoaCliente);

        PessoaModel resultado = pessoaService.cadastraPessoa(pessoaCliente);

        assertNotNull(resultado);
        verify(pessoaRepositoryPort, times(1)).cadastrarPessoa(pessoaCliente);
    }

    @Test
    void deveManterMensagemDeErroEspecificaParaCpfDuplicado() {
        when(pessoaRepositoryPort.buscarPessoaPorCpf(anyString())).thenReturn(Optional.of(pessoaCliente));

        CpfCadastradoException exception = assertThrows(CpfCadastradoException.class, () -> {
            pessoaService.cadastraPessoa(pessoaCliente);
        });

        assertEquals("Cliente ja cadastrado com o CPF informado", exception.getMessage());
    }

    @Test
    void deveUsarCpfDaPessoaParaBuscarDuplicidade() {
        PessoaModel pessoaComCpfEspecifico = new PessoaModel.Builder()
                .setCdDocPessoa("44444444444")
                .setNmPessoa("Teste")
                .setTpPessoa(TipoPessoaEnum.CLIENTE)
                .setDsEmail("teste@email.com")
                .build();

        when(pessoaRepositoryPort.buscarPessoaPorCpf("44444444444")).thenReturn(Optional.of(pessoaComCpfEspecifico));

        assertThrows(CpfCadastradoException.class, () -> {
            pessoaService.cadastraPessoa(pessoaComCpfEspecifico);
        });

        verify(pessoaRepositoryPort, times(1)).buscarPessoaPorCpf("44444444444");
    }
}

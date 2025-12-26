package br.com.fiap.infraestructure.persistence.repositories;

import br.com.fiap.fasfoodpessoas.domain.enums.TipoPessoaEnum;
import br.com.fiap.fasfoodpessoas.domain.models.PessoaModel;
import br.com.fiap.fasfoodpessoas.infraestructure.persistence.entities.PessoaEntity;
import br.com.fiap.fasfoodpessoas.infraestructure.persistence.repositories.PessoaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.Key;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PessoaRepositoryTest {

    @Mock
    private DynamoDbTable<PessoaEntity> tabelaPessoa;

    @Mock
    private software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient enhancedClient;

    private PessoaRepository pessoaRepository;

    private PessoaModel pessoaModelCliente;
    private PessoaModel pessoaModelFuncionario;
    private PessoaEntity pessoaEntityCliente;
    private PessoaEntity pessoaEntityFuncionario;

    @BeforeEach
    void setUp() {
        when(enhancedClient.table(anyString(), any(software.amazon.awssdk.enhanced.dynamodb.TableSchema.class)))
                .thenReturn(tabelaPessoa);
        pessoaRepository = new PessoaRepository(enhancedClient);

        pessoaModelCliente = new PessoaModel.Builder()
                .setCdDocPessoa("12345678900")
                .setNmPessoa("João Silva")
                .setTpPessoa(TipoPessoaEnum.CLIENTE)
                .setDsEmail("joao@email.com")
                .build();

        pessoaModelFuncionario = new PessoaModel.Builder()
                .setCdDocPessoa("98765432100")
                .setNmPessoa("Maria Santos")
                .setTpPessoa(TipoPessoaEnum.FUNCIONARIO)
                .setDsEmail("maria@empresa.com")
                .build();

        pessoaEntityCliente = new PessoaEntity();
        pessoaEntityCliente.setCdDocPessoa("12345678900");
        pessoaEntityCliente.setNmPessoa("João Silva");
        pessoaEntityCliente.setTpPessoa(TipoPessoaEnum.CLIENTE);
        pessoaEntityCliente.setDsEmail("joao@email.com");

        pessoaEntityFuncionario = new PessoaEntity();
        pessoaEntityFuncionario.setCdDocPessoa("98765432100");
        pessoaEntityFuncionario.setNmPessoa("Maria Santos");
        pessoaEntityFuncionario.setTpPessoa(TipoPessoaEnum.FUNCIONARIO);
        pessoaEntityFuncionario.setDsEmail("maria@empresa.com");
    }

    @Test
    void deveCadastrarPessoaComSucesso() {
        PessoaModel resultado = pessoaRepository.cadastrarPessoa(pessoaModelCliente);

        assertNotNull(resultado);
        assertEquals("12345678900", resultado.getCdDocPessoa());
        assertEquals("João Silva", resultado.getNmPessoa());
        assertEquals(TipoPessoaEnum.CLIENTE, resultado.getTpPessoa());
        assertEquals("joao@email.com", resultado.getDsEmail());

        verify(tabelaPessoa, times(1)).putItem(any(PessoaEntity.class));
    }

    @Test
    void deveCadastrarFuncionarioComSucesso() {
        PessoaModel resultado = pessoaRepository.cadastrarPessoa(pessoaModelFuncionario);

        assertNotNull(resultado);
        assertEquals("98765432100", resultado.getCdDocPessoa());
        assertEquals("Maria Santos", resultado.getNmPessoa());
        assertEquals(TipoPessoaEnum.FUNCIONARIO, resultado.getTpPessoa());
        assertEquals("maria@empresa.com", resultado.getDsEmail());

        verify(tabelaPessoa, times(1)).putItem(any(PessoaEntity.class));
    }

    @Test
    void deveBuscarPessoaPorCpfComSucesso() {
        when(tabelaPessoa.getItem(any(Key.class))).thenReturn(pessoaEntityCliente);

        Optional<PessoaModel> resultado = pessoaRepository.buscarPessoaPorCpf("12345678900");

        assertTrue(resultado.isPresent());
        assertEquals("12345678900", resultado.get().getCdDocPessoa());
        assertEquals("João Silva", resultado.get().getNmPessoa());
        assertEquals(TipoPessoaEnum.CLIENTE, resultado.get().getTpPessoa());

        verify(tabelaPessoa, times(1)).getItem(any(Key.class));
    }

    @Test
    void deveRetornarOptionalVazioQuandoCpfNaoEncontrado() {
        when(tabelaPessoa.getItem(any(Key.class))).thenReturn(null);

        Optional<PessoaModel> resultado = pessoaRepository.buscarPessoaPorCpf("00000000000");

        assertFalse(resultado.isPresent());
        assertTrue(resultado.isEmpty());

        verify(tabelaPessoa, times(1)).getItem(any(Key.class));
    }

    @Test
    void deveBuscarFuncionarioPorCpf() {
        when(tabelaPessoa.getItem(any(Key.class))).thenReturn(pessoaEntityFuncionario);

        Optional<PessoaModel> resultado = pessoaRepository.buscarPessoaPorCpf("98765432100");

        assertTrue(resultado.isPresent());
        assertEquals(TipoPessoaEnum.FUNCIONARIO, resultado.get().getTpPessoa());
        assertEquals("Maria Santos", resultado.get().getNmPessoa());

        verify(tabelaPessoa, times(1)).getItem(any(Key.class));
    }

    @Test
    void deveConverterModelParaEntityAoCadastrar() {
        doAnswer(invocation -> {
            PessoaEntity entity = invocation.getArgument(0);
            assertEquals("12345678900", entity.getCdDocPessoa());
            assertEquals("João Silva", entity.getNmPessoa());
            return null;
        }).when(tabelaPessoa).putItem(any(PessoaEntity.class));

        pessoaRepository.cadastrarPessoa(pessoaModelCliente);

        verify(tabelaPessoa, times(1)).putItem(any(PessoaEntity.class));
    }

    @Test
    void deveConverterEntityParaModelAoBuscar() {
        when(tabelaPessoa.getItem(any(Key.class))).thenReturn(pessoaEntityCliente);

        Optional<PessoaModel> resultado = pessoaRepository.buscarPessoaPorCpf("12345678900");

        assertTrue(resultado.isPresent());
        assertInstanceOf(PessoaModel.class, resultado.get());

        verify(tabelaPessoa, times(1)).getItem(any(Key.class));
    }

    @Test
    void deveRetornarModelAposCadastrar() {
        PessoaModel resultado = pessoaRepository.cadastrarPessoa(pessoaModelCliente);

        assertNotNull(resultado);
        assertInstanceOf(PessoaModel.class, resultado);

        verify(tabelaPessoa, times(1)).putItem(any(PessoaEntity.class));
    }

    @Test
    void deveImplementarPessoaRepositoryPort() {
        assertTrue(br.com.fiap.fasfoodpessoas.domain.ports.out.PessoaRepositoryPort.class.isAssignableFrom(PessoaRepository.class));
    }

    @Test
    void deveTerAnotacaoComponent() {
        org.springframework.stereotype.Component annotation = PessoaRepository.class.getAnnotation(org.springframework.stereotype.Component.class);
        assertNotNull(annotation);
    }

    @Test
    void deveDelegarCadastroParaDynamoDbTable() {
        pessoaRepository.cadastrarPessoa(pessoaModelCliente);

        verify(tabelaPessoa, times(1)).putItem(any(PessoaEntity.class));
        verifyNoMoreInteractions(tabelaPessoa);
    }

    @Test
    void deveDelegarBuscaParaDynamoDbTable() {
        when(tabelaPessoa.getItem(any(Key.class))).thenReturn(pessoaEntityCliente);

        pessoaRepository.buscarPessoaPorCpf("12345678900");

        verify(tabelaPessoa, times(1)).getItem(any(Key.class));
        verifyNoMoreInteractions(tabelaPessoa);
    }

    @Test
    void deveCadastrarMultiplasPessoasIndependentemente() {
        PessoaModel resultado1 = pessoaRepository.cadastrarPessoa(pessoaModelCliente);
        PessoaModel resultado2 = pessoaRepository.cadastrarPessoa(pessoaModelFuncionario);

        assertNotNull(resultado1);
        assertNotNull(resultado2);
        assertNotEquals(resultado1.getCdDocPessoa(), resultado2.getCdDocPessoa());

        verify(tabelaPessoa, times(2)).putItem(any(PessoaEntity.class));
    }

    @Test
    void deveBuscarMultiplasPessoasPorCpfDiferentes() {
        when(tabelaPessoa.getItem(any(Key.class)))
                .thenReturn(pessoaEntityCliente)
                .thenReturn(pessoaEntityFuncionario);

        Optional<PessoaModel> resultado1 = pessoaRepository.buscarPessoaPorCpf("12345678900");
        Optional<PessoaModel> resultado2 = pessoaRepository.buscarPessoaPorCpf("98765432100");

        assertTrue(resultado1.isPresent());
        assertTrue(resultado2.isPresent());
        assertNotEquals(resultado1.get().getCdDocPessoa(), resultado2.get().getCdDocPessoa());

        verify(tabelaPessoa, times(2)).getItem(any(Key.class));
    }

    @Test
    void deveManterDadosAposCadastroEBusca() {
        when(tabelaPessoa.getItem(any(Key.class))).thenReturn(pessoaEntityCliente);

        PessoaModel cadastrada = pessoaRepository.cadastrarPessoa(pessoaModelCliente);
        Optional<PessoaModel> buscada = pessoaRepository.buscarPessoaPorCpf("12345678900");

        assertTrue(buscada.isPresent());
        assertEquals(cadastrada.getCdDocPessoa(), buscada.get().getCdDocPessoa());
        assertEquals(cadastrada.getNmPessoa(), buscada.get().getNmPessoa());
    }

    @Test
    void devePreservarTipoPessoaAoCadastrar() {
        PessoaModel resultado = pessoaRepository.cadastrarPessoa(pessoaModelCliente);

        assertEquals(TipoPessoaEnum.CLIENTE, resultado.getTpPessoa());
    }

    @Test
    void devePreservarTipoPessoaAoBuscar() {
        when(tabelaPessoa.getItem(any(Key.class))).thenReturn(pessoaEntityFuncionario);

        Optional<PessoaModel> resultado = pessoaRepository.buscarPessoaPorCpf("98765432100");

        assertTrue(resultado.isPresent());
        assertEquals(TipoPessoaEnum.FUNCIONARIO, resultado.get().getTpPessoa());
    }

    @Test
    void deveCadastrarPessoaComTodosOsCampos() {
        PessoaModel pessoaCompleta = new PessoaModel.Builder()
                .setCdDocPessoa("11111111111")
                .setNmPessoa("Pedro Costa")
                .setTpPessoa(TipoPessoaEnum.CLIENTE)
                .setDsEmail("pedro@email.com")
                .build();

        PessoaModel resultado = pessoaRepository.cadastrarPessoa(pessoaCompleta);

        assertEquals("11111111111", resultado.getCdDocPessoa());
        assertEquals("Pedro Costa", resultado.getNmPessoa());
        assertEquals(TipoPessoaEnum.CLIENTE, resultado.getTpPessoa());
        assertEquals("pedro@email.com", resultado.getDsEmail());
    }

    @Test
    void deveBuscarPessoaComTodosOsCamposPreenchidos() {
        when(tabelaPessoa.getItem(any(Key.class))).thenReturn(pessoaEntityCliente);

        Optional<PessoaModel> resultado = pessoaRepository.buscarPessoaPorCpf("12345678900");

        assertTrue(resultado.isPresent());
        assertNotNull(resultado.get().getCdDocPessoa());
        assertNotNull(resultado.get().getNmPessoa());
        assertNotNull(resultado.get().getTpPessoa());
        assertNotNull(resultado.get().getDsEmail());
    }

    @Test
    void deveUsarMapperParaConversoes() {
        PessoaModel resultado = pessoaRepository.cadastrarPessoa(pessoaModelCliente);

        // Verifica que a conversão foi feita corretamente
        assertNotNull(resultado);
        assertEquals(pessoaModelCliente.getCdDocPessoa(), resultado.getCdDocPessoa());
    }

    @Test
    void deveRetornarOptionalDeModelAoBuscar() {
        when(tabelaPessoa.getItem(any(Key.class))).thenReturn(pessoaEntityCliente);

        Optional<PessoaModel> resultado = pessoaRepository.buscarPessoaPorCpf("12345678900");

        assertInstanceOf(Optional.class, resultado);
        assertTrue(resultado.isPresent());
        assertInstanceOf(PessoaModel.class, resultado.get());
    }
}

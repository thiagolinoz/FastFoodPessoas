package com.fiap.fasfoodpessoas.infraestructure.persistence.repositories;

import br.com.fiap.fasfoodpessoas.domain.enums.TipoPessoaEnum;
import br.com.fiap.fasfoodpessoas.domain.models.PessoaModel;
import br.com.fiap.fasfoodpessoas.domain.ports.out.PessoaRepositoryPort;
import br.com.fiap.fasfoodpessoas.infraestructure.persistence.entities.PessoaEntity;
import br.com.fiap.fasfoodpessoas.infraestructure.persistence.repositories.PessoaRepository;
import br.com.fiap.fasfoodpessoas.infraestructure.persistence.repositories.dynamo.PessoaMongoDBRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.stereotype.Component;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PessoaRepositoryTest {

   /* @Mock
    private PessoaMongoDBRepository pessoaMongoDBRepository;

    @InjectMocks
    private PessoaRepository pessoaRepository;

    private PessoaModel pessoaModelCliente;
    private PessoaModel pessoaModelFuncionario;
    private PessoaEntity pessoaEntityCliente;
    private PessoaEntity pessoaEntityFuncionario;

    @BeforeEach
    void setUp() {
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
        pessoaEntityCliente.setId("1");
        pessoaEntityCliente.setCdDocPessoa("12345678900");
        pessoaEntityCliente.setNmPessoa("João Silva");
        pessoaEntityCliente.setTpPessoa(TipoPessoaEnum.CLIENTE);
        pessoaEntityCliente.setDsEmail("joao@email.com");

        pessoaEntityFuncionario = new PessoaEntity();
        pessoaEntityFuncionario.setId("2");
        pessoaEntityFuncionario.setCdDocPessoa("98765432100");
        pessoaEntityFuncionario.setNmPessoa("Maria Santos");
        pessoaEntityFuncionario.setTpPessoa(TipoPessoaEnum.FUNCIONARIO);
        pessoaEntityFuncionario.setDsEmail("maria@empresa.com");
    }

    @Test
    void deveCadastrarPessoaComSucesso() {
        when(pessoaMongoDBRepository.save(any(PessoaEntity.class))).thenReturn(pessoaEntityCliente);

        PessoaModel resultado = pessoaRepository.cadastrarPessoa(pessoaModelCliente);

        assertNotNull(resultado);
        assertEquals("12345678900", resultado.getCdDocPessoa());
        assertEquals("João Silva", resultado.getNmPessoa());
        assertEquals(TipoPessoaEnum.CLIENTE, resultado.getTpPessoa());
        assertEquals("joao@email.com", resultado.getDsEmail());

        verify(pessoaMongoDBRepository, times(1)).save(any(PessoaEntity.class));
    }

    @Test
    void deveCadastrarFuncionarioComSucesso() {
        when(pessoaMongoDBRepository.save(any(PessoaEntity.class))).thenReturn(pessoaEntityFuncionario);

        PessoaModel resultado = pessoaRepository.cadastrarPessoa(pessoaModelFuncionario);

        assertNotNull(resultado);
        assertEquals("98765432100", resultado.getCdDocPessoa());
        assertEquals("Maria Santos", resultado.getNmPessoa());
        assertEquals(TipoPessoaEnum.FUNCIONARIO, resultado.getTpPessoa());
        assertEquals("maria@empresa.com", resultado.getDsEmail());

        verify(pessoaMongoDBRepository, times(1)).save(any(PessoaEntity.class));
    }

    @Test
    void deveBuscarPessoaPorCpfComSucesso() {
        when(pessoaMongoDBRepository.findByCdDocPessoa("12345678900")).thenReturn(pessoaEntityCliente);

        Optional<PessoaModel> resultado = pessoaRepository.buscarPessoaPorCpf("12345678900");

        assertTrue(resultado.isPresent());
        assertEquals("12345678900", resultado.get().getCdDocPessoa());
        assertEquals("João Silva", resultado.get().getNmPessoa());
        assertEquals(TipoPessoaEnum.CLIENTE, resultado.get().getTpPessoa());

        verify(pessoaMongoDBRepository, times(1)).findByCdDocPessoa("12345678900");
    }

    @Test
    void deveRetornarOptionalVazioQuandoCpfNaoEncontrado() {
        when(pessoaMongoDBRepository.findByCdDocPessoa("00000000000")).thenReturn(null);

        Optional<PessoaModel> resultado = pessoaRepository.buscarPessoaPorCpf("00000000000");

        assertFalse(resultado.isPresent());
        assertTrue(resultado.isEmpty());

        verify(pessoaMongoDBRepository, times(1)).findByCdDocPessoa("00000000000");
    }

    @Test
    void deveBuscarFuncionarioPorCpf() {
        when(pessoaMongoDBRepository.findByCdDocPessoa("98765432100")).thenReturn(pessoaEntityFuncionario);

        Optional<PessoaModel> resultado = pessoaRepository.buscarPessoaPorCpf("98765432100");

        assertTrue(resultado.isPresent());
        assertEquals(TipoPessoaEnum.FUNCIONARIO, resultado.get().getTpPessoa());
        assertEquals("Maria Santos", resultado.get().getNmPessoa());

        verify(pessoaMongoDBRepository, times(1)).findByCdDocPessoa("98765432100");
    }

    @Test
    void deveConverterModelParaEntityAoCadastrar() {
        when(pessoaMongoDBRepository.save(any(PessoaEntity.class))).thenAnswer(invocation -> {
            PessoaEntity entity = invocation.getArgument(0);
            assertEquals("12345678900", entity.getCdDocPessoa());
            assertEquals("João Silva", entity.getNmPessoa());
            return entity;
        });

        pessoaRepository.cadastrarPessoa(pessoaModelCliente);

        verify(pessoaMongoDBRepository, times(1)).save(any(PessoaEntity.class));
    }

    @Test
    void deveConverterEntityParaModelAoBuscar() {
        when(pessoaMongoDBRepository.findByCdDocPessoa("12345678900")).thenReturn(pessoaEntityCliente);

        Optional<PessoaModel> resultado = pessoaRepository.buscarPessoaPorCpf("12345678900");

        assertTrue(resultado.isPresent());
        assertInstanceOf(PessoaModel.class, resultado.get());

        verify(pessoaMongoDBRepository, times(1)).findByCdDocPessoa("12345678900");
    }

    @Test
    void deveRetornarModelAposCadastrar() {
        when(pessoaMongoDBRepository.save(any(PessoaEntity.class))).thenReturn(pessoaEntityCliente);

        PessoaModel resultado = pessoaRepository.cadastrarPessoa(pessoaModelCliente);

        assertNotNull(resultado);
        assertInstanceOf(PessoaModel.class, resultado);

        verify(pessoaMongoDBRepository, times(1)).save(any(PessoaEntity.class));
    }

    @Test
    void deveImplementarPessoaRepositoryPort() {
        assertTrue(PessoaRepositoryPort.class.isAssignableFrom(PessoaRepository.class));
    }

    @Test
    void deveTerAnotacaoComponent() {
        Component annotation = PessoaRepository.class.getAnnotation(Component.class);
        assertNotNull(annotation);
    }

    @Test
    void deveDelegarCadastroParaMongoRepository() {
        when(pessoaMongoDBRepository.save(any(PessoaEntity.class))).thenReturn(pessoaEntityCliente);

        pessoaRepository.cadastrarPessoa(pessoaModelCliente);

        verify(pessoaMongoDBRepository, times(1)).save(any(PessoaEntity.class));
        verifyNoMoreInteractions(pessoaMongoDBRepository);
    }

    @Test
    void deveDelegarBuscaParaMongoRepository() {
        when(pessoaMongoDBRepository.findByCdDocPessoa(anyString())).thenReturn(pessoaEntityCliente);

        pessoaRepository.buscarPessoaPorCpf("12345678900");

        verify(pessoaMongoDBRepository, times(1)).findByCdDocPessoa("12345678900");
        verifyNoMoreInteractions(pessoaMongoDBRepository);
    }

    @Test
    void deveCadastrarMultiplasPessoasIndependentemente() {
        when(pessoaMongoDBRepository.save(any(PessoaEntity.class)))
                .thenReturn(pessoaEntityCliente)
                .thenReturn(pessoaEntityFuncionario);

        PessoaModel resultado1 = pessoaRepository.cadastrarPessoa(pessoaModelCliente);
        PessoaModel resultado2 = pessoaRepository.cadastrarPessoa(pessoaModelFuncionario);

        assertNotNull(resultado1);
        assertNotNull(resultado2);
        assertNotEquals(resultado1.getCdDocPessoa(), resultado2.getCdDocPessoa());

        verify(pessoaMongoDBRepository, times(2)).save(any(PessoaEntity.class));
    }

    @Test
    void deveBuscarMultiplasPessoasPorCpfDiferentes() {
        when(pessoaMongoDBRepository.findByCdDocPessoa("12345678900")).thenReturn(pessoaEntityCliente);
        when(pessoaMongoDBRepository.findByCdDocPessoa("98765432100")).thenReturn(pessoaEntityFuncionario);

        Optional<PessoaModel> resultado1 = pessoaRepository.buscarPessoaPorCpf("12345678900");
        Optional<PessoaModel> resultado2 = pessoaRepository.buscarPessoaPorCpf("98765432100");

        assertTrue(resultado1.isPresent());
        assertTrue(resultado2.isPresent());
        assertNotEquals(resultado1.get().getCdDocPessoa(), resultado2.get().getCdDocPessoa());

        verify(pessoaMongoDBRepository, times(1)).findByCdDocPessoa("12345678900");
        verify(pessoaMongoDBRepository, times(1)).findByCdDocPessoa("98765432100");
    }

    @Test
    void deveManterDadosAposCadastroEBusca() {
        when(pessoaMongoDBRepository.save(any(PessoaEntity.class))).thenReturn(pessoaEntityCliente);
        when(pessoaMongoDBRepository.findByCdDocPessoa("12345678900")).thenReturn(pessoaEntityCliente);

        PessoaModel cadastrada = pessoaRepository.cadastrarPessoa(pessoaModelCliente);
        Optional<PessoaModel> buscada = pessoaRepository.buscarPessoaPorCpf("12345678900");

        assertTrue(buscada.isPresent());
        assertEquals(cadastrada.getCdDocPessoa(), buscada.get().getCdDocPessoa());
        assertEquals(cadastrada.getNmPessoa(), buscada.get().getNmPessoa());
    }

    @Test
    void devePreservarTipoPessoaAoCadastrar() {
        when(pessoaMongoDBRepository.save(any(PessoaEntity.class))).thenReturn(pessoaEntityCliente);

        PessoaModel resultado = pessoaRepository.cadastrarPessoa(pessoaModelCliente);

        assertEquals(TipoPessoaEnum.CLIENTE, resultado.getTpPessoa());
    }

    @Test
    void devePreservarTipoPessoaAoBuscar() {
        when(pessoaMongoDBRepository.findByCdDocPessoa("98765432100")).thenReturn(pessoaEntityFuncionario);

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

        PessoaEntity entityCompleta = new PessoaEntity();
        entityCompleta.setCdDocPessoa("11111111111");
        entityCompleta.setNmPessoa("Pedro Costa");
        entityCompleta.setTpPessoa(TipoPessoaEnum.CLIENTE);
        entityCompleta.setDsEmail("pedro@email.com");

        when(pessoaMongoDBRepository.save(any(PessoaEntity.class))).thenReturn(entityCompleta);

        PessoaModel resultado = pessoaRepository.cadastrarPessoa(pessoaCompleta);

        assertEquals("11111111111", resultado.getCdDocPessoa());
        assertEquals("Pedro Costa", resultado.getNmPessoa());
        assertEquals(TipoPessoaEnum.CLIENTE, resultado.getTpPessoa());
        assertEquals("pedro@email.com", resultado.getDsEmail());
    }

    @Test
    void deveBuscarPessoaComTodosOsCamposPreenchidos() {
        when(pessoaMongoDBRepository.findByCdDocPessoa("12345678900")).thenReturn(pessoaEntityCliente);

        Optional<PessoaModel> resultado = pessoaRepository.buscarPessoaPorCpf("12345678900");

        assertTrue(resultado.isPresent());
        assertNotNull(resultado.get().getCdDocPessoa());
        assertNotNull(resultado.get().getNmPessoa());
        assertNotNull(resultado.get().getTpPessoa());
        assertNotNull(resultado.get().getDsEmail());
    }

    @Test
    void deveUsarMapperParaConversoes() {
        when(pessoaMongoDBRepository.save(any(PessoaEntity.class))).thenReturn(pessoaEntityCliente);

        PessoaModel resultado = pessoaRepository.cadastrarPessoa(pessoaModelCliente);

        // Verifica que a conversão foi feita corretamente
        assertNotNull(resultado);
        assertEquals(pessoaModelCliente.getCdDocPessoa(), resultado.getCdDocPessoa());
    }

    @Test
    void deveRetornarOptionalDeModelAoBuscar() {
        when(pessoaMongoDBRepository.findByCdDocPessoa("12345678900")).thenReturn(pessoaEntityCliente);

        Optional<PessoaModel> resultado = pessoaRepository.buscarPessoaPorCpf("12345678900");

        assertInstanceOf(Optional.class, resultado);
        assertTrue(resultado.isPresent());
        assertInstanceOf(PessoaModel.class, resultado.get());
    }*/
}

package com.fiap.fasfoodpessoas.infraestructure.persistence.repositories.mongo;

import br.com.fiap.fasfoodpessoas.domain.enums.TipoPessoaEnum;
import br.com.fiap.fasfoodpessoas.infraestructure.persistence.entities.PessoaEntity;
import br.com.fiap.fasfoodpessoas.infraestructure.persistence.repositories.dynamo.PessoaMongoDBRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class PessoaMongoDBRepositoryTest {

    private PessoaMongoDBRepository repository;
    private PessoaEntity pessoaCliente;
    private PessoaEntity pessoaFuncionario;

   /* @BeforeEach
    void setUp() {
        repository = mock(PessoaMongoDBRepository.class);

        pessoaCliente = new PessoaEntity();
        pessoaCliente.setId("1");
        pessoaCliente.setCdDocPessoa("12345678900");
        pessoaCliente.setNmPessoa("João Silva");
        pessoaCliente.setTpPessoa(TipoPessoaEnum.CLIENTE);
        pessoaCliente.setDsEmail("joao@email.com");

        pessoaFuncionario = new PessoaEntity();
        pessoaFuncionario.setId("2");
        pessoaFuncionario.setCdDocPessoa("98765432100");
        pessoaFuncionario.setNmPessoa("Maria Santos");
        pessoaFuncionario.setTpPessoa(TipoPessoaEnum.FUNCIONARIO);
        pessoaFuncionario.setDsEmail("maria@empresa.com");
    }

    @Test
    void deveSerUmaInterface() {
        assertTrue(PessoaMongoDBRepository.class.isInterface());
    }

    @Test
    void deveEstenderMongoRepository() {
        assertTrue(MongoRepository.class.isAssignableFrom(PessoaMongoDBRepository.class));
    }

    @Test
    void deveTerAnotacaoRepository() {
        Repository annotation = PessoaMongoDBRepository.class.getAnnotation(Repository.class);
        assertNotNull(annotation);
    }

    @Test
    void deveDefinirMetodoFindByCdDocPessoa() throws NoSuchMethodException {
        Method method = PessoaMongoDBRepository.class.getMethod("findByCdDocPessoa", String.class);
        
        assertNotNull(method);
        assertEquals(PessoaEntity.class, method.getReturnType());
        assertEquals(1, method.getParameterCount());
        assertEquals(String.class, method.getParameterTypes()[0]);
    }

    @Test
    void deveBuscarPessoaPorCpfComSucesso() {
        when(repository.findByCdDocPessoa("12345678900")).thenReturn(pessoaCliente);

        PessoaEntity resultado = repository.findByCdDocPessoa("12345678900");

        assertNotNull(resultado);
        assertEquals("12345678900", resultado.getCdDocPessoa());
        assertEquals("João Silva", resultado.getNmPessoa());
        verify(repository, times(1)).findByCdDocPessoa("12345678900");
    }

    @Test
    void deveRetornarNullQuandoCpfNaoEncontrado() {
        when(repository.findByCdDocPessoa("00000000000")).thenReturn(null);

        PessoaEntity resultado = repository.findByCdDocPessoa("00000000000");

        assertNull(resultado);
        verify(repository, times(1)).findByCdDocPessoa("00000000000");
    }

    @Test
    void deveSalvarPessoaComSucesso() {
        when(repository.save(any(PessoaEntity.class))).thenReturn(pessoaCliente);

        PessoaEntity resultado = repository.save(pessoaCliente);

        assertNotNull(resultado);
        assertEquals("12345678900", resultado.getCdDocPessoa());
        verify(repository, times(1)).save(pessoaCliente);
    }

    @Test
    void deveBuscarPorIdComSucesso() {
        when(repository.findById("1")).thenReturn(Optional.of(pessoaCliente));

        Optional<PessoaEntity> resultado = repository.findById("1");

        assertTrue(resultado.isPresent());
        assertEquals("1", resultado.get().getId());
        verify(repository, times(1)).findById("1");
    }

    @Test
    void deveRetornarOptionalVazioQuandoIdNaoEncontrado() {
        when(repository.findById("999")).thenReturn(Optional.empty());

        Optional<PessoaEntity> resultado = repository.findById("999");

        assertFalse(resultado.isPresent());
        verify(repository, times(1)).findById("999");
    }

    @Test
    void deveListarTodasAsPessoas() {
        List<PessoaEntity> pessoas = List.of(pessoaCliente, pessoaFuncionario);
        when(repository.findAll()).thenReturn(pessoas);

        List<PessoaEntity> resultado = repository.findAll();

        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        verify(repository, times(1)).findAll();
    }

    @Test
    void deveDeletarPessoaPorId() {
        doNothing().when(repository).deleteById("1");

        repository.deleteById("1");

        verify(repository, times(1)).deleteById("1");
    }

    @Test
    void deveDeletarPessoaPorEntidade() {
        doNothing().when(repository).delete(pessoaCliente);

        repository.delete(pessoaCliente);

        verify(repository, times(1)).delete(pessoaCliente);
    }

    @Test
    void deveVerificarSeExistePorId() {
        when(repository.existsById("1")).thenReturn(true);

        boolean resultado = repository.existsById("1");

        assertTrue(resultado);
        verify(repository, times(1)).existsById("1");
    }

    @Test
    void deveRetornarFalseQuandoNaoExistePorId() {
        when(repository.existsById("999")).thenReturn(false);

        boolean resultado = repository.existsById("999");

        assertFalse(resultado);
        verify(repository, times(1)).existsById("999");
    }

    @Test
    void deveContarTotalDePessoas() {
        when(repository.count()).thenReturn(10L);

        long resultado = repository.count();

        assertEquals(10L, resultado);
        verify(repository, times(1)).count();
    }

    @Test
    void deveBuscarClientePorCpf() {
        when(repository.findByCdDocPessoa("12345678900")).thenReturn(pessoaCliente);

        PessoaEntity resultado = repository.findByCdDocPessoa("12345678900");

        assertNotNull(resultado);
        assertEquals(TipoPessoaEnum.CLIENTE, resultado.getTpPessoa());
        verify(repository, times(1)).findByCdDocPessoa("12345678900");
    }

    @Test
    void deveBuscarFuncionarioPorCpf() {
        when(repository.findByCdDocPessoa("98765432100")).thenReturn(pessoaFuncionario);

        PessoaEntity resultado = repository.findByCdDocPessoa("98765432100");

        assertNotNull(resultado);
        assertEquals(TipoPessoaEnum.FUNCIONARIO, resultado.getTpPessoa());
        assertEquals("Maria Santos", resultado.getNmPessoa());
        verify(repository, times(1)).findByCdDocPessoa("98765432100");
    }

    @Test
    void deveSalvarMultiplasPessoas() {
        List<PessoaEntity> pessoas = List.of(pessoaCliente, pessoaFuncionario);
        when(repository.saveAll(pessoas)).thenReturn(pessoas);

        List<PessoaEntity> resultado = repository.saveAll(pessoas);

        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        verify(repository, times(1)).saveAll(pessoas);
    }

    @Test
    void deveDeletarTodasAsPessoas() {
        doNothing().when(repository).deleteAll();

        repository.deleteAll();

        verify(repository, times(1)).deleteAll();
    }

    @Test
    void deveAtualizarPessoaExistente() {
        pessoaCliente.setNmPessoa("João Silva Junior");
        when(repository.save(pessoaCliente)).thenReturn(pessoaCliente);

        PessoaEntity resultado = repository.save(pessoaCliente);

        assertNotNull(resultado);
        assertEquals("João Silva Junior", resultado.getNmPessoa());
        verify(repository, times(1)).save(pessoaCliente);
    }

    @Test
    void deveBuscarPorCpfComDiferentesFormatos() {
        when(repository.findByCdDocPessoa(anyString())).thenReturn(pessoaCliente);

        PessoaEntity resultado1 = repository.findByCdDocPessoa("12345678900");
        PessoaEntity resultado2 = repository.findByCdDocPessoa("123.456.789-00");

        assertNotNull(resultado1);
        assertNotNull(resultado2);
        verify(repository, times(2)).findByCdDocPessoa(anyString());
    }

    @Test
    void deveRetornarListaVaziaQuandoNaoHaPessoas() {
        when(repository.findAll()).thenReturn(List.of());

        List<PessoaEntity> resultado = repository.findAll();

        assertNotNull(resultado);
        assertTrue(resultado.isEmpty());
        verify(repository, times(1)).findAll();
    }

    @Test
    void deveUsarTipoPessoaEntityComoGenerico() {
        // Verifica se a interface usa PessoaEntity como tipo genérico
        Class<?>[] interfaces = PessoaMongoDBRepository.class.getInterfaces();
        
        assertTrue(interfaces.length > 0);
        assertEquals(MongoRepository.class, interfaces[0]);
    }

    @Test
    void deveUsarStringComoTipoDoId() throws NoSuchMethodException {
        Method findByIdMethod = MongoRepository.class.getMethod("findById", Object.class);
        
        assertNotNull(findByIdMethod);
        // MongoRepository usa ID como genérico, que é String neste caso
    }

    @Test
    void devePermitirBuscaPorCpfNulo() {
        when(repository.findByCdDocPessoa(null)).thenReturn(null);

        PessoaEntity resultado = repository.findByCdDocPessoa(null);

        assertNull(resultado);
        verify(repository, times(1)).findByCdDocPessoa(null);
    }

    @Test
    void devePermitirBuscaPorCpfVazio() {
        when(repository.findByCdDocPessoa("")).thenReturn(null);

        PessoaEntity resultado = repository.findByCdDocPessoa("");

        assertNull(resultado);
        verify(repository, times(1)).findByCdDocPessoa("");
    }

    @Test
    void deveTerMetodoPersonalizadoFindByCdDocPessoa() {
        Method[] methods = PessoaMongoDBRepository.class.getDeclaredMethods();
        
        boolean hasMethod = false;
        for (Method method : methods) {
            if (method.getName().equals("findByCdDocPessoa")) {
                hasMethod = true;
                break;
            }
        }
        
        assertTrue(hasMethod);
    }

    @Test
    void deveEstarNoPacoteCorreto() {
        String packageName = PessoaMongoDBRepository.class.getPackageName();
        
        assertEquals("br.com.fiap.fasfoodpessoas.infraestructure.persistence.repositories.mongo", packageName);
    }*/
}

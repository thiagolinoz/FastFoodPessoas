package br.com.fiap.infraestructure.web.api.exceptions;

import br.com.fiap.fasfoodpessoas.infraestructure.web.api.exceptions.CpfCadastradoException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CpfCadastradoExceptionTest {

    @Test
    void deveCriarExcecaoComMensagem() {
        String mensagem = "Cliente ja cadastrado com o CPF informado";

        CpfCadastradoException exception = new CpfCadastradoException(mensagem);

        assertNotNull(exception);
        assertEquals(mensagem, exception.getMessage());
    }

    @Test
    void deveCriarExcecaoComMensagemECausa() {
        String mensagem = "Cliente ja cadastrado com o CPF informado";
        Throwable causa = new RuntimeException("Causa original");

        CpfCadastradoException exception = new CpfCadastradoException(mensagem, causa);

        assertNotNull(exception);
        assertEquals(mensagem, exception.getMessage());
        assertEquals(causa, exception.getCause());
    }

    @Test
    void deveEstenderRuntimeException() {
        CpfCadastradoException exception = new CpfCadastradoException("Mensagem");

        assertTrue(exception instanceof RuntimeException);
    }

    @Test
    void deveSerExcecaoNaoVerificada() {
        // RuntimeException é unchecked, então pode ser lançada sem declaração
        assertThrows(CpfCadastradoException.class, () -> {
            throw new CpfCadastradoException("Teste");
        });
    }

    @Test
    void deveLancarExcecaoComMensagemPadrao() {
        String mensagemPadrao = "Cliente ja cadastrado com o CPF informado";

        CpfCadastradoException exception = assertThrows(CpfCadastradoException.class, () -> {
            throw new CpfCadastradoException(mensagemPadrao);
        });

        assertEquals(mensagemPadrao, exception.getMessage());
    }

    @Test
    void deveLancarExcecaoComMensagemPersonalizada() {
        String mensagemPersonalizada = "CPF 12345678900 ja está cadastrado no sistema";

        CpfCadastradoException exception = assertThrows(CpfCadastradoException.class, () -> {
            throw new CpfCadastradoException(mensagemPersonalizada);
        });

        assertEquals(mensagemPersonalizada, exception.getMessage());
    }

    @Test
    void devePreservarMensagemOriginal() {
        String mensagem = "Teste de mensagem";
        CpfCadastradoException exception = new CpfCadastradoException(mensagem);

        assertEquals(mensagem, exception.getMessage());
    }

    @Test
    void devePreservarCausaOriginal() {
        Exception causaOriginal = new IllegalArgumentException("Erro de validação");
        CpfCadastradoException exception = new CpfCadastradoException("Mensagem", causaOriginal);

        assertEquals(causaOriginal, exception.getCause());
        assertEquals("Erro de validação", exception.getCause().getMessage());
    }

    @Test
    void deveCriarExcecaoComMensagemNull() {
        CpfCadastradoException exception = new CpfCadastradoException(null);

        assertNotNull(exception);
        assertNull(exception.getMessage());
    }

    @Test
    void deveCriarExcecaoComMensagemECausaNull() {
        CpfCadastradoException exception = new CpfCadastradoException("Mensagem", null);

        assertNotNull(exception);
        assertEquals("Mensagem", exception.getMessage());
        assertNull(exception.getCause());
    }

    @Test
    void deveCriarExcecaoComMensagemVazia() {
        CpfCadastradoException exception = new CpfCadastradoException("");

        assertNotNull(exception);
        assertEquals("", exception.getMessage());
    }

    @Test
    void devePoderSerCapturadaComoRuntimeException() {
        try {
            throw new CpfCadastradoException("Teste");
        } catch (RuntimeException e) {
            assertTrue(e instanceof CpfCadastradoException);
            assertEquals("Teste", e.getMessage());
        }
    }

    @Test
    void devePoderSerCapturadaComoException() {
        try {
            throw new CpfCadastradoException("Teste");
        } catch (Exception e) {
            assertTrue(e instanceof CpfCadastradoException);
            assertEquals("Teste", e.getMessage());
        }
    }

    @Test
    void deveConterStackTrace() {
        CpfCadastradoException exception = new CpfCadastradoException("Mensagem");

        assertNotNull(exception.getStackTrace());
        assertTrue(exception.getStackTrace().length > 0);
    }

    @Test
    void deveTerDoisConstrutores() {
        assertDoesNotThrow(() -> new CpfCadastradoException("Mensagem"));
        assertDoesNotThrow(() -> new CpfCadastradoException("Mensagem", new Exception()));
    }

    @Test
    void deveLancarExcecaoEmContextoDeNegocio() {
        // Simula cenário de negócio
        String cpf = "12345678900";
        
        CpfCadastradoException exception = assertThrows(CpfCadastradoException.class, () -> {
            // Simula validação de CPF duplicado
            boolean cpfJaCadastrado = true;
            if (cpfJaCadastrado) {
                throw new CpfCadastradoException("Cliente ja cadastrado com o CPF informado");
            }
        });

        assertNotNull(exception);
        assertTrue(exception.getMessage().contains("cadastrado"));
    }

    @Test
    void deveEncadearExcecoes() {
        Exception causaRaiz = new IllegalStateException("Estado inválido");
        RuntimeException causaIntermediaria = new RuntimeException("Erro intermediário", causaRaiz);
        CpfCadastradoException exception = new CpfCadastradoException("CPF duplicado", causaIntermediaria);

        assertEquals("CPF duplicado", exception.getMessage());
        assertEquals(causaIntermediaria, exception.getCause());
        assertEquals(causaRaiz, exception.getCause().getCause());
    }

    @Test
    void deveMensagemConterInformacaoRelevante() {
        String mensagem = "Cliente ja cadastrado com o CPF informado";
        CpfCadastradoException exception = new CpfCadastradoException(mensagem);

        assertTrue(exception.getMessage().contains("CPF"));
        assertTrue(exception.getMessage().contains("cadastrado"));
    }

    @Test
    void deveSerLancadaEmServico() {
        // Simula método de serviço
        assertThrows(CpfCadastradoException.class, () -> {
            validarCpfDuplicado("12345678900");
        });
    }

    @Test
    void deveTerMensagemDescritiva() {
        CpfCadastradoException exception = new CpfCadastradoException(
                "Cliente ja cadastrado com o CPF informado"
        );

        assertFalse(exception.getMessage().isEmpty());
        assertTrue(exception.getMessage().length() > 10);
    }

    @Test
    void devePermitirMensagemComCpfEspecifico() {
        String cpf = "12345678900";
        String mensagem = String.format("Cliente com CPF %s ja está cadastrado", cpf);
        
        CpfCadastradoException exception = new CpfCadastradoException(mensagem);

        assertTrue(exception.getMessage().contains(cpf));
    }

    @Test
    void deveSerExcecaoNaoRecuperavel() {
        // RuntimeException indica erro não recuperável
        CpfCadastradoException exception = new CpfCadastradoException("Erro");
        
        assertTrue(exception instanceof RuntimeException);
        assertFalse(exception instanceof Exception && !(exception instanceof RuntimeException));
    }

    @Test
    void deveMensagemSerAcessivelAposLancamento() {
        String mensagemOriginal = "CPF duplicado no sistema";
        
        try {
            throw new CpfCadastradoException(mensagemOriginal);
        } catch (CpfCadastradoException e) {
            assertEquals(mensagemOriginal, e.getMessage());
        }
    }

    @Test
    void deveCausaSerAcessivelAposLancamento() {
        Exception causaOriginal = new Exception("Causa teste");
        
        try {
            throw new CpfCadastradoException("Mensagem", causaOriginal);
        } catch (CpfCadastradoException e) {
            assertEquals(causaOriginal, e.getCause());
        }
    }

    @Test
    void devePoderSerRelancada() {
        CpfCadastradoException exception = new CpfCadastradoException("Mensagem inicial");
        
        assertThrows(CpfCadastradoException.class, () -> {
            throw exception;
        });
    }

    @Test
    void deveSerInstanciaDeThrowable() {
        CpfCadastradoException exception = new CpfCadastradoException("Teste");
        
        assertTrue(exception instanceof Throwable);
    }

    @Test
    void deveToStringConterInformacaoUtil() {
        CpfCadastradoException exception = new CpfCadastradoException("Cliente ja cadastrado");
        
        String toString = exception.toString();
        
        assertNotNull(toString);
        assertTrue(toString.contains("CpfCadastradoException"));
    }

    // Método auxiliar para simular validação
    private void validarCpfDuplicado(String cpf) {
        throw new CpfCadastradoException("Cliente ja cadastrado com o CPF informado");
    }
}

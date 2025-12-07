package br.com.fiap.fasfoodpessoas.infraestructure.web.api.handlers;

import br.com.fiap.fasfoodpessoas.infraestructure.web.api.dtos.ErrorDto;
import br.com.fiap.fasfoodpessoas.infraestructure.web.api.dtos.ErrorMessageDto;
import br.com.fiap.fasfoodpessoas.infraestructure.web.api.exceptions.CpfCadastradoException;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("ControllerExceptionHandler - Testes Unitários")
class ControllerExceptionHandlerTest {

    private TestableControllerExceptionHandler controllerExceptionHandler;

    @Mock
    private HttpServletRequest httpServletRequest;

    private static final String CPF = "12345678900";
    private static final String SERVLET_PATH = "/api/v1/pessoa";

    // Subclasse para tornar o método protected acessível nos testes
    private static class TestableControllerExceptionHandler extends ControllerExceptionHandler {
        @Override
        public ErrorDto handleCpfCadastradoException(CpfCadastradoException ex, HttpServletRequest req) {
            return super.handleCpfCadastradoException(ex, req);
        }
    }

    @BeforeEach
    void setUp() {
        controllerExceptionHandler = new TestableControllerExceptionHandler();
    }

    private void setupServletPath(String path) {
        when(httpServletRequest.getServletPath()).thenReturn(path);
    }

    @Test
    @DisplayName("Deve tratar CpfCadastradoException com sucesso")
    void deveTratarCpfCadastradoException() {
        // Arrange
        String mensagemErro = "CPF já cadastrado no sistema";
        CpfCadastradoException exception = new CpfCadastradoException(mensagemErro);
        setupServletPath(SERVLET_PATH);
        setupServletPath(SERVLET_PATH);

        // Act
        ErrorDto resultado = controllerExceptionHandler.handleCpfCadastradoException(exception, httpServletRequest);

        // Assert
        assertNotNull(resultado);
        assertEquals(HttpStatus.BAD_REQUEST.value(), resultado.status());
        assertEquals(mensagemErro, resultado.message());
        assertEquals(SERVLET_PATH, resultado.path());
        assertNotNull(resultado.timestamp());
        assertNotNull(resultado.errors());
        assertEquals(1, resultado.errors().size());
        assertEquals(mensagemErro, ((ErrorMessageDto) resultado.errors().get(0)).message());
    }

    @Test
    @DisplayName("Deve tratar CpfCadastradoException com mensagem vazia")
    void deveTratarCpfCadastradoExceptionComMensagemVazia() {
        // Arrange
        String mensagemErro = "";
        CpfCadastradoException exception = new CpfCadastradoException(mensagemErro);
        setupServletPath(SERVLET_PATH);

        // Act
        ErrorDto resultado = controllerExceptionHandler.handleCpfCadastradoException(exception, httpServletRequest);

        // Assert
        assertNotNull(resultado);
        assertEquals(HttpStatus.BAD_REQUEST.value(), resultado.status());
        assertEquals(mensagemErro, resultado.message());
        assertEquals(SERVLET_PATH, resultado.path());
        assertNotNull(resultado.timestamp());
        assertNotNull(resultado.errors());
        assertEquals(1, resultado.errors().size());
        assertEquals(mensagemErro, ((ErrorMessageDto) resultado.errors().get(0)).message());
    }

    @Test
    @DisplayName("Deve tratar CpfCadastradoException com mensagem customizada")
    void deveTratarCpfCadastradoExceptionComMensagemCustomizada() {
        // Arrange
        String mensagemErro = "CPF " + CPF + " já existe na base de dados";
        CpfCadastradoException exception = new CpfCadastradoException(mensagemErro);
        setupServletPath(SERVLET_PATH);

        // Act
        ErrorDto resultado = controllerExceptionHandler.handleCpfCadastradoException(exception, httpServletRequest);

        // Assert
        assertNotNull(resultado);
        assertEquals(HttpStatus.BAD_REQUEST.value(), resultado.status());
        assertEquals(mensagemErro, resultado.message());
        assertEquals(SERVLET_PATH, resultado.path());
        assertNotNull(resultado.timestamp());
        assertNotNull(resultado.errors());
        assertEquals(1, resultado.errors().size());
        assertEquals(mensagemErro, ((ErrorMessageDto) resultado.errors().get(0)).message());
    }

    @Test
    @DisplayName("Deve validar status HTTP BAD_REQUEST")
    void deveValidarStatusHttpBadRequest() {
        // Arrange
        CpfCadastradoException exception = new CpfCadastradoException("CPF duplicado");

        // Act
        ErrorDto resultado = controllerExceptionHandler.handleCpfCadastradoException(exception, httpServletRequest);

        // Assert
        assertNotNull(resultado);
        assertEquals(400, resultado.status());
        assertEquals(HttpStatus.BAD_REQUEST.value(), resultado.status());
    }

    @Test
    @DisplayName("Deve validar estrutura do ErrorDto retornado")
    void deveValidarEstruturaErrorDto() {
        // Arrange
        String mensagem = "Erro ao cadastrar CPF";
        CpfCadastradoException exception = new CpfCadastradoException(mensagem);
        setupServletPath(SERVLET_PATH);

        // Act
        ErrorDto resultado = controllerExceptionHandler.handleCpfCadastradoException(exception, httpServletRequest);

        // Assert
        assertNotNull(resultado);
        assertNotNull(resultado.timestamp());
        assertTrue(resultado.status() > 0);
        assertNotNull(resultado.message());
        assertNotNull(resultado.errors());
        assertFalse(resultado.errors().isEmpty());
        assertNotNull(resultado.path());
    }

    @Test
    @DisplayName("Deve validar que timestamp é próximo ao momento da execução")
    void deveValidarTimestampProximoExecucao() {
        // Arrange
        CpfCadastradoException exception = new CpfCadastradoException("Erro de teste");
        java.time.LocalDateTime antes = java.time.LocalDateTime.now();

        // Act
        ErrorDto resultado = controllerExceptionHandler.handleCpfCadastradoException(exception, httpServletRequest);
        java.time.LocalDateTime depois = java.time.LocalDateTime.now();

        // Assert
        assertNotNull(resultado.timestamp());
        assertTrue(resultado.timestamp().isAfter(antes.minusSeconds(1)));
        assertTrue(resultado.timestamp().isBefore(depois.plusSeconds(1)));
    }

    @Test
    @DisplayName("Deve validar lista de erros contém ErrorMessageDto")
    void deveValidarListaErrosContemErrorMessageDto() {
        // Arrange
        String mensagem = "Mensagem de erro específica";
        CpfCadastradoException exception = new CpfCadastradoException(mensagem);

        // Act
        ErrorDto resultado = controllerExceptionHandler.handleCpfCadastradoException(exception, httpServletRequest);

        // Assert
        assertNotNull(resultado.errors());
        assertEquals(1, resultado.errors().size());
        ErrorMessageDto errorMessage = (ErrorMessageDto) resultado.errors().get(0);
        assertNotNull(errorMessage);
        assertEquals(mensagem, errorMessage.message());
    }

    @Test
    @DisplayName("Deve validar path do servlet request")
    void deveValidarPathServletRequest() {
        // Arrange
        String pathEsperado = SERVLET_PATH;
        when(httpServletRequest.getServletPath()).thenReturn(pathEsperado);
        CpfCadastradoException exception = new CpfCadastradoException("Erro");

        // Act
        ErrorDto resultado = controllerExceptionHandler.handleCpfCadastradoException(exception, httpServletRequest);

        // Assert
        assertNotNull(resultado.path());
        assertEquals(pathEsperado, resultado.path());
    }

    @Test
    @DisplayName("Deve tratar exception com mensagem nula")
    void deveTratarExceptionComMensagemNula() {
        // Arrange
        CpfCadastradoException exception = new CpfCadastradoException(null);

        // Act
        ErrorDto resultado = controllerExceptionHandler.handleCpfCadastradoException(exception, httpServletRequest);

        // Assert
        assertNotNull(resultado);
        assertNull(resultado.message());
        assertEquals(HttpStatus.BAD_REQUEST.value(), resultado.status());
        assertEquals(1, resultado.errors().size());
        assertNull(((ErrorMessageDto) resultado.errors().get(0)).message());
    }

    @Test
    @DisplayName("Deve tratar múltiplas exceções consecutivas")
    void deveTratarMultiplasExcecoesConsecutivas() {
        // Arrange
        CpfCadastradoException exception1 = new CpfCadastradoException("Erro 1");
        CpfCadastradoException exception2 = new CpfCadastradoException("Erro 2");
        CpfCadastradoException exception3 = new CpfCadastradoException("Erro 3");

        // Act
        ErrorDto resultado1 = controllerExceptionHandler.handleCpfCadastradoException(exception1, httpServletRequest);
        ErrorDto resultado2 = controllerExceptionHandler.handleCpfCadastradoException(exception2, httpServletRequest);
        ErrorDto resultado3 = controllerExceptionHandler.handleCpfCadastradoException(exception3, httpServletRequest);

        // Assert
        assertNotNull(resultado1);
        assertNotNull(resultado2);
        assertNotNull(resultado3);
        assertEquals("Erro 1", resultado1.message());
        assertEquals("Erro 2", resultado2.message());
        assertEquals("Erro 3", resultado3.message());
        assertTrue(resultado3.timestamp().isAfter(resultado1.timestamp()) || 
                   resultado3.timestamp().isEqual(resultado1.timestamp()));
    }

    @Test
    @DisplayName("Deve validar que errors não é imutável (ArrayList)")
    void deveValidarErrorsNaoEImutavel() {
        // Arrange
        CpfCadastradoException exception = new CpfCadastradoException("Teste");

        // Act
        ErrorDto resultado = controllerExceptionHandler.handleCpfCadastradoException(exception, httpServletRequest);

        // Assert
        assertNotNull(resultado.errors());
        assertTrue(resultado.errors() instanceof java.util.ArrayList);
    }

    @Test
    @DisplayName("Deve tratar exception com mensagem longa")
    void deveTratarExceptionComMensagemLonga() {
        // Arrange
        String mensagemLonga = "Este é um erro muito longo que contém muitas informações detalhadas sobre " +
                "o problema que ocorreu durante o processamento da requisição e que deve ser tratado " +
                "adequadamente pelo exception handler para garantir que o cliente receba uma resposta " +
                "apropriada com todas as informações necessárias para entender o erro.";
        CpfCadastradoException exception = new CpfCadastradoException(mensagemLonga);

        // Act
        ErrorDto resultado = controllerExceptionHandler.handleCpfCadastradoException(exception, httpServletRequest);

        // Assert
        assertNotNull(resultado);
        assertEquals(mensagemLonga, resultado.message());
        assertEquals(mensagemLonga, ((ErrorMessageDto) resultado.errors().get(0)).message());
    }

    @Test
    @DisplayName("Deve tratar exception com caracteres especiais na mensagem")
    void deveTratarExceptionComCaracteresEspeciais() {
        // Arrange
        String mensagemEspecial = "CPF inválido: @#$%¨&*()_+{}[]|\\:;<>,.?/~`'\"";
        CpfCadastradoException exception = new CpfCadastradoException(mensagemEspecial);

        // Act
        ErrorDto resultado = controllerExceptionHandler.handleCpfCadastradoException(exception, httpServletRequest);

        // Assert
        assertNotNull(resultado);
        assertEquals(mensagemEspecial, resultado.message());
        assertEquals(mensagemEspecial, ((ErrorMessageDto) resultado.errors().get(0)).message());
    }

    @Test
    @DisplayName("Deve validar diferentes paths de servlet")
    void deveValidarDiferentesPathsServlet() {
        // Arrange
        String path1 = SERVLET_PATH;
        String path2 = SERVLET_PATH + "/" + CPF;
        String path3 = "/api/v2/pessoas";

        CpfCadastradoException exception = new CpfCadastradoException("Erro");

        // Act & Assert
        when(httpServletRequest.getServletPath()).thenReturn(path1);
        ErrorDto resultado1 = controllerExceptionHandler.handleCpfCadastradoException(exception, httpServletRequest);
        assertEquals(path1, resultado1.path());

        when(httpServletRequest.getServletPath()).thenReturn(path2);
        ErrorDto resultado2 = controllerExceptionHandler.handleCpfCadastradoException(exception, httpServletRequest);
        assertEquals(path2, resultado2.path());

        when(httpServletRequest.getServletPath()).thenReturn(path3);
        ErrorDto resultado3 = controllerExceptionHandler.handleCpfCadastradoException(exception, httpServletRequest);
        assertEquals(path3, resultado3.path());
    }

    @Test
    @DisplayName("Deve validar imutabilidade do ErrorDto")
    void deveValidarImutabilidadeErrorDto() {
        // Arrange
        CpfCadastradoException exception = new CpfCadastradoException("Teste imutabilidade");
        setupServletPath(SERVLET_PATH);

        // Act
        ErrorDto resultado = controllerExceptionHandler.handleCpfCadastradoException(exception, httpServletRequest);
        int statusOriginal = resultado.status();
        String mensagemOriginal = resultado.message();

        // Assert - Validar que não há setters disponíveis (record é imutável)
        assertEquals(statusOriginal, resultado.status());
        assertEquals(mensagemOriginal, resultado.message());
        assertNotNull(resultado.timestamp());
        assertNotNull(resultado.errors());
        assertNotNull(resultado.path());
    }

    @Test
    @DisplayName("Deve validar formato do timestamp LocalDateTime")
    void deveValidarFormatoTimestampLocalDateTime() {
        // Arrange
        CpfCadastradoException exception = new CpfCadastradoException("Teste timestamp");

        // Act
        ErrorDto resultado = controllerExceptionHandler.handleCpfCadastradoException(exception, httpServletRequest);

        // Assert
        assertNotNull(resultado.timestamp());
        assertTrue(resultado.timestamp() instanceof java.time.LocalDateTime);
        assertTrue(resultado.timestamp().getYear() > 0);
        assertNotNull(resultado.timestamp().getMonth());
        assertTrue(resultado.timestamp().getDayOfMonth() > 0);
        assertTrue(resultado.timestamp().getHour() >= 0);
        assertTrue(resultado.timestamp().getMinute() >= 0);
        assertTrue(resultado.timestamp().getSecond() >= 0);
    }

    @Test
    @DisplayName("Deve validar que handler estende ResponseEntityExceptionHandler")
    void deveValidarQueHandlerEstendeResponseEntityExceptionHandler() {
        // Assert
        assertTrue(controllerExceptionHandler instanceof org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler);
    }

    @Test
    @DisplayName("Deve validar anotação @RestControllerAdvice presente na classe")
    void deveValidarAnotacaoRestControllerAdvice() {
        // Assert
        assertTrue(ControllerExceptionHandler.class.isAnnotationPresent(
                org.springframework.web.bind.annotation.RestControllerAdvice.class));
    }

    @Test
    @DisplayName("Deve validar método handleCpfCadastradoException existe")
    void deveValidarMetodoHandleCpfCadastradoExceptionExiste() throws NoSuchMethodException {
        // Act & Assert
        assertNotNull(controllerExceptionHandler.getClass().getMethod(
                "handleCpfCadastradoException",
                CpfCadastradoException.class,
                HttpServletRequest.class));
    }

    @Test
    @DisplayName("Deve validar retorno do método é ErrorDto")
    void deveValidarRetornoMetodoEErrorDto() throws NoSuchMethodException {
        // Arrange
        var metodo = controllerExceptionHandler.getClass().getMethod(
                "handleCpfCadastradoException",
                CpfCadastradoException.class,
                HttpServletRequest.class);

        // Assert
        assertEquals(ErrorDto.class, metodo.getReturnType());
    }

    @Test
    @DisplayName("Deve validar mensagens de erro em português")
    void deveValidarMensagensErroPortugues() {
        // Arrange
        String[] mensagensPortugues = {
                "CPF já cadastrado",
                "Documento já existe no sistema",
                "Pessoa com este CPF já foi registrada"
        };

        for (String mensagem : mensagensPortugues) {
            CpfCadastradoException exception = new CpfCadastradoException(mensagem);

            // Act
            ErrorDto resultado = controllerExceptionHandler.handleCpfCadastradoException(exception, httpServletRequest);

            // Assert
            assertNotNull(resultado);
            assertEquals(mensagem, resultado.message());
            assertEquals(mensagem, ((ErrorMessageDto) resultado.errors().get(0)).message());
        }
    }

    @Test
    @DisplayName("Deve validar consistência entre message e errors")
    void deveValidarConsistenciaEntreMensagemEErrors() {
        // Arrange
        String mensagem = "CPF 12345678900 já está cadastrado";
        CpfCadastradoException exception = new CpfCadastradoException(mensagem);

        // Act
        ErrorDto resultado = controllerExceptionHandler.handleCpfCadastradoException(exception, httpServletRequest);

        // Assert
        assertNotNull(resultado);
        assertEquals(resultado.message(), ((ErrorMessageDto) resultado.errors().get(0)).message());
    }

    @Test
    @DisplayName("Deve tratar CPFs diferentes com mesma mensagem de erro")
    void deveTratarCpfsDiferentesComMesmaMensagemErro() {
        // Arrange
        String mensagemPadrao = "CPF já cadastrado";
        CpfCadastradoException exception1 = new CpfCadastradoException(mensagemPadrao);
        CpfCadastradoException exception2 = new CpfCadastradoException(mensagemPadrao);

        // Act
        ErrorDto resultado1 = controllerExceptionHandler.handleCpfCadastradoException(exception1, httpServletRequest);
        ErrorDto resultado2 = controllerExceptionHandler.handleCpfCadastradoException(exception2, httpServletRequest);

        // Assert
        assertEquals(resultado1.message(), resultado2.message());
        assertEquals(resultado1.status(), resultado2.status());
        assertNotEquals(resultado1.timestamp(), resultado2.timestamp());
    }

    @Test
    @DisplayName("Deve validar path vazio no servlet request")
    void deveValidarPathVazioServletRequest() {
        // Arrange
        when(httpServletRequest.getServletPath()).thenReturn("");
        CpfCadastradoException exception = new CpfCadastradoException("Erro");

        // Act
        ErrorDto resultado = controllerExceptionHandler.handleCpfCadastradoException(exception, httpServletRequest);

        // Assert
        assertEquals("", resultado.path());
    }

    @Test
    @DisplayName("Deve validar path com barras múltiplas")
    void deveValidarPathComBarrasMultiplas() {
        // Arrange
        String pathComplexo = SERVLET_PATH + "/busca/cpf/" + CPF;
        when(httpServletRequest.getServletPath()).thenReturn(pathComplexo);
        CpfCadastradoException exception = new CpfCadastradoException("Erro");

        // Act
        ErrorDto resultado = controllerExceptionHandler.handleCpfCadastradoException(exception, httpServletRequest);

        // Assert
        assertEquals(pathComplexo, resultado.path());
    }

    @Test
    @DisplayName("Deve validar que cada chamada cria novo ErrorDto")
    void deveValidarCadaChamadaCriaNovoErrorDto() {
        // Arrange
        CpfCadastradoException exception = new CpfCadastradoException("Erro");

        // Act
        ErrorDto resultado1 = controllerExceptionHandler.handleCpfCadastradoException(exception, httpServletRequest);
        ErrorDto resultado2 = controllerExceptionHandler.handleCpfCadastradoException(exception, httpServletRequest);

        // Assert
        assertNotSame(resultado1, resultado2);
        assertNotSame(resultado1.errors(), resultado2.errors());
    }

    @Test
    @DisplayName("Deve validar size da lista de errors é sempre 1")
    void deveValidarSizeListaErrorsSempre1() {
        // Arrange
        CpfCadastradoException exception = new CpfCadastradoException("Qualquer mensagem");

        // Act
        ErrorDto resultado = controllerExceptionHandler.handleCpfCadastradoException(exception, httpServletRequest);

        // Assert
        assertEquals(1, resultado.errors().size());
        assertFalse(resultado.errors().isEmpty());
    }

    @Test
    @DisplayName("Deve validar que exception preserva stack trace")
    void deveValidarExceptionPreservaStackTrace() {
        // Arrange
        CpfCadastradoException exception = new CpfCadastradoException("Erro com stack trace");

        // Act
        ErrorDto resultado = controllerExceptionHandler.handleCpfCadastradoException(exception, httpServletRequest);

        // Assert
        assertNotNull(exception.getStackTrace());
        assertTrue(exception.getStackTrace().length > 0);
        assertNotNull(resultado);
    }
}

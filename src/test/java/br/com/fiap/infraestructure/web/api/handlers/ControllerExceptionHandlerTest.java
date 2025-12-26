package br.com.fiap.infraestructure.web.api.handlers;

import br.com.fiap.fasfoodpessoas.infraestructure.web.api.dtos.ErrorDto;
import br.com.fiap.fasfoodpessoas.infraestructure.web.api.handlers.ControllerExceptionHandler;
import br.com.fiap.fasfoodpessoas.infraestructure.web.api.exceptions.CpfCadastradoException;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("ControllerExceptionHandler - Testes unitários")
class ControllerExceptionHandlerTest {

    @InjectMocks
    private ControllerExceptionHandler exceptionHandler;

    @Mock
    private HttpServletRequest request;

    private CpfCadastradoException cpfCadastradoException;

    @BeforeEach
    void setUp() {
        cpfCadastradoException = new CpfCadastradoException("CPF 12345678900 já cadastrado no sistema");
        when(request.getServletPath()).thenReturn("/api/pessoas");
    }

    @Test
    @DisplayName("Deve criar lista de erros com um ErrorMessageDto")
    void deveCriarListaDeErrosComUmErrorMessageDto() {
        ErrorDto errorDto = exceptionHandler.handleCpfCadastradoException(cpfCadastradoException, request);

        assertNotNull(errorDto.errors());
        assertEquals(1, errorDto.errors().size());
        assertNotNull(errorDto.errors().get(0));
    }

    @Test
    @DisplayName("Deve adicionar ErrorMessageDto com mensagem da exceção na lista")
    void deveAdicionarErrorMessageDtoComMensagemDaExcecaoNaLista() {
        ErrorDto errorDto = exceptionHandler.handleCpfCadastradoException(cpfCadastradoException, request);

        assertEquals("CPF 12345678900 já cadastrado no sistema", errorDto.errors().get(0).message());
    }

    @Test
    @DisplayName("Deve retornar ErrorDto com timestamp atual")
    void deveRetornarErrorDtoComTimestampAtual() {
        ErrorDto errorDto = exceptionHandler.handleCpfCadastradoException(cpfCadastradoException, request);

        assertNotNull(errorDto.timestamp());
        assertNotNull(errorDto.timestamp().toString());
    }

    @Test
    @DisplayName("Deve retornar ErrorDto com status BAD_REQUEST (400)")
    void deveRetornarErrorDtoComStatusBadRequest() {
        ErrorDto errorDto = exceptionHandler.handleCpfCadastradoException(cpfCadastradoException, request);

        assertEquals(HttpStatus.BAD_REQUEST.value(), errorDto.status());
        assertEquals(400, errorDto.status());
    }

    @Test
    @DisplayName("Deve retornar ErrorDto com mensagem da exceção")
    void deveRetornarErrorDtoComMensagemDaExcecao() {
        ErrorDto errorDto = exceptionHandler.handleCpfCadastradoException(cpfCadastradoException, request);

        assertEquals("CPF 12345678900 já cadastrado no sistema", errorDto.message());
    }

    @Test
    @DisplayName("Deve retornar ErrorDto com lista de erros não nula")
    void deveRetornarErrorDtoComListaDeErrosNaoNula() {
        ErrorDto errorDto = exceptionHandler.handleCpfCadastradoException(cpfCadastradoException, request);

        assertNotNull(errorDto.errors());
        assertFalse(errorDto.errors().isEmpty());
    }

    @Test
    @DisplayName("Deve retornar ErrorDto com caminho da requisição")
    void deveRetornarErrorDtoComCaminhoDaRequisicao() {
        ErrorDto errorDto = exceptionHandler.handleCpfCadastradoException(cpfCadastradoException, request);

        assertEquals("/api/pessoas", errorDto.path());
    }

    @Test
    @DisplayName("Deve criar novo ArrayList para armazenar erros")
    void deveCriarNovoArrayListParaArmazenarErros() {
        ErrorDto errorDto = exceptionHandler.handleCpfCadastradoException(cpfCadastradoException, request);

        assertTrue(errorDto.errors() instanceof java.util.List);
        assertEquals(1, errorDto.errors().size());
    }

    @Test
    @DisplayName("Deve adicionar erro na lista após criação")
    void deveAdicionarErroNaListaAposCriacao() {
        ErrorDto errorDto = exceptionHandler.handleCpfCadastradoException(cpfCadastradoException, request);

        assertEquals(1, errorDto.errors().size());
        assertNotNull(errorDto.errors().get(0).message());
    }

    @Test
    @DisplayName("Deve criar ErrorDto com todos os campos preenchidos")
    void deveCriarErrorDtoComTodosOsCamposPreenchidos() {
        ErrorDto errorDto = exceptionHandler.handleCpfCadastradoException(cpfCadastradoException, request);

        assertNotNull(errorDto.timestamp());
        assertEquals(400, errorDto.status());
        assertNotNull(errorDto.message());
        assertNotNull(errorDto.errors());
        assertNotNull(errorDto.path());
    }

    @Test
    @DisplayName("Deve retornar ErrorDto com mensagem consistente entre message e errors")
    void deveRetornarErrorDtoComMensagemConsistenteEntreMensagemEErros() {
        ErrorDto errorDto = exceptionHandler.handleCpfCadastradoException(cpfCadastradoException, request);

        assertEquals(errorDto.message(), errorDto.errors().get(0).message());
    }

    @Test
    @DisplayName("Deve tratar exceção com mensagem vazia")
    void deveTratarExcecaoComMensagemVazia() {
        CpfCadastradoException exception = new CpfCadastradoException("");
        
        ErrorDto errorDto = exceptionHandler.handleCpfCadastradoException(exception, request);

        assertNotNull(errorDto);
        assertEquals("", errorDto.message());
        assertEquals(1, errorDto.errors().size());
    }

    @Test
    @DisplayName("Deve tratar exceção com mensagem nula")
    void deveTratarExcecaoComMensagemNula() {
        CpfCadastradoException exception = new CpfCadastradoException(null);
        
        ErrorDto errorDto = exceptionHandler.handleCpfCadastradoException(exception, request);

        assertNotNull(errorDto);
        assertNull(errorDto.message());
    }

    @Test
    @DisplayName("Deve usar servletPath do request para path do ErrorDto")
    void deveUsarServletPathDoRequestParaPathDoErrorDto() {
        when(request.getServletPath()).thenReturn("/api/clientes/cadastrar");
        
        ErrorDto errorDto = exceptionHandler.handleCpfCadastradoException(cpfCadastradoException, request);

        assertEquals("/api/clientes/cadastrar", errorDto.path());
    }

    @Test
    @DisplayName("Deve criar ErrorDto com timestamp próximo ao momento da chamada")
    void deveCriarErrorDtoComTimestampProximoAoMomentoDaChamada() {
        long before = System.currentTimeMillis();
        ErrorDto errorDto = exceptionHandler.handleCpfCadastradoException(cpfCadastradoException, request);
        long after = System.currentTimeMillis();

        long errorTimestamp = errorDto.timestamp().atZone(java.time.ZoneId.systemDefault()).toInstant().toEpochMilli();

        assertTrue(errorTimestamp >= before - 1000 && errorTimestamp <= after + 1000);
    }

    @Test
    @DisplayName("Deve garantir que lista de erros contém exatamente um elemento")
    void deveGarantirQueListaDeErrosContemExatamenteUmElemento() {
        ErrorDto errorDto = exceptionHandler.handleCpfCadastradoException(cpfCadastradoException, request);

        assertEquals(1, errorDto.errors().size());
    }

    @Test
    @DisplayName("Deve criar ErrorMessageDto com construtor que recebe mensagem")
    void deveCriarErrorMessageDtoComConstrutorQueRecebeMensagem() {
        ErrorDto errorDto = exceptionHandler.handleCpfCadastradoException(cpfCadastradoException, request);

        assertNotNull(errorDto.errors().get(0));
        assertEquals("CPF 12345678900 já cadastrado no sistema", errorDto.errors().get(0).message());
    }

    @Test
    @DisplayName("Deve retornar status HTTP 400 para CpfCadastradoException")
    void deveRetornarStatusHttp400ParaCpfCadastradoException() {
        ErrorDto errorDto = exceptionHandler.handleCpfCadastradoException(cpfCadastradoException, request);

        assertEquals(HttpStatus.BAD_REQUEST.value(), errorDto.status());
    }

    @Test
    @DisplayName("Deve processar múltiplas chamadas do handler corretamente")
    void deveProcessarMultiplasChamadasDoHandlerCorretamente() {
        ErrorDto errorDto1 = exceptionHandler.handleCpfCadastradoException(cpfCadastradoException, request);
        ErrorDto errorDto2 = exceptionHandler.handleCpfCadastradoException(cpfCadastradoException, request);

        assertNotNull(errorDto1);
        assertNotNull(errorDto2);
        assertEquals(errorDto1.message(), errorDto2.message());
        assertEquals(errorDto1.status(), errorDto2.status());
    }

    @Test
    @DisplayName("Deve manter independência entre instâncias de ErrorDto")
    void deveManterIndependenciaEntreInstanciasDeErrorDto() {
        CpfCadastradoException exception1 = new CpfCadastradoException("CPF 111 cadastrado");
        CpfCadastradoException exception2 = new CpfCadastradoException("CPF 222 cadastrado");

        ErrorDto errorDto1 = exceptionHandler.handleCpfCadastradoException(exception1, request);
        ErrorDto errorDto2 = exceptionHandler.handleCpfCadastradoException(exception2, request);

        assertNotEquals(errorDto1.message(), errorDto2.message());
        assertEquals("CPF 111 cadastrado", errorDto1.message());
        assertEquals("CPF 222 cadastrado", errorDto2.message());
    }
}

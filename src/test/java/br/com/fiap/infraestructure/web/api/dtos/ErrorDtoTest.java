package br.com.fiap.infraestructure.web.api.dtos;

import br.com.fiap.fasfoodpessoas.infraestructure.web.api.dtos.ErrorDto;
import br.com.fiap.fasfoodpessoas.infraestructure.web.api.dtos.ErrorMessageDto;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ErrorDtoTest {

    @Test
    void deveCriarErrorDtoComTodosOsCampos() {
        LocalDateTime timestamp = LocalDateTime.now();
        Integer status = 400;
        String message = "Erro de validação";
        List<ErrorMessageDto> errors = List.of(
                new ErrorMessageDto("Campo obrigatório"),
                new ErrorMessageDto("Formato inválido")
        );
        String path = "/api/v1/pessoa";

        ErrorDto errorDto = new ErrorDto(timestamp, status, message, errors, path);

        assertNotNull(errorDto);
        assertEquals(timestamp, errorDto.timestamp());
        assertEquals(status, errorDto.status());
        assertEquals(message, errorDto.message());
        assertEquals(errors, errorDto.errors());
        assertEquals(path, errorDto.path());
    }

    @Test
    void deveCriarErrorDtoSemListaDeErros() {
        LocalDateTime timestamp = LocalDateTime.now();
        Integer status = 404;
        String message = "Recurso não encontrado";
        String path = "/api/v1/pessoa/123";

        ErrorDto errorDto = new ErrorDto(timestamp, status, message, path);

        assertNotNull(errorDto);
        assertEquals(timestamp, errorDto.timestamp());
        assertEquals(status, errorDto.status());
        assertEquals(message, errorDto.message());
        assertTrue(errorDto.errors().isEmpty());
        assertEquals(path, errorDto.path());
    }

    @Test
    void deveRetornarListaVaziaQuandoCriadoSemErros() {
        LocalDateTime timestamp = LocalDateTime.now();
        ErrorDto errorDto = new ErrorDto(timestamp, 500, "Erro interno", "/api/teste");

        assertNotNull(errorDto.errors());
        assertEquals(0, errorDto.errors().size());
    }

    @Test
    void deveCriarErrorDtoComStatus400() {
        LocalDateTime timestamp = LocalDateTime.now();
        ErrorDto errorDto = new ErrorDto(timestamp, 400, "Bad Request", "/api/v1/pessoa");

        assertEquals(400, errorDto.status());
    }

    @Test
    void deveCriarErrorDtoComStatus404() {
        LocalDateTime timestamp = LocalDateTime.now();
        ErrorDto errorDto = new ErrorDto(timestamp, 404, "Not Found", "/api/v1/pessoa/123");

        assertEquals(404, errorDto.status());
    }

    @Test
    void deveCriarErrorDtoComStatus500() {
        LocalDateTime timestamp = LocalDateTime.now();
        ErrorDto errorDto = new ErrorDto(timestamp, 500, "Internal Server Error", "/api/v1/pessoa");

        assertEquals(500, errorDto.status());
    }

    @Test
    void deveManterTimestampFornecido() {
        LocalDateTime timestamp = LocalDateTime.of(2025, 12, 3, 10, 30, 0);
        ErrorDto errorDto = new ErrorDto(timestamp, 400, "Erro", "/api/teste");

        assertEquals(timestamp, errorDto.timestamp());
    }

    @Test
    void deveManterMensagemDeErro() {
        String mensagem = "Cliente ja cadastrado com o CPF informado";
        ErrorDto errorDto = new ErrorDto(LocalDateTime.now(), 409, mensagem, "/api/v1/pessoa");

        assertEquals(mensagem, errorDto.message());
    }

    @Test
    void deveManterPathCompleto() {
        String path = "/api/v1/pessoa/12345678900";
        ErrorDto errorDto = new ErrorDto(LocalDateTime.now(), 404, "Not Found", path);

        assertEquals(path, errorDto.path());
    }

    @Test
    void deveCriarErrorDtoComMultiplosErros() {
        List<ErrorMessageDto> errors = List.of(
                new ErrorMessageDto("O atributo cdDocPessoa é obrigatório."),
                new ErrorMessageDto("O atributo nmPessoa é obrigatório."),
                new ErrorMessageDto("O atributo dsEmail é obrigatório.")
        );

        ErrorDto errorDto = new ErrorDto(
                LocalDateTime.now(),
                400,
                "Erro de validação",
                errors,
                "/api/v1/pessoa"
        );

        assertEquals(3, errorDto.errors().size());
        assertTrue(errorDto.errors().contains(new ErrorMessageDto("O atributo cdDocPessoa é obrigatório.")));
    }

    @Test
    void deveCriarErrorDtoComErroUnico() {
        List<ErrorMessageDto> errors = List.of(new ErrorMessageDto("O CPF informado no atributo cdDocPessoa é inválido."));

        ErrorDto errorDto = new ErrorDto(
                LocalDateTime.now(),
                400,
                "Erro de validação",
                errors,
                "/api/v1/pessoa"
        );

        assertEquals(1, errorDto.errors().size());
        assertEquals("O CPF informado no atributo cdDocPessoa é inválido.", errorDto.errors().get(0).message());
    }

    @Test
    void deveCriarErrorDtoComListaVaziaExplicita() {
        ErrorDto errorDto = new ErrorDto(
                LocalDateTime.now(),
                404,
                "Not Found",
                List.of(),
                "/api/v1/pessoa/123"
        );

        assertTrue(errorDto.errors().isEmpty());
        assertEquals(0, errorDto.errors().size());
    }

    @Test
    void deveSerRecord() {
        assertTrue(ErrorDto.class.isRecord());
    }

    @Test
    void deveCompararErrorDtosIguais() {
        LocalDateTime timestamp = LocalDateTime.now();
        ErrorDto error1 = new ErrorDto(timestamp, 400, "Erro", "/api/teste");
        ErrorDto error2 = new ErrorDto(timestamp, 400, "Erro", "/api/teste");

        assertEquals(error1, error2);
    }

    @Test
    void deveCompararErrorDtosDiferentes() {
        LocalDateTime timestamp = LocalDateTime.now();
        ErrorDto error1 = new ErrorDto(timestamp, 400, "Erro 1", "/api/teste1");
        ErrorDto error2 = new ErrorDto(timestamp, 404, "Erro 2", "/api/teste2");

        assertNotEquals(error1, error2);
    }

    @Test
    void deveGerarHashCodeConsistente() {
        LocalDateTime timestamp = LocalDateTime.now();
        ErrorDto error1 = new ErrorDto(timestamp, 400, "Erro", "/api/teste");
        ErrorDto error2 = new ErrorDto(timestamp, 400, "Erro", "/api/teste");

        assertEquals(error1.hashCode(), error2.hashCode());
    }

    @Test
    void deveGerarToStringComTodosOsCampos() {
        LocalDateTime timestamp = LocalDateTime.now();
        List<ErrorMessageDto> errors = List.of(new ErrorMessageDto("Erro 1"));
        ErrorDto errorDto = new ErrorDto(timestamp, 400, "Bad Request", errors, "/api/teste");

        String toString = errorDto.toString();

        assertNotNull(toString);
        assertTrue(toString.contains("400"));
        assertTrue(toString.contains("Bad Request"));
        assertTrue(toString.contains("/api/teste"));
    }

    @Test
    void deveCriarErrorDtoComStatusNull() {
        ErrorDto errorDto = new ErrorDto(LocalDateTime.now(), null, "Erro", "/api/teste");

        assertNull(errorDto.status());
    }

    @Test
    void deveCriarErrorDtoComMensagemNull() {
        ErrorDto errorDto = new ErrorDto(LocalDateTime.now(), 400, null, "/api/teste");

        assertNull(errorDto.message());
    }

    @Test
    void deveCriarErrorDtoComPathNull() {
        ErrorDto errorDto = new ErrorDto(LocalDateTime.now(), 400, "Erro", (String) null);

        assertNull(errorDto.path());
    }

    @Test
    void deveCriarErrorDtoComTimestampNull() {
        ErrorDto errorDto = new ErrorDto(null, 400, "Erro", "/api/teste");

        assertNull(errorDto.timestamp());
    }

    @Test
    void deveTerConstrutorComCincoParametros() {
        LocalDateTime timestamp = LocalDateTime.now();
        List<ErrorMessageDto> errors = List.of(new ErrorMessageDto("Erro"));

        ErrorDto errorDto = new ErrorDto(timestamp, 400, "Erro", errors, "/api/teste");

        assertNotNull(errorDto);
        assertEquals(5, ErrorDto.class.getRecordComponents().length);
    }

    @Test
    void deveTerConstrutorComQuatroParametros() {
        LocalDateTime timestamp = LocalDateTime.now();

        ErrorDto errorDto = new ErrorDto(timestamp, 400, "Erro", "/api/teste");

        assertNotNull(errorDto);
        assertTrue(errorDto.errors().isEmpty());
    }

    @Test
    void devePermitirDiferentesTiposNaListaDeErros() {
        List<ErrorMessageDto> errors = List.of(new ErrorMessageDto("String error"));

        ErrorDto errorDto = new ErrorDto(
                LocalDateTime.now(),
                400,
                "Erro",
                errors,
                "/api/teste"
        );

        assertEquals(1, errorDto.errors().size());
    }

    @Test
    void deveCriarErrorDtoParaErro409Conflict() {
        ErrorDto errorDto = new ErrorDto(
                LocalDateTime.now(),
                409,
                "Cliente ja cadastrado com o CPF informado",
                "/api/v1/pessoa"
        );

        assertEquals(409, errorDto.status());
        assertEquals("Cliente ja cadastrado com o CPF informado", errorDto.message());
    }

    @Test
    void deveCriarErrorDtoParaValidacaoDeCpf() {
        List<ErrorMessageDto> errors = List.of(new ErrorMessageDto("O CPF informado no atributo cdDocPessoa é inválido."));

        ErrorDto errorDto = new ErrorDto(
                LocalDateTime.now(),
                400,
                "Erro de validação",
                errors,
                "/api/v1/pessoa"
        );

        assertFalse(errorDto.errors().isEmpty());
        assertTrue(errorDto.errors().get(0).message().contains("CPF"));
    }

    @Test
    void deveCriarErrorDtoParaValidacaoDeEmail() {
        List<ErrorMessageDto> errors = List.of(new ErrorMessageDto("O valor informado para dsEmail não é um e-mail válido"));

        ErrorDto errorDto = new ErrorDto(
                LocalDateTime.now(),
                400,
                "Erro de validação",
                errors,
                "/api/v1/pessoa"
        );

        assertFalse(errorDto.errors().isEmpty());
        assertTrue(errorDto.errors().get(0).message().contains("Email"));
    }

    @Test
    void deveCriarErrorDtoComPathVazio() {
        ErrorDto errorDto = new ErrorDto(LocalDateTime.now(), 400, "Erro", "");

        assertEquals("", errorDto.path());
    }
}

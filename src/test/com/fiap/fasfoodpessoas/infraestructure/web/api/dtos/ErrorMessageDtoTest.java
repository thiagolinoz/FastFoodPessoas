package com.fiap.fasfoodpessoas.infraestructure.web.api.dtos;

import br.com.fiap.fasfoodpessoas.infraestructure.web.api.dtos.ErrorMessageDto;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ErrorMessageDtoTest {

    @Test
    void deveCriarErrorMessageDtoComMensagem() {
        String mensagem = "Cliente ja cadastrado com o CPF informado";

        ErrorMessageDto errorMessageDto = new ErrorMessageDto(mensagem);

        assertNotNull(errorMessageDto);
        assertEquals(mensagem, errorMessageDto.message());
    }

    @Test
    void deveCriarErrorMessageDtoComMensagemDeValidacao() {
        String mensagem = "O atributo cdDocPessoa é obrigatório.";

        ErrorMessageDto errorMessageDto = new ErrorMessageDto(mensagem);

        assertEquals(mensagem, errorMessageDto.message());
    }

    @Test
    void deveCriarErrorMessageDtoComMensagemDeCpfInvalido() {
        String mensagem = "O CPF informado no atributo cdDocPessoa é inválido.";

        ErrorMessageDto errorMessageDto = new ErrorMessageDto(mensagem);

        assertEquals(mensagem, errorMessageDto.message());
    }

    @Test
    void deveCriarErrorMessageDtoComMensagemDeEmailInvalido() {
        String mensagem = "O valor informado para dsEmail não é um e-mail válido";

        ErrorMessageDto errorMessageDto = new ErrorMessageDto(mensagem);

        assertEquals(mensagem, errorMessageDto.message());
    }

    @Test
    void deveCriarErrorMessageDtoComMensagemDeNomeInvalido() {
        String mensagem = "O atributo nmPessoa deve ter entre 3 e 200 caracteres.";

        ErrorMessageDto errorMessageDto = new ErrorMessageDto(mensagem);

        assertEquals(mensagem, errorMessageDto.message());
    }

    @Test
    void deveCriarErrorMessageDtoComMensagemNull() {
        ErrorMessageDto errorMessageDto = new ErrorMessageDto(null);

        assertNotNull(errorMessageDto);
        assertNull(errorMessageDto.message());
    }

    @Test
    void deveCriarErrorMessageDtoComMensagemVazia() {
        ErrorMessageDto errorMessageDto = new ErrorMessageDto("");

        assertNotNull(errorMessageDto);
        assertEquals("", errorMessageDto.message());
    }

    @Test
    void deveSerRecord() {
        assertTrue(ErrorMessageDto.class.isRecord());
    }

    @Test
    void deveCompararErrorMessageDtosIguais() {
        String mensagem = "Erro de validação";
        ErrorMessageDto dto1 = new ErrorMessageDto(mensagem);
        ErrorMessageDto dto2 = new ErrorMessageDto(mensagem);

        assertEquals(dto1, dto2);
    }

    @Test
    void deveCompararErrorMessageDtosDiferentes() {
        ErrorMessageDto dto1 = new ErrorMessageDto("Mensagem 1");
        ErrorMessageDto dto2 = new ErrorMessageDto("Mensagem 2");

        assertNotEquals(dto1, dto2);
    }

    @Test
    void deveGerarHashCodeConsistente() {
        String mensagem = "Erro de teste";
        ErrorMessageDto dto1 = new ErrorMessageDto(mensagem);
        ErrorMessageDto dto2 = new ErrorMessageDto(mensagem);

        assertEquals(dto1.hashCode(), dto2.hashCode());
    }

    @Test
    void deveGerarToStringComMensagem() {
        String mensagem = "Cliente não encontrado";
        ErrorMessageDto errorMessageDto = new ErrorMessageDto(mensagem);

        String toString = errorMessageDto.toString();

        assertNotNull(toString);
        assertTrue(toString.contains("Cliente não encontrado"));
    }

    @Test
    void deveTerApenasUmCampo() {
        assertEquals(1, ErrorMessageDto.class.getRecordComponents().length);
    }

    @Test
    void deveTerCampoMessageDoTipoString() {
        var component = ErrorMessageDto.class.getRecordComponents()[0];

        assertEquals("message", component.getName());
        assertEquals(String.class, component.getType());
    }

    @Test
    void deveCriarErrorMessageDtoComMensagemLonga() {
        String mensagemLonga = "Este é um erro muito longo que contém várias informações " +
                "sobre o que deu errado na validação do campo e deve ser preservado " +
                "completamente no objeto ErrorMessageDto";

        ErrorMessageDto errorMessageDto = new ErrorMessageDto(mensagemLonga);

        assertEquals(mensagemLonga, errorMessageDto.message());
    }

    @Test
    void deveCriarErrorMessageDtoComMensagemComCaracteresEspeciais() {
        String mensagem = "Erro: O campo 'nmPessoa' não pode conter @#$%!";

        ErrorMessageDto errorMessageDto = new ErrorMessageDto(mensagem);

        assertEquals(mensagem, errorMessageDto.message());
    }

    @Test
    void deveCriarErrorMessageDtoComMensagemComAcentos() {
        String mensagem = "Atenção: É obrigatório informar número válido de CPF";

        ErrorMessageDto errorMessageDto = new ErrorMessageDto(mensagem);

        assertEquals(mensagem, errorMessageDto.message());
    }

    @Test
    void deveCriarErrorMessageDtoComMensagemDeErroInterno() {
        String mensagem = "Erro interno do servidor";

        ErrorMessageDto errorMessageDto = new ErrorMessageDto(mensagem);

        assertEquals(mensagem, errorMessageDto.message());
    }

    @Test
    void deveCriarErrorMessageDtoComMensagemDeRecursoNaoEncontrado() {
        String mensagem = "Pessoa não encontrada";

        ErrorMessageDto errorMessageDto = new ErrorMessageDto(mensagem);

        assertEquals(mensagem, errorMessageDto.message());
    }

    @Test
    void deveCriarErrorMessageDtoComMensagemDeConflito() {
        String mensagem = "Recurso já existe";

        ErrorMessageDto errorMessageDto = new ErrorMessageDto(mensagem);

        assertEquals(mensagem, errorMessageDto.message());
    }

    @Test
    void deveCriarMultiplosErrorMessageDtosIndependentes() {
        ErrorMessageDto dto1 = new ErrorMessageDto("Erro 1");
        ErrorMessageDto dto2 = new ErrorMessageDto("Erro 2");
        ErrorMessageDto dto3 = new ErrorMessageDto("Erro 3");

        assertNotEquals(dto1.message(), dto2.message());
        assertNotEquals(dto2.message(), dto3.message());
        assertNotEquals(dto1.message(), dto3.message());
    }

    @Test
    void deveCriarErrorMessageDtoComMensagemComQuebrasDeLinha() {
        String mensagem = "Erro:\nLinha 1\nLinha 2\nLinha 3";

        ErrorMessageDto errorMessageDto = new ErrorMessageDto(mensagem);

        assertEquals(mensagem, errorMessageDto.message());
        assertTrue(errorMessageDto.message().contains("\n"));
    }

    @Test
    void deveCriarErrorMessageDtoComMensagemComTabs() {
        String mensagem = "Erro:\tValor\tinválido";

        ErrorMessageDto errorMessageDto = new ErrorMessageDto(mensagem);

        assertEquals(mensagem, errorMessageDto.message());
        assertTrue(errorMessageDto.message().contains("\t"));
    }

    @Test
    void deveCriarErrorMessageDtoComMensagemEmIngles() {
        String mensagem = "Invalid CPF format";

        ErrorMessageDto errorMessageDto = new ErrorMessageDto(mensagem);

        assertEquals(mensagem, errorMessageDto.message());
    }

    @Test
    void deveCriarErrorMessageDtoComMensagemJSON() {
        String mensagem = "{\"error\": \"validation failed\"}";

        ErrorMessageDto errorMessageDto = new ErrorMessageDto(mensagem);

        assertEquals(mensagem, errorMessageDto.message());
    }

    @Test
    void deveCompararErrorMessageDtoComNull() {
        ErrorMessageDto errorMessageDto = new ErrorMessageDto("Mensagem");

        assertNotEquals(null, errorMessageDto);
    }

    @Test
    void deveCompararErrorMessageDtoComOutroTipo() {
        ErrorMessageDto errorMessageDto = new ErrorMessageDto("Mensagem");
        String string = "Mensagem";

        assertNotEquals(errorMessageDto, string);
    }

    @Test
    void deveCriarErrorMessageDtoComMensagemDeTamanhoMinimo() {
        String mensagem = "A";

        ErrorMessageDto errorMessageDto = new ErrorMessageDto(mensagem);

        assertEquals(mensagem, errorMessageDto.message());
        assertEquals(1, errorMessageDto.message().length());
    }

    @Test
    void deveCriarErrorMessageDtoComEspacos() {
        String mensagem = "   Mensagem com espaços   ";

        ErrorMessageDto errorMessageDto = new ErrorMessageDto(mensagem);

        assertEquals(mensagem, errorMessageDto.message());
    }

    @Test
    void deveSerImutavel() {
        ErrorMessageDto errorMessageDto = new ErrorMessageDto("Mensagem original");
        String mensagemOriginal = errorMessageDto.message();

        // Records são imutáveis, então não há setters
        assertEquals(mensagemOriginal, errorMessageDto.message());
    }

    @Test
    void deveTerConstrutorCanonigo() {
        // Verifica que o construtor canônico existe
        assertDoesNotThrow(() -> new ErrorMessageDto("Mensagem de teste"));
    }

    @Test
    void deveCriarErrorMessageDtoParaCadaCampoInvalido() {
        ErrorMessageDto erroCpf = new ErrorMessageDto("CPF inválido");
        ErrorMessageDto erroNome = new ErrorMessageDto("Nome inválido");
        ErrorMessageDto erroEmail = new ErrorMessageDto("Email inválido");

        assertNotNull(erroCpf);
        assertNotNull(erroNome);
        assertNotNull(erroEmail);
        
        assertNotEquals(erroCpf.message(), erroNome.message());
        assertNotEquals(erroNome.message(), erroEmail.message());
    }
}

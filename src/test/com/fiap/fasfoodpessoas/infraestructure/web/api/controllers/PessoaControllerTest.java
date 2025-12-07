package com.fiap.fasfoodpessoas.infraestructure.web.api.controllers;

import br.com.fiap.fasfoodpessoas.domain.enums.TipoPessoaEnum;
import br.com.fiap.fasfoodpessoas.domain.models.PessoaModel;
import br.com.fiap.fasfoodpessoas.domain.ports.in.PessoaServicePort;
import br.com.fiap.fasfoodpessoas.infraestructure.web.api.controllers.PessoaController;
import br.com.fiap.fasfoodpessoas.infraestructure.web.api.dtos.PessoaRequestDto;
import br.com.fiap.fasfoodpessoas.infraestructure.web.api.dtos.PessoaResponseDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PessoaController.class)
class PessoaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private PessoaServicePort pessoaServicePort;

    private PessoaRequestDto pessoaRequestDto;
    private PessoaModel pessoaModel;

    @BeforeEach
    void setUp() {
        pessoaRequestDto = new PessoaRequestDto(
                "12345678900",
                "João Silva",
                TipoPessoaEnum.CLIENTE,
                "joao@email.com"
        );

        pessoaModel = new PessoaModel.Builder()
                .setCdDocPessoa("12345678900")
                .setNmPessoa("João Silva")
                .setTpPessoa(TipoPessoaEnum.CLIENTE)
                .setDsEmail("joao@email.com")
                .build();
    }

    @Test
    void deveCadastrarPessoaComSucesso() throws Exception {
        when(pessoaServicePort.cadastraPessoa(any(PessoaModel.class))).thenReturn(pessoaModel);

        mockMvc.perform(post("/api/v1/pessoa")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(pessoaRequestDto)))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "/api/v1/pessoa/12345678900"))
                .andExpect(jsonPath("$.cdDocPessoa", is("12345678900")))
                .andExpect(jsonPath("$.nmPessoa", is("João Silva")))
                .andExpect(jsonPath("$.tpPessoa", is("CLIENTE")))
                .andExpect(jsonPath("$.dsEmail", is("joao@email.com")));

        verify(pessoaServicePort, times(1)).cadastraPessoa(any(PessoaModel.class));
    }

    @Test
    void deveCadastrarFuncionarioComSucesso() throws Exception {
        PessoaRequestDto funcionarioDto = new PessoaRequestDto(
                "98765432100",
                "Maria Santos",
                TipoPessoaEnum.FUNCIONARIO,
                "maria@empresa.com"
        );

        PessoaModel funcionarioModel = new PessoaModel.Builder()
                .setCdDocPessoa("98765432100")
                .setNmPessoa("Maria Santos")
                .setTpPessoa(TipoPessoaEnum.FUNCIONARIO)
                .setDsEmail("maria@empresa.com")
                .build();

        when(pessoaServicePort.cadastraPessoa(any(PessoaModel.class))).thenReturn(funcionarioModel);

        mockMvc.perform(post("/api/v1/pessoa")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(funcionarioDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.tpPessoa", is("FUNCIONARIO")))
                .andExpect(jsonPath("$.nmPessoa", is("Maria Santos")));

        verify(pessoaServicePort, times(1)).cadastraPessoa(any(PessoaModel.class));
    }

    @Test
    void deveBuscarPessoaPorCpfComSucesso() throws Exception {
        when(pessoaServicePort.buscaPessoaPorCpf("12345678900")).thenReturn(Optional.of(pessoaModel));

        mockMvc.perform(get("/api/v1/pessoa/12345678900")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.cdDocPessoa", is("12345678900")))
                .andExpect(jsonPath("$.nmPessoa", is("João Silva")))
                .andExpect(jsonPath("$.tpPessoa", is("CLIENTE")))
                .andExpect(jsonPath("$.dsEmail", is("joao@email.com")));

        verify(pessoaServicePort, times(1)).buscaPessoaPorCpf("12345678900");
    }

    @Test
    void deveRetornarNotFoundQuandoPessoaNaoEncontrada() throws Exception {
        when(pessoaServicePort.buscaPessoaPorCpf("00000000000")).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/v1/pessoa/00000000000")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        verify(pessoaServicePort, times(1)).buscaPessoaPorCpf("00000000000");
    }

    @Test
    void deveRetornarBadRequestQuandoCpfInvalido() throws Exception {
        PessoaRequestDto dtoInvalido = new PessoaRequestDto(
                "123",
                "João Silva",
                TipoPessoaEnum.CLIENTE,
                "joao@email.com"
        );

        mockMvc.perform(post("/api/v1/pessoa")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dtoInvalido)))
                .andExpect(status().isBadRequest());

        verify(pessoaServicePort, never()).cadastraPessoa(any(PessoaModel.class));
    }

    @Test
    void deveRetornarBadRequestQuandoNomeVazio() throws Exception {
        PessoaRequestDto dtoInvalido = new PessoaRequestDto(
                "12345678900",
                "",
                TipoPessoaEnum.CLIENTE,
                "joao@email.com"
        );

        mockMvc.perform(post("/api/v1/pessoa")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dtoInvalido)))
                .andExpect(status().isBadRequest());

        verify(pessoaServicePort, never()).cadastraPessoa(any(PessoaModel.class));
    }

    @Test
    void deveRetornarBadRequestQuandoEmailInvalido() throws Exception {
        PessoaRequestDto dtoInvalido = new PessoaRequestDto(
                "12345678900",
                "João Silva",
                TipoPessoaEnum.CLIENTE,
                "email-invalido"
        );

        mockMvc.perform(post("/api/v1/pessoa")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dtoInvalido)))
                .andExpect(status().isBadRequest());

        verify(pessoaServicePort, never()).cadastraPessoa(any(PessoaModel.class));
    }

    @Test
    void deveRetornarBadRequestQuandoCpfNulo() throws Exception {
        PessoaRequestDto dtoInvalido = new PessoaRequestDto(
                null,
                "João Silva",
                TipoPessoaEnum.CLIENTE,
                "joao@email.com"
        );

        mockMvc.perform(post("/api/v1/pessoa")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dtoInvalido)))
                .andExpect(status().isBadRequest());

        verify(pessoaServicePort, never()).cadastraPessoa(any(PessoaModel.class));
    }

    @Test
    void deveRetornarBadRequestQuandoTipoPessoaNulo() throws Exception {
        PessoaRequestDto dtoInvalido = new PessoaRequestDto(
                "12345678900",
                "João Silva",
                null,
                "joao@email.com"
        );

        mockMvc.perform(post("/api/v1/pessoa")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dtoInvalido)))
                .andExpect(status().isBadRequest());

        verify(pessoaServicePort, never()).cadastraPessoa(any(PessoaModel.class));
    }

    @Test
    void deveRetornarLocationHeaderAoCadastrar() throws Exception {
        when(pessoaServicePort.cadastraPessoa(any(PessoaModel.class))).thenReturn(pessoaModel);

        mockMvc.perform(post("/api/v1/pessoa")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(pessoaRequestDto)))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"))
                .andExpect(header().string("Location", "/api/v1/pessoa/12345678900"));
    }

    @Test
    void deveConverterRequestDtoParaModel() throws Exception {
        when(pessoaServicePort.cadastraPessoa(any(PessoaModel.class))).thenReturn(pessoaModel);

        mockMvc.perform(post("/api/v1/pessoa")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(pessoaRequestDto)))
                .andExpect(status().isCreated());

        verify(pessoaServicePort).cadastraPessoa(argThat(model ->
                model.getCdDocPessoa().equals("12345678900") &&
                model.getNmPessoa().equals("João Silva") &&
                model.getTpPessoa().equals(TipoPessoaEnum.CLIENTE) &&
                model.getDsEmail().equals("joao@email.com")
        ));
    }

    @Test
    void deveConverterModelParaResponseDto() throws Exception {
        when(pessoaServicePort.buscaPessoaPorCpf("12345678900")).thenReturn(Optional.of(pessoaModel));

        mockMvc.perform(get("/api/v1/pessoa/12345678900"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.cdDocPessoa").value("12345678900"))
                .andExpect(jsonPath("$.nmPessoa").value("João Silva"))
                .andExpect(jsonPath("$.tpPessoa").value("CLIENTE"))
                .andExpect(jsonPath("$.dsEmail").value("joao@email.com"));
    }

    @Test
    void deveCadastrarPessoaComNomeCompleto() throws Exception {
        PessoaRequestDto dtoNomeCompleto = new PessoaRequestDto(
                "11111111111",
                "José da Silva Santos Júnior",
                TipoPessoaEnum.CLIENTE,
                "jose@email.com"
        );

        PessoaModel modelNomeCompleto = new PessoaModel.Builder()
                .setCdDocPessoa("11111111111")
                .setNmPessoa("José da Silva Santos Júnior")
                .setTpPessoa(TipoPessoaEnum.CLIENTE)
                .setDsEmail("jose@email.com")
                .build();

        when(pessoaServicePort.cadastraPessoa(any(PessoaModel.class))).thenReturn(modelNomeCompleto);

        mockMvc.perform(post("/api/v1/pessoa")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dtoNomeCompleto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nmPessoa", is("José da Silva Santos Júnior")));
    }

    @Test
    void deveBuscarDiferentesPessoasPorCpf() throws Exception {
        PessoaModel pessoa1 = new PessoaModel.Builder()
                .setCdDocPessoa("11111111111")
                .setNmPessoa("Pessoa 1")
                .setTpPessoa(TipoPessoaEnum.CLIENTE)
                .setDsEmail("pessoa1@email.com")
                .build();

        PessoaModel pessoa2 = new PessoaModel.Builder()
                .setCdDocPessoa("22222222222")
                .setNmPessoa("Pessoa 2")
                .setTpPessoa(TipoPessoaEnum.FUNCIONARIO)
                .setDsEmail("pessoa2@email.com")
                .build();

        when(pessoaServicePort.buscaPessoaPorCpf("11111111111")).thenReturn(Optional.of(pessoa1));
        when(pessoaServicePort.buscaPessoaPorCpf("22222222222")).thenReturn(Optional.of(pessoa2));

        mockMvc.perform(get("/api/v1/pessoa/11111111111"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nmPessoa", is("Pessoa 1")));

        mockMvc.perform(get("/api/v1/pessoa/22222222222"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nmPessoa", is("Pessoa 2")));

        verify(pessoaServicePort, times(1)).buscaPessoaPorCpf("11111111111");
        verify(pessoaServicePort, times(1)).buscaPessoaPorCpf("22222222222");
    }

    @Test
    void deveRetornarContentTypeJsonAoCadastrar() throws Exception {
        when(pessoaServicePort.cadastraPessoa(any(PessoaModel.class))).thenReturn(pessoaModel);

        mockMvc.perform(post("/api/v1/pessoa")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(pessoaRequestDto)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void deveRetornarContentTypeJsonAoBuscar() throws Exception {
        when(pessoaServicePort.buscaPessoaPorCpf("12345678900")).thenReturn(Optional.of(pessoaModel));

        mockMvc.perform(get("/api/v1/pessoa/12345678900"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void deveValidarTamanhoMinimoDoNome() throws Exception {
        PessoaRequestDto dtoInvalido = new PessoaRequestDto(
                "12345678900",
                "Jo",
                TipoPessoaEnum.CLIENTE,
                "joao@email.com"
        );

        mockMvc.perform(post("/api/v1/pessoa")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dtoInvalido)))
                .andExpect(status().isBadRequest());

        verify(pessoaServicePort, never()).cadastraPessoa(any(PessoaModel.class));
    }

    @Test
    void deveAceitarNomeComTamanhoValido() throws Exception {
        PessoaRequestDto dtoValido = new PessoaRequestDto(
                "12345678900",
                "João",
                TipoPessoaEnum.CLIENTE,
                "joao@email.com"
        );

        PessoaModel model = new PessoaModel.Builder()
                .setCdDocPessoa("12345678900")
                .setNmPessoa("João")
                .setTpPessoa(TipoPessoaEnum.CLIENTE)
                .setDsEmail("joao@email.com")
                .build();

        when(pessoaServicePort.cadastraPessoa(any(PessoaModel.class))).thenReturn(model);

        mockMvc.perform(post("/api/v1/pessoa")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dtoValido)))
                .andExpect(status().isCreated());

        verify(pessoaServicePort, times(1)).cadastraPessoa(any(PessoaModel.class));
    }

    @Test
    void deveResponderNaRota_ApiV1Pessoa() throws Exception {
        when(pessoaServicePort.cadastraPessoa(any(PessoaModel.class))).thenReturn(pessoaModel);

        mockMvc.perform(post("/api/v1/pessoa")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(pessoaRequestDto)))
                .andExpect(status().isCreated());
    }

    @Test
    void deveResponderNaRota_ApiV1PessoaComPathVariable() throws Exception {
        when(pessoaServicePort.buscaPessoaPorCpf(anyString())).thenReturn(Optional.of(pessoaModel));

        mockMvc.perform(get("/api/v1/pessoa/12345678900"))
                .andExpect(status().isOk());
    }
}


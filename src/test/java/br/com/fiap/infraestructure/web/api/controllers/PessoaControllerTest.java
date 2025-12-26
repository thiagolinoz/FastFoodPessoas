package br.com.fiap.infraestructure.web.api.controllers;

import br.com.fiap.fasfoodpessoas.FastFoodPessoasApplication;
import br.com.fiap.fasfoodpessoas.domain.enums.TipoPessoaEnum;
import br.com.fiap.fasfoodpessoas.domain.models.PessoaModel;
import br.com.fiap.fasfoodpessoas.domain.ports.in.PessoaServicePort;
import br.com.fiap.fasfoodpessoas.infraestructure.web.api.controllers.PessoaController;
import br.com.fiap.fasfoodpessoas.infraestructure.web.api.dtos.PessoaRequestDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureWebMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
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

@WebMvcTest(controllers = PessoaController.class)
@ContextConfiguration(classes = FastFoodPessoasApplication.class)
@AutoConfigureWebMvc
class PessoaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private PessoaServicePort pessoaServicePort;

    private PessoaRequestDto pessoaRequestDto;
    private PessoaModel pessoaModel;

    @BeforeEach
    void setUp() {
        pessoaRequestDto = new PessoaRequestDto(
                "11144477735",
                "João Silva",
                TipoPessoaEnum.CLIENTE,
                "joao@email.com"
        );

        pessoaModel = new PessoaModel.Builder()
                .setCdDocPessoa("11144477735")
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
                .andExpect(header().string("Location", "/api/v1/pessoa/11144477735"))
                .andExpect(jsonPath("$.cdDocPessoa", is("11144477735")))
                .andExpect(jsonPath("$.nmPessoa", is("João Silva")))
                .andExpect(jsonPath("$.tpPessoa", is("CLIENTE")))
                .andExpect(jsonPath("$.dsEmail", is("joao@email.com")));

        verify(pessoaServicePort, times(1)).cadastraPessoa(any(PessoaModel.class));
    }

    @Test
    void deveCadastrarFuncionarioComSucesso() throws Exception {
        PessoaRequestDto funcionarioDto = new PessoaRequestDto(
                "19119119100",
                "Maria Santos",
                TipoPessoaEnum.FUNCIONARIO,
                "maria@empresa.com"
        );

        PessoaModel funcionarioModel = new PessoaModel.Builder()
                .setCdDocPessoa("19119119100")
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
        when(pessoaServicePort.buscaPessoaPorCpf("11144477735")).thenReturn(Optional.of(pessoaModel));

        mockMvc.perform(get("/api/v1/pessoa/11144477735")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.cdDocPessoa", is("11144477735")))
                .andExpect(jsonPath("$.nmPessoa", is("João Silva")))
                .andExpect(jsonPath("$.tpPessoa", is("CLIENTE")))
                .andExpect(jsonPath("$.dsEmail", is("joao@email.com")));

        verify(pessoaServicePort, times(1)).buscaPessoaPorCpf("11144477735");
    }

    @Test
    void deveRetornarNoContentQuandoPessoaNaoEncontrada() throws Exception {
        when(pessoaServicePort.buscaPessoaPorCpf("12345678909")).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/v1/pessoa/12345678909")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        verify(pessoaServicePort, times(1)).buscaPessoaPorCpf("12345678909");
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
                "11144477735",
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
                "11144477735",
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
                "11144477735",
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
                .andExpect(header().string("Location", "/api/v1/pessoa/11144477735"));
    }

    @Test
    void deveConverterRequestDtoParaModel() throws Exception {
        when(pessoaServicePort.cadastraPessoa(any(PessoaModel.class))).thenReturn(pessoaModel);

        mockMvc.perform(post("/api/v1/pessoa")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(pessoaRequestDto)))
                .andExpect(status().isCreated());

        verify(pessoaServicePort).cadastraPessoa(argThat(model ->
                model.getCdDocPessoa().equals("11144477735") &&
                model.getNmPessoa().equals("João Silva") &&
                model.getTpPessoa().equals(TipoPessoaEnum.CLIENTE) &&
                model.getDsEmail().equals("joao@email.com")
        ));
    }

    @Test
    void deveConverterModelParaResponseDto() throws Exception {
        when(pessoaServicePort.buscaPessoaPorCpf("11144477735")).thenReturn(Optional.of(pessoaModel));

        mockMvc.perform(get("/api/v1/pessoa/11144477735"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.cdDocPessoa").value("11144477735"))
                .andExpect(jsonPath("$.nmPessoa").value("João Silva"))
                .andExpect(jsonPath("$.tpPessoa").value("CLIENTE"))
                .andExpect(jsonPath("$.dsEmail").value("joao@email.com"));
    }

    @Test
    void deveCadastrarPessoaComNomeCompleto() throws Exception {
        PessoaRequestDto dtoNomeCompleto = new PessoaRequestDto(
                "52998224725",
                "José da Silva Santos Júnior",
                TipoPessoaEnum.CLIENTE,
                "jose@email.com"
        );

        PessoaModel modelNomeCompleto = new PessoaModel.Builder()
                .setCdDocPessoa("52998224725")
                .setNmPessoa("José da Silva Santos Júnior")
                .setTpPessoa(TipoPessoaEnum.CLIENTE)
                .setDsEmail("jose@email.com")
                .build();

        when(pessoaServicePort.cadastraPessoa(any(PessoaModel.class))).thenReturn(modelNomeCompleto);

        String jsonContent = objectMapper.writeValueAsString(dtoNomeCompleto);
        System.out.println("JSON enviado: " + jsonContent);

        mockMvc.perform(post("/api/v1/pessoa")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonContent))
                .andDo(result -> System.out.println("Response: " + result.getResponse().getContentAsString()))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nmPessoa", is("José da Silva Santos Júnior")));
    }

    @Test
    void deveBuscarDiferentesPessoasPorCpf() throws Exception {
        PessoaModel pessoa1 = new PessoaModel.Builder()
                .setCdDocPessoa("52998224725")
                .setNmPessoa("Pessoa 1")
                .setTpPessoa(TipoPessoaEnum.CLIENTE)
                .setDsEmail("pessoa1@email.com")
                .build();

        PessoaModel pessoa2 = new PessoaModel.Builder()
                .setCdDocPessoa("71503154071")
                .setNmPessoa("Pessoa 2")
                .setTpPessoa(TipoPessoaEnum.FUNCIONARIO)
                .setDsEmail("pessoa2@email.com")
                .build();

        when(pessoaServicePort.buscaPessoaPorCpf("52998224725")).thenReturn(Optional.of(pessoa1));
        when(pessoaServicePort.buscaPessoaPorCpf("71503154071")).thenReturn(Optional.of(pessoa2));

        mockMvc.perform(get("/api/v1/pessoa/52998224725"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nmPessoa", is("Pessoa 1")));

        mockMvc.perform(get("/api/v1/pessoa/71503154071"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nmPessoa", is("Pessoa 2")));

        verify(pessoaServicePort, times(1)).buscaPessoaPorCpf("52998224725");
        verify(pessoaServicePort, times(1)).buscaPessoaPorCpf("71503154071");
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
        when(pessoaServicePort.buscaPessoaPorCpf("11144477735")).thenReturn(Optional.of(pessoaModel));

        mockMvc.perform(get("/api/v1/pessoa/11144477735"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void deveValidarTamanhoMinimoDoNome() throws Exception {
        PessoaRequestDto dtoInvalido = new PessoaRequestDto(
                "11144477735",
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
                "11144477735",
                "João",
                TipoPessoaEnum.CLIENTE,
                "joao@email.com"
        );

        PessoaModel model = new PessoaModel.Builder()
                .setCdDocPessoa("11144477735")
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

        mockMvc.perform(get("/api/v1/pessoa/11144477735"))
                .andExpect(status().isOk());
    }
}


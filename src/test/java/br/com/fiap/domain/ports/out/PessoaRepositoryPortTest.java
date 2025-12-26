package br.com.fiap.domain.ports.out;

import br.com.fiap.fasfoodpessoas.domain.enums.TipoPessoaEnum;
import br.com.fiap.fasfoodpessoas.domain.models.PessoaModel;
import br.com.fiap.fasfoodpessoas.domain.ports.out.PessoaRepositoryPort;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class PessoaRepositoryPortTest {

    @Test
    void deveDefinirMetodoCadastrarPessoa() throws NoSuchMethodException {
        var method = PessoaRepositoryPort.class.getMethod("cadastrarPessoa", PessoaModel.class);
        
        assertNotNull(method);
        assertEquals(PessoaModel.class, method.getReturnType());
        assertEquals(1, method.getParameterCount());
        assertEquals(PessoaModel.class, method.getParameterTypes()[0]);
    }

    @Test
    void deveDefinirMetodoBuscarPessoaPorCpf() throws NoSuchMethodException {
        var method = PessoaRepositoryPort.class.getMethod("buscarPessoaPorCpf", String.class);
        
        assertNotNull(method);
        assertEquals(Optional.class, method.getReturnType());
        assertEquals(1, method.getParameterCount());
        assertEquals(String.class, method.getParameterTypes()[0]);
    }

    @Test
    void deveSerUmaInterface() {
        assertTrue(PessoaRepositoryPort.class.isInterface());
    }

    @Test
    void deveConterDoisMetodos() {
        assertEquals(2, PessoaRepositoryPort.class.getDeclaredMethods().length);
    }

    @Test
    void devePermitirImplementacao() {
        PessoaRepositoryPort implementacao = new PessoaRepositoryPort() {
            @Override
            public PessoaModel cadastrarPessoa(PessoaModel pessoaModel) {
                return pessoaModel;
            }

            @Override
            public Optional<PessoaModel> buscarPessoaPorCpf(String cdDocPessoa) {
                return Optional.empty();
            }
        };

        assertNotNull(implementacao);
    }

    @Test
    void deveExecutarCadastrarPessoaNaImplementacao() {
        PessoaModel pessoaEsperada = new PessoaModel.Builder()
                .setCdDocPessoa("12345678900")
                .setNmPessoa("Ana Silva")
                .setTpPessoa(TipoPessoaEnum.CLIENTE)
                .setDsEmail("ana@email.com")
                .build();

        PessoaRepositoryPort implementacao = new PessoaRepositoryPort() {
            @Override
            public PessoaModel cadastrarPessoa(PessoaModel pessoaModel) {
                return pessoaModel;
            }

            @Override
            public Optional<PessoaModel> buscarPessoaPorCpf(String cdDocPessoa) {
                return Optional.empty();
            }
        };

        PessoaModel resultado = implementacao.cadastrarPessoa(pessoaEsperada);

        assertNotNull(resultado);
        assertEquals(pessoaEsperada.getCdDocPessoa(), resultado.getCdDocPessoa());
        assertEquals(pessoaEsperada.getNmPessoa(), resultado.getNmPessoa());
    }

    @Test
    void deveExecutarBuscarPessoaPorCpfNaImplementacao() {
        PessoaModel pessoa = new PessoaModel.Builder()
                .setCdDocPessoa("98765432100")
                .setNmPessoa("Carlos Eduardo")
                .setTpPessoa(TipoPessoaEnum.FUNCIONARIO)
                .setDsEmail("carlos@email.com")
                .build();

        PessoaRepositoryPort implementacao = new PessoaRepositoryPort() {
            @Override
            public PessoaModel cadastrarPessoa(PessoaModel pessoaModel) {
                return pessoaModel;
            }

            @Override
            public Optional<PessoaModel> buscarPessoaPorCpf(String cdDocPessoa) {
                if ("98765432100".equals(cdDocPessoa)) {
                    return Optional.of(pessoa);
                }
                return Optional.empty();
            }
        };

        Optional<PessoaModel> resultado = implementacao.buscarPessoaPorCpf("98765432100");

        assertTrue(resultado.isPresent());
        assertEquals("98765432100", resultado.get().getCdDocPessoa());
        assertEquals("Carlos Eduardo", resultado.get().getNmPessoa());
    }

    @Test
    void deveRetornarOptionalVazioQuandoCpfNaoEncontrado() {
        PessoaRepositoryPort implementacao = new PessoaRepositoryPort() {
            @Override
            public PessoaModel cadastrarPessoa(PessoaModel pessoaModel) {
                return pessoaModel;
            }

            @Override
            public Optional<PessoaModel> buscarPessoaPorCpf(String cdDocPessoa) {
                return Optional.empty();
            }
        };

        Optional<PessoaModel> resultado = implementacao.buscarPessoaPorCpf("00000000000");

        assertFalse(resultado.isPresent());
        assertTrue(resultado.isEmpty());
    }

    @Test
    void devePermitirCadastroDeCliente() {
        PessoaModel cliente = new PessoaModel.Builder()
                .setCdDocPessoa("11111111111")
                .setNmPessoa("Maria Santos")
                .setTpPessoa(TipoPessoaEnum.CLIENTE)
                .setDsEmail("maria@email.com")
                .build();

        PessoaRepositoryPort implementacao = new PessoaRepositoryPort() {
            @Override
            public PessoaModel cadastrarPessoa(PessoaModel pessoaModel) {
                return pessoaModel;
            }

            @Override
            public Optional<PessoaModel> buscarPessoaPorCpf(String cdDocPessoa) {
                return Optional.empty();
            }
        };

        PessoaModel resultado = implementacao.cadastrarPessoa(cliente);

        assertEquals(TipoPessoaEnum.CLIENTE, resultado.getTpPessoa());
    }

    @Test
    void devePermitirCadastroDeFuncionario() {
        PessoaModel funcionario = new PessoaModel.Builder()
                .setCdDocPessoa("22222222222")
                .setNmPessoa("Pedro Alves")
                .setTpPessoa(TipoPessoaEnum.FUNCIONARIO)
                .setDsEmail("pedro@empresa.com")
                .build();

        PessoaRepositoryPort implementacao = new PessoaRepositoryPort() {
            @Override
            public PessoaModel cadastrarPessoa(PessoaModel pessoaModel) {
                return pessoaModel;
            }

            @Override
            public Optional<PessoaModel> buscarPessoaPorCpf(String cdDocPessoa) {
                return Optional.empty();
            }
        };

        PessoaModel resultado = implementacao.cadastrarPessoa(funcionario);

        assertEquals(TipoPessoaEnum.FUNCIONARIO, resultado.getTpPessoa());
    }

    @Test
    void devePermitirImplementacaoComArmazenamentoEmMemoria() {
        PessoaRepositoryPort implementacao = new PessoaRepositoryPort() {
            private PessoaModel pessoaArmazenada;

            @Override
            public PessoaModel cadastrarPessoa(PessoaModel pessoaModel) {
                this.pessoaArmazenada = pessoaModel;
                return pessoaModel;
            }

            @Override
            public Optional<PessoaModel> buscarPessoaPorCpf(String cdDocPessoa) {
                if (pessoaArmazenada != null && pessoaArmazenada.getCdDocPessoa().equals(cdDocPessoa)) {
                    return Optional.of(pessoaArmazenada);
                }
                return Optional.empty();
            }
        };

        PessoaModel pessoa = new PessoaModel.Builder()
                .setCdDocPessoa("33333333333")
                .setNmPessoa("Julia Costa")
                .setTpPessoa(TipoPessoaEnum.CLIENTE)
                .setDsEmail("julia@email.com")
                .build();

        implementacao.cadastrarPessoa(pessoa);
        Optional<PessoaModel> resultado = implementacao.buscarPessoaPorCpf("33333333333");

        assertTrue(resultado.isPresent());
        assertEquals("Julia Costa", resultado.get().getNmPessoa());
    }

    @Test
    void deveVerificarAssinaturaCompletaDoMetodoCadastrarPessoa() throws NoSuchMethodException {
        var method = PessoaRepositoryPort.class.getMethod("cadastrarPessoa", PessoaModel.class);
        
        assertFalse(method.isDefault());
        assertTrue(java.lang.reflect.Modifier.isAbstract(method.getModifiers()));
        assertTrue(java.lang.reflect.Modifier.isPublic(method.getModifiers()));
    }

    @Test
    void deveVerificarAssinaturaCompletaDoMetodoBuscarPessoaPorCpf() throws NoSuchMethodException {
        var method = PessoaRepositoryPort.class.getMethod("buscarPessoaPorCpf", String.class);
        
        assertFalse(method.isDefault());
        assertTrue(java.lang.reflect.Modifier.isAbstract(method.getModifiers()));
        assertTrue(java.lang.reflect.Modifier.isPublic(method.getModifiers()));
    }

    @Test
    void deveEstarNoPacoteCorreto() {
        assertEquals("br.com.fiap.fasfoodpessoas.domain.ports.out", 
                     PessoaRepositoryPort.class.getPackageName());
    }

    @Test
    void devePermitirMultiplasImplementacoesDiferentes() {
        PessoaRepositoryPort implementacao1 = new PessoaRepositoryPort() {
            @Override
            public PessoaModel cadastrarPessoa(PessoaModel pessoaModel) {
                return pessoaModel;
            }

            @Override
            public Optional<PessoaModel> buscarPessoaPorCpf(String cdDocPessoa) {
                return Optional.empty();
            }
        };

        PessoaRepositoryPort implementacao2 = new PessoaRepositoryPort() {
            @Override
            public PessoaModel cadastrarPessoa(PessoaModel pessoaModel) {
                return new PessoaModel.Builder()
                        .setCdDocPessoa(pessoaModel.getCdDocPessoa())
                        .setNmPessoa(pessoaModel.getNmPessoa().toUpperCase())
                        .setTpPessoa(pessoaModel.getTpPessoa())
                        .setDsEmail(pessoaModel.getDsEmail().toLowerCase())
                        .build();
            }

            @Override
            public Optional<PessoaModel> buscarPessoaPorCpf(String cdDocPessoa) {
                return Optional.of(new PessoaModel());
            }
        };

        assertNotNull(implementacao1);
        assertNotNull(implementacao2);
        assertNotEquals(implementacao1.getClass(), implementacao2.getClass());
    }
}

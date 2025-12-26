package br.com.fiap.domain.ports.in;

import br.com.fiap.fasfoodpessoas.domain.enums.TipoPessoaEnum;
import br.com.fiap.fasfoodpessoas.domain.models.PessoaModel;
import br.com.fiap.fasfoodpessoas.domain.ports.in.PessoaServicePort;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class PessoaServicePortTest {

    @Test
    void deveDefinirMetodoCadastraPessoa() throws NoSuchMethodException {
        var method = PessoaServicePort.class.getMethod("cadastraPessoa", PessoaModel.class);
        
        assertNotNull(method);
        assertEquals(PessoaModel.class, method.getReturnType());
        assertEquals(1, method.getParameterCount());
        assertEquals(PessoaModel.class, method.getParameterTypes()[0]);
    }

    @Test
    void deveDefinirMetodoBuscaPessoaPorCpf() throws NoSuchMethodException {
        var method = PessoaServicePort.class.getMethod("buscaPessoaPorCpf", String.class);
        
        assertNotNull(method);
        assertEquals(Optional.class, method.getReturnType());
        assertEquals(1, method.getParameterCount());
        assertEquals(String.class, method.getParameterTypes()[0]);
    }

    @Test
    void deveSerUmaInterface() {
        assertTrue(PessoaServicePort.class.isInterface());
    }

    @Test
    void deveConterDoisMetodos() {
        assertEquals(2, PessoaServicePort.class.getDeclaredMethods().length);
    }

    @Test
    void devePermitirImplementacao() {
        PessoaServicePort implementacao = new PessoaServicePort() {
            @Override
            public PessoaModel cadastraPessoa(PessoaModel pessoa) {
                return pessoa;
            }

            @Override
            public Optional<PessoaModel> buscaPessoaPorCpf(String cdDocPessoa) {
                return Optional.empty();
            }
        };

        assertNotNull(implementacao);
    }

    @Test
    void deveExecutarCadastraPessoaNaImplementacao() {
        PessoaModel pessoaEsperada = new PessoaModel.Builder()
                .setCdDocPessoa("12345678900")
                .setNmPessoa("João Silva")
                .setTpPessoa(TipoPessoaEnum.CLIENTE)
                .setDsEmail("joao@email.com")
                .build();

        PessoaServicePort implementacao = new PessoaServicePort() {
            @Override
            public PessoaModel cadastraPessoa(PessoaModel pessoa) {
                return pessoa;
            }

            @Override
            public Optional<PessoaModel> buscaPessoaPorCpf(String cdDocPessoa) {
                return Optional.empty();
            }
        };

        PessoaModel resultado = implementacao.cadastraPessoa(pessoaEsperada);

        assertNotNull(resultado);
        assertEquals(pessoaEsperada.getCdDocPessoa(), resultado.getCdDocPessoa());
    }

    @Test
    void deveExecutarBuscaPessoaPorCpfNaImplementacao() {
        PessoaModel pessoa = new PessoaModel.Builder()
                .setCdDocPessoa("98765432100")
                .setNmPessoa("Maria Santos")
                .setTpPessoa(TipoPessoaEnum.FUNCIONARIO)
                .setDsEmail("maria@email.com")
                .build();

        PessoaServicePort implementacao = new PessoaServicePort() {
            @Override
            public PessoaModel cadastraPessoa(PessoaModel pessoa) {
                return pessoa;
            }

            @Override
            public Optional<PessoaModel> buscaPessoaPorCpf(String cdDocPessoa) {
                if ("98765432100".equals(cdDocPessoa)) {
                    return Optional.of(pessoa);
                }
                return Optional.empty();
            }
        };

        Optional<PessoaModel> resultado = implementacao.buscaPessoaPorCpf("98765432100");

        assertTrue(resultado.isPresent());
        assertEquals("98765432100", resultado.get().getCdDocPessoa());
    }

    @Test
    void deveRetornarOptionalVazioQuandoCpfNaoEncontrado() {
        PessoaServicePort implementacao = new PessoaServicePort() {
            @Override
            public PessoaModel cadastraPessoa(PessoaModel pessoa) {
                return pessoa;
            }

            @Override
            public Optional<PessoaModel> buscaPessoaPorCpf(String cdDocPessoa) {
                return Optional.empty();
            }
        };

        Optional<PessoaModel> resultado = implementacao.buscaPessoaPorCpf("00000000000");

        assertFalse(resultado.isPresent());
        assertTrue(resultado.isEmpty());
    }

    @Test
    void devePermitirMultiplasImplementacoesDiferentes() {
        PessoaServicePort implementacao1 = new PessoaServicePort() {
            @Override
            public PessoaModel cadastraPessoa(PessoaModel pessoa) {
                return pessoa;
            }

            @Override
            public Optional<PessoaModel> buscaPessoaPorCpf(String cdDocPessoa) {
                return Optional.empty();
            }
        };

        PessoaServicePort implementacao2 = new PessoaServicePort() {
            @Override
            public PessoaModel cadastraPessoa(PessoaModel pessoa) {
                return new PessoaModel.Builder()
                        .setCdDocPessoa(pessoa.getCdDocPessoa())
                        .setNmPessoa(pessoa.getNmPessoa().toUpperCase())
                        .setTpPessoa(pessoa.getTpPessoa())
                        .setDsEmail(pessoa.getDsEmail())
                        .build();
            }

            @Override
            public Optional<PessoaModel> buscaPessoaPorCpf(String cdDocPessoa) {
                return Optional.of(new PessoaModel());
            }
        };

        assertNotNull(implementacao1);
        assertNotNull(implementacao2);
        assertNotEquals(implementacao1.getClass(), implementacao2.getClass());
    }

    @Test
    void deveVerificarAssinaturaCompletaDoMetodoCadastraPessoa() throws NoSuchMethodException {
        var method = PessoaServicePort.class.getMethod("cadastraPessoa", PessoaModel.class);
        
        assertFalse(method.isDefault());
        assertTrue(java.lang.reflect.Modifier.isAbstract(method.getModifiers()));
        assertTrue(java.lang.reflect.Modifier.isPublic(method.getModifiers()));
    }

    @Test
    void deveVerificarAssinaturaCompletaDoMetodoBuscaPessoaPorCpf() throws NoSuchMethodException {
        var method = PessoaServicePort.class.getMethod("buscaPessoaPorCpf", String.class);
        
        assertFalse(method.isDefault());
        assertTrue(java.lang.reflect.Modifier.isAbstract(method.getModifiers()));
        assertTrue(java.lang.reflect.Modifier.isPublic(method.getModifiers()));
    }

    @Test
    void deveEstarNoPacoteCorreto() {
        assertEquals("br.com.fiap.fasfoodpessoas.domain.ports.in", 
                     PessoaServicePort.class.getPackageName());
    }
}

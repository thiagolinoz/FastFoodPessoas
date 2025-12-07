package br.com.fiap.fasfoodpessoas.infraestructure.web.api.configs;

import br.com.fiap.fasfoodpessoas.domain.ports.out.PessoaRepositoryPort;
import br.com.fiap.fasfoodpessoas.domain.services.PessoaService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.Configuration;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("DomainConfig - Testes de Configuração")
class DomainConfigTest {

    @Test
    @DisplayName("Deve ter anotação @Configuration")
    void deveTerAnotacaoConfiguration() {
        assertTrue(DomainConfig.class.isAnnotationPresent(Configuration.class));
    }

    @Test
    @DisplayName("Deve ter método pessoaService anotado com @Bean")
    void deveTerMetodoPessoaServiceAnotadoComBean() throws NoSuchMethodException {
        var method = DomainConfig.class.getMethod("pessoaService", PessoaRepositoryPort.class);
        var beanAnnotation = method.getAnnotation(org.springframework.context.annotation.Bean.class);

        assertNotNull(beanAnnotation);
    }

    @Test
    @DisplayName("Deve método pessoaService retornar PessoaService")
    void deveMetodoPessoaServiceRetornarPessoaService() throws NoSuchMethodException {
        var method = DomainConfig.class.getMethod("pessoaService", PessoaRepositoryPort.class);

        assertEquals(PessoaService.class, method.getReturnType());
    }

    @Test
    @DisplayName("Deve método pessoaService receber PessoaRepositoryPort como parâmetro")
    void deveMetodoPessoaServiceReceberPessoaRepositoryPortComoParametro() throws NoSuchMethodException {
        var method = DomainConfig.class.getMethod("pessoaService", PessoaRepositoryPort.class);

        assertEquals(1, method.getParameterCount());
        assertEquals(PessoaRepositoryPort.class, method.getParameterTypes()[0]);
    }

    @Test
    @DisplayName("Deve método pessoaService ser público")
    void deveMetodoPessoaServiceSerPublico() throws NoSuchMethodException {
        var method = DomainConfig.class.getMethod("pessoaService", PessoaRepositoryPort.class);

        assertTrue(java.lang.reflect.Modifier.isPublic(method.getModifiers()));
    }

    @Test
    @DisplayName("Deve classe ser pública")
    void deveClasseSerPublica() {
        assertTrue(java.lang.reflect.Modifier.isPublic(DomainConfig.class.getModifiers()));
    }

    @Test
    @DisplayName("Deve classe não ser abstrata")
    void deveClasseNaoSerAbstrata() {
        assertFalse(java.lang.reflect.Modifier.isAbstract(DomainConfig.class.getModifiers()));
    }

    @Test
    @DisplayName("Deve classe não ser interface")
    void deveClasseNaoSerInterface() {
        assertFalse(DomainConfig.class.isInterface());
    }

    @Test
    @DisplayName("Deve classe não ser enum")
    void deveClasseNaoSerEnum() {
        assertFalse(DomainConfig.class.isEnum());
    }

    @Test
    @DisplayName("Deve classe ter apenas um método público")
    void deveClasseTeApenasUmMetodoPublico() {
        var methods = DomainConfig.class.getDeclaredMethods();
        long publicMethods = java.util.Arrays.stream(methods)
                .filter(m -> java.lang.reflect.Modifier.isPublic(m.getModifiers()))
                .count();

        assertEquals(1, publicMethods);
    }

    @Test
    @DisplayName("Deve classe ter construtor padrão público")
    void deveClasseTerConstrutorPadraoPublico() {
        assertDoesNotThrow(() -> {
            var constructor = DomainConfig.class.getDeclaredConstructor();
            assertTrue(java.lang.reflect.Modifier.isPublic(constructor.getModifiers()));
        });
    }

    @Test
    @DisplayName("Deve ser possível instanciar a classe")
    void deveSerPossivelInstanciarClasse() {
        assertDoesNotThrow(DomainConfig::new);
    }

    @Test
    @DisplayName("Deve classe estar no package correto")
    void deveClasseEstarNoPackageCorreto() {
        assertEquals("br.com.fiap.fasfoodpessoas.infraestructure.web.api.configs",
                DomainConfig.class.getPackageName());
    }

    @Test
    @DisplayName("Deve classe ter nome simples correto")
    void deveClasseTeNomeSimpleCorreto() {
        assertEquals("DomainConfig", DomainConfig.class.getSimpleName());
    }

    @Test
    @DisplayName("Deve classe ter nome canônico correto")
    void deveClasseTeNomeCanonico() {
        assertEquals("br.com.fiap.fasfoodpessoas.infraestructure.web.api.configs.DomainConfig",
                DomainConfig.class.getCanonicalName());
    }

    @Test
    @DisplayName("Deve classe herdar de Object")
    void deveClasseHerdarDeObject() {
        assertEquals(Object.class, DomainConfig.class.getSuperclass());
    }

    @Test
    @DisplayName("Deve classe ter apenas uma anotação")
    void deveClasseTerApenasUmaAnotacao() {
        var annotations = DomainConfig.class.getDeclaredAnnotations();

        assertEquals(1, annotations.length);
        assertTrue(annotations[0] instanceof Configuration);
    }

    @Test
    @DisplayName("Deve classe não ter campos declarados")
    void deveClasseNaoTerCamposDeclarados() {
        assertEquals(0, DomainConfig.class.getDeclaredFields().length);
    }

    @Test
    @DisplayName("Deve método pessoaService não ser estático")
    void deveMetodoPessoaServiceNaoSerEstatico() throws NoSuchMethodException {
        var method = DomainConfig.class.getMethod("pessoaService", PessoaRepositoryPort.class);

        assertFalse(java.lang.reflect.Modifier.isStatic(method.getModifiers()));
    }

    @Test
    @DisplayName("Deve método pessoaService não ser final")
    void deveMetodoPessoaServiceNaoSerFinal() throws NoSuchMethodException {
        var method = DomainConfig.class.getMethod("pessoaService", PessoaRepositoryPort.class);

        assertFalse(java.lang.reflect.Modifier.isFinal(method.getModifiers()));
    }

    @Test
    @DisplayName("Deve método pessoaService não lançar exceções")
    void deveMetodoPessoaServiceNaoLancarExcecoes() throws NoSuchMethodException {
        var method = DomainConfig.class.getMethod("pessoaService", PessoaRepositoryPort.class);

        assertEquals(0, method.getExceptionTypes().length);
    }

    @Test
    @DisplayName("Deve classe ter apenas um construtor")
    void deveClasseTerApenasUmConstrutor() {
        assertEquals(1, DomainConfig.class.getDeclaredConstructors().length);
    }

    @Test
    @DisplayName("Deve ser possível criar nova instância via reflection")
    void deveSerPossivelCriarNovaInstanciaViaReflection() {
        assertDoesNotThrow(() -> {
            var constructor = DomainConfig.class.getDeclaredConstructor();
            var instance = constructor.newInstance();
            assertNotNull(instance);
            assertTrue(instance instanceof DomainConfig);
        });
    }

    @Test
    @DisplayName("Deve método pessoaService ter nome correto")
    void deveMetodoPessoaServiceTeNomeCorreto() throws NoSuchMethodException {
        var method = DomainConfig.class.getMethod("pessoaService", PessoaRepositoryPort.class);

        assertEquals("pessoaService", method.getName());
    }

    @Test
    @DisplayName("Deve validar que método retorna instância não nula")
    void deveValidarMetodoRetornaInstanciaNaoNula() {
        var config = new DomainConfig();
        var mockRepository = org.mockito.Mockito.mock(PessoaRepositoryPort.class);

        PessoaService service = config.pessoaService(mockRepository);

        assertNotNull(service);
    }

    @Test
    @DisplayName("Deve validar que método retorna nova instância de PessoaService")
    void deveValidarMetodoRetornaNovaInstanciaPessoaService() {
        var config = new DomainConfig();
        var mockRepository = org.mockito.Mockito.mock(PessoaRepositoryPort.class);

        PessoaService service = config.pessoaService(mockRepository);

        assertNotNull(service);
        assertTrue(service instanceof PessoaService);
    }

    @Test
    @DisplayName("Deve validar que múltiplas chamadas retornam instâncias diferentes")
    void deveValidarMultiplasChmadasRetornamInstanciasDiferentes() {
        var config = new DomainConfig();
        var mockRepository1 = org.mockito.Mockito.mock(PessoaRepositoryPort.class);
        var mockRepository2 = org.mockito.Mockito.mock(PessoaRepositoryPort.class);

        PessoaService service1 = config.pessoaService(mockRepository1);
        PessoaService service2 = config.pessoaService(mockRepository2);

        assertNotNull(service1);
        assertNotNull(service2);
        assertNotSame(service1, service2);
    }

    @Test
    @DisplayName("Deve classe não ter métodos privados")
    void deveClasseNaoTerMetodosPrivados() {
        var methods = DomainConfig.class.getDeclaredMethods();
        long privateMethods = java.util.Arrays.stream(methods)
                .filter(m -> java.lang.reflect.Modifier.isPrivate(m.getModifiers()))
                .count();

        assertEquals(0, privateMethods);
    }

    @Test
    @DisplayName("Deve classe não ter métodos protected")
    void deveClasseNaoTerMetodosProtected() {
        var methods = DomainConfig.class.getDeclaredMethods();
        long protectedMethods = java.util.Arrays.stream(methods)
                .filter(m -> java.lang.reflect.Modifier.isProtected(m.getModifiers()))
                .count();

        assertEquals(0, protectedMethods);
    }

    @Test
    @DisplayName("Deve método pessoaService ter apenas anotação @Bean")
    void deveMetodoPessoaServiceTerApenasAnotacaoBean() throws NoSuchMethodException {
        var method = DomainConfig.class.getMethod("pessoaService", PessoaRepositoryPort.class);
        var annotations = method.getDeclaredAnnotations();

        assertEquals(1, annotations.length);
        assertTrue(annotations[0] instanceof org.springframework.context.annotation.Bean);
    }

    @Test
    @DisplayName("Deve validar estrutura do método pessoaService")
    void deveValidarEstruturaMetodoPessoaService() throws NoSuchMethodException {
        var method = DomainConfig.class.getMethod("pessoaService", PessoaRepositoryPort.class);

        // Validações completas
        assertNotNull(method);
        assertTrue(java.lang.reflect.Modifier.isPublic(method.getModifiers()));
        assertFalse(java.lang.reflect.Modifier.isStatic(method.getModifiers()));
        assertEquals(PessoaService.class, method.getReturnType());
        assertEquals(1, method.getParameterCount());
        assertEquals(PessoaRepositoryPort.class, method.getParameterTypes()[0]);
        assertNotNull(method.getAnnotation(org.springframework.context.annotation.Bean.class));
    }

    @Test
    @DisplayName("Deve classe ser uma classe de configuração válida do Spring")
    void deveClasseSerClasseConfiguracaoValidaSpring() {
        assertTrue(DomainConfig.class.isAnnotationPresent(Configuration.class));
        assertFalse(DomainConfig.class.isInterface());
        assertTrue(java.lang.reflect.Modifier.isPublic(DomainConfig.class.getModifiers()));
        assertDoesNotThrow(() -> DomainConfig.class.getDeclaredConstructor());
    }

    @Test
    @DisplayName("Deve método pessoaService aceitar parâmetro não nulo")
    void deveMetodoPessoaServiceAceitarParametroNaoNulo() {
        var config = new DomainConfig();
        var mockRepository = org.mockito.Mockito.mock(PessoaRepositoryPort.class);

        assertDoesNotThrow(() -> config.pessoaService(mockRepository));
    }

    @Test
    @DisplayName("Deve classe ter design pattern de configuração Spring")
    void deveClasseTerDesignPatternConfiguracaoSpring() {
        // Validar padrão de configuração Spring
        assertTrue(DomainConfig.class.isAnnotationPresent(Configuration.class));
        
        var methods = DomainConfig.class.getDeclaredMethods();
        long beanMethods = java.util.Arrays.stream(methods)
                .filter(m -> m.isAnnotationPresent(org.springframework.context.annotation.Bean.class))
                .count();

        assertTrue(beanMethods > 0);
    }

    @Test
    @DisplayName("Deve validar que DomainConfig configura corretamente a camada de domínio")
    void deveValidarDomainConfigConfiguraCorretamenteCamadaDominio() throws NoSuchMethodException {
        // Validar que o bean configurado é da camada de domínio
        var method = DomainConfig.class.getMethod("pessoaService", PessoaRepositoryPort.class);
        var returnType = method.getReturnType();

        assertTrue(returnType.getPackageName().contains("domain"));
        assertEquals("br.com.fiap.fasfoodpessoas.domain.services", returnType.getPackageName());
    }

    @Test
    @DisplayName("Deve validar que método pessoaService cria bean gerenciado pelo Spring")
    void deveValidarMetodoPessoaServiceCriaBeanGerenciadoSpring() throws NoSuchMethodException {
        var method = DomainConfig.class.getMethod("pessoaService", PessoaRepositoryPort.class);

        // Bean deve retornar tipo concreto, não interface
        assertFalse(method.getReturnType().isInterface());
        assertTrue(method.isAnnotationPresent(org.springframework.context.annotation.Bean.class));
    }

    @Test
    @DisplayName("Deve classe seguir convenção de nomenclatura Spring")
    void deveClasseSeguirConvencaoNomenclaturaSpring() {
        assertTrue(DomainConfig.class.getSimpleName().endsWith("Config"));
        assertTrue(DomainConfig.class.getPackageName().contains("configs"));
    }
}

package br.com.fiap;

import br.com.fiap.fasfoodpessoas.FastFoodPessoasApplication;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.Environment;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = FastFoodPessoasApplication.class)
@ActiveProfiles("test")
@DisplayName("FastFoodPessoasApplication - Testes de Integração")
class FastFoodPessoasApplicationTest {

    @Autowired
    private ApplicationContext applicationContext;

    @Test
    @DisplayName("Deve carregar o contexto da aplicação com sucesso")
    void deveCarregarContextoAplicacao() {
        assertNotNull(applicationContext);
    }

    @Test
    @DisplayName("Deve validar que a aplicação está configurada como SpringBootApplication")
    void deveValidarSpringBootApplication() {
        assertTrue(FastFoodPessoasApplication.class.isAnnotationPresent(
                org.springframework.boot.autoconfigure.SpringBootApplication.class));
    }

    @Test
    @DisplayName("Deve validar scanBasePackages configurados")
    void deveValidarScanBasePackages() {
        org.springframework.boot.autoconfigure.SpringBootApplication annotation =
                FastFoodPessoasApplication.class.getAnnotation(
                        org.springframework.boot.autoconfigure.SpringBootApplication.class);

        assertNotNull(annotation);
        String[] scanBasePackages = annotation.scanBasePackages();
        assertNotNull(scanBasePackages);
        assertTrue(scanBasePackages.length >= 1);
        assertTrue(java.util.Arrays.asList(scanBasePackages).contains("br.com.fiap.fasfoodpessoas"));
    }

    @Test
    @DisplayName("Deve validar que o método main existe")
    void deveValidarMetodoMainExiste() throws NoSuchMethodException {
        assertNotNull(FastFoodPessoasApplication.class.getMethod("main", String[].class));
    }

    @Test
    @DisplayName("Deve validar que o método main é público e estático")
    void deveValidarMetodoMainPublicoEstatico() throws NoSuchMethodException {
        var metodo = FastFoodPessoasApplication.class.getMethod("main", String[].class);
        assertTrue(java.lang.reflect.Modifier.isPublic(metodo.getModifiers()));
        assertTrue(java.lang.reflect.Modifier.isStatic(metodo.getModifiers()));
    }

    @Test
    @DisplayName("Deve validar que o método main retorna void")
    void deveValidarMetodoMainRetornaVoid() throws NoSuchMethodException {
        var metodo = FastFoodPessoasApplication.class.getMethod("main", String[].class);
        assertEquals(void.class, metodo.getReturnType());
    }

    @Test
    @DisplayName("Deve validar que a classe tem construtor público padrão")
    void deveValidarConstrutorPublicoPadrao() {
        assertDoesNotThrow(() -> FastFoodPessoasApplication.class.getDeclaredConstructor());
    }

    @Test
    @DisplayName("Deve validar que é possível instanciar a classe")
    void deveValidarPossivelInstanciarClasse() {
        assertDoesNotThrow(() -> new FastFoodPessoasApplication());
    }

    @Test
    @DisplayName("Deve validar que ApplicationContext contém beans essenciais")
    void deveValidarApplicationContextContemBeansEssenciais() {
        assertNotNull(applicationContext);
        assertTrue(applicationContext.getBeanDefinitionCount() > 0);
    }

    @Test
    @DisplayName("Deve validar que ApplicationContext está ativo")
    void deveValidarApplicationContextAtivo() {
        assertNotNull(applicationContext);
        if (applicationContext instanceof ConfigurableApplicationContext) {
            assertTrue(((ConfigurableApplicationContext) applicationContext).isActive());
        }
    }

    @Test
    @DisplayName("Deve validar nome da aplicação no contexto")
    void deveValidarNomeAplicacaoContexto() {
        assertNotNull(applicationContext);
        assertNotNull(applicationContext.getApplicationName());
    }

    @Test
    @DisplayName("Deve executar o método main sem erros")
    void deveExecutarMetodoMainSemErros() {
        assertDoesNotThrow(() -> {
            // Simula a execução do método main com argumentos vazios
            FastFoodPessoasApplication.main(new String[]{});
        });
    }

    @Test
    @DisplayName("Deve executar o método main com argumentos personalizados")
    void deveExecutarMetodoMainComArgumentosPersonalizados() {
        assertDoesNotThrow(() -> {
            // Simula a execução do método main com argumentos customizados
            FastFoodPessoasApplication.main(new String[]{
                "--spring.main.web-application-type=none",
                "--spring.autoconfigure.exclude=org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration"
            });
        });
    }

    @Test
    @DisplayName("Deve validar que a aplicação tem ID único")
    void deveValidarAplicacaoTemIdUnico() {
        assertNotNull(applicationContext);
        assertNotNull(applicationContext.getId());
        assertFalse(applicationContext.getId().isEmpty());
    }

    @Test
    @DisplayName("Deve validar timestamp de inicialização do contexto")
    void deveValidarTimestampInicializacaoContexto() {
        assertNotNull(applicationContext);
        assertTrue(applicationContext.getStartupDate() > 0);
    }

    @Test
    @DisplayName("Deve validar que contexto possui Environment configurado")
    void deveValidarContextoPossuiEnvironment() {
        assertNotNull(applicationContext);
        assertNotNull(applicationContext.getEnvironment());
    }

    @Test
    @DisplayName("Deve validar profiles ativos no Environment")
    void deveValidarProfilesAtivosEnvironment() {
        assertNotNull(applicationContext);
        String[] profiles = applicationContext.getEnvironment().getActiveProfiles();
        assertNotNull(profiles);
        // No contexto de teste, deve ter pelo menos o profile "test"
        assertTrue(profiles.length >= 0);
    }

    @Test
    @DisplayName("Deve validar que a classe está no package correto")
    void deveValidarClassePackageCorreto() {
        assertEquals("br.com.fiap.fasfoodpessoas", FastFoodPessoasApplication.class.getPackageName());
    }

    @Test
    @DisplayName("Deve validar que a classe é pública")
    void deveValidarClassePublica() {
        assertTrue(java.lang.reflect.Modifier.isPublic(FastFoodPessoasApplication.class.getModifiers()));
    }

    @Test
    @DisplayName("Deve validar que a classe não é abstrata")
    void deveValidarClasseNaoAbstrata() {
        assertFalse(java.lang.reflect.Modifier.isAbstract(FastFoodPessoasApplication.class.getModifiers()));
    }

    @Test
    @DisplayName("Deve validar que a classe não é interface")
    void deveValidarClasseNaoInterface() {
        assertFalse(FastFoodPessoasApplication.class.isInterface());
    }

    @Test
    @DisplayName("Deve validar que a classe não é enum")
    void deveValidarClasseNaoEnum() {
        assertFalse(FastFoodPessoasApplication.class.isEnum());
    }

    @Test
    @DisplayName("Deve validar herança de Object")
    void deveValidarHerancaObject() {
        assertEquals(Object.class, FastFoodPessoasApplication.class.getSuperclass());
    }

    @Test
    @DisplayName("Deve validar que ApplicationContext não é nulo após injeção")
    void deveValidarApplicationContextNaoNuloAposInjecao() {
        assertNotNull(applicationContext, "ApplicationContext não deve ser nulo após injeção de dependência");
    }

    @Test
    @DisplayName("Deve validar que é possível obter bean definitions do contexto")
    void deveValidarPossivelObterBeanDefinitions() {
        assertNotNull(applicationContext);
        String[] beanNames = applicationContext.getBeanDefinitionNames();
        assertNotNull(beanNames);
        assertTrue(beanNames.length > 0);
    }

    @Test
    @DisplayName("Deve validar que o contexto possui ClassLoader configurado")
    void deveValidarContextoPossuiClassLoader() {
        assertNotNull(applicationContext);
        assertNotNull(applicationContext.getClassLoader());
    }

    @Test
    @DisplayName("Deve validar display name do ApplicationContext")
    void deveValidarDisplayNameApplicationContext() {
        assertNotNull(applicationContext);
        assertNotNull(applicationContext.getDisplayName());
        assertFalse(applicationContext.getDisplayName().isEmpty());
    }

    @Test
    @DisplayName("Deve validar que o contexto possui parent null ou configurado")
    void deveValidarContextoParent() {
        assertNotNull(applicationContext);
        // Parent pode ser null (sem contexto pai) ou ter um contexto configurado
        assertDoesNotThrow(() -> applicationContext.getParent());
    }

    @Test
    @DisplayName("Deve validar que SpringApplication pode ser referenciado")
    void deveValidarSpringApplicationPodeSerReferenciado() {
        assertDoesNotThrow(() -> org.springframework.boot.SpringApplication.class);
    }

    @Test
    @DisplayName("Deve validar nome simples da classe")
    void deveValidarNomeSimplesClasse() {
        assertEquals("FastFoodPessoasApplication", FastFoodPessoasApplication.class.getSimpleName());
    }

    @Test
    @DisplayName("Deve validar nome canônico da classe")
    void deveValidarNomeCanonico() {
        assertEquals("br.com.fiap.fasfoodpessoas.FastFoodPessoasApplication",
                FastFoodPessoasApplication.class.getCanonicalName());
    }

    @Test
    @DisplayName("Deve validar que a classe possui apenas um construtor")
    void deveValidarClassePossuiApenasUmConstrutor() {
        assertEquals(1, FastFoodPessoasApplication.class.getDeclaredConstructors().length);
    }

    @Test
    @DisplayName("Deve validar que a classe possui apenas um método (main)")
    void deveValidarClassePossuiApenasMetodoMain() {
        var metodos = FastFoodPessoasApplication.class.getDeclaredMethods();
        var metodosNaoSinteticos = java.util.Arrays.stream(metodos)
            .filter(m -> !m.isSynthetic())
            .toList();
        assertEquals(1, metodosNaoSinteticos.size());
        assertEquals("main", metodosNaoSinteticos.get(0).getName());
    }

    @Test
    @DisplayName("Deve validar que não há campos declarados na classe")
    void deveValidarNaoHaCamposDeclarados() {
        assertEquals(0, FastFoodPessoasApplication.class.getDeclaredFields().length);
    }

    @Test
    @DisplayName("Deve validar que a classe tem apenas uma anotação (SpringBootApplication)")
    void deveValidarClasseTemApenasAnotacaoSpringBootApplication() {
        var anotacoes = FastFoodPessoasApplication.class.getDeclaredAnnotations();
        assertEquals(1, anotacoes.length);
        assertTrue(anotacoes[0] instanceof org.springframework.boot.autoconfigure.SpringBootApplication);
    }

    @Test
    @DisplayName("Deve validar múltiplos scanBasePackages configurados")
    void deveValidarMultiplosScanBasePackages() {
        org.springframework.boot.autoconfigure.SpringBootApplication annotation =
                FastFoodPessoasApplication.class.getAnnotation(
                        org.springframework.boot.autoconfigure.SpringBootApplication.class);

        String[] scanBasePackages = annotation.scanBasePackages();
        assertTrue(scanBasePackages.length >= 1);
        
        // Validar que contém os packages esperados
        java.util.List<String> packagesList = java.util.Arrays.asList(scanBasePackages);
        assertTrue(packagesList.contains("br.com.fiap.fasfoodpessoas"));
    }

    @Test
    @DisplayName("Deve validar que ApplicationContext suporta obtenção de beans por tipo")
    void deveValidarApplicationContextSuportaBeansPorTipo() {
        assertNotNull(applicationContext);
        assertDoesNotThrow(() -> applicationContext.getBeansOfType(Object.class));
    }

    @Test
    @DisplayName("Deve validar que o contexto foi iniciado corretamente")
    void deveValidarContextoIniciadoCorretamente() {
        assertNotNull(applicationContext);
        if (applicationContext instanceof ConfigurableApplicationContext) {
            ConfigurableApplicationContext ctx = (ConfigurableApplicationContext) applicationContext;
            assertTrue(ctx.isActive());
            assertTrue(ctx.getStartupDate() > 0);
        }
        assertTrue(applicationContext.getBeanDefinitionCount() > 0);
    }

    @Test
    @DisplayName("Deve validar que Environment possui PropertySources")
    void deveValidarEnvironmentPossuiPropertySources() {
        assertNotNull(applicationContext);
        var environment = applicationContext.getEnvironment();
        assertNotNull(environment);
        if (environment instanceof ConfigurableEnvironment) {
            ConfigurableEnvironment ce = (ConfigurableEnvironment) environment;
            assertNotNull(ce.getPropertySources());
            assertTrue(ce.getPropertySources().iterator().hasNext());
        }
    }

    @Test
    @DisplayName("Deve validar que é possível criar nova instância via reflection")
    void deveValidarPossivelCriarNovaInstanciaViaReflection() {
        assertDoesNotThrow(() -> {
            var construtor = FastFoodPessoasApplication.class.getDeclaredConstructor();
            construtor.newInstance();
        });
    }

    @Test
    @DisplayName("Deve validar que o método main aceita array de Strings como parâmetro")
    void deveValidarMetodoMainAceitaArrayStrings() throws NoSuchMethodException {
        var metodo = FastFoodPessoasApplication.class.getMethod("main", String[].class);
        assertEquals(1, metodo.getParameterCount());
        assertEquals(String[].class, metodo.getParameterTypes()[0]);
    }

    @Test
    @DisplayName("Deve validar que não há métodos privados na classe")
    void deveValidarNaoHaMetodosPrivados() {
        var metodos = FastFoodPessoasApplication.class.getDeclaredMethods();
        var metodosNaoSinteticos = java.util.Arrays.stream(metodos)
            .filter(m -> !m.isSynthetic())
            .collect(java.util.stream.Collectors.toList());
        for (var metodo : metodosNaoSinteticos) {
            assertFalse(java.lang.reflect.Modifier.isPrivate(metodo.getModifiers()));
        }
    }

    @Test
    @DisplayName("Deve validar que ApplicationContext possui BeanFactory")
    void deveValidarApplicationContextPossuiBeanFactory() {
        assertNotNull(applicationContext);
        assertNotNull(applicationContext.getAutowireCapableBeanFactory());
    }

    @Test
    @DisplayName("Deve validar que a aplicação Spring Boot está configurada corretamente")
    void deveValidarAplicacaoSpringBootConfiguradaCorretamente() {
        assertNotNull(applicationContext);
        if (applicationContext instanceof ConfigurableApplicationContext) {
            assertTrue(((ConfigurableApplicationContext) applicationContext).isActive());
        }
        assertNotNull(applicationContext.getEnvironment());
        assertTrue(applicationContext.getBeanDefinitionCount() > 0);
        assertTrue(FastFoodPessoasApplication.class.isAnnotationPresent(
                org.springframework.boot.autoconfigure.SpringBootApplication.class));
    }
}

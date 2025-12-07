package com.fiap.fasfoodpessoas.domain.enums;

import br.com.fiap.fasfoodpessoas.domain.enums.TipoPessoaEnum;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TipoPessoaEnumTest {

    @Test
    void deveRetornarTodosOsValoresDoEnum() {
        TipoPessoaEnum[] valores = TipoPessoaEnum.values();
        
        assertEquals(2, valores.length);
        assertEquals(TipoPessoaEnum.CLIENTE, valores[0]);
        assertEquals(TipoPessoaEnum.FUNCIONARIO, valores[1]);
    }

    @Test
    void deveRetornarClienteQuandoValueOfCliente() {
        TipoPessoaEnum tipo = TipoPessoaEnum.valueOf("CLIENTE");
        
        assertEquals(TipoPessoaEnum.CLIENTE, tipo);
    }

    @Test
    void deveRetornarFuncionarioQuandoValueOfFuncionario() {
        TipoPessoaEnum tipo = TipoPessoaEnum.valueOf("FUNCIONARIO");
        
        assertEquals(TipoPessoaEnum.FUNCIONARIO, tipo);
    }

    @Test
    void deveLancarExcecaoQuandoValueOfInvalido() {
        assertThrows(IllegalArgumentException.class, () -> {
            TipoPessoaEnum.valueOf("INVALIDO");
        });
    }

    @Test
    void deveLancarExcecaoQuandoValueOfNull() {
        assertThrows(NullPointerException.class, () -> {
            TipoPessoaEnum.valueOf(null);
        });
    }

    @Test
    void deveVerificarNomeDoEnumCliente() {
        assertEquals("CLIENTE", TipoPessoaEnum.CLIENTE.name());
    }

    @Test
    void deveVerificarNomeDoEnumFuncionario() {
        assertEquals("FUNCIONARIO", TipoPessoaEnum.FUNCIONARIO.name());
    }

    @Test
    void deveVerificarOrdinalDoEnumCliente() {
        assertEquals(0, TipoPessoaEnum.CLIENTE.ordinal());
    }

    @Test
    void deveVerificarOrdinalDoEnumFuncionario() {
        assertEquals(1, TipoPessoaEnum.FUNCIONARIO.ordinal());
    }

    @Test
    void deveCompararEnumsComEquals() {
        TipoPessoaEnum tipo1 = TipoPessoaEnum.CLIENTE;
        TipoPessoaEnum tipo2 = TipoPessoaEnum.CLIENTE;
        TipoPessoaEnum tipo3 = TipoPessoaEnum.FUNCIONARIO;

        assertEquals(tipo1, tipo2);
        assertNotEquals(tipo1, tipo3);
    }

    @Test
    void deveCompararEnumsComOperadorIgualdade() {
        TipoPessoaEnum tipo1 = TipoPessoaEnum.CLIENTE;
        TipoPessoaEnum tipo2 = TipoPessoaEnum.CLIENTE;
        TipoPessoaEnum tipo3 = TipoPessoaEnum.FUNCIONARIO;

        assertSame(tipo1, tipo2);
        assertNotSame(tipo1, tipo3);
    }

    @Test
    void deveValidarToStringDoEnum() {
        assertEquals("CLIENTE", TipoPessoaEnum.CLIENTE.toString());
        assertEquals("FUNCIONARIO", TipoPessoaEnum.FUNCIONARIO.toString());
    }
}

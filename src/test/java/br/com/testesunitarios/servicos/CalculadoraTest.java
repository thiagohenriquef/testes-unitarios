package br.com.testesunitarios.servicos;

import br.com.testesunitarios.exceptions.NaoPodeDividirPorZeroException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CalculadoraTest {

    private Calculadora calculadora;

    @Before
    public void setUp() {
        calculadora = new Calculadora();
    }

    @Test
    public void deveRealizarSoma() {
        int resultado = calculadora.soma(2, 2);

        assertEquals(4, resultado);
    }

    @Test
    public void deveRealizarSubtracao() {
        int resultado = calculadora.subtracao(5, 3);

        assertEquals(2, resultado);
    }

    @Test
    public void deveDividirDoisValores() throws NaoPodeDividirPorZeroException {
        // cenário
        int i = 6;
        int j = 2;
        // ação
        int resultado = calculadora.divisao(i, j);
        // verificação
        assertEquals(resultado, 3);
    }

    @Test(expected = NaoPodeDividirPorZeroException.class)
    public void deveLancarExcecaoDivisaoPorZero() throws NaoPodeDividirPorZeroException {
        // cenário
        int i = 6;
        int j = 0;
        // ação
        int resultado = calculadora.divisao(i, j);
        // verificação
        assertEquals(resultado, 3);
    }
}

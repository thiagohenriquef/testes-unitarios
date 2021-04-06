package br.com.testesunitarios.servicos;

import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

public class CalculadoraMockTest {
    @Test
    public void teste() {
        Calculadora calculadora = Mockito.mock(Calculadora.class);
        when(calculadora.soma(1, 2)).thenReturn(3);

        assertEquals(3, calculadora.soma(1, 2));
    }

}

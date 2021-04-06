package br.com.testesunitarios.servicos;

import br.com.testesunitarios.entidades.Locacao;
import org.junit.Before;
import org.junit.Test;
import org.mockito.*;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

public class CalculadoraMockTest {
    @Mock
    private Calculadora calcMock;

    @Spy
    private Calculadora calcSpy;

    @Before
    public void setup() {
        initMocks(this);
    }

    @Test
    public void diferencaCalcMockESpy() {
        when(calcMock.soma(1, 5)).thenCallRealMethod();
        when(calcSpy.soma(1, 2)).thenReturn(3);
        doReturn(5).when(calcSpy).soma(2, 3);
        System.out.println("Mock: " + calcMock.soma(1, 5));
        System.out.println("Spy: " + calcSpy.soma(1, 5));

        doNothing().when(calcSpy).imprime();

        calcMock.imprime();
        calcSpy.imprime();
    }


    @Test
    public void teste() {
        Calculadora calculadora = Mockito.mock(Calculadora.class);

        ArgumentCaptor<Integer> argumentCaptor = ArgumentCaptor.forClass(Integer.class);
        Mockito.when(calculadora.soma(argumentCaptor.capture(), argumentCaptor.capture() )).thenReturn(3);

        assertEquals(3, calculadora.soma(1, 2));
        System.out.println(argumentCaptor.getAllValues());
    }

}

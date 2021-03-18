package br.com.testesunitarios.servicos;

import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import static org.junit.Assert.assertEquals;
import static org.junit.runners.MethodSorters.*;

@FixMethodOrder(NAME_ASCENDING)
public class OrdemTest {

    public static int counter = 0;

    @Test
    public void test1_inicia() {
        counter = 1;
    }

    @Test
    public void test2_verifica() {
        assertEquals(1, counter);
    }
}

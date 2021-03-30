package br.com.testesunitarios.suite;

import br.com.testesunitarios.servicos.CalculadoraTest;
import br.com.testesunitarios.servicos.CalculoLocacaoServiceTest;
import br.com.testesunitarios.servicos.LocacaoService;
import br.com.testesunitarios.servicos.LocacaoServiceTest;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({
        CalculadoraTest.class,
        CalculoLocacaoServiceTest.class,
        LocacaoServiceTest.class
})
public class SuiteExecucao {

    @BeforeClass
    public static void before() {
        System.out.println("Before class");
    }

    @AfterClass
    public static void after() {
        System.out.println("After class");
    }
}

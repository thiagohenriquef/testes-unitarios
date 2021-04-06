package br.com.testesunitarios.servicos;

import br.com.testesunitarios.dao.LocacaoDAO;
import br.com.testesunitarios.dao.LocacaoDAOFake;
import br.com.testesunitarios.entidades.Filme;
import br.com.testesunitarios.entidades.Locacao;
import br.com.testesunitarios.entidades.User;
import br.com.testesunitarios.exceptions.FilmeSemEstoqueException;
import br.com.testesunitarios.exceptions.LocadoraException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static br.com.testesunitarios.builders.FilmeBuilder.umFilme;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

@RunWith(Parameterized.class)
public class CalculoLocacaoServiceTest {
    @InjectMocks
    private LocacaoService service;
    @Mock
    private LocacaoDAO locacaoDAO;
    @Mock
    private SPCService spcService;

    @Parameter
    public List<Filme> filmes;

    @Parameter(value=1)
    public Double valorLocacao;

    @Parameter(value=2)
    public String cenario;

    @Before
    public void setup(){
        MockitoAnnotations.initMocks(this);
    }

    private static Filme filme1 = umFilme().comValoEspecifico(4.0).agora();
    private static Filme filme2 = umFilme().comValoEspecifico(4.0).agora();
    private static Filme filme3 = umFilme().comValoEspecifico(4.0).agora();
    private static Filme filme4 = umFilme().comValoEspecifico(4.0).agora();
    private static Filme filme5 = umFilme().comValoEspecifico(4.0).agora();
    private static Filme filme6 = umFilme().comValoEspecifico(4.0).agora();
    private static Filme filme7 = umFilme().comValoEspecifico(4.0).agora();

    @Parameters(name="{2}")
    public static Collection<Object[]> getParametros(){
        return Arrays.asList(new Object[][] {
                {Arrays.asList(filme1, filme2), 8.0, "2 Filmes: Sem Desconto"},
                {Arrays.asList(filme1, filme2, filme3), 11.0, "3 Filmes: 25%"},
                {Arrays.asList(filme1, filme2, filme3, filme4), 13.0, "4 Filmes: 50%"},
                {Arrays.asList(filme1, filme2, filme3, filme4, filme5), 14.0, "5 Filmes: 75%"},
                {Arrays.asList(filme1, filme2, filme3, filme4, filme5, filme6), 14.0, "6 Filmes: 100%"},
                {Arrays.asList(filme1, filme2, filme3, filme4, filme5, filme6, filme7), 18.0, "7 Filmes: Sem Desconto"}
        });
    }

    @Test
    public void deveCalcularValorLocacaoConsiderandoDescontos() throws FilmeSemEstoqueException, LocadoraException{
        //cenario
        User usuario = new User("Usuario 1");
//        when(spcService.possuiNegativacao(usuario)).thenReturn(false);

        //acao
        Locacao resultado = service.alugarFilme(usuario, filmes);

        //verificacao
        assertThat(resultado.getValor(), is(valorLocacao));
    }

}

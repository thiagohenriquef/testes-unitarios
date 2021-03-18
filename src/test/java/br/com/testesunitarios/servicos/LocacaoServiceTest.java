package br.com.testesunitarios.servicos;

import br.com.testesunitarios.entidades.Filme;
import br.com.testesunitarios.entidades.Locacao;
import br.com.testesunitarios.entidades.User;
import br.com.testesunitarios.exceptions.FilmeSemEstoqueException;
import br.com.testesunitarios.exceptions.LocadoraException;
import org.junit.*;
import org.junit.rules.ErrorCollector;
import org.junit.rules.ExpectedException;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static br.com.testesunitarios.exceptions.Mensagens.FILME_NAO_ENCONTRADO;
import static br.com.testesunitarios.exceptions.Mensagens.USUARIO_NAO_ENCONTRADO;
import static br.com.testesunitarios.utils.DataUtils.isMesmaData;
import static br.com.testesunitarios.utils.DataUtils.obterDataComDiferencaDias;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

public class LocacaoServiceTest {
    private LocacaoService service;
    private List<Filme> filmes;

    @Rule
    public ErrorCollector error = new ErrorCollector();

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Before
    public void before() {
        service = new LocacaoService();
        filmes = new ArrayList<>();
    }

    @BeforeClass
    public static void beforeClass() {
        System.out.println("BEFORE CLASS");
    }

    @AfterClass
    public static void afterClass() {
        System.out.println("AFTER CLASS");
    }

    @Test
    public void teste() throws Exception {
        //cenario
        User user = new User("Usuario 1");
        filmes.add(new Filme("Filme 1", 2, 5.0));
        //acao
        Locacao locacao = service.alugarFilme(user, filmes);

        //verificacao
        assertThat(locacao.getValor(), is(equalTo(5.0)));
        assertThat(locacao.getValor(), is(not(6.0)));
        assertThat(isMesmaData(locacao.getDataLocacao(), new Date()), is(true));
        assertThat(isMesmaData(locacao.getDataRetorno(), obterDataComDiferencaDias(1)), is(true));

        error.checkThat(locacao.getValor(), is(equalTo(5.0)));
        error.checkThat(locacao.getValor(), is(not(6.0)));
        error.checkThat(isMesmaData(locacao.getDataLocacao(), new Date()), is(true));
        error.checkThat(isMesmaData(locacao.getDataRetorno(), obterDataComDiferencaDias(1)), is(true));
    }

    @Test(expected = FilmeSemEstoqueException.class)
    public void testLocacaoFilmeSemEstoqueUm() throws Exception {
        //cenario
        User user = new User("Usuario 1");
        filmes.add(new Filme("Filme 1", 0, 5.0));

        Locacao locacao = service.alugarFilme(user, filmes);
    }

    @Test
    public void testLocacaoFilmeSemEstoqueDois() throws FilmeSemEstoqueException, LocadoraException {
        //cenario
        User user = new User("Usuario 1");
        filmes.add(new Filme("Filme 1", 0, 5.0));

        expectedException.expect(Exception.class);
        Locacao locacao = service.alugarFilme(user, filmes);
    }

    @Test
    public void testLocacaoSemUsuario() throws FilmeSemEstoqueException {
        System.out.println("Forma Robusta");
        //cenario
        User user = null;
        filmes.add(new Filme("Filme 1", 1, 5.0));

        try {
            Locacao locacao = service.alugarFilme(user, filmes);
            fail();
        } catch (LocadoraException e) {
            assertThat(e.getMessage(), is(USUARIO_NAO_ENCONTRADO.name()));
        }

    }

    @Test
    public void testLocacaoSemFilme() throws FilmeSemEstoqueException, LocadoraException {
        System.out.println("Forma nova");
        //cenario
        User user = new User("Usuario 1");
        filmes.add(null);

        expectedException.expect(LocadoraException.class);
        expectedException.expectMessage(FILME_NAO_ENCONTRADO.name());

        Locacao locacao = service.alugarFilme(user, filmes);
    }

}
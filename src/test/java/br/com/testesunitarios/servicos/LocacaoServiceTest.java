package br.com.testesunitarios.servicos;

import br.com.testesunitarios.entidades.Filme;
import br.com.testesunitarios.entidades.Locacao;
import br.com.testesunitarios.entidades.User;
import br.com.testesunitarios.exceptions.FilmeSemEstoqueException;
import br.com.testesunitarios.exceptions.LocadoraException;
import br.com.testesunitarios.matchers.DiaDaSemanaMatcher;
import br.com.testesunitarios.matchers.MatchersProprios;
import br.com.testesunitarios.utils.DataUtils;
import org.junit.*;
import org.junit.rules.ErrorCollector;
import org.junit.rules.ExpectedException;

import java.util.*;

import static br.com.testesunitarios.exceptions.Mensagens.FILME_NAO_ENCONTRADO;
import static br.com.testesunitarios.exceptions.Mensagens.USUARIO_NAO_ENCONTRADO;
import static br.com.testesunitarios.matchers.MatchersProprios.*;
import static br.com.testesunitarios.utils.DataUtils.isMesmaData;
import static br.com.testesunitarios.utils.DataUtils.obterDataComDiferencaDias;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

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
    public void deveAlugarFilme() throws Exception {
        Assume.assumeFalse(DataUtils.verificarDiaSemana(new Date(), Calendar.SATURDAY));

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
        error.checkThat(locacao.getDataLocacao(), ehHoje());
        error.checkThat(locacao.getDataLocacao(), ehHojeComDiferencaDeDias(1));
    }

    @Test(expected = FilmeSemEstoqueException.class)
    public void deveLancarExcecaoAoAlugarFilmeSemEstoque() throws Exception {
        //cenario
        User user = new User("Usuario 1");
        filmes.add(new Filme("Filme 1", 0, 5.0));

        Locacao locacao = service.alugarFilme(user, filmes);
    }

    @Test
    public void naoDeveAlugarFilmeSemEstoque() throws FilmeSemEstoqueException, LocadoraException {
        //cenario
        User user = new User("Usuario 1");
        filmes.add(new Filme("Filme 1", 0, 5.0));

        expectedException.expect(Exception.class);
        Locacao locacao = service.alugarFilme(user, filmes);
    }

    @Test
    public void naoDeveAlugarFilmeSemUsuario() throws FilmeSemEstoqueException {
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
    public void naoDeveAlugarFilmeSemFilme() throws FilmeSemEstoqueException, LocadoraException {
        System.out.println("Forma nova");
        //cenario
        User user = new User("Usuario 1");
        filmes.add(null);

        expectedException.expect(LocadoraException.class);
        expectedException.expectMessage(FILME_NAO_ENCONTRADO.name());

        Locacao locacao = service.alugarFilme(user, filmes);
    }

    @Test
    public void deveDevolverFilmeNaSegundaAoAlugarNoSabado() throws FilmeSemEstoqueException, LocadoraException {
        Assume.assumeTrue(DataUtils.verificarDiaSemana(new Date(), Calendar.SATURDAY));

        //cenario
        User usuario = new User("Usuario 1");
        List<Filme> filmes = Arrays.asList(new Filme("Filme 1", 1, 5.0));

        //acao
        Locacao retorno = service.alugarFilme(usuario, filmes);

        //verificacao
        boolean ehSegunda = DataUtils.verificarDiaSemana(retorno.getDataRetorno(), Calendar.MONDAY);
        assertTrue(ehSegunda);
        assertThat(retorno.getDataRetorno(), new DiaDaSemanaMatcher(Calendar.MONDAY));
        assertThat(retorno.getDataRetorno(), caiEm(Calendar.MONDAY));
        assertThat(retorno.getDataRetorno(), caiEmUmaSegunda());
    }

}
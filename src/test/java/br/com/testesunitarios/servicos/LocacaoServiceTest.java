package br.com.testesunitarios.servicos;

import br.com.testesunitarios.entidades.Filme;
import br.com.testesunitarios.entidades.Locacao;
import br.com.testesunitarios.entidades.User;
import br.com.testesunitarios.exceptions.FilmeSemEstoqueException;
import br.com.testesunitarios.exceptions.LocadoraException;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import org.junit.rules.ExpectedException;

import java.util.Date;

import static br.com.testesunitarios.exceptions.Mensagens.FILME_NAO_ENCONTRADO;
import static br.com.testesunitarios.exceptions.Mensagens.USUARIO_NAO_ENCONTRADO;
import static br.com.testesunitarios.utils.DataUtils.isMesmaData;
import static br.com.testesunitarios.utils.DataUtils.obterDataComDiferencaDias;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

public class LocacaoServiceTest {

    @Rule
    public ErrorCollector error = new ErrorCollector();

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void teste() throws Exception {
        //cenario
        LocacaoService service = new LocacaoService();
        User user = new User("Usuario 1");
        Filme filme = new Filme("Filme 1", 2, 5.0);

        //acao
        Locacao locacao = service.alugarFilme(user, filme);

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
        LocacaoService service = new LocacaoService();
        User user = new User("Usuario 1");
        Filme filme = new Filme("Filme 1", 0, 5.0);

        Locacao locacao = service.alugarFilme(user, filme);
    }

    @Test
    public void testLocacaoFilmeSemEstoqueDois() throws FilmeSemEstoqueException, LocadoraException {
        //cenario
        LocacaoService service = new LocacaoService();
        User user = new User("Usuario 1");
        Filme filme = new Filme("Filme 1", 0, 5.0);

        expectedException.expect(Exception.class);
        Locacao locacao = service.alugarFilme(user, filme);
    }

    @Test
    public void testLocacaoSemUsuario() throws FilmeSemEstoqueException {
        //cenario
         LocacaoService service = new LocacaoService();
        User user = null;
        Filme filme = new Filme("Filme 1", 1, 5.0);

        try {
            Locacao locacao = service.alugarFilme(user, filme);
            fail();
        } catch (LocadoraException e) {
            assertThat(e.getMessage(), is(USUARIO_NAO_ENCONTRADO.name()));
        }

        System.out.println("Forma Robusta");
    }

    @Test
    public void testLocacaoSemFilme() throws FilmeSemEstoqueException, LocadoraException {
        //cenario
        LocacaoService service = new LocacaoService();
        User user = new User("Usuario 1");
        Filme filme = null;

        expectedException.expect(LocadoraException.class);
        expectedException.expectMessage(FILME_NAO_ENCONTRADO.name());

        Locacao locacao = service.alugarFilme(user, filme);

        System.out.println("Forma nova");
    }

}
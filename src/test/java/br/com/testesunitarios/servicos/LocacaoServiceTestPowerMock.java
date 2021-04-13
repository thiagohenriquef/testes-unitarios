package br.com.testesunitarios.servicos;

import br.com.testesunitarios.dao.LocacaoDAO;
import br.com.testesunitarios.entidades.Filme;
import br.com.testesunitarios.entidades.Locacao;
import br.com.testesunitarios.entidades.User;
import br.com.testesunitarios.exceptions.FilmeSemEstoqueException;
import br.com.testesunitarios.exceptions.LocadoraException;
import br.com.testesunitarios.utils.DataUtils;
import org.junit.*;
import org.junit.rules.ErrorCollector;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import java.util.*;

import static br.com.testesunitarios.builders.FilmeBuilder.umFilme;
import static br.com.testesunitarios.builders.FilmeBuilder.umFilmeSemEstoque;
import static br.com.testesunitarios.builders.LocacaoBuilder.umaLocacao;
import static br.com.testesunitarios.builders.UsuarioBuilder.umUsuario;
import static br.com.testesunitarios.exceptions.Mensagens.*;
import static br.com.testesunitarios.matchers.MatchersProprios.*;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ LocacaoService.class})
@PowerMockIgnore("jdk.internal.reflect.*")
public class LocacaoServiceTestPowerMock {
    @InjectMocks
    private LocacaoService service;

    @Mock
    private LocacaoDAO dao;
    @Mock
    private SPCService spc;
    @Mock
    private EmailService email;

    private List<Filme> filmes;

    @Rule
    public ErrorCollector error = new ErrorCollector();

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Before
    public void before() {
        initMocks(this);
        filmes = new ArrayList<>();
        service = PowerMockito.spy(service);
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
        //cenario
        User usuario = umUsuario().agora();
        List<Filme> filmes = Collections.singletonList(umFilme().comValoEspecifico(5.0).agora());

        PowerMockito.whenNew(Date.class).withNoArguments().thenReturn(DataUtils.obterData(28, 4, 2017));

        //acao
        Locacao locacao = service.alugarFilme(usuario, filmes);

        //verificacao
        error.checkThat(locacao.getValor(), is(equalTo(5.0)));
//        error.checkThat(locacao.getDataLocacao(), ehHoje());
//        error.checkThat(locacao.getDataRetorno(), ehHojeComDiferencaDeDias(1));
        error.checkThat(DataUtils.isMesmaData(locacao.getDataLocacao(), DataUtils.obterData(28, 4, 2017)), is(true));
        error.checkThat(DataUtils.isMesmaData(locacao.getDataRetorno(), DataUtils.obterData(29, 4, 2017)), is(true));

        PowerMockito.verifyStatic(Mockito.times(2));
        Calendar.getInstance();
    }

    @Test
    public void deveDevolverFilmeNaSegundaAoAlugarNoSabado() throws Exception {
//        Assume.assumeTrue(DataUtils.verificarDiaSemana(new Date(), Calendar.SATURDAY));

        //cenario
        User user = umUsuario().agora();
        List<Filme> filmes = Collections.singletonList(umFilme().agora());

        PowerMockito.whenNew(Date.class).withNoArguments().thenReturn(DataUtils.obterData(10, 4, 2021));


        //acao
        Locacao retorno = service.alugarFilme(user, filmes);

        //verificacao
        boolean ehSegunda = DataUtils.verificarDiaSemana(retorno.getDataRetorno(), Calendar.MONDAY);
        assertTrue(ehSegunda);
//        assertThat(retorno.getDataRetorno(), new DiaDaSemanaMatcher(Calendar.MONDAY));
//        assertThat(retorno.getDataRetorno(), caiEm(Calendar.MONDAY));
        assertThat(retorno.getDataRetorno(), caiEmUmaSegunda());

//        PowerMockito.verifyNew(Date.class, times(2)).withNoArguments();
        PowerMockito.verifyStatic(Mockito.times(2));
        Calendar.getInstance();
    }

    @Test
    public void deveAlugarFilmeSemCalcularValor() throws Exception {
//        cenário
        User usuario = umUsuario().agora();
        List<Filme> filmes = Collections.singletonList(umFilme().agora());

        PowerMockito.doReturn(1.0).when(service, "aplicaDescontosNoValor", filmes);
//        ação
        Locacao locacao = service.alugarFilme(usuario, filmes);

//        verificação

        Assert.assertThat(locacao.getValor(), is(1.0));
        PowerMockito.verifyPrivate(service).invoke("aplicaDescontosNoValor", filmes);
    }

    @Test
    public void deveCalcularValorLocacao() throws Exception {
//        cenário
        User usuario = umUsuario().agora();
        List<Filme> filmes = Collections.singletonList(umFilme().agora());
        PowerMockito.doReturn(1.0).when(service, "aplicaDescontosNoValor", filmes);

//        ação
        Locacao locacao = service.alugarFilme(usuario, filmes);
        Double value = Whitebox.invokeMethod(service, "aplicaDescontosNoValor", filmes);

//        verificação
        Assert.assertThat(value, is(1.0));
    }
}
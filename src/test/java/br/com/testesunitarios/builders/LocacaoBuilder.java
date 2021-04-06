package br.com.testesunitarios.builders;

import br.com.testesunitarios.entidades.Filme;
import br.com.testesunitarios.entidades.Locacao;
import br.com.testesunitarios.entidades.User;
import br.com.testesunitarios.utils.DataUtils;

import java.util.Arrays;
import java.util.Collections;
import java.util.Date;

import static br.com.testesunitarios.builders.FilmeBuilder.umFilme;
import static br.com.testesunitarios.builders.UsuarioBuilder.umUsuario;
import static br.com.testesunitarios.utils.DataUtils.obterDataComDiferencaDias;

public class LocacaoBuilder {
    private Locacao elemento;

    private LocacaoBuilder() {
    }

    public static LocacaoBuilder umaLocacao() {
        LocacaoBuilder builder = new LocacaoBuilder();
        inicializarDadosPadroes(builder);
        return builder;
    }

    public static void inicializarDadosPadroes(LocacaoBuilder builder) {
        builder.elemento = new Locacao();
        Locacao elemento = builder.elemento;


        elemento.setUsuario(umUsuario().agora());
        elemento.setFilme(Collections.singletonList(umFilme().agora()));
        elemento.setDataLocacao(new Date());
        elemento.setDataRetorno(obterDataComDiferencaDias(1));
        elemento.setValor(4.0);
    }

    public LocacaoBuilder comUsuario(User param) {
        elemento.setUsuario(param);
        return this;
    }

    public LocacaoBuilder comListaFilmes(Filme... params) {
        elemento.setFilme(Arrays.asList(params));
        return this;
    }

    public LocacaoBuilder comDataLocacao(Date param) {
        elemento.setDataLocacao(param);
        return this;
    }

    public LocacaoBuilder comDataRetorno(Date param) {
        elemento.setDataRetorno(param);
        return this;
    }

    public LocacaoBuilder atrasado(){
        elemento.setDataLocacao(obterDataComDiferencaDias(-4));
        elemento.setDataRetorno(obterDataComDiferencaDias(-2));
        return this;
    }
    public LocacaoBuilder comValor(Double param) {
        elemento.setValor(param);
        return this;
    }

    public Locacao agora() {
        return elemento;
    }
}
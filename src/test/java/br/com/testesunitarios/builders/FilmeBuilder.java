package br.com.testesunitarios.builders;

import br.com.testesunitarios.entidades.Filme;

public class FilmeBuilder {

    private Filme filme;

    public FilmeBuilder() {
    }

    public static FilmeBuilder umFilme() {
        FilmeBuilder builder = new FilmeBuilder();
        builder.filme = new Filme();
        builder.filme.setEstoque(2);
        builder.filme.setNome("Filme");
        builder.filme.setPrecoLocacao(5.0);
        return  builder;
    }

    public static FilmeBuilder umFilmeSemEstoque() {
        FilmeBuilder builder = new FilmeBuilder();
        builder.filme = new Filme();
        builder.filme.setEstoque(0);
        builder.filme.setNome("Filme");
        builder.filme.setPrecoLocacao(5.0);
        return  builder;
    }

    public FilmeBuilder semEstoque() {
        filme.setEstoque(0);
        return this;
    }

    public FilmeBuilder comValoEspecifico(double preco) {
        filme.setPrecoLocacao(preco);
        return this;
    }

    public Filme agora() {
        return filme;
    }
}

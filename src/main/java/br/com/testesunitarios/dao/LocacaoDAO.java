package br.com.testesunitarios.dao;

import br.com.testesunitarios.entidades.Locacao;

import java.util.List;

public interface LocacaoDAO {
    void salvar(Locacao locacao);

    List<Locacao> obterLocacoesPendentes();
}

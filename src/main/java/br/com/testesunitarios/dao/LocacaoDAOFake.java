package br.com.testesunitarios.dao;

import br.com.testesunitarios.entidades.Locacao;

import java.util.List;

public class LocacaoDAOFake implements LocacaoDAO {

    @Override
    public void salvar(Locacao locacao) {

    }

    @Override
    public List<Locacao> obterLocacoesPendentes() {
        return null;
    }
}

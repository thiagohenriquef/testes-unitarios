package br.com.testesunitarios.servicos;

import br.com.testesunitarios.entidades.Filme;
import br.com.testesunitarios.entidades.Locacao;
import br.com.testesunitarios.entidades.User;
import br.com.testesunitarios.exceptions.FilmeSemEstoqueException;
import br.com.testesunitarios.exceptions.LocadoraException;
import br.com.testesunitarios.exceptions.Mensagens;

import java.util.Date;

import static br.com.testesunitarios.utils.DataUtils.adicionarDias;
import static java.util.Objects.isNull;

public class LocacaoService {

    public Locacao alugarFilme(User user, Filme filme) throws FilmeSemEstoqueException, LocadoraException {
        if (isNull(user)) {
            throw new LocadoraException(Mensagens.USUARIO_NAO_ENCONTRADO.name());
        }
        if (isNull(filme)) {
            throw new LocadoraException(Mensagens.FILME_NAO_ENCONTRADO.name());
        }
        if (filme.getEstoque() == 0) {
            throw new FilmeSemEstoqueException();
        }
        Locacao locacao = new Locacao();
        locacao.setFilme(filme);
        locacao.setUsuario(user);
        locacao.setDataLocacao(new Date());
        locacao.setValor(filme.getPrecoLocacao());

        //Entrega no dia seguinte
        Date dataEntrega = new Date();
        dataEntrega = adicionarDias(dataEntrega, 1);
        locacao.setDataRetorno(dataEntrega);

        //Salvando a locacao...
        //TODO adicionar método para salvar

        return locacao;
    }
}
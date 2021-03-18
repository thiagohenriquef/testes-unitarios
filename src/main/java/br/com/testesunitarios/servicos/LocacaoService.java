package br.com.testesunitarios.servicos;

import br.com.testesunitarios.entidades.Filme;
import br.com.testesunitarios.entidades.Locacao;
import br.com.testesunitarios.entidades.User;
import br.com.testesunitarios.exceptions.FilmeSemEstoqueException;
import br.com.testesunitarios.exceptions.LocadoraException;
import br.com.testesunitarios.exceptions.Mensagens;

import java.util.Date;
import java.util.List;
import java.util.Objects;

import static br.com.testesunitarios.utils.DataUtils.adicionarDias;
import static java.util.Objects.isNull;

public class LocacaoService {

    public Locacao alugarFilme(User user, List<Filme> filmes) throws FilmeSemEstoqueException, LocadoraException {
        if (isNull(user)) {
            throw new LocadoraException(Mensagens.USUARIO_NAO_ENCONTRADO.name());
        }
        if (filmes.stream().anyMatch(Objects::isNull)) {
            throw new LocadoraException(Mensagens.FILME_NAO_ENCONTRADO.name());
        }
        if (filmes.stream().anyMatch(x -> x.getEstoque() == 0)) {
            throw new FilmeSemEstoqueException();
        }
        Locacao locacao = new Locacao();
        locacao.setFilme(filmes);
        locacao.setUsuario(user);
        locacao.setDataLocacao(new Date());
        locacao.setValor(filmes.stream().mapToDouble(Filme::getPrecoLocacao).sum());

        //Entrega no dia seguinte
        Date dataEntrega = new Date();
        dataEntrega = adicionarDias(dataEntrega, 1);
        locacao.setDataRetorno(dataEntrega);

        //Salvando a locacao...
        //TODO adicionar método para salvar

        return locacao;
    }
}
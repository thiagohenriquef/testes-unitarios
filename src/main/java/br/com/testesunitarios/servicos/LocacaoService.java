package br.com.testesunitarios.servicos;

import br.com.testesunitarios.dao.LocacaoDAO;
import br.com.testesunitarios.entidades.Filme;
import br.com.testesunitarios.entidades.Locacao;
import br.com.testesunitarios.entidades.User;
import br.com.testesunitarios.exceptions.FilmeSemEstoqueException;
import br.com.testesunitarios.exceptions.LocadoraException;
import br.com.testesunitarios.exceptions.Mensagens;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import static br.com.testesunitarios.utils.DataUtils.adicionarDias;
import static br.com.testesunitarios.utils.DataUtils.verificarDiaSemana;
import static java.util.Objects.isNull;

public class LocacaoService {

    private LocacaoDAO dao;
    private SPCService spcService;

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

        if (spcService.possuiNegativacao(user)) {
            throw new LocadoraException(Mensagens.USUARIO_NEGATIVADO_SPC.name());
        }

        Locacao locacao = new Locacao();
        locacao.setFilme(filmes);
        locacao.setUsuario(user);
        locacao.setDataLocacao(new Date());
        locacao.setValor(aplicaDescontosNoValor(filmes));

        //Entrega no dia seguinte
        Date dataEntrega = new Date();
        dataEntrega = adicionarDias(dataEntrega, 1);
        if (verificarDiaSemana(dataEntrega, Calendar.SUNDAY)) {
            dataEntrega = adicionarDias(dataEntrega, 1);
        }
        locacao.setDataRetorno(dataEntrega);

        //Salvando a locacao...
        dao.salvar(locacao);

        return locacao;
    }

    public void setLocacaoDAO(LocacaoDAO dao) {
        this.dao = dao;
    }

    public void setSpcService(SPCService spcService) {
        this.spcService = spcService;
    }

    private Double aplicaDescontosNoValor(List<Filme> filmes) {
        Double valorTotal = 0.0;
        for(int i = 0; i < filmes.size(); i++) {
            Filme filme = filmes.get(i);
            Double valorFilme = filme.getPrecoLocacao();
            switch (i) {
                case 2:
                    valorFilme = valorFilme * 0.75;
                    break;
                case 3:
                    valorFilme = valorFilme * 0.5;
                    break;
                case 4:
                    valorFilme = valorFilme * 0.25;
                    break;
                case 5:
                    valorFilme = 0d;
                    break;
            }
            valorTotal += valorFilme;
        }
        return valorTotal;
    }
}
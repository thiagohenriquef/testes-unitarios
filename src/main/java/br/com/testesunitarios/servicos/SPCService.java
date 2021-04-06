package br.com.testesunitarios.servicos;

import br.com.testesunitarios.entidades.User;

public interface SPCService {
    boolean possuiNegativacao(User user) throws Exception;
}

package br.com.testesunitarios.servicos;

import br.com.testesunitarios.entidades.User;

public interface EmailService {
    void notificarAtraso(User user);

}

package br.com.testesunitarios.builders;

import br.com.testesunitarios.entidades.User;

public class UsuarioBuilder {

    private User user;

    public static UsuarioBuilder umUsuario() {
        UsuarioBuilder builder = new UsuarioBuilder();
        builder.user = new User();
        builder.user.setNome("User");
        return  builder;
    }

    public UsuarioBuilder comNome(String nome) {
        user.setNome(nome);
        return this;
    }
    public User agora() {
        return user;
    }
}

package br.com.testesunitarios.entidades;

import java.util.Objects;

public class User {

	private String nome;
	
	public User() {}
	
	public User(String nome) {
		this.nome = nome;
	}

	public String getNome() {
		return nome;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		User user = (User) o;
		return Objects.equals(nome, user.nome);
	}

	@Override
	public int hashCode() {
		return Objects.hash(nome);
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
}
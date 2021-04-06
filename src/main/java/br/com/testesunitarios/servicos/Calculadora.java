package br.com.testesunitarios.servicos;

import br.com.testesunitarios.exceptions.NaoPodeDividirPorZeroException;

public class Calculadora {
    public int soma(int i, int j) {
        return i + j;
    }

    public int subtracao(int i, int j) {
        return i - j;
    }
    public int divisao(int i, int j) throws NaoPodeDividirPorZeroException {
        if (j == 0) {
            throw new NaoPodeDividirPorZeroException();
        }
        return i / j;
    }

    public void imprime() {
        System.out.println("Passei aqui");
    }
}

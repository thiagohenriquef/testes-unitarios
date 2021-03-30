package br.com.testesunitarios.matchers;

import br.com.testesunitarios.utils.DataUtils;
import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

import java.util.Calendar;
import java.util.Date;

public class MatchersProprios {
    public static DiaDaSemanaMatcher caiEm(Integer diaDaSemana) {
        return new DiaDaSemanaMatcher(diaDaSemana);
    }

    public static DiaDaSemanaMatcher caiEmUmaSegunda() {
        return new DiaDaSemanaMatcher(Calendar.MONDAY);
    }

    public static QuantidadeDiasDaSemanaMatcher ehHoje() {
        return new QuantidadeDiasDaSemanaMatcher(0);
    }

    public static QuantidadeDiasDaSemanaMatcher ehHojeComDiferencaDeDias(Integer qtdDias) {
        return new QuantidadeDiasDaSemanaMatcher(qtdDias);
    }
}

package br.com.testesunitarios.matchers;

import br.com.testesunitarios.utils.DataUtils;
import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import static br.com.testesunitarios.utils.DataUtils.isMesmaData;
import static br.com.testesunitarios.utils.DataUtils.obterDataComDiferencaDias;

public class QuantidadeDiasDaSemanaMatcher extends TypeSafeMatcher<Date> {
    private Integer qtdDias;

    public QuantidadeDiasDaSemanaMatcher(Integer qtdDias) {
        this.qtdDias = qtdDias;
    }

    @Override
    protected boolean matchesSafely(Date date) {
        return isMesmaData(date, obterDataComDiferencaDias(qtdDias));
    }

    @Override
    public void describeTo(Description description) {
        Date dataEsperada = obterDataComDiferencaDias(qtdDias);
        DateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        description.appendText(format.format(dataEsperada));
    }
}

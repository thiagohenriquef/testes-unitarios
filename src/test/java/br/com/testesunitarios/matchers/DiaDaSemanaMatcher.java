package br.com.testesunitarios.matchers;

import br.com.testesunitarios.utils.DataUtils;
import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DiaDaSemanaMatcher extends TypeSafeMatcher<Date> {
    private Integer diaDaSemana;

    public DiaDaSemanaMatcher(Integer diaDaSemana) {
        this.diaDaSemana = diaDaSemana;
    }

    @Override
    protected boolean matchesSafely(Date date) {
        return DataUtils.verificarDiaSemana(date, diaDaSemana);
    }

    @Override
    public void describeTo(Description description) {
        Calendar date = Calendar.getInstance();
        date.set(Calendar.DAY_OF_WEEK, diaDaSemana);
        String dataExtenso = date.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, new Locale("pt", "BR"));
        description.appendText(dataExtenso);
    }
}

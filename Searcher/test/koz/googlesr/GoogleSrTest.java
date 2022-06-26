package koz.googlesr;


import koz.googlesr.fa.FactAnswerNotFoundException;
import koz.googlesr.fa.FactAnswerNotSupportedException;
import koz.googlesr.fa.FactAnswerUnknownException;
import org.junit.Test;

import java.io.IOException;
import static org.junit.Assert.assertNotEquals;

public class GoogleSrTest {
    private final Google sr = new Google();
    @Test
    public void searchTest() throws IOException {
        ResultOfSearch[] list = sr.search("Математика");
        assertNotEquals(list.length, 0);
    }

    @Test
    public void factAnswer() throws IOException, FactAnswerUnknownException, FactAnswerNotFoundException, FactAnswerNotSupportedException {
        IFastAnswer fact = sr.factAnswer("Закон ома");
    }

}

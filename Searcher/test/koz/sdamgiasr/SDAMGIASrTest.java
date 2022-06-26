package koz.sdamgiasr;

import koz.sdamgiasr.subject.*;
import org.junit.Test;
import static org.junit.Assert.assertNotEquals;

import java.io.IOException;

public class SDAMGIASrTest {
    @Test
    public void rusTest() throws IOException {
        search(new RusSubject(), "Белую ночь мы встреча..м в стари(н,нн)ой келье монастыря на Соловках");
    }

    @Test
    public void mathTest() throws IOException {
        search(new MathSubject(), "В школе открыты две спортивные секции: по футболу и по лёгкой атлетике. Заниматься можно только в одной из них. Число школьников, занимающихся в секции по футболу, относится к числу школьников, занимающихся в секции по лёгкой атлетике, как 11:8. Сколько школьников занимаются в секции по футболу, если всего в двух секциях занимаются 57 школьников?");
    }

    @Test
    public void geoTest() throws IOException {
        search(new GeoSubject(), "Географические названия форм рельефа: Становое");
    }

    @Test
    public void histTest() throws IOException {
        search(new HistSubject(), "Князь Орлов цены не ставил моей работе");
    }

    @Test
    public void chemTest() throws IOException {
        search(new ChemSubject(), "Укажите номер рисунка, на котором изображен объект, содержащий индивидуальное химическое вещество");
    }

    @Test
    public void socTest() throws IOException {
        search(new SocSubject(), "Как Вы думаете, почему при пользовании Интернетом необходимо соблюдать специальные правила безопасного поведения?");
    }

    @Test
    public void physTest() throws IOException {
        search(new PhysSubject(), "На рисунке изображена картина линий магнитного поля двух постоянных магнитов");
    }

    @Test
    public void bioTest() throws IOException {
        search(new BioSubject(), "Список организмов: 1) ламинария 2) мухомор 3) кукушкин лён 4) кукушка 5) дождевой червь 6) репчатый лук");
    }

    private void search(ISubject subject, String s) throws IOException {
        SDAMGIA sr = new SDAMGIA(subject);

        ResultOfSearch[] r = sr.search(s);
        assertNotEquals(r.length, 0);
    }
}


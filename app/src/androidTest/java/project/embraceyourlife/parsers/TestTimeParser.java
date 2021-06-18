package project.embraceyourlife.parsers;

import org.junit.Assert;
import org.junit.Test;

public class TestTimeParser {

    @Test
    public void parse_format_rownosc(){
        String wejscie = "21:37";
        String oczekiwane = "21:37";

        int liczba = TimeParser.parse(wejscie);
        String otrzymane = TimeParser.format(liczba);

        Assert.assertEquals(oczekiwane, otrzymane);
    }

    @Test
    public void parse_format_rownosc_bezgodzin(){
        String wejscie = "0:37";
        String oczekiwane = "0:37";

        int liczba = TimeParser.parse(wejscie);
        String otrzymane = TimeParser.format(liczba);

        Assert.assertEquals(otrzymane, oczekiwane);
    }

    @Test
    public void format_parse_rownosc(){
        int wejscie = 1297;
        int oczekiwane = 1297;

        String napis = TimeParser.format(wejscie);
        int otrzymane = TimeParser.parse(napis);

        Assert.assertEquals(oczekiwane, otrzymane);
    }

    @Test
    public void format_uzupelnienie() {
        int wejscie = 37;
        String oczekiwane = "0:37";

        String otrzymane = TimeParser.format(wejscie);

        Assert.assertEquals(otrzymane, oczekiwane);
    }

    @Test
    public void truncDate_string() {
        String wejscie = "24/03/2021 13:20";
        String oczekiwane = "13:20";

        String otrzymane = TimeParser.truncDate(wejscie);

        Assert.assertEquals(otrzymane, oczekiwane);
    }

    @Test
    public void truncDate_date() {
        String wejscie = "24/03/2021 13:20";
        String oczekiwane = "13:20";

        String otrzymane = TimeParser.truncDate(DateParser.parse(wejscie));

        Assert.assertEquals(otrzymane, oczekiwane);
    }
}

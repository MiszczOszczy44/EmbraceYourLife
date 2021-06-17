package project.embraceyourlife;

import org.junit.Assert;
import org.junit.Test;

import java.util.Date;

import project.embraceyourlife.datatypes.Wydarzenie;

public class TestWydarzenie {

    @Test
    public void formatDaty_rownosc() {
        String dataString = "12/04/1995 1:45";
        String oczekiwane = "12/04/1995 01:45";

        try {
            Date data = Wydarzenie.formatDaty.parse(dataString);
            String otrzymane = Wydarzenie.formatDaty.format(data);

            Assert.assertEquals(oczekiwane, otrzymane);
        } catch (Exception e) {
            Assert.fail();
        }
    }

    @Test
    public void formatDaty_dodawanieCzasu() {
        String dataString = "12/04/1995 12:50";
        int czasTrwania_minuty = 90;
        String oczekiwane = "12/04/1995 14:20";

        try {
            Date data = Wydarzenie.formatDaty.parse(dataString);
            Date nowaData = new Date(data.getTime() + czasTrwania_minuty * 60000);

            String otrzymane = Wydarzenie.formatDaty.format(nowaData);
            Assert.assertEquals(otrzymane, oczekiwane);
        } catch (Exception e) {
            Assert.fail();
        }
    }

    @Test
    public void formatDaty_dodawanieCzasu_overflow() {
        String dataString = "12/04/1995 23:50";
        int czasTrwania_minuty = 90;
        String oczekiwane = "13/04/1995 01:20";

        try {
            Date data = Wydarzenie.formatDaty.parse(dataString);
            Date nowaData = new Date(data.getTime() + czasTrwania_minuty * 60000);

            String otrzymane = Wydarzenie.formatDaty.format(nowaData);
            Assert.assertEquals(otrzymane, oczekiwane);
        } catch (Exception e) {
            Assert.fail();
        }
    }

    @Test
    public void formatDaty_rownosc_rozwiniecie() {
        String dataString = "2/4/1995 1:45";
        String oczekiwane = "02/04/1995 01:45";

        try {
            Date data = Wydarzenie.formatDaty.parse(dataString);
            String otrzymane = Wydarzenie.formatDaty.format(data);

            Assert.assertEquals(oczekiwane, otrzymane);
        } catch (Exception e) {
            Assert.fail();
        }
    }
}

package project.embraceyourlife;


import android.content.Context;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;

import project.embraceyourlife.datatypes.CwiczenieINFO;
import project.embraceyourlife.datatypes.Wydarzenie;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
public class TestDatabase {

    @Test
    public void insert_getCwiczenieINFO_removeCwiczenie() {
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        Database db = Database.getInstance(appContext);

        String nazwaCwiczenia = "nazwa";

        CwiczenieINFO out = new CwiczenieINFO(nazwaCwiczenia,
                true, false, true, false);

        db.insert(out);
        CwiczenieINFO in = db.getCwiczenieINFO(nazwaCwiczenia);

        assertEquals(out.nazwa, in.nazwa);
        assertEquals(out.czas, in.czas);
        assertEquals(out.dystans, in.dystans);
        assertEquals(out.powtorzenia, in.powtorzenia);
        assertEquals(out.obciazenie, in.obciazenie);

        db.removeCwiczenie(nazwaCwiczenia);
        CwiczenieINFO poUsunieciu = db.getCwiczenieINFO(nazwaCwiczenia);
        assertNull(poUsunieciu);
    }

    @Test
    public void insert_getWydarzenia_removeWydarzenie() {
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        Database db = Database.getInstance(appContext);

        String data = "12/04/2015 14:20";

        Wydarzenie wydarzenie = new Wydarzenie("nazwa", "",
                data, 90, "Przykładowe wydarzenie");

        db.insert(wydarzenie);
        assertTrue(findWydarzenieInList(wydarzenie, db.getWydarzenia(data)));

        db.removeWydarzenie(wydarzenie);
        assertFalse(findWydarzenieInList(wydarzenie, db.getWydarzenia(data)));
    }

    public boolean findWydarzenieInList(Wydarzenie wydarzenie, ArrayList<Wydarzenie> lista) {
        for (Wydarzenie w : lista) {
            if (w.getNazwa().equals(wydarzenie.getNazwa()) &&
                w.getPowtarzalnosc().equals(wydarzenie.getPowtarzalnosc()) &&
                w.getData().equals(wydarzenie.getData()) &&
                w.getCzasTrwania() == wydarzenie.getCzasTrwania() &&
                w.getOpis().equals(wydarzenie.getOpis())) {

                return true;
            }
        }
        return false;
    }
}

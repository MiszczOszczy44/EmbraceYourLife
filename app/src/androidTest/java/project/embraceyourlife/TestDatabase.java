package project.embraceyourlife;


import android.content.Context;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import org.junit.Test;
import org.junit.runner.RunWith;

import project.embraceyourlife.datatypes.CwiczenieINFO;

import static org.junit.Assert.assertEquals;

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
        assertEquals(poUsunieciu, null);
    }
}

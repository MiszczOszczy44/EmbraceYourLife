package project.embraceyourlife;

import android.provider.ContactsContract;

import org.junit.Assert;
import org.junit.Test;
import java.util.List;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class TestDatabase {
    Database baza;

    @Test
    void InsertCwiczenieTest()
    {
        String nazwa = "bieganie";
        String kategoria = "Jednorazowy";
        boolean Obciazenie = true;
        boolean IloscPowtorzen = true;
        boolean CzasTrwania = true;
        boolean Dystans = true;
        Database.Cwiczenie cw = new Database.Cwiczenie;
        cw.Nazwa = "bieganie";
        cw.Kategoria = "Jednorazowy";
        cw.Obciazenie = 1;
        cw.Ilosc_powtorzen = 1;
        cw.Czas_trwania = 1;
        cw.Dystans = 1;
        baza.insertIntoCwiczenie(nazwa, kategoria, Obciazenie, IloscPowtorzen, CzasTrwania, Dystans);
        List<Database.Cwiczenie> List = baza.getCwiczenie();
        Assert.assertEquals(cw, List.get(List.size() - 1));
        baza.deleteFromCwiczenie(String.valueOf(List.get(List.size() - 1).ID));
    }
    @Test
    void InsertWydarzenieTest(){
        Database.Wydarzenie wd = new Database.Wydarzenie;
        wd.Nazwa = "nazwa";
        wd.Opis = "brak";
        wd.Powtarzalnosc = "Jednorazowo";
        wd.Data = "brak";
        wd.Czas_trwania = "1,5h";
        baza.insertIntoWydarzenie(wd.Nazwa, wd.Powtarzalnosc, wd.Czas_trwania, wd.Data, wd.Opis, wd.Lista_cwiczen);
        List<Database.Wydarzenie> List = baza.getWydarzenia(wd.Data);
        Assert.assertEquals(wd, List.get(List.size()-1));
        baza.deleteFromWydarzenie(String.valueOf(List.get(List.size()-1)));
    }
}

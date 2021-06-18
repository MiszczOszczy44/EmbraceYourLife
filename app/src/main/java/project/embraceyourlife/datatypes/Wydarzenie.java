package project.embraceyourlife.datatypes;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import project.embraceyourlife.parsers.DateParser;

public class Wydarzenie {
    private static final String prefixTreningu = "TRENING::";


    private final int id;
    private final String nazwa;
    private final String powtarzalnosc;
    private final Date data;
    private final int czasTrwania;
    private final String opis;

    private final Cwiczenie[] cwiczenia;


    public Wydarzenie(int id, String nazwa, String powtarzalnosc, String data, int czasTrwania, String opis) {
        this.id = id;
        this.nazwa = nazwa;
        this.powtarzalnosc = powtarzalnosc;
        this.data = DateParser.parse(data);
        this.czasTrwania = czasTrwania;

        // Jeśli wydarzenie jest treningiem
        if (opis.startsWith(prefixTreningu)) {
            this.opis = "";
            String[] cwiczeniaString = opis.substring(prefixTreningu.length())
                    .split(";");
            cwiczenia = new Cwiczenie[cwiczeniaString.length];
            for (int i = 0; i < cwiczeniaString.length; ++i) {
                cwiczenia[i] = new Cwiczenie(cwiczeniaString[i]);
            }
        } else {
            this.opis = opis;
            this.cwiczenia = null;
        }
    }

    public Wydarzenie(String nazwa, String powtarzalnosc, String data, int czasTrwania, String opis) {
        this(-1, nazwa, powtarzalnosc, data, czasTrwania, opis);
    }
    

    // Zwraca Stringa reprezentującego datę
    public String getData() {
        return DateParser.format(data);
    }

    public long getSortINFO() {
        return data.getTime();
    }

    // zwraca opis wydarzenia
    // jeśli to trening zwraca zserializowane ćwiczenia
    public String getOpis() {
        if (cwiczenia == null)
            return opis;

        StringBuilder sb = new StringBuilder();
        sb.append(prefixTreningu);
        for (Cwiczenie c : cwiczenia) {
            sb.append(c.serialize());
            sb.append(';');
        }
        return sb.toString();
    }

    public String getNazwa() {
        return nazwa;
    }

    public String getPowtarzalnosc() {
        return powtarzalnosc;
    }

    public int getCzasTrwania() {
        return czasTrwania;
    }
}
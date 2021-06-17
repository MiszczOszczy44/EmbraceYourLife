package project.embraceyourlife.datatypes;


// Klasa służy do przechowywania informacji na temat ćwiczenia
public class CwiczenieINFO {
    public final int id;
    public final String nazwa;
    public final boolean powtorzenia;
    public final boolean obciazenie;
    public final boolean czas;
    public final boolean dystans;


    // Konstruktor dla bazy danych
    public CwiczenieINFO(int id, String nazwa, boolean powtorzenia, boolean obciazenie, boolean czas, boolean dystans) {
        this.id = id;
        this.nazwa = nazwa;
        this.powtorzenia = powtorzenia;
        this.obciazenie = obciazenie;
        this.czas = czas;
        this.dystans = dystans;
    }


    // Konstruktor dla tworzenia nowych ćwiczeń
    public CwiczenieINFO(String nazwa, boolean powtorzenia, boolean obciazenie, boolean czas, boolean dystans) {
        this.id = -1;
        this.nazwa = nazwa;
        this.powtorzenia = powtorzenia;
        this.obciazenie = obciazenie;
        this.czas = czas;
        this.dystans = dystans;
    }
}

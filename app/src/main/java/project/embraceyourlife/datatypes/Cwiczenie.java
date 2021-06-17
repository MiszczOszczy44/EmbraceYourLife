package project.embraceyourlife.datatypes;

public class Cwiczenie {
    // ID pod którym można znaleźć nazwę
    // i parametry ćwiczenia
    public final int id_info;

    public final int ilosc_powtorzen;
    public final int obciazenie;
    public final int czasTrwania;
    public final int dystans;


    public Cwiczenie(int id_info,
                     int ilosc_powtorzen, int obciazenie, int czasTrwania, int dystans) {
        this.id_info = id_info;
        this.ilosc_powtorzen = ilosc_powtorzen;
        this.obciazenie = obciazenie;
        this.czasTrwania = czasTrwania;
        this.dystans = dystans;
    }


    // Za zserializowane ćwiczenie przyjmujemy ciąg znaków
    // id:ilosc_powtorzen,obciazenie,czas_trwania,dystans
    // gdzie jeśli jakaś wartość nie jest brana pod uwagę pojawia się -1
    public Cwiczenie(String serialized) {
        int dwukropek = serialized.indexOf(":");
        id_info = Integer.parseInt(serialized.substring(0, dwukropek));
        String[] splitted = serialized.substring(dwukropek + 1).split(",");

        ilosc_powtorzen = Integer.parseInt(splitted[0]);
        obciazenie      = Integer.parseInt(splitted[1]);
        czasTrwania     = Integer.parseInt(splitted[2]);
        dystans         = Integer.parseInt(splitted[3]);
    }


    public String serialize() {
        StringBuilder sb = new StringBuilder();
        sb.append(id_info);
        sb.append(':');
        sb.append(ilosc_powtorzen);
        sb.append(',');
        sb.append(obciazenie);
        sb.append(',');
        sb.append(czasTrwania);
        sb.append(',');
        sb.append(dystans);
        return sb.toString();
    }
}

package project.embraceyourlife.parsers;

public class TimeParser {

    // przyjmuje minuty
    // zwraca czas jako
    // hh:mm
    public static String format(int minuty) {
        int godz = minuty / 60;
        int min = minuty % 60;
        StringBuilder sb = new StringBuilder();
        sb.append(godz);
        sb.append(":");
        sb.append(min);
        return sb.toString();
    }

    // Przyjmuje czas w postaci:
    // hh:min
    // lub
    // min
    // zwraca minuty jako int
    public static int parse(String czas) {
        String[] tablica = czas.split(":");
        if(tablica.length == 1){
            return Integer.parseInt(tablica[0]);
        }
        int godz = Integer.parseInt(tablica[0]);
        int min = Integer.parseInt(tablica[1]);
        return godz*60 + min;
    }
}

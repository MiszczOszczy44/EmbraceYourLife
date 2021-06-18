package project.embraceyourlife.parsers;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateParser {
    private static SimpleDateFormat dateFormater;
    static {
        dateFormater = new SimpleDateFormat("dd/MM/yyyy HH:mm");
    }

    public static String format(Date data) {
        try {
            return dateFormater.format(data);
        } catch (Exception e) {
            return "02/04/2005 21:37";
        }
    }

    public static Date parse(String data) {
        try {
            return dateFormater.parse(data);
        } catch (Exception e) {
            return new Date();
        }
    }

    public static String truncTime(String data) {
        int spacja = data.indexOf(' ');
        return data.substring(0, spacja);
    }

    public static String truncTime(Date data) {
        return truncTime(format(data));
    }
}

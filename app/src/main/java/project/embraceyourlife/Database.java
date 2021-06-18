package project.embraceyourlife;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.Collection;
import java.util.TreeMap;

import project.embraceyourlife.datatypes.CwiczenieINFO;
import project.embraceyourlife.datatypes.Wydarzenie;


public class Database extends SQLiteOpenHelper {

    private static Database _instance;


    private TreeMap<String, CwiczenieINFO>  cwiczeniaNazwa;
    private TreeMap<Integer, CwiczenieINFO> cwiczeniaID;



    // Po każdej zmianie struktury bazy danych (tabel)
    // Trzeba zinkrementować wersję w ostatnim argumencie super() o 1
    private Database(Context context)
    {
        super(context, "DATABASE", null, 5);
    }

    public static Database getInstance(Context context) {
        if (_instance == null) {
            _instance = new Database(context);
        }
        return _instance;
    }


    private CwiczenieINFO findCwiczenieINFO(String nazwaCiwczenia) {
        SQLiteDatabase db = getWritableDatabase();

        String[] columns = {"id",         "nazwa",        "powtorzenia",
                            "obciazenie", "czas_trwania", "dystans"};

            Cursor cursor = db.query("Cwiczenia", columns,
                null, new String[]{ "nazwa = " + nazwaCiwczenia },null,null, null);

        CwiczenieINFO cwiczenieINFO = null;
        if (cursor.moveToNext())
        {
            int id = cursor.getInt(cursor.getColumnIndex("id"));
            String nazwa = cursor.getString(cursor.getColumnIndex("nazwa"));
            boolean powtorzenia = cursor.getInt(cursor.getColumnIndex("powtorzenia")) == 1;
            boolean obciazenie  = cursor.getInt(cursor.getColumnIndex("obciazenie")) == 1;
            boolean czas        = cursor.getInt(cursor.getColumnIndex("czas_trwania")) == 1;
            boolean dystans     = cursor.getInt(cursor.getColumnIndex("dystans")) == 1;
            cwiczenieINFO = new CwiczenieINFO(id, nazwa, powtorzenia, obciazenie, czas, dystans);
        }
        return cwiczenieINFO;
    }

    public void insert(CwiczenieINFO cwiczenie) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("nazwa", cwiczenie.nazwa);
        cv.put("powtorzenia", cwiczenie.powtorzenia);
        cv.put("obciazenie", cwiczenie.obciazenie);
        cv.put("czas_trwania", cwiczenie.czas);
        cv.put("dystans", cwiczenie.dystans);
        db.insert("Cwiczenia", null, cv);

        if(!cacheCwiczenieINFO()) {
            CwiczenieINFO tmp = findCwiczenieINFO(cwiczenie.nazwa);
            cwiczeniaNazwa.put(tmp.nazwa, tmp);
            cwiczeniaID.put(tmp.id, tmp);
        }
    }

    public void insert(Wydarzenie wydarzenie) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("Nazwa", wydarzenie.getNazwa());
        cv.put("Powtarzalnosc", wydarzenie.getPowtarzalnosc());
        cv.put("Czas_trwania", wydarzenie.getCzasTrwania());
        cv.put("Data", wydarzenie.getData());
        cv.put("Opis", wydarzenie.getOpis());
        db.insert("Wydarzenia", null, cv);
    }


    // Zwraca true jeśli wczytało wszystkie ćwiczenia od nowa
    private boolean cacheCwiczenieINFO() {
        if (cwiczeniaID != null && cwiczeniaNazwa != null)
            return false;

        SQLiteDatabase db = getWritableDatabase();

        String[] columns = {"id",         "nazwa",        "powtorzenia",
                            "obciazenie", "czas_trwania", "dystans"};

        Cursor cursor = db.query("Cwiczenia", columns,
                null,null,null,null, null);

        cwiczeniaID = new TreeMap<>();
        cwiczeniaNazwa = new TreeMap<>();
        while (cursor.moveToNext())
        {
            int id = cursor.getInt(cursor.getColumnIndex("id"));
            String nazwa = cursor.getString(cursor.getColumnIndex("nazwa"));
            boolean powtorzenia = cursor.getInt(cursor.getColumnIndex("powtorzenia")) == 1;
            boolean obciazenie  = cursor.getInt(cursor.getColumnIndex("obciazenie")) == 1;
            boolean czas        = cursor.getInt(cursor.getColumnIndex("czas_trwania")) == 1;
            boolean dystans     = cursor.getInt(cursor.getColumnIndex("dystans")) == 1;
            CwiczenieINFO cwiczenieINFO = new CwiczenieINFO(id, nazwa, powtorzenia, obciazenie, czas, dystans);
            cwiczeniaID.put(cwiczenieINFO.id, cwiczenieINFO);
            cwiczeniaNazwa.put(cwiczenieINFO.nazwa, cwiczenieINFO);
        }
        return true;
    }

    // Zwraca null jeśli nie ma takiego id w bazie
    public CwiczenieINFO getCwiczenieINFO(int id) {
        return cwiczeniaID.get(id);
    }

    // Zwraca null jeśli nie ma ćwiczenia o takiej nazwie w bazie
    public CwiczenieINFO getCwiczenieINFO(String nazwa) {
        return cwiczeniaNazwa.get(nazwa);
    }

    public Collection<CwiczenieINFO> getCwiczeniaINFO() {
        if (cwiczeniaID == null || cwiczeniaNazwa == null)
            cacheCwiczenieINFO();
        return cwiczeniaID.values();
    }

    public ArrayList<Wydarzenie> getWydarzenia(String Data)
    {
        SQLiteDatabase db = getWritableDatabase();
        String[] columns = {"id", "nazwa", "powtarzalnosc", "czas_trwania", "data", "opis"};
        Cursor cursor =db.query("Wydarzenia",columns,null,null,null,null,null);
        ArrayList<Wydarzenie> templist = new ArrayList<>(cursor.getCount());
        while (cursor.moveToNext())
        {
            int id = cursor.getInt(cursor.getColumnIndex("id"));
            String nazwa = cursor.getString(cursor.getColumnIndex("nazwa"));
            String powtarzalnosc = cursor.getString(cursor.getColumnIndex("powtarzalnosc"));
            String data = cursor.getString(cursor.getColumnIndex("data"));
            int czasTrwania = cursor.getInt(cursor.getColumnIndex("czas_trwania"));
            String opis = cursor.getString(cursor.getColumnIndex("opis"));
            Wydarzenie wydarzenie = new Wydarzenie(id, nazwa, powtarzalnosc, data, czasTrwania, opis);
            templist.add(wydarzenie);
        }
        return templist;
    }


    public void removeCwiczenie(String nazwa)
    {
        SQLiteDatabase db = getWritableDatabase();
        String[] whereArgs ={nazwa};

        if (cwiczeniaID != null && cwiczeniaNazwa != null) {
            CwiczenieINFO removed = cwiczeniaNazwa.remove(nazwa);
            cwiczeniaID.remove(removed.id);
        }

        db.delete("Cwiczenia" ,"nazwa = ?", whereArgs);
    }


//    public  int deleteFromWydarzenie(String ID)
//    {
//        SQLiteDatabase db = getWritableDatabase();
//        String[] whereArgs ={ID};
//
//        int count =db.delete("Wydarzenie" ,"ID"+" = ?",whereArgs);
//        return  count;
//    }


    public void onCreate(SQLiteDatabase db) {

        try {
            db.execSQL( // Tworzenie tableli Wydarzenia
                "CREATE TABLE Wydarzenia " +
                "(id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "nazwa TEXT, " +
                "powtarzalnosc TEXT, " +
                "czas_trwania INTEGER, " +
                "data TEXT, " +
                "opis TEXT);"
            );
            db.execSQL( // Tworzenie tabeli Ćwiczenia
                "CREATE TABLE Cwiczenia" +
                "(id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "nazwa TEXT UNIQUE, " +
                "powtorzenia BLOB," +
                "obciazenie BLOB, " +
                "czas_trwania BLOB, " +
                "dystans BLOB);"
            );
        } catch (Exception e) {
        }
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        try {
            db.execSQL("DROP TABLE Cwiczenia");
            db.execSQL("DROP TABLE Wydarzenia");
            onCreate(db);
        }catch (Exception e) {
        }
    }

}

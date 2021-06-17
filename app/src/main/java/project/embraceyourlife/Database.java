package project.embraceyourlife;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import project.embraceyourlife.datatypes.CwiczenieINFO;
import project.embraceyourlife.datatypes.Wydarzenie;


public class Database extends SQLiteOpenHelper {

    private static Database _instance;


    private ArrayList<CwiczenieINFO> cwiczeniaLista;
    private TreeMap<String, CwiczenieINFO> cwiczeniaMapa;



    // Po każdej zmianie struktury bazy danych (tabel)
    // Trzeba zinkrementować wersję w ostatnim argumencie super() o 1
    private Database(Context context)
    {
        super(context, "DATABASE", null, 4);
    }

    public static Database getInstance(Context context) {
        if (_instance == null) {
            _instance = new Database(context);
        }
        return _instance;
    }


    public void insert(CwiczenieINFO cwiczenie) {
        cacheCwiczenieINFO();
        cwiczeniaMapa.put(cwiczenie.nazwa, cwiczenie);
        cwiczeniaLista.add(cwiczenie);

        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("nazwa", cwiczenie.nazwa);
        cv.put("powtorzenia", cwiczenie.powtorzenia);
        cv.put("obciazenie", cwiczenie.obciazenie);
        cv.put("czas_trwania", cwiczenie.czas);
        cv.put("dystans", cwiczenie.dystans);
        db.insert("Cwiczenia", null, cv);
    }

    public void insert(Wydarzenie wydarzenie) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("Nazwa", wydarzenie.getNazwa());
        cv.put("Powtarzalnosc", wydarzenie.getPowtarzalnosc());
        cv.put("Czas_trwania", wydarzenie.getCzasTrwania());
        cv.put("Data", wydarzenie.getData());
        cv.put("Opis", wydarzenie.getOpis());
        db.insert("Wydarzenie", null, cv);
    }



    private void cacheCwiczenieINFO() {
        if (cwiczeniaLista != null && cwiczeniaMapa != null)
            return;

        SQLiteDatabase db = getWritableDatabase();

        String[] columns = {"id",         "nazwa",        "powtorzenia",
                            "obciazenie", "czas_trwania", "dystans"};

        Cursor cursor = db.query("Cwiczenia", columns,
                null,null,null,null, "id");

        cwiczeniaLista = new ArrayList<>(cursor.getCount());
        cwiczeniaMapa = new TreeMap<>();
        while (cursor.moveToNext())
        {
            int id = cursor.getInt(cursor.getColumnIndex("id"));
            String nazwa = cursor.getString(cursor.getColumnIndex("nazwa"));
            boolean powtorzenia = cursor.getInt(cursor.getColumnIndex("powtorzenia")) == 1;
            boolean obciazenie  = cursor.getInt(cursor.getColumnIndex("obciazenie")) == 1;
            boolean czas        = cursor.getInt(cursor.getColumnIndex("czas_trwania")) == 1;
            boolean dystans     = cursor.getInt(cursor.getColumnIndex("dystans")) == 1;
            CwiczenieINFO cwiczenieINFO = new CwiczenieINFO(id, nazwa, powtorzenia, obciazenie, czas, dystans);
            cwiczeniaLista.add(cwiczenieINFO);
            cwiczeniaMapa.put(cwiczenieINFO.nazwa, cwiczenieINFO);
        }
    }

    // Jeśli pojawi się nieciągłość w bazie danych wszystko się wywali.
    // Zwraca null jeśli nie ma takiego id w bazie
    public CwiczenieINFO getCwiczenieINFO(int id) {
        if (id >= cwiczeniaLista.size())
            return null;
        return cwiczeniaLista.get(id);
    }

    // Zwraca null jeśli nie ma ćwiczenia o takiej nazwie w bazie
    public CwiczenieINFO getCwiczenieINFO(String nazwa) {
        return cwiczeniaMapa.get(nazwa);
    }



    public List<Wydarzenie> getWydarzenia(String Data)
    {
        SQLiteDatabase db = getWritableDatabase();
        String[] columns = {"id", "nazwa", "powtarzalnosc", "czas_trwania", "data", "opis"};
        Cursor cursor =db.query("Wydarzenie",columns,null,null,null,null,null);
        StringBuffer buffer= new StringBuffer();
        List<Wydarzenie> templist = null;
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
        
        if (cwiczeniaLista != null && cwiczeniaMapa != null) {
            cwiczeniaMapa.remove(nazwa);
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
                "nazwa TEXT, " +
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

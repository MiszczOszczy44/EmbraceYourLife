package project.embraceyourlife;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.ContactsContract;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;


public class Database {
    DBHelperClass DBHelper;
    public Database(Context context)
    {
        DBHelper = new DBHelperClass(context);
    }

    private static Database _instance;
    private Database()
    {

    }

    public static Database getInstance()
    {
        if (_instance == null)
        {
            _instance = new Database();
        }
        return _instance;
    }

    public class Cwiczenie{
        int ID;
        String Nazwa;
        String Kategoria;
        byte[] Ilosc_powtorzen;
        byte[] Obciazenie;
        byte[] Czas_trwania;
        byte[] Dystans;
    }
    public class Wydarzenie{
        int ID;
        String Nazwa;
        String Powtarzalnosc;
        String Czas_trwania;
        String Data;
        String Opis;
        String Lista_cwiczen;
    }

    public long insertIntoCwiczenie(String Nazwa, String Kategoria, boolean Ilosc_powtorzen, boolean Obciazenie, boolean Czas_trwania, boolean Dystans)
    {
        SQLiteDatabase dbb = DBHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("Nazwa_wydarzenia", Nazwa);
        contentValues.put("Kategoria", Kategoria);
        contentValues.put("Ilosc_powtorzen", Ilosc_powtorzen);
        contentValues.put("Obciazenie", Obciazenie);
        contentValues.put("Czas_trwania", Czas_trwania);
        contentValues.put("Dystans", Dystans);
        long id = dbb.insert("Cwiczenie", null , contentValues);
        return id;
    }
    public long insertIntoWydarzenie(String Nazwa, String Powtarzalnosc, String Czas_trwania, String Data, String Opis, String Lista_cwiczen)
    {
        SQLiteDatabase dbb = DBHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("Nazwa", Nazwa);
        contentValues.put("Powtarzalnosc", Powtarzalnosc);
        contentValues.put("Czas_trwania", Czas_trwania);
        contentValues.put("Data", Data);
        contentValues.put("Opis", Opis);
        contentValues.put("Lista_cwiczen", Lista_cwiczen);
        long id = dbb.insert("Wydarzenie", null , contentValues);
        return id;
    }

    public List<Wydarzenie> getWydarzenia(String Data)
    {
        SQLiteDatabase db = DBHelper.getWritableDatabase();
        String[] columns = {"ID", "Nazwa", "Powtarzalnosc", "Czas_trwania", "Data", "Opis", "Lista_cwiczen"};
        Cursor cursor =db.query("Wydarzenie",columns,null,null,null,null,"Data");
        StringBuffer buffer= new StringBuffer();
        List<Wydarzenie> templist = null;
        while (cursor.moveToNext())
        {
            Wydarzenie temp = new Wydarzenie();
            temp.ID =cursor.getInt(cursor.getColumnIndex("ID"));
            temp.Nazwa =cursor.getString(cursor.getColumnIndex("Nazwa"));
            temp.Powtarzalnosc =cursor.getString(cursor.getColumnIndex("Powtarzalnosc"));
            temp.Czas_trwania =cursor.getString(cursor.getColumnIndex("Czas_trwania"));
            temp.Data =cursor.getString(cursor.getColumnIndex("Data"));
            temp.Opis =cursor.getString(cursor.getColumnIndex("Opis"));
            temp.Lista_cwiczen =cursor.getString(cursor.getColumnIndex("Lista_cwiczen"));
            templist.add(temp);
        }
        return templist;
    }
    public List<Cwiczenie> getCwiczenie(String Data)
    {
        SQLiteDatabase db = DBHelper.getWritableDatabase();
        String[] columns = {"ID_Cwiczenia", "Nazwa_cwiczenia", "Kategoria", "Ilosc_powtorzen", "Obciazenie", "Czas_trwania", "Dystans"};
        Cursor cursor =db.query("Cwiczenie",columns,null,null,null,null,"Data");
        StringBuffer buffer= new StringBuffer();
        List<Cwiczenie> templist = null;
        while (cursor.moveToNext())
        {
            Cwiczenie temp = new Cwiczenie();
            temp.ID =cursor.getInt(cursor.getColumnIndex("ID_Cwiczenia"));
            temp.Nazwa =cursor.getString(cursor.getColumnIndex("Nazwa_cwiczenia"));
            temp.Kategoria =cursor.getString(cursor.getColumnIndex("Kategoria"));
            temp.Ilosc_powtorzen =cursor.getBlob(cursor.getColumnIndex("Ilosc_powtorzen"));
            temp.Obciazenie =cursor.getBlob(cursor.getColumnIndex("Obciazenie"));
            temp.Czas_trwania =cursor.getBlob(cursor.getColumnIndex("Czas_trwania"));
            temp.Dystans =cursor.getBlob(cursor.getColumnIndex("Dystans"));
            templist.add(temp);
        }
        return templist;
    }








    public  int deleteFromCwiczenie(String ID)
    {
        SQLiteDatabase db = DBHelper.getWritableDatabase();
        String[] whereArgs ={ID};

        int count =db.delete("Cwiczenie" ,"ID_Cwiczenia"+" = ?",whereArgs);
        return  count;
    }
    public  int deleteFromWydarzenie(String ID)
    {
        SQLiteDatabase db = DBHelper.getWritableDatabase();
        String[] whereArgs ={ID};

        int count =db.delete("Wydarzenie" ,"ID"+" = ?",whereArgs);
        return  count;
    }


    static class DBHelperClass extends SQLiteOpenHelper
    {
        private static final String DATABASE_NAME = "DATABASE";    // Database Name
        private static final int DATABASE_Version = 1;    // Database Version
        private static final String CREATE_TABLE_Cwiczenie = "CREATE TABLE Cwiczenie (\"" +
                "ID_Cwiczenia INTEGER PRIMARY KEY AUTO INCREMENT," +
                "Nazwa_cwiczenia TEXT, " +
                "Kategoria TEXT," +
                "Ilosc_powtorzen BLOB," +
                "Obciazenie BLOB," +
                "Czas_trwania BLOB," +
                "Dystans BLOB\");";

        private static final String CREATE_TABLE_Wydarzenie = "CREATE TABLE Wydarzenie (\"" +
                "ID INTEGER PRIMARY KEY AUTO INCREMENT," +
                "Nazwa TEXT," +
                "Powtarzalnosc TEXT," +
                "Czas_trwania TEXT," +
                "Data TEXT," +
                "Opis TEXT," +
                "Lista_cwiczen TEXT\");";
        private Context context;

        public DBHelperClass(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_Version);
            this.context=context;
        }

        public void onCreate(SQLiteDatabase db) {

            try {
                db.execSQL(CREATE_TABLE_Wydarzenie);
                db.execSQL(CREATE_TABLE_Cwiczenie);
            } catch (Exception e) {
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            try {
                db.execSQL("DROP TABLE Cwiczenie");
                db.execSQL("DROP TABLE Wydarzenie");
                onCreate(db);
            }catch (Exception e) {
            }
        }
    }
}

package com.example.sqliteoperations;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class Database {
    DBHelperClass DBHelper;
    public Database(Context context)
    {
        DBHelper = new DBHelperClass(context);
    }

    public long insertIntoCwiczenie_info(String Nazwa_cwiczenia, String opis_cwiczenia, String Nazwa_kategorii)
    {
        SQLiteDatabase dbb = DBHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("Nazwa_cwiczenia", Nazwa_cwiczenia);
        contentValues.put("Opis_cwiczenia", opis_cwiczenia);
        contentValues.put("Nazwa_kategorii", Nazwa_kategorii);
        long id = dbb.insert("Cwiczenie_info", null , contentValues);
        return id;
    }
    public long insertIntoDzien(String Data_dnia, int numer_tygodnia, int miesiac, int rok)
    {
        SQLiteDatabase dbb = DBHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("Data_dnia", Data_dnia);
        contentValues.put("Numer_tygodnia", numer_tygodnia);
        contentValues.put("Miesiac", miesiac);
        contentValues.put("Rok", rok);
        long id = dbb.insert("Dzien", null , contentValues);
        return id;
    }
    public long insertIntoWydarzenie(String Data_dnia, String Nazwa_wydarzenia, String Opis_wydarzenia, String Godzina_rozpoczecia, int Czas_trwania, boolean CzyCwiczenie)
    {
        SQLiteDatabase dbb = DBHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("Data_dnia", Data_dnia);
        contentValues.put("Nazwa_wydarzenia", Nazwa_wydarzenia);
        contentValues.put("Opis_wydarzenia", Opis_wydarzenia);
        contentValues.put("Godzina_rozpoczecia", Godzina_rozpoczecia);
        contentValues.put("Czas_trwana", Czas_trwania);
        contentValues.put("CzyCwiczenie", CzyCwiczenie);
        long id = dbb.insert("Wydarzenie", null , contentValues);
        return id;
    }
    public long insertIntoCwiczenie(int ID_Wydarzenia, String Nazwa_cwiczenia, int Index_kolejnosci, int Ilosc_powtorzen, float Obciazenie, int Czas_trwania, float Dystans)
    {
        SQLiteDatabase dbb = DBHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("ID_Wydarzenia", ID_Wydarzenia);
        contentValues.put("Nazwa_cwiczenia", Nazwa_cwiczenia);
        contentValues.put("Index_kolejnosci", Index_kolejnosci);
        contentValues.put("Ilosc_powtorzen", Ilosc_powtorzen);
        contentValues.put("Obciazenie", Obciazenie);
        contentValues.put("Czas_trwania", Czas_trwania);
        contentValues.put("Dystans", Dystans);
        long id = dbb.insert("Cwiczenie", null , contentValues);
        return id;
    }

    public String getData(String Table_name, String selection, String selectionArgs[], String groupBy, String having, String orderBy)
    {
        SQLiteDatabase db = DBHelper.getWritableDatabase();
        String[] columns = {"ID_Wydarzenia", "Nazwa_cwiczenia", "Index_kolejnosci", "Ilosc_powtorzen", "Obciazenie", "Czas_trwania", "Dystans"};
        Cursor cursor =db.query(Table_name,columns,selection,selectionArgs,groupBy,having,orderBy);
        StringBuffer buffer= new StringBuffer();
        while (cursor.moveToNext())
        {
            int cid =cursor.getInt(cursor.getColumnIndex(DBHelperClass.UID));
            String name =cursor.getString(cursor.getColumnIndex(DBHelperClass.NAME));
            String  password =cursor.getString(cursor.getColumnIndex(DBHelperClass.MyPASSWORD));
            buffer.append(cid+ "   " + name + "   " + password +" \n");
        }
        return buffer.toString();
    }

    public  int delete(String uname)
    {
        SQLiteDatabase db = DBHelper.getWritableDatabase();
        String[] whereArgs ={uname};

        int count =db.delete(DBHelperClass.TABLE_NAME ,DBHelperClass.NAME+" = ?",whereArgs);
        return  count;
    }

    public int updateName(String oldName , String newName)
    {
        SQLiteDatabase db = DBHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DBHelperClass.NAME,newName);
        String[] whereArgs= {oldName};
        int count =db.update(DBHelperClass.TABLE_NAME,contentValues, DBHelperClass.NAME+" = ?",whereArgs );
        return count;
    }

    static class DBHelperClass extends SQLiteOpenHelper
    {
        private static final String DATABASE_NAME = "DATABASE";    // Database Name
        private static final int DATABASE_Version = 1;    // Database Version

        private static final String CREATE_TABLE_Cwiczenie_info = "CREATE TABLE Cwiczenie_info (\"" +
                "Nazwa_cwiczenia TEXT PRIMARY KEY AUTO INCREMENT," +
                "Opis_cwiczenia TEXT, " +
                "Nazwa_kategorii TEXT\");";

        private static final String CREATE_TABLE_Cwiczenie = "CREATE TABLE Cwiczenie (\"" +
                "ID_Wydarzenia INTEGER ," +
                "Nazwa_cwiczenia TEXT, " +
                "Index_kolejnosci INTEGER," +
                "Ilosc_powtorzen INTEGER," +
                "Obciazenie REAL," +
                "Czas_trwania INTEGER," +
                "Dystans REAL," +
                "FOREIGN KEY(ID_Wydarzenia) REFERENCES Wydarzenie(ID_Wydarzenia)," +
                "FOREIGN KEY(Nazwa_cwiczenia) REFERENCES Cwiczenie_info(Nazwa_cwiczenia)\");";

        private static final String CREATE_TABLE_Wydarzenie = "CREATE TABLE Wydarzenie (\"ID_Wydarzenia INTEGER PRIMARY KEY AUTO INCREMENT," +
                "Data_dnia TEXT," +
                "Nazwa_wydarzenia TEXT," +
                "Opis_wydarzenia TEXT," +
                "Godzina_rozpoczecia TEXT," +
                "Czas_trwania INTEGER," +
                "Czy_cwiczenie BLOB," +
                "FOREIGN KEY(Data_dnia) REFERENCES Dzien(Data_dnia)\");";

        private static final String CREATE_TABLE_Dzien = "CREATE TABLE Dzien (\"" +
                "Data_dnia TEXT PRIMARY KEY, " +
                "Numer_tygodnia INTEGER," +
                "Miesiac INTEGER, " +
                "Rok INTEGER)\");";
        private Context context;

        public DBHelperClass(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_Version);
            this.context=context;
        }

        public void onCreate(SQLiteDatabase db) {

            try {
                db.execSQL(CREATE_TABLE_Cwiczenie_info);
                db.execSQL(CREATE_TABLE_Dzien);
                db.execSQL(CREATE_TABLE_Wydarzenie);
                db.execSQL(CREATE_TABLE_Cwiczenie);
            } catch (Exception e) {
                Message.message(context,""+e);
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            try {
                Message.message(context,"OnUpgrade");
                db.execSQL("DROP TABLE Cwiczenie");
                db.execSQL("DROP TABLE Wydarzenie");
                db.execSQL("DROP TABLE Dzien");
                db.execSQL("DROP TABLE Cwiczenie_info");
                onCreate(db);
            }catch (Exception e) {
                Message.message(context,""+e);
            }
        }
    }
}
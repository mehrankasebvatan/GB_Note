package app.gb.note.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class DataBaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "GB_NOTE.db";
    public static final int DATABASE_VERSION = 1;
    public static final String TABLE_NAME = "notes_tbl";

    public static final String Col1 = "_id";
    public static final String Col2 = "title";
    public static final String Col3 = "text";


    public DataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE " + TABLE_NAME + "("+
                Col1 + " INTEGER PRIMARY KEY, " +
                Col2 + " TEXT , " +
                Col3 + " TEXT )" );




    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);

    }

    public boolean insertNotes(String title, String text){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Col2, title);
        contentValues.put(Col3, text);
        db.insert(TABLE_NAME, null, contentValues);
        return true;

    }

    public Cursor getAllNotes(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        return res;
    }

    public void deleteSingleNote(String id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME,Col1 +"= ?",new String[]{id});
    }


    public boolean updateNote(String noteId, DataNote note){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Col2, note.title);
        contentValues.put(Col3, note.text);
        int res = db.update(TABLE_NAME, contentValues, "_id= ?", new String[]{noteId});
        return res>0;
    }



}

package data;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import models.MyWish;

/**
 * Created by Brenda on 30/10/2017.
 */

public class DatabaseHandler extends SQLiteOpenHelper {
    public DatabaseHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, "wishdb", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //membuat db
        String query = "create table wishes (itemId integer primary key autoincrement, title text, content text, recordDate long);";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists whishes");
        onCreate(db);
    }

    //menambahkan data di db wishes
    public void addWish(MyWish wish){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues val = new ContentValues();
        val.put("title", wish.getTitle());
        val.put("content", wish.getContent());
        val.put("recordDate", System.currentTimeMillis()); //tak coba tanpa java.lang
        db.insert("wishes", null, val);
        db.close();
    }
}

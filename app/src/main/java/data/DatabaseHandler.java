package data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;

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

    //hapus data di db wishes
    public void deleteWish(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("wishes", "itemId=?", new String[]{String.valueOf(id)});
        db.close();
    }

    //update data di db wishes
    public void updateWish(MyWish wish){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues val = new ContentValues();
        val.put("title", wish.getTitle());
        val.put("content", wish.getContent());
        val.put("recordDate", System.currentTimeMillis()); //coba tanpa java.lang
        db.update("wishes", val, "itemId=?", new String[]{String.valueOf(wish.getItemId())});
        db.close();
    }

    //ambil data perItem berdasarkan itemId
    public MyWish getWishById(int id){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from wishes where itemId=?", new String[]{String.valueOf(id)});
        if(cursor != null){
            cursor.moveToFirst();
        }
        //membuat objek untuk menampung data yang dipilih cursor
        MyWish wish = new MyWish();
        wish.setItemId(cursor.getInt(cursor.getColumnIndex("itemId")));
        wish.setTitle(cursor.getString(cursor.getColumnIndex("title")));
        wish.setContent(cursor.getString(cursor.getColumnIndex("content")));
        //mengubah format data tanggal
        DateFormat date = DateFormat.getDateInstance();
        String dataDate = date.format(new Date(cursor.getLong(cursor.getColumnIndex("recordDate"))).getTime());
        wish.setRecordDate(dataDate);
        db.close();
        return wish;
    }

    public ArrayList<MyWish> getAllWish(){
        ArrayList<MyWish> wishList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from wishes order by title asc", null);
        if(cursor.moveToFirst()){
            do{
                MyWish wish = new MyWish();
                wish.setItemId(cursor.getInt(cursor.getColumnIndex("itemId")));
                wish.setTitle(cursor.getString(cursor.getColumnIndex("title")));
                wish.setContent(cursor.getString(cursor.getColumnIndex("content")));
                DateFormat dateFormat = DateFormat.getDateInstance();
                String dataDate = dateFormat.format(new Date(cursor.getLong(cursor.getColumnIndex("recordDate"))).getTime());
                wish.setRecordDate(dataDate);
                wishList.add(wish);
            }while (cursor.moveToNext());
        }
        db.close();
        return wishList;
    }
}

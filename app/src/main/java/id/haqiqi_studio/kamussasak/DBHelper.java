package id.haqiqi_studio.kamussasak;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.media.tv.TvContract;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.Toast;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;

import id.haqiqi_studio.kamussasak.Model.DataModel;

public class DBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "dictionary.db";
    public static final String TABLE_NAME = "word";
    public static final String WORD_COLUMN_ID = "id";
    public static final String WORD_COLUMN_WORD = "word";
    public static final String WORD_COLUMN_MEANING = "meaning";
    public static final String WORD_COLUMN_FORM = "form";
    public static final String WORD_COLUMN_STAT = "stat";
    private HashMap hp;
    SQLiteDatabase db;
    Connection connection;

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub
        db.execSQL(
                "create table word " +
                        "(id integer primary key, word text,meaning text,form text, stat text)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
        db.execSQL("DROP TABLE IF EXISTS word");
        onCreate(db);
    }

    public Cursor getData(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from " + TABLE_NAME + " where id=" + id + "", null);

        return res;
    }

    public int numberOfRows() {
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, TABLE_NAME);
        return numRows;
    }

    public String getPath() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.getPath();
    }

    //region CRUD
    public boolean insertWord(String word, String meaning, String form, String stat) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("word", word);
        contentValues.put("meaning", meaning);
        contentValues.put("form", form);
        contentValues.put("stat", stat);
        db.insert(TABLE_NAME, null, contentValues);
        return true;
    }

    public boolean updateWord(Integer id, String word, String meaning, String form, String stat) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("word", word);
        contentValues.put("meaning", meaning);
        contentValues.put("form", form);
        contentValues.put("stat", stat);
        db.update("wor", contentValues, "id = ? ", new String[]{Integer.toString(id)});
        return true;
    }

    public boolean truncateWord() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, null, null);
        return true;
    }
    //endregion

    public Integer deleteWord(Integer id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("contacts",
                "id = ? ",
                new String[]{Integer.toString(id)});
    }


    public CustomAdapter getAllWords(Context context) {
        ArrayList<DataModel> dataModels = new ArrayList<>();

        db = this.getReadableDatabase();
        Cursor cur = db.rawQuery("select * from " + TABLE_NAME + "", null);
        cur.moveToFirst();

        while (cur.isAfterLast() == false) {
            dataModels.add(new DataModel(cur.getString(cur.getColumnIndex(WORD_COLUMN_WORD)), cur.getString(cur.getColumnIndex(WORD_COLUMN_MEANING)), cur.getString(cur.getColumnIndex(WORD_COLUMN_FORM))));
            cur.moveToNext();
        }
        cur.close();
        CustomAdapter adapter= new CustomAdapter(dataModels, context);

      return adapter;
    }


    public CustomAdapter chooseTransltation(Context context, String field) {
        ArrayList<DataModel> dataModels = new ArrayList<>();

        db = this.getReadableDatabase();
        Cursor cur = db.rawQuery("select * from " + TABLE_NAME + " Order by " + field + " ASC", null);
        cur.moveToFirst();

        while (cur.isAfterLast() == false) {
            if (field.matches("word")) {
                dataModels.add(new DataModel(cur.getString(cur.getColumnIndex(WORD_COLUMN_WORD)), cur.getString(cur.getColumnIndex(WORD_COLUMN_MEANING)), cur.getString(cur.getColumnIndex(WORD_COLUMN_FORM))));
            }
            else {
                dataModels.add(new DataModel(cur.getString(cur.getColumnIndex(WORD_COLUMN_MEANING)), cur.getString(cur.getColumnIndex(WORD_COLUMN_WORD)), cur.getString(cur.getColumnIndex(WORD_COLUMN_FORM))));
            }
            cur.moveToNext();
        }
        cur.close();
        CustomAdapter adapter= new CustomAdapter(dataModels, context);

        return adapter;
    }

    public CustomAdapter filterDataIndoSasak(String txt, Context context, View v1) {
        ArrayList<DataModel> dataModels = new ArrayList<>();
        int count = 0;

        //hp = new HashMap();
        db = this.getReadableDatabase();
        String sql = "SELECT * FROM " + TABLE_NAME + " WHERE " + WORD_COLUMN_WORD + " LIKE '%" + txt + "%'";
        Cursor cur = db.rawQuery(sql, null);

        count = cur.getCount();

        if (count <= 0) {
            v1.setVisibility(View.VISIBLE);
        }
        else {
            v1.setVisibility(View.INVISIBLE);
            cur.moveToFirst();

            while (cur.isAfterLast() == false) {
                dataModels.add(new DataModel(cur.getString(cur.getColumnIndex(WORD_COLUMN_WORD)), cur.getString(cur.getColumnIndex(WORD_COLUMN_MEANING)), cur.getString(cur.getColumnIndex(WORD_COLUMN_FORM))));
                cur.moveToNext();
            }
        }


        cur.close();
        CustomAdapter adapter= new CustomAdapter(dataModels, context);
        return adapter;
    }

    public CustomAdapter filterDataSasakIndo(String txt, Context context, View v1) {
        ArrayList<DataModel> dataModels = new ArrayList<>();
        int count = 0;

        //hp = new HashMap();
        db = this.getReadableDatabase();
        String sql = "SELECT * FROM " + TABLE_NAME + " WHERE " + WORD_COLUMN_MEANING + " LIKE '%" + txt + "%'";
        Cursor cur = db.rawQuery(sql, null);

        count = cur.getCount();

        if (count <= 0) {
            v1.setVisibility(View.VISIBLE);
        }
        else {
            v1.setVisibility(View.INVISIBLE);
            cur.moveToFirst();

            while (cur.isAfterLast() == false) {
                dataModels.add(new DataModel(cur.getString(cur.getColumnIndex(WORD_COLUMN_MEANING)), cur.getString(cur.getColumnIndex(WORD_COLUMN_WORD)), cur.getString(cur.getColumnIndex(WORD_COLUMN_FORM))));
                cur.moveToNext();
            }
        }


        cur.close();
        CustomAdapter adapter= new CustomAdapter(dataModels, context);
        return adapter;
    }

    public int getCount() {
        int count = 0;

        //hp = new HashMap();
        db = this.getReadableDatabase();
        String sql = "SELECT *FROM " + TABLE_NAME + "";
        Cursor cur = db.rawQuery(sql, null);

        count = cur.getCount();
        return count;
    }

    public void getPosition(int pos, Context context, View v) {
        ArrayList<DataModel> dataModels = new ArrayList<>();

        db = this.getReadableDatabase();
        Cursor cur = db.rawQuery("select * from " + TABLE_NAME + "", null);
        cur.moveToFirst();

        while (cur.isAfterLast() == false) {
            dataModels.add(new DataModel(cur.getString(cur.getColumnIndex(WORD_COLUMN_WORD)), cur.getString(cur.getColumnIndex(WORD_COLUMN_MEANING)), cur.getString(cur.getColumnIndex(WORD_COLUMN_FORM))));
            cur.moveToNext();
        }
        cur.close();

        DataModel dataModel= dataModels.get(pos);
        Snackbar.make(v, dataModel.getMeaning() + " ("+dataModel.getForm() + ")" ,Snackbar.LENGTH_LONG)
                .setAction("No action", null).show();
    }


}
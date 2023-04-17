package edu.hebut.dundun.dataBase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MyDataBaseHelper extends SQLiteOpenHelper {

    private String TAG = "MyDataBaseHelper";
    public static final String CREATE_REMIND_TEXT = "create table remind_text (" +
            "id integer primary key autoincrement," +
            "title text," +
            "remindText text )";

    public static final String CREATE_WATER_INTAKE = "create table water_intake(" +
            "id integer primary key autoincrement," +
            "date interger," +
            "value real )";

    private Context mContext;

    public MyDataBaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory,
                            int version) {
        super(context, name, factory, version);
        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_REMIND_TEXT);
        db.execSQL(CREATE_WATER_INTAKE);
        Log.d(TAG, "create table");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}

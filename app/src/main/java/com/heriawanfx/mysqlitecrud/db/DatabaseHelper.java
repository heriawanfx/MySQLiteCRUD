package com.heriawanfx.mysqlitecrud.db;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static String NAMA_DATABASE = "db_catatan";
    private static final int VERSI_DATABASE = 1;
    private static final String QUERY_CREATE_TABLE = String.format("CREATE TABLE %s"
                    + " (%s INTEGER PRIMARY KEY AUTOINCREMENT," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL)",
            DatabaseContract.TABLE_CATATAN,
            DatabaseContract.CatatanColumns._ID,
            DatabaseContract.CatatanColumns.JUDUL,
            DatabaseContract.CatatanColumns.DESKRIPSI,
            DatabaseContract.CatatanColumns.TANGGAL
    );

    //buat constructor
    public DatabaseHelper(Context context){
        super(context, NAMA_DATABASE, null, VERSI_DATABASE);
    }

    //implementasi
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        //menjalankan query create
        sqLiteDatabase.execSQL(QUERY_CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        //dijalankan ketika intall ulang dan update skema database
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + DatabaseContract.TABLE_CATATAN);
        onCreate(sqLiteDatabase);
    }
}

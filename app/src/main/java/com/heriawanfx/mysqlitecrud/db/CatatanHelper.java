package com.heriawanfx.mysqlitecrud.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.heriawanfx.mysqlitecrud.model.Catatan;

import java.util.ArrayList;

import static android.provider.BaseColumns._ID;
import static com.heriawanfx.mysqlitecrud.db.DatabaseContract.CatatanColumns.DESKRIPSI;
import static com.heriawanfx.mysqlitecrud.db.DatabaseContract.CatatanColumns.JUDUL;
import static com.heriawanfx.mysqlitecrud.db.DatabaseContract.CatatanColumns.TANGGAL;
import static com.heriawanfx.mysqlitecrud.db.DatabaseContract.TABLE_CATATAN;

public class CatatanHelper {
    private static String TABLE_DATABASE = TABLE_CATATAN;
    private Context context;
    private DatabaseHelper dataBaseHelper;

    private SQLiteDatabase database;

    public CatatanHelper(Context context){
        this.context = context;
    }

    public CatatanHelper open() throws SQLException {
        dataBaseHelper = new DatabaseHelper(context);
        database = dataBaseHelper.getWritableDatabase();
        return this;
    }

    public void close(){
        dataBaseHelper.close();
    }

    public ArrayList<Catatan> query(){
        ArrayList<Catatan> arrayList = new ArrayList<Catatan>();
        Cursor cursor = database.query(TABLE_DATABASE,null,null,null,null,null,_ID +" DESC",null);
        cursor.moveToFirst();
        Catatan mCatatan;
        if (cursor.getCount()>0) {
            do {

                mCatatan = new Catatan();
                mCatatan.setId(cursor.getInt(cursor.getColumnIndexOrThrow(_ID)));
                mCatatan.setJudul(cursor.getString(cursor.getColumnIndexOrThrow(JUDUL)));
                mCatatan.setDeskrsipsi(cursor.getString(cursor.getColumnIndexOrThrow(DESKRIPSI)));
                mCatatan.setTanggal(cursor.getString(cursor.getColumnIndexOrThrow(TANGGAL)));

                arrayList.add(mCatatan);
                cursor.moveToNext();

            } while (!cursor.isAfterLast());
        }
        cursor.close();
        return arrayList;
    }

    //fungsi insert
    public long insert(Catatan mCatatan){
        ContentValues initialValues =  new ContentValues();
        initialValues.put(JUDUL, mCatatan.getJudul());
        initialValues.put(DESKRIPSI, mCatatan.getDeskrsipsi());
        initialValues.put(TANGGAL, mCatatan.getTanggal());
        return database.insert(TABLE_DATABASE, null, initialValues);
    }

    //fungsi update
    public int update(Catatan mCatatan){
        ContentValues args = new ContentValues();
        args.put(JUDUL, mCatatan.getJudul());
        args.put(DESKRIPSI, mCatatan.getDeskrsipsi());
        args.put(TANGGAL, mCatatan.getTanggal());
        return database.update(TABLE_DATABASE, args, _ID + "= '" + mCatatan.getId() + "'", null);
    }

    //fungsi delete
    public int delete(int id){
        return database.delete(TABLE_CATATAN, _ID + " = '"+id+"'", null);
    }
}

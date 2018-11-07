package com.heriawanfx.mysqlitecrud.db;

import android.provider.BaseColumns;

public class DatabaseContract {

    static String TABLE_CATATAN = "tbl_catatan";
    static final class CatatanColumns implements BaseColumns {

        static String JUDUL = "judul";
        static String DESKRIPSI = "deskripsi";
        static String TANGGAL = "tanggal";
    }
}

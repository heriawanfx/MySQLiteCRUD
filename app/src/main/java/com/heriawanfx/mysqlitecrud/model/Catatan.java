package com.heriawanfx.mysqlitecrud.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Catatan implements Parcelable {
    private int id;
    private String judul;
    private String deskrsipsi;
    private String tanggal;

    //buat constructor kosong
    public Catatan() {
    }

    //rubah dari protected ke public
    public Catatan(Parcel in) {
        id = in.readInt();
        judul = in.readString();
        deskrsipsi = in.readString();
        tanggal = in.readString();
    }

    public static final Creator<Catatan> CREATOR = new Creator<Catatan>() {
        @Override
        public Catatan createFromParcel(Parcel in) {
            return new Catatan(in);
        }

        @Override
        public Catatan[] newArray(int size) {
            return new Catatan[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getJudul() {
        return judul;
    }

    public void setJudul(String judul) {
        this.judul = judul;
    }

    public String getDeskrsipsi() {
        return deskrsipsi;
    }

    public void setDeskrsipsi(String deskrsipsi) {
        this.deskrsipsi = deskrsipsi;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }


    //implementasi
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(judul);
        parcel.writeString(deskrsipsi);
        parcel.writeString(tanggal);
    }
}

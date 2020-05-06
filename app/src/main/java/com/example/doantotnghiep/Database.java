package com.example.doantotnghiep;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.doantotnghiep.model.ArrayVi;

import java.util.ArrayList;

public class Database extends SQLiteOpenHelper {
    private SQLiteDatabase data;

    public Database(@Nullable Context context) {
        super(context, "data", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            db.execSQL("create table if not exists tbltaikhoan(tentaikhoan text primary key, masobimat text, matkhau text);");
//            db.execSQL("create table if not exists tblphannhom(manhom int primary key, tennhom text, tenkhoan text, " +
//                    "mataikhoan text constraint mataikhoan references tbltaikhoan(mataikhoan) on delete cascade);");
//            db.execSQL("create table if not exists tblthuchi(mathuchi int primary key, loaitaikhoan text, sotien int, ngay date, " +
//                    "tuan int, manhom int constraint manhom references tblphannhom(manhom) on delete cascade);");
//            db.execSQL("create table if not exists tblgiaodich(magiaodich int primary key, lydo text, trangthai text, gio time, " +
//                    "mathuchi int constraint mathuchi references tblthuchi(mathuchi) on delete cascade);");
            db.execSQL("create table if not exists tblvi(mavi int primary key, tenvi text, motavi text, sotien text);");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists tblvi");
        db.execSQL("drop table if exists tbltaikhoan");
        onCreate(db);
    }

    public ArrayList<ArrayVi> LayDanhSachVi() {
        ArrayList<ArrayVi> danhsachvi = new ArrayList<ArrayVi>();
        SQLiteDatabase database = this.getReadableDatabase();
        String query = "select * from tblvi";
        Cursor cursor = database.rawQuery(query, null);
        if (cursor != null)
            cursor.moveToFirst();
        while (cursor.isAfterLast() == false) {
            danhsachvi.add(new ArrayVi(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3)));
            cursor.moveToNext();
        }
        cursor.close();
        database.close();
        return danhsachvi;
    }
}

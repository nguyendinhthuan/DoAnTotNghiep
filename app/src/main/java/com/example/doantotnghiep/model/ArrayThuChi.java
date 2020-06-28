package com.example.doantotnghiep.model;

public class ArrayThuChi  {
    public String thoigian, danhmucthuchi, vi, loaikhoan, mota;
    public int ma;
    public Double tien;

    public ArrayThuChi(String thoigian, String danhmucthuchi, String vi, Double tien, int ma, String loaikhoan, String mota) {
        this.thoigian = thoigian;
        this.danhmucthuchi = danhmucthuchi;
        this.vi = vi;
        this.tien = tien;
        this.ma = ma;
        this.loaikhoan = loaikhoan;
        this.mota = mota;
    }
}

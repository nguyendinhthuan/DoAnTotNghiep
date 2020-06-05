package com.example.doantotnghiep.model;

public class ArrayThuChi  {
    public String time, danhmucthuchi, vi, loaikhoan;
    public int ma;
    public Double tien;

    public ArrayThuChi(String time, String danhmucthuchi, String vi, Double tien, int ma, String loaikhoan) {
        this.time = time;
        this.danhmucthuchi = danhmucthuchi;
        this.vi = vi;
        this.tien = tien;
        this.ma = ma;
        this.loaikhoan = loaikhoan;
    }
}

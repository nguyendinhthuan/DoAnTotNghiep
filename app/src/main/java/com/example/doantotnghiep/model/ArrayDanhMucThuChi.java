package com.example.doantotnghiep.model;

public class ArrayDanhMucThuChi {
    public int madanhmuc;
    public String tendanhmuc;
    public String loaikhoan;

    public ArrayDanhMucThuChi(int madanhmuc, String tendanhmuc, String loaikhoan) {
        this.madanhmuc = madanhmuc;
        this.tendanhmuc = tendanhmuc;
        this.loaikhoan = loaikhoan;
    }

    public ArrayDanhMucThuChi() {

    }

    public int getMadanhmuc() {
        return madanhmuc;
    }

    public void setMadanhmuc(int madanhmuc) {
        this.madanhmuc = madanhmuc;
    }

    public String getTendanhmuc() {
        return tendanhmuc;
    }

    public void setTendanhmuc(String tendanhmuc) {
        this.tendanhmuc = tendanhmuc;
    }

    public String getLoaikhoan() {
        return loaikhoan;
    }

    public void setLoaikhoan(String loaikhoan) {
        this.loaikhoan = loaikhoan;
    }
}

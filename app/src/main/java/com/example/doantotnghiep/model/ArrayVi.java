package com.example.doantotnghiep.model;

public class ArrayVi {
    public int mavi;
    public String tenvi;
    public String motavi;
    public String sotien;

    public ArrayVi(int mavi, String tenvi, String motavi, String sotien) {
        this.mavi = mavi;
        this.tenvi = tenvi;
        this.motavi = motavi;
        this.sotien = sotien;
    }

    public int getMavi() {
        return mavi;
    }

    public void setMavi(int mavi) {
        this.mavi = mavi;
    }

    public String getTenvi() {
        return tenvi;
    }

    public void setTenvi(String tenvi) {
        this.tenvi = tenvi;
    }

    public String getMotavi() {
        return motavi;
    }

    public void setMotavi(String motavi) {
        this.motavi = motavi;
    }

    public String getSotien() {
        return sotien;
    }

    public void setSotien(String sotien) {
        this.sotien = sotien;
    }

    @Override
    public String toString() {
        return "ArrayVi{" +
                "mavi=" + mavi +
                ", tenvi='" + tenvi + '\'' +
                ", motavi='" + motavi + '\'' +
                ", sotien='" + sotien + '\'' +
                '}';
    }
}

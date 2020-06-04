package com.example.doantotnghiep.model;

public class ArrayVi {
    public int mavi;
    public String tenvi;
    public String motavi;
    public Double sotienvi, sodu;

    public ArrayVi(int mavi, String tenvi, String motavi, Double sotienvi, Double sodu) {
        this.mavi = mavi;
        this.tenvi = tenvi;
        this.motavi = motavi;
        this.sotienvi = sotienvi;
        this.sodu = sodu;
    }

    public ArrayVi() {

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

    public Double getSotienvi() {
        return sotienvi;
    }

    public void setSotienvi(Double sotien) {
        this.sotienvi = sotien;
    }

    public Double getSodu() {
        return sodu;
    }

    public void setSodu(Double sodu) {
        this.sodu = sodu;
    }

    @Override
    public String toString() {
        return "ArrayVi{" +
                "mavi=" + mavi +
                ", tenvi='" + tenvi + '\'' +
                ", motavi='" + motavi + '\'' +
                ", sotienvi=" + sotienvi +
                ", sodu=" + sodu +
                '}';
    }
}

package com.example.doantotnghiep.model;

public class ArrayKeHoachTietKiem {
    public String tenkehoachtietkiem, ngaybatdaukehoachtietkiem, ngayketthuckehoachtietkiem;
    public Double sotienkehoachtietkiem, sotiendatietkiem;
    public int makehoachtietkiem;
    public String trangthai;

    public ArrayKeHoachTietKiem(String tenkehoachtietkiem, String ngaybatdaukehoachtietkiem, String ngayketthuckehoachtietkiem, Double sotienkehoachtietkiem, Double sotiendatietkiem, int makehoachtietkiem, String trangthai) {
        this.tenkehoachtietkiem = tenkehoachtietkiem;
        this.ngaybatdaukehoachtietkiem = ngaybatdaukehoachtietkiem;
        this.ngayketthuckehoachtietkiem = ngayketthuckehoachtietkiem;
        this.sotienkehoachtietkiem = sotienkehoachtietkiem;
        this.sotiendatietkiem = sotiendatietkiem;
        this.makehoachtietkiem = makehoachtietkiem;
        this.trangthai = trangthai;
    }
}

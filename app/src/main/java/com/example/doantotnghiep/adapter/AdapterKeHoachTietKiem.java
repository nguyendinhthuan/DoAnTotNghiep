package com.example.doantotnghiep.adapter;

import android.app.Activity;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.doantotnghiep.R;
import com.example.doantotnghiep.model.ArrayKeHoachTietKiem;
import com.example.doantotnghiep.model.ArrayThuChi;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

public class AdapterKeHoachTietKiem extends ArrayAdapter<ArrayKeHoachTietKiem> {
    private Activity a;
    private int id;
    private ArrayList<ArrayKeHoachTietKiem> arr;
    private TextView tenkehoachtietkiem, ngaybatdaukehoachtietkiem, ngayketthuckehoachtietkiem, sotienkehoachtietkiem, trangthai, sotiendatietkiem;

    public AdapterKeHoachTietKiem(Activity context, int resource, ArrayList<ArrayKeHoachTietKiem> objects) {
        super(context, resource, objects);
        this.a = context;
        this.id = resource;
        this.arr = objects;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater in = a.getLayoutInflater();
        view = in.inflate(id, null);
        if (arr.size()>0 && position>=0) {
            tenkehoachtietkiem = (TextView) view.findViewById(R.id.txtTenKeHoachTietKiem);
            ngayketthuckehoachtietkiem = (TextView) view.findViewById(R.id.txtNgayKetThucKeHoachTietKiem);
            ngaybatdaukehoachtietkiem = (TextView) view.findViewById(R.id.txtNgayBatDauKeHoachTietKiem);
            sotienkehoachtietkiem = (TextView) view.findViewById(R.id.txtSoTienKeHoachTietKiem);
            trangthai = (TextView) view.findViewById(R.id.txtTrangThaiKeHoachTietKiem);
            sotiendatietkiem = (TextView) view.findViewById(R.id.txtSoTienDaTietKiem);

            tenkehoachtietkiem.setText(arr.get(position).tenkehoachtietkiem);
            ngayketthuckehoachtietkiem.setText(arr.get(position).ngayketthuckehoachtietkiem);
            ngaybatdaukehoachtietkiem.setText(arr.get(position).ngaybatdaukehoachtietkiem);
            sotienkehoachtietkiem.setText(DoiSoSangTien(arr.get(position).sotienkehoachtietkiem));
            trangthai.setText(arr.get(position).trangthai);
            trangthai.setTextColor(Color.BLUE);
            sotiendatietkiem.setText(DoiSoSangTien(arr.get(position).sotiendatietkiem));
        }
        return view;
    }

    public static String DoiSoSangTien(Double so) {
        DecimalFormat decimalFormat = (DecimalFormat) NumberFormat.getInstance(Locale.US);
        decimalFormat.applyPattern("#,###,###,###");
        return decimalFormat.format((so)) + " đ";
    }
}

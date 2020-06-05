package com.example.doantotnghiep.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.doantotnghiep.R;
import com.example.doantotnghiep.model.ArrayThuChi;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

public class AdapterThuChi extends ArrayAdapter<ArrayThuChi> {
    private Activity a;
    private int id;
    private ArrayList<ArrayThuChi> arr;
    private TextView date, tien, danhmucthuchi, vi, loaikhoan;

    public AdapterThuChi(Activity context, int resource, ArrayList<ArrayThuChi> objects) {
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
            date = (TextView) view.findViewById(R.id.txtNgay);
            tien = (TextView) view.findViewById(R.id.txtTien);
            danhmucthuchi = (TextView) view.findViewById(R.id.txtDanhMucThuChi);
            vi = (TextView) view.findViewById(R.id.txtVi);
            loaikhoan = (TextView) view.findViewById(R.id.txtLoaiKhoan);

            date.setText(""+ arr.get(position).time);
            tien.setText(DoiSoSangTien(arr.get(position).tien));
            danhmucthuchi.setText(arr.get(position).danhmucthuchi);
            vi.setText(arr.get(position).vi);
            loaikhoan.setText(arr.get(position).loaikhoan + ":");
        }
        return view;
    }

    public static String DoiSoSangTien(Double so) {
        DecimalFormat decimalFormat = (DecimalFormat) NumberFormat.getInstance(Locale.US);
        decimalFormat.applyPattern("#,###,###,###");
        return decimalFormat.format((so)) + " đ";
    }
}

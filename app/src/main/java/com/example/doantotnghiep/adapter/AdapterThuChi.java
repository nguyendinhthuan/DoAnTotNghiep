package com.example.doantotnghiep.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.doantotnghiep.R;
import com.example.doantotnghiep.model.ArrayThuChi;

import org.w3c.dom.Text;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

public class AdapterThuChi extends ArrayAdapter<ArrayThuChi> {
    private Activity a;
    private int id;
    private ArrayList<ArrayThuChi> arr;
    private TextView ngay, tien, danhmucthuchi, vi, loaikhoan, mota;

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
            ngay = (TextView) view.findViewById(R.id.txtNgayThuChi);
            tien = (TextView) view.findViewById(R.id.txtTienThuChi);
            danhmucthuchi = (TextView) view.findViewById(R.id.txtDanhMucThuChi);
            vi = (TextView) view.findViewById(R.id.txtViThuChi);
            loaikhoan = (TextView) view.findViewById(R.id.txtLoaiKhoanThuChi);
            mota = (TextView) view.findViewById(R.id.txtMoTaThuChi);

            ngay.setText(""+ arr.get(position).thoigian);
            tien.setText(DoiSoSangTien(arr.get(position).tien));
            danhmucthuchi.setText(arr.get(position).danhmucthuchi);
            vi.setText(arr.get(position).vi);
            loaikhoan.setText(arr.get(position).loaikhoan + ":");
            mota.setText(arr.get(position).mota);
        }
        return view;
    }

    public static String DoiSoSangTien(Double so) {
        DecimalFormat decimalFormat = (DecimalFormat) NumberFormat.getInstance(Locale.US);
        decimalFormat.applyPattern("#,###,###,###");
        return decimalFormat.format((so)) + " đ";
    }
}

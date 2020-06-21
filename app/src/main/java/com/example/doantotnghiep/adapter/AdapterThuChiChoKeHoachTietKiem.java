package com.example.doantotnghiep.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.doantotnghiep.R;
import com.example.doantotnghiep.model.ArrayLichSuChuyenTien;
import com.example.doantotnghiep.model.ArrayThuChiChoKeHoachTietKiem;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

public class AdapterThuChiChoKeHoachTietKiem extends ArrayAdapter<ArrayThuChiChoKeHoachTietKiem> {
    private Activity a;
    private int id;
    private ArrayList<ArrayThuChiChoKeHoachTietKiem> arr;
    private TextView ngaythuchienthuchichokehoach, sotienthuchichokehoach, loaithuchichokehoach, motathuchichokehoach;

    public AdapterThuChiChoKeHoachTietKiem(Activity context, int resource, ArrayList<ArrayThuChiChoKeHoachTietKiem> objects) {
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
             ngaythuchienthuchichokehoach = (TextView) view.findViewById(R.id.txtNgayThucHienThuChiChoKeHoach);
             sotienthuchichokehoach = (TextView) view.findViewById(R.id.txtSoTienThuChiChoKeHoachTietKiem);
             loaithuchichokehoach = (TextView) view.findViewById(R.id.txtLoaiThuChiChoKeHoachTietKiem);
             motathuchichokehoach = (TextView) view.findViewById(R.id.txtMoTaThuChiChoKeHoach);

            ngaythuchienthuchichokehoach.setText(""+ arr.get(position).ngaythuchienthuchichokehoach);
            sotienthuchichokehoach.setText(DoiSoSangTien(arr.get(position).sotienthuchichokehoach));
            loaithuchichokehoach.setText(arr.get(position).loaithuchichokehoach);
            motathuchichokehoach.setText(arr.get(position).motathuchichokehoach);
        }
        return view;
    }

    public static String DoiSoSangTien(Double so) {
        DecimalFormat decimalFormat = (DecimalFormat) NumberFormat.getInstance(Locale.US);
        decimalFormat.applyPattern("#,###,###,###");
        return decimalFormat.format((so)) + " đ";
    }
}

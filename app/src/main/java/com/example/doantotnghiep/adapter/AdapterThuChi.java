package com.example.doantotnghiep.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.doantotnghiep.R;
import com.example.doantotnghiep.model.ArrayThuChi;

import java.util.ArrayList;

public class AdapterThuChi extends ArrayAdapter<ArrayThuChi> {
    private Activity a;
    private int id;
    private ArrayList<ArrayThuChi> arr;
    private TextView date, tien, danhmucthuchi, vi;

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
            date.setText(" " + arr.get(position).time);
            tien.setText(""+arr.get(position).tien);
            danhmucthuchi.setText(arr.get(position).danhmucthuchi);
            vi.setText(arr.get(position).vi);
        }
        return view;
    }
}

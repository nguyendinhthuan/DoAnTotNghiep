package com.example.doantotnghiep.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.doantotnghiep.R;
import com.example.doantotnghiep.model.ArrayVi;

import java.util.ArrayList;

public class AdapterVi extends ArrayAdapter<ArrayVi> {
    private Activity activity;
    private int mavi;
    private ArrayList<ArrayVi> arr;
    private TextView textView_TenVi, textView_SoTien;

    public AdapterVi(@NonNull Context context, int resource, @NonNull ArrayList<ArrayVi> objects) {
        super(context, resource, objects);
        this.activity = (Activity) context;
        this.mavi = resource;
        this.arr = objects;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater in = activity.getLayoutInflater();
        view = in.inflate(mavi, null);
        if (arr.size() > 0 && position >= 0) {
            textView_TenVi = (TextView) view.findViewById(R.id.textView_TenVi);
            textView_SoTien = (TextView) view.findViewById(R.id.textView_SoTien);
            textView_TenVi.setText(arr.get(position).tenvi);
            textView_SoTien.setText(arr.get(position).sotien);
        }
        return view;
    }
}

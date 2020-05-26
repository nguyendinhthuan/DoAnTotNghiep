package com.example.doantotnghiep.ui.ThongKeThuChi;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.Image;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;

import com.example.doantotnghiep.HomeActivity;
import com.example.doantotnghiep.R;
import com.example.doantotnghiep.ThuChiActivity;
import com.example.doantotnghiep.adapter.AdapterThongKe;
import com.example.doantotnghiep.adapter.PagerAdapter;
import com.example.doantotnghiep.model.ArrayThongKe;
import com.google.android.material.tabs.TabLayout;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Calendar;

public class ThongKeThuChiFragment extends Fragment {
    View myFragment;
    private Activity activity;
    private SQLiteDatabase data;
    private Animation animation;
    private ThongKeThuChiViewModel quanLyThuChiViewModel;
    private String taikhoan;
    private Spinner spinner_LichSuThuChi;
    private String ngaythang;
    private Calendar today;
    private int thang, nam;
    private ArrayList<ArrayThongKe> arrthu, arrchi;
    private String[] arrSpinner, arrGroup;
    private ArrayAdapter<String> adapterSpinner;
    private ExpandableListView listView_ThongKeThuChi;
    private AdapterThongKe adapterThongKe;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        myFragment = inflater.inflate(R.layout.fragment_thongkethuchi, container, false);
        return myFragment;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        activity = getActivity();
        data = activity.openOrCreateDatabase("data.db", activity.MODE_PRIVATE, null);
        animation = AnimationUtils.loadAnimation(getActivity(), R.anim.animation_edittext);

        AnhXa();
        setSpinner();
        setListview();
        LocCoSoDuLieu();
    }

    public void AnhXa() {
        spinner_LichSuThuChi = (Spinner) myFragment.findViewById(R.id.spinner_LichSuThuChi);

        listView_ThongKeThuChi = (ExpandableListView) myFragment.findViewById(R.id.listView_ThongKeThuChi);
    }

    public void setListview() {
        arrGroup = getResources().getStringArray(R.array.thongketong);
        arrthu = new ArrayList<ArrayThongKe>();
        arrchi = new ArrayList<ArrayThongKe>();
        adapterThongKe = new AdapterThongKe(this.activity, arrGroup, arrthu, arrchi);
        listView_ThongKeThuChi.setAdapter(adapterThongKe);
    }

    public void setSpinner() {
        arrSpinner = getResources().getStringArray(R.array.chonloailichsuthuchi);
        adapterSpinner = new ArrayAdapter<String>(this.activity, android.R.layout.simple_spinner_item, arrSpinner);
        adapterSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_LichSuThuChi.setAdapter(adapterSpinner);
        spinner_LichSuThuChi.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //LocTheoLuaChon(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void LocTheoLuaChon(int position) {
        switch (position) {
            case 0:
                //setToday();
                break;
        }
    }

    public void setToday() {
        ngaythang = today.get(Calendar.DAY_OF_MONTH) + "/" + thang +"/" + nam;
        //LocCoSoDuLieu(ngaythang, "is not null");
    }

    public void LocCoSoDuLieu() {
        arrthu.clear();
        arrchi.clear();
        Cursor cursor = data.rawQuery("select tendanhmuc, sum(sotienthuchi) as tien, loaikhoan, ngaythuchien " +
                " from tblthuchi inner join tbldanhmucthuchi on tblthuchi.madanhmuc = tbldanhmucthuchi.madanhmuc " +
                " group by tbldanhmucthuchi.madanhmuc", null);
        cursor.moveToFirst();
        while (cursor.isAfterLast() == false) {
            if (cursor.getString(cursor.getColumnIndex("loaikhoan")).equals("Khoản thu")) {
                arrthu.add(new ArrayThongKe(cursor.getString(cursor.getColumnIndex("tendanhmuc")), cursor.getInt(cursor.getColumnIndex("tien"))));
            } else {
                arrchi.add(new ArrayThongKe(cursor.getString(cursor.getColumnIndex("tendanhmuc")), cursor.getInt(cursor.getColumnIndex("tien"))));
            }
            cursor.moveToNext();
        }
        adapterThongKe.notifyDataSetChanged();
    }
}
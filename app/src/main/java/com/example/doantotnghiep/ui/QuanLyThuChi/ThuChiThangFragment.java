package com.example.doantotnghiep.ui.QuanLyThuChi;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.doantotnghiep.R;
import com.example.doantotnghiep.ThuChiActivity;
import com.example.doantotnghiep.adapter.AdapterThongKe;
import com.example.doantotnghiep.model.ArrayThongKe;

import java.util.ArrayList;
import java.util.Calendar;

public class ThuChiThangFragment extends Fragment {
    private View myFragment;
    private ImageButton imageButton_ThemThuChiThang;
    private Activity a;
    private String[] arrtong;
    private ArrayList<ArrayThongKe> arrthu, arrchi;
    private ExpandableListView listView;
    private AdapterThongKe adapterlistview;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        myFragment = inflater.inflate(R.layout.fragment_thu_chi_thang, container, false);
        return myFragment;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        a = getActivity();

        setListView();
        AnhXa();
        ThemThuChi();
    }

    public void AnhXa() {
        imageButton_ThemThuChiThang = (ImageButton) myFragment.findViewById(R.id.imageButton_ThemThuChiThang);
    }

    public void setListView() {
        arrtong = getResources().getStringArray(R.array.thongketong);
        arrthu = new ArrayList<ArrayThongKe>();
        arrchi = new ArrayList<ArrayThongKe>();
        listView = (ExpandableListView)a.findViewById(R.id.lvThongKeThang);
        adapterlistview = new AdapterThongKe(this.a, arrtong, arrthu, arrchi);
        listView.setAdapter(adapterlistview);
    }

    public void ThemThuChi() {
        imageButton_ThemThuChiThang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), ThuChiActivity.class);
                startActivity(i);
            }
        });
    }

}

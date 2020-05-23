package com.example.doantotnghiep.ui.QuanLyThuChi;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
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
import android.widget.ListView;
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
import com.example.doantotnghiep.adapter.AdapterThuChi;
import com.example.doantotnghiep.adapter.PagerAdapter;
import com.example.doantotnghiep.model.ArrayThongKe;
import com.example.doantotnghiep.model.ArrayThuChi;
import com.google.android.material.tabs.TabLayout;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class QuanLyThuChiFragment extends Fragment {
    View myFragment;
    private Activity activity;
    private SQLiteDatabase data;
    private Animation animation;
    private ImageButton imageButton_ThemThuChi;
    private Button button_ReloadThuChi;
    private QuanLyThuChiViewModel quanLyThuChiViewModel;
    private String taikhoan;
    private Spinner spinner_LichSuThuChi;
    private String ngaythang;
    private Calendar today;
    private int thang, nam;
    private ArrayList<ArrayThongKe> arrthu, arrchi;
    private String[] arrSpinner, arrGroup;
    private ArrayAdapter<String> adapterSpinner;
    private ListView listView_LichSuThuChi;
    private AdapterThongKe adapterThongKe;
    private ArrayList<ArrayThuChi> arr;
    private AdapterThuChi adapterThuChi;
    private SharedPreferences sharedPreferences;
    private List<ArrayThuChi> list = null;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        myFragment = inflater.inflate(R.layout.fragment_quanlythuchi, container, false);
        return myFragment;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        activity = getActivity();
        data = activity.openOrCreateDatabase("data.db", activity.MODE_PRIVATE, null);
        animation = AnimationUtils.loadAnimation(getActivity(), R.anim.animation_edittext);

        sharedPreferences = getActivity().getSharedPreferences("tendangnhap", Context.MODE_PRIVATE);
        taikhoan = sharedPreferences.getString("taikhoancanchuyen","khong tim thay");

        AnhXa();
        ThemThuChi();
//        setSpinner();
        setListview();
//        LocCoSoDuLieu();
        TaiDanhSachThuChi();
        LocCoSoDuLieu();
        XoaThuChi();
    }

    public void AnhXa() {
        imageButton_ThemThuChi = (ImageButton) myFragment.findViewById(R.id.imageButton_ThemThuChi);

        button_ReloadThuChi = (Button) myFragment.findViewById(R.id.button_ReloadThuChi);


        //spinner_LichSuThuChi = (Spinner) myFragment.findViewById(R.id.spinner_LichSuThuChi);

        listView_LichSuThuChi = (ListView) myFragment.findViewById(R.id.listView_LichSuThuChi);
    }

    public void TaiDanhSachThuChi() {
        button_ReloadThuChi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LocCoSoDuLieu();
                Toast.makeText(activity, "Tải lịch sử thu chi thành công", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void ThemThuChi() {
        imageButton_ThemThuChi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(), ThuChiActivity.class);
                startActivity(i);
            }
        });
    }

    public void setListview() {
        arr = new ArrayList<ArrayThuChi>();
        adapterThuChi = new AdapterThuChi(getActivity(), R.layout.activity_thuchi_item, arr);
        listView_LichSuThuChi.setAdapter(adapterThuChi);
    }
//
//    public void setSpinner() {
//        arrSpinner = getResources().getStringArray(R.array.chonloailichsuthuchi);
//        adapterSpinner = new ArrayAdapter<String>(this.activity, android.R.layout.simple_spinner_item, arrSpinner);
//        adapterSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        spinner_LichSuThuChi.setAdapter(adapterSpinner);
//        spinner_LichSuThuChi.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                //LocTheoLuaChon(position);
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });
//    }
//
//    public void LocTheoLuaChon(int position) {
//        switch (position) {
//            case 0:
//                //setToday();
//                break;
//        }
//    }
//
//    public void setToday() {
//        ngaythang = today.get(Calendar.DAY_OF_MONTH) + "/" + thang +"/" + nam;
//        //LocCoSoDuLieu(ngaythang, "is not null");
//    }

    public void LocCoSoDuLieu() {
        arr.clear();
        Cursor cursor = data.rawQuery("select mathuchi, ngaythuchien, sotienthuchi, tendanhmuc, tenvi " +
                " from tblthuchi inner join tbldanhmucthuchi on tblthuchi.madanhmuc = tbldanhmucthuchi.madanhmuc " +
                " inner join tblvi on tbldanhmucthuchi.tentaikhoan = tblvi.tentaikhoan " +
                " where tbldanhmucthuchi.tentaikhoan = '" + taikhoan + "'", null);
        cursor.moveToFirst();
        while (cursor.isAfterLast() == false) {
            arr.add(new ArrayThuChi(cursor.getString(cursor.getColumnIndex("ngaythuchien")), cursor.getString(cursor.getColumnIndex("tendanhmuc")), cursor.getString(cursor.getColumnIndex("tenvi")), cursor.getInt(cursor.getColumnIndex("sotienthuchi")), cursor.getInt(cursor.getColumnIndex("mathuchi"))));
            cursor.moveToNext();
        }
        cursor.close();
        adapterThuChi.notifyDataSetChanged();
    }

    public void XoaThuChi() {
        listView_LichSuThuChi.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                HamXoaThuChi(position);
                return false;
            }
        });
    }

    public void HamXoaThuChi(final int mathuchi) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.Theme_AppCompat_DayNight_Dialog_Alert);
        builder.setTitle("Thông báo !");
        builder.setMessage("Bạn có chắc chắn muốn xóa khoản này ?");
        builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                data.rawQuery("delete from tblthuchi where mathuchi = '" + arr.get(mathuchi).ma + "'", null).moveToFirst();
                LocCoSoDuLieu();
                Toast.makeText(activity, "Xóa thành công", Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();
    }
}
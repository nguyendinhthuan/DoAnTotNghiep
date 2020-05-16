package com.example.doantotnghiep;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.example.doantotnghiep.adapter.AdapterDanhMucThuChi;
import com.example.doantotnghiep.adapter.AdapterVi;
import com.example.doantotnghiep.model.ArrayDanhMucThuChi;
import com.example.doantotnghiep.model.ArrayVi;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ThuChiActivity extends AppCompatActivity {
    private Button button_ThoatThuChi, button_LuuThuChi, button_NgayThuChi, button_GioThuChi, button_ThemViNong;
    private Button button_ThoiGianHienTai;
    private Spinner spinner_LoaiThuChi, spinner_Vi, spinner_DanhMuc;
    private Calendar today;
    private String gio;
    private Date date;
    private SimpleDateFormat simpleDateFormat;
    private String[] arrSpinner;
    private ArrayAdapter<String> adapterSpinner, adapterVi, adapterDanhMuc;
    private ArrayList<Integer> arrMaVi, arrMaDanhMuc;
    private ArrayList<String> arrTenVi, arrTenDanhMuc;
    private Cursor cursor;
    private SQLiteDatabase data;
    private DatePicker datePicker;
    private List<ArrayVi> list = null;
    private AdapterVi adapterVi1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thu_chi);

        simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        date = new Date();

        AnhXa();
        ThemMoiThuChi();
        ThoatThemThuChi();
        HienThiThoiGian();
        ThemVi();
        LoadSpinner();
        //LoadDanhSachViLenSpinner();
    }

    public void AnhXa() {
        button_ThoatThuChi = (Button) findViewById(R.id.button_ThoatThuChi);
        button_LuuThuChi = (Button) findViewById(R.id.button_LuuThuChi);
        button_ThemViNong = (Button) findViewById(R.id.button_ThemViNong);

        button_NgayThuChi = (Button) findViewById(R.id.button_NgayThuChi);
        button_GioThuChi = (Button) findViewById(R.id.button_GioThuchi);

        button_ThoiGianHienTai = (Button) findViewById(R.id.button_ThoiGianHienTai);

        spinner_LoaiThuChi = (Spinner) findViewById(R.id.spinner_LoaiThuChi);
        spinner_Vi = (Spinner) findViewById(R.id.spinner_Vi);
        spinner_DanhMuc = (Spinner) findViewById(R.id.spinner_DanhMuc);

        today = Calendar.getInstance();
    }

    public void ThoatThemThuChi() {
        button_ThoatThuChi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    public void ThemMoiThuChi() {
        button_LuuThuChi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ThemThuChi();
            }
        });
    }

    public void ThemThuChi() {

    }

    public void HienThiThoiGian() {
        int thang = today.get(Calendar.MONTH) + 1;
        gio = today.get(Calendar.HOUR_OF_DAY) + ":"+ today.get(Calendar.MINUTE) + ":" +today.get(Calendar.SECOND);
        date = today.getTime();
        button_NgayThuChi.setText(simpleDateFormat.format(date));
        button_GioThuChi.setText(gio);
    }

    public void ChonNgay(View v) {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.activity_chonngay);
        dialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        dialog.show();

        final DatePicker datePicker = (DatePicker) dialog.findViewById(R.id.datePicker);
        Button button_ChonNgayXong = (Button) dialog.findViewById(R.id.button_ChonNgayXong);
        button_ChonNgayXong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    date = simpleDateFormat.parse(datePicker.getDayOfMonth() + "/" +(datePicker.getMonth() + 1) + "/" + datePicker.getYear());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                button_NgayThuChi.setText(simpleDateFormat.format(date));
                dialog.cancel();
            }
        });
    }

    public void LayNgayHienTai(View v) {
        HienThiThoiGian();
    }

    public void LoadSpinner() {
        //Spinner Loai thu chi
        arrSpinner = getResources().getStringArray(R.array.loaithuchi);
        adapterSpinner = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arrSpinner);
        adapterSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_LoaiThuChi.setAdapter(adapterSpinner);
        //Spinner Vi
        arrMaVi = new ArrayList<Integer>();
        arrTenVi = new ArrayList<String>();
        adapterVi = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arrTenVi);
        adapterVi.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_Vi.setAdapter(adapterVi);
        spinner_Vi.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //LoadDanhSachViLenSpinner();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        //Spinner Danh muc
        arrMaDanhMuc = new ArrayList<Integer>();
        arrTenDanhMuc = new ArrayList<String>();
        adapterDanhMuc = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arrTenDanhMuc);
        adapterDanhMuc.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_DanhMuc.setAdapter(adapterDanhMuc);
        spinner_DanhMuc.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                LoadDanhSachDanhMucLenSpinner();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void ThemVi() {
        button_ThemViNong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), ViActivity.class);
                startActivity(i);
            }
        });
    }

    public void LoadDanhSachViLenSpinner() {
        arrMaVi.clear();
        arrTenVi.clear();
        cursor = data.query("tblvi", null, null, null, null, null, null);
        cursor.moveToFirst();
        list = new ArrayList<ArrayVi>();
        while (cursor.isAfterLast() == false) {
            ArrayVi a = new ArrayVi();
            a.setMavi(cursor.getInt(0));
            a.setTenvi(cursor.getString(1));
            a.setMotavi(cursor.getString(2));
            a.setSotien(cursor.getString(3));
            list.add(a);

            cursor.moveToNext();
        }
        adapterVi1.notifyDataSetChanged();
        adapterVi1 = new AdapterVi(getApplicationContext(), android.R.layout.simple_list_item_1, list);
        adapterVi1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_Vi.setAdapter(adapterVi1);

    }

    public void LoadDanhSachDanhMucLenSpinner() {
        arrMaDanhMuc.clear();
        arrTenDanhMuc.clear();
        cursor = data.rawQuery("select madanhmuc, tendanhmuc from tbldanhmucthuchi", null);
        cursor.moveToFirst();
        while (cursor.isAfterLast() == false) {
            arrMaDanhMuc.add(cursor.getInt(cursor.getColumnIndex("madanhmuc")));
            arrTenDanhMuc.add(cursor.getString(cursor.getColumnIndex("tendanhmuc")));
            cursor.moveToNext();
        }
        adapterDanhMuc.notifyDataSetChanged();
    }
}

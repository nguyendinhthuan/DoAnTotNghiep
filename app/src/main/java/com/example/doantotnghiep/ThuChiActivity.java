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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class ThuChiActivity extends AppCompatActivity {
    private Button button_ThoatThuChi, button_LuuThuChi, button_NgayThuChi, button_GioThuChi, button_ThemViNong;
    private Button button_ThoiGianHienTai;
    private Spinner spinner_LoaiThuChi, spinner_Vi;
    private Calendar today;
    private String gio;
    private Date date;
    private SimpleDateFormat simpleDateFormat;
    private String[] arrSpinner;
    private ArrayAdapter<String> adapterSpinner, adapterVi;
    private ArrayList<Integer> arrMaVi;
    private ArrayList<String> arrTenVi;
    private Cursor cursor;
    private SQLiteDatabase data;
    private DatePicker datePicker;

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
                LoadDanhSachViLenSpinner();
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
        cursor = data.rawQuery("select mavi, ten vi from tblvi", null);
        cursor.moveToFirst();
        while (cursor.isAfterLast() == false) {
            arrMaVi.add(cursor.getInt(cursor.getColumnIndex("mavi")));
            arrTenVi.add(cursor.getString(cursor.getColumnIndex("tenvi")));
            cursor.moveToNext();
        }
        adapterVi.notifyDataSetChanged();
    }


}

package com.example.doantotnghiep;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class ThuChiActivity extends AppCompatActivity {
    private Button button_ThoatThuChi, button_LuuThuChi, button_NgayThuChi, button_GioThuChi;
    private Calendar today;
    private String gio;
    private Date date;
    private SimpleDateFormat simpleDateFormat;

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
    }

    public void AnhXa() {
        button_ThoatThuChi = (Button) findViewById(R.id.button_ThoatThuChi);
        button_LuuThuChi = (Button) findViewById(R.id.button_LuuThuChi);

        button_NgayThuChi = (Button) findViewById(R.id.button_NgayThuChi);
        button_GioThuChi = (Button) findViewById(R.id.button_GioThuchi);

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
}

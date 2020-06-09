package com.example.doantotnghiep.ui.QuanLyVi;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.example.doantotnghiep.R;
import com.example.doantotnghiep.adapter.AdapterLichSuChuyenTien;
import com.example.doantotnghiep.model.ArrayLichSuChuyenTien;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class LichSuChuyenTienActivity extends AppCompatActivity {
    private Activity a;
    private ArrayList<ArrayLichSuChuyenTien> arr;
    private AdapterLichSuChuyenTien adapterLichSuChuyenTien;
    private ImageButton imageButton_TroVeQuanLyVi;
    private Button button_ChonNgayLocChuyenTien, button_ChonTatCaChuyentien;
    private ListView listView_LichSuChuyenTien;
    private Date date;
    private SimpleDateFormat simpleDateFormatDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lichsuchuyentien);
        a = getParent();


        AnhXa();
        TroVeQuanLyVi();
        ChonNgayLocLichSuChuyenTien();
        setListView();
    }

    public void AnhXa() {
        imageButton_TroVeQuanLyVi = (ImageButton) findViewById(R.id.imageButton_TroVeQuanLyVi);
        button_ChonNgayLocChuyenTien = (Button) findViewById(R.id.button_ChonNgayLocChuyenTien);
        button_ChonTatCaChuyentien = (Button) findViewById(R.id.button_ChonTatCaChuyenTien);
        listView_LichSuChuyenTien = (ListView) findViewById(R.id.listView_LichSuChuyenTien);
    }

    public void TroVeQuanLyVi() {
        imageButton_TroVeQuanLyVi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void ChonNgayLocLichSuChuyenTien() {
        button_ChonTatCaChuyentien.setEnabled(false);
        button_ChonNgayLocChuyenTien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoadNgayLocChuyenTien();
                button_ChonTatCaChuyentien.setEnabled(true);
            }
        });

        button_ChonTatCaChuyentien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LocCoSoDuLieu();
                button_ChonNgayLocChuyenTien.setText("Chọn ngày");
                button_ChonTatCaChuyentien.setEnabled(false);
            }
        });
    }

    public void LoadNgayLocChuyenTien() {
        final Dialog dialog = new Dialog(getApplicationContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_chonngay);
        dialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        dialog.show();

        //Anh xa
        final DatePicker datePicker = (DatePicker) dialog.findViewById(R.id.datePicker);
        Button button_ChonNgayXong = (Button) dialog.findViewById(R.id.button_ChonNgayXong);

        button_ChonNgayXong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    date = simpleDateFormatDialog.parse(datePicker.getDayOfMonth() + "/" +(datePicker.getMonth() + 1) + "/" + datePicker.getYear());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                button_ChonNgayLocChuyenTien.setText(simpleDateFormatDialog.format(date));
                dialog.cancel();
                LoadLichSuChuyenTienTheoNgay();
            }
        });
    }

    public void setListView() {
        arr = new ArrayList<ArrayLichSuChuyenTien>();
        adapterLichSuChuyenTien = new AdapterLichSuChuyenTien(this, R.layout.adapter_lichsuchuyentien_item, arr);
        listView_LichSuChuyenTien.setAdapter(adapterLichSuChuyenTien);
    }

    public void LoadLichSuChuyenTienTheoNgay() {

    }

    public void LocCoSoDuLieu() {

    }
}

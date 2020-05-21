package com.example.doantotnghiep;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

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
    private Button button_ThoatThuChi, button_LuuThuChi, button_NgayThuChi, button_GioThuChi;
    private Button button_ThoiGianHienTai;
    private Spinner spinner_LoaiThuChi, spinner_Vi, spinner_DanhMuc;
    private EditText editText_SoTienThuChi, editText_MoTaThuChi;
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
    private Animation animation;
    private String taikhoan;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thu_chi);

        data = openOrCreateDatabase("data.db", MODE_PRIVATE, null);
        animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.animation_edittext);
        simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        date = new Date();

        sharedPreferences = getSharedPreferences("tendangnhap", Context.MODE_PRIVATE);
        taikhoan = sharedPreferences.getString("taikhoancanchuyen","khong tim thay");

        AnhXa();
        ThemMoiThuChi();
        ThoatThemThuChi();
        HienThiThoiGian();
        LoadSpinner();
        LoadDanhSachViLenSpinner();
    }

    public void AnhXa() {
        button_ThoatThuChi = (Button) findViewById(R.id.button_ThoatThuChi);
        button_LuuThuChi = (Button) findViewById(R.id.button_LuuThuChi);

        button_NgayThuChi = (Button) findViewById(R.id.button_NgayThuChi);
        button_GioThuChi = (Button) findViewById(R.id.button_GioThuchi);

        button_ThoiGianHienTai = (Button) findViewById(R.id.button_ThoiGianHienTai);

        editText_SoTienThuChi = (EditText) findViewById(R.id.editText_SoTienThuChi);
        editText_MoTaThuChi = (EditText) findViewById(R.id.editText_MoTaThuChi);

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

    public boolean ThemThuChi() {
        if (editText_SoTienThuChi.getText().toString().equals("")) {
            editText_SoTienThuChi.startAnimation(animation);
            Toast.makeText(this, "Bạn chưa nhập số tiền", Toast.LENGTH_SHORT).show();
        } else if (editText_MoTaThuChi.getText().toString().equals("")) {
            editText_MoTaThuChi.startAnimation(animation);
            Toast.makeText(this, "Bạn chưa nhập mô tả", Toast.LENGTH_SHORT).show();
        } else {
            int mathuchi = 1;
            int sotienthuchi = 0;
            cursor = data.rawQuery("select mathuchi from tblthuchi", null);
            if (cursor.moveToLast() == true) {
                mathuchi = cursor.getInt(cursor.getColumnIndex("mathuchi")) + 1;
            }
            String thongbao = "";
            ContentValues values = new ContentValues();
            values.put("mathuchi", mathuchi);
            values.put("mota", editText_MoTaThuChi.getText().toString());
            values.put("loaithuchi", spinner_LoaiThuChi.getSelectedItem().toString());
            if (spinner_LoaiThuChi.getSelectedItem().toString().equals("Khoản thu")) {
                sotienthuchi = Integer.parseInt(editText_SoTienThuChi.getText().toString());
            } else {
                sotienthuchi = -Integer.parseInt(editText_SoTienThuChi.getText().toString());
            }
            values.put("sotienthuchi", sotienthuchi);
            values.put("mavi", spinner_Vi.getSelectedItem().toString());
            values.put("ngaythuchien", simpleDateFormat.format(date));
            values.put("madanhmuc", arrMaDanhMuc.get(spinner_DanhMuc.getSelectedItemPosition()));
            values.put("tentaikhoan", taikhoan);

            if (data.insert("tblthuchi", null, values) == -1) {
                return false;
            }
            thongbao = "Lưu thành công";
            finish();

            Toast.makeText(this, thongbao, Toast.LENGTH_SHORT).show();
        }
        return true;
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
        //Spinner Danh muc
        arrMaDanhMuc = new ArrayList<Integer>();
        arrTenDanhMuc = new ArrayList<String>();
        adapterDanhMuc = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arrTenDanhMuc);
        adapterDanhMuc.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_DanhMuc.setAdapter(adapterDanhMuc);
        //Spinner Loai thu chi
        arrSpinner = getResources().getStringArray(R.array.loaithuchi);
        adapterSpinner = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arrSpinner);
        adapterSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_LoaiThuChi.setAdapter(adapterSpinner);
        spinner_LoaiThuChi.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                LoadDanhSachDanhMucLenSpinner();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        //Spinner Vi
        arrMaVi = new ArrayList<Integer>();
        arrTenVi = new ArrayList<String>();
        adapterVi = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arrTenVi);
        adapterVi.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_Vi.setAdapter(adapterVi);
    }

    public void LoadDanhSachViLenSpinner() {
        arrMaVi.clear();
        arrTenVi.clear();
        //cursor = data.query("tblvi", null, null, null, null, null, null);
        Cursor cursor = data.rawQuery("select * from tblvi where tentaikhoan = '" + taikhoan +"'", null);
        cursor.moveToFirst();
        list = new ArrayList<ArrayVi>();
        while (cursor.isAfterLast() == false) {
            arrMaVi.add(cursor.getInt(cursor.getColumnIndex("mavi")));
            arrTenVi.add(cursor.getString(cursor.getColumnIndex("tenvi")));
            cursor.moveToNext();
        }
        adapterVi.notifyDataSetChanged();
    }

    public void LoadDanhSachDanhMucLenSpinner() {
        arrMaDanhMuc.clear();
        arrTenDanhMuc.clear();
        Cursor cursor = data.rawQuery("select madanhmuc, tendanhmuc from tbldanhmucthuchi where loaikhoan = '" + spinner_LoaiThuChi.getSelectedItem().toString() + "'", null);
        cursor.moveToFirst();
        while (cursor.isAfterLast() == false) {
            arrMaDanhMuc.add(cursor.getInt(cursor.getColumnIndex("madanhmuc")));
            arrTenDanhMuc.add(cursor.getString(cursor.getColumnIndex("tendanhmuc")));
            cursor.moveToNext();
        }
        adapterDanhMuc.notifyDataSetChanged();
    }
}

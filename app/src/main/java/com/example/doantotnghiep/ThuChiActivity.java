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
    private Button button_ThoatThuChiDialog, button_LuuThuChiDialog, button_NgayThuChiDialog, button_GioThuChiDialog;
    private Button button_ThoiGianHienTaiDialog;
    private Spinner spinner_LoaiThuChiDialog, spinner_ViDialog, spinner_DanhMucDialog;
    private EditText editText_SoTienThuChiDialog, editText_MoTaThuChiDialog;
    private Calendar todayDialog;
    private String gioDialog;
    private Date dateDialog;
    private SimpleDateFormat simpleDateFormatDialog;
    private String[] arrSpinnerDialog;
    private ArrayAdapter<String> adapterSpinnerDialog, adapterViDialog, adapterDanhMucDialog;
    private ArrayList<Integer> arrMaViDialog, arrMaDanhMucDialog;
    private ArrayList<String> arrTenViDialog, arrTenDanhMucDialog;
    private Cursor cursor;
    private SQLiteDatabase data;
    private DatePicker datePicker;
    private List<ArrayVi> listDialog = null;
    private AdapterVi adapterVi1;
    private Animation animation;
    private String taikhoan;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thu_chi);

        data= openOrCreateDatabase("data.db", MODE_PRIVATE, null);
        animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.animation_edittext);
        simpleDateFormatDialog = new SimpleDateFormat("dd/MM/yyyy");
        dateDialog = new Date();

        sharedPreferences = getSharedPreferences("tendangnhap", Context.MODE_PRIVATE);
        taikhoan = sharedPreferences.getString("taikhoancanchuyen","khong tim thay");

        AnhXa();
        ThemMoiThuChi();
        ThoatThemThuChi();
        HienThiThoiGian();
        LoadSpinnerDialog();
        LoadDanhSachViLenSpinnerDialog();
    }

    public void AnhXa() {
        button_ThoatThuChiDialog = (Button) findViewById(R.id.button_ThoatThuChi);
        button_LuuThuChiDialog = (Button) findViewById(R.id.button_LuuThuChi);

        button_NgayThuChiDialog = (Button) findViewById(R.id.button_NgayThuChi);
        button_GioThuChiDialog = (Button) findViewById(R.id.button_GioThuchi);

        button_ThoiGianHienTaiDialog = (Button) findViewById(R.id.button_ThoiGianHienTai);

        editText_SoTienThuChiDialog = (EditText) findViewById(R.id.editText_SoTienThuChi);
        editText_MoTaThuChiDialog = (EditText) findViewById(R.id.editText_MoTaThuChi);

        spinner_LoaiThuChiDialog = (Spinner) findViewById(R.id.spinner_LoaiThuChi);
        spinner_ViDialog = (Spinner) findViewById(R.id.spinner_Vi);
        spinner_DanhMucDialog = (Spinner) findViewById(R.id.spinner_DanhMuc);

        todayDialog = Calendar.getInstance();
    }

    public void ThoatThemThuChi() {
        button_ThoatThuChiDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    public void ThemMoiThuChi() {
        button_LuuThuChiDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ThemThuChiDialog();
               // TinhSoDuVi();
                finish();
            }
        });
    }

    public boolean ThemThuChiDialog() {
        if (editText_SoTienThuChiDialog.getText().toString().equals("")) {
            editText_SoTienThuChiDialog.startAnimation(animation);
            Toast.makeText(this, "Bạn chưa nhập số tiền", Toast.LENGTH_SHORT).show();
        } else if (editText_MoTaThuChiDialog.getText().toString().equals("")) {
            editText_MoTaThuChiDialog.startAnimation(animation);
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
            values.put("mota", editText_MoTaThuChiDialog.getText().toString());
            values.put("loaithuchi", spinner_LoaiThuChiDialog.getSelectedItem().toString());
            if (spinner_LoaiThuChiDialog.getSelectedItem().toString().equals("Khoản thu")) {
                sotienthuchi = Integer.parseInt(editText_SoTienThuChiDialog.getText().toString());
            } else {
                sotienthuchi = -Integer.parseInt(editText_SoTienThuChiDialog.getText().toString());
            }
            values.put("sotienthuchi", sotienthuchi);
            values.put("mavi", arrMaViDialog.get(spinner_ViDialog.getSelectedItemPosition()));
            values.put("ngaythuchien", simpleDateFormatDialog.format(dateDialog));
            values.put("madanhmuc", arrMaDanhMucDialog.get(spinner_DanhMucDialog.getSelectedItemPosition()));
            values.put("tentaikhoan", taikhoan);

            if (data.insert("tblthuchi", null, values) == -1) {
                return false;
            }
            thongbao = "Lưu thành công";

            //finish();

            Toast.makeText(this, thongbao, Toast.LENGTH_SHORT).show();
        }
        return true;
    }



    public void HienThiThoiGian() {
        int thang = todayDialog.get(Calendar.MONTH) + 1;
        gioDialog = todayDialog.get(Calendar.HOUR_OF_DAY) + ":"+ todayDialog.get(Calendar.MINUTE) + ":" +todayDialog.get(Calendar.SECOND);
        dateDialog = todayDialog.getTime();
        button_NgayThuChiDialog.setText(simpleDateFormatDialog.format(dateDialog));
        button_GioThuChiDialog.setText(gioDialog);
    }

    public void ChonNgay() {
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
                    dateDialog = simpleDateFormatDialog.parse(datePicker.getDayOfMonth() + "/" +(datePicker.getMonth() + 1) + "/" + datePicker.getYear());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                button_NgayThuChiDialog.setText(simpleDateFormatDialog.format(dateDialog));
                dialog.cancel();
            }
        });
    }

    public void LayNgayHienTai(View v) {
        HienThiThoiGian();
    }

    public void LoadSpinnerDialog() {
        //Spinner Danh muc
        arrMaDanhMucDialog = new ArrayList<Integer>();
        arrTenDanhMucDialog = new ArrayList<String>();
        adapterDanhMucDialog = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arrTenDanhMucDialog);
        adapterDanhMucDialog.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_DanhMucDialog.setAdapter(adapterDanhMucDialog);
        //Spinner Loai thu chi
        arrSpinnerDialog = getResources().getStringArray(R.array.loaithuchi);
        adapterSpinnerDialog = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arrSpinnerDialog);
        adapterSpinnerDialog.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_LoaiThuChiDialog.setAdapter(adapterSpinnerDialog);
        spinner_LoaiThuChiDialog.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                LoadDanhSachDanhMucLenSpinnerDialog();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        //Spinner Vi
        arrMaViDialog = new ArrayList<Integer>();
        arrTenViDialog = new ArrayList<String>();
        adapterViDialog = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arrTenViDialog);
        adapterViDialog.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_ViDialog.setAdapter(adapterViDialog);
    }

    public void LoadDanhSachViLenSpinnerDialog() {
        arrMaViDialog.clear();
        arrTenViDialog.clear();
        //cursor = data.query("tblvi", null, null, null, null, null, null);
        Cursor cursor = data.rawQuery("select * from tblvi where tentaikhoan = '" + taikhoan +"'", null);
        cursor.moveToFirst();
        listDialog = new ArrayList<ArrayVi>();
        while (cursor.isAfterLast() == false) {
            arrMaViDialog.add(cursor.getInt(cursor.getColumnIndex("mavi")));
            arrTenViDialog.add(cursor.getString(cursor.getColumnIndex("tenvi")));
            cursor.moveToNext();
        }
        adapterViDialog.notifyDataSetChanged();
    }

    public void LoadDanhSachDanhMucLenSpinnerDialog() {
        arrMaDanhMucDialog.clear();
        arrTenDanhMucDialog.clear();
        Cursor cursor = data.rawQuery("select madanhmuc, tendanhmuc from tbldanhmucthuchi where loaikhoan = '" + spinner_LoaiThuChiDialog.getSelectedItem().toString() + "'", null);
        cursor.moveToFirst();
        while (cursor.isAfterLast() == false) {
            arrMaDanhMucDialog.add(cursor.getInt(cursor.getColumnIndex("madanhmuc")));
            arrTenDanhMucDialog.add(cursor.getString(cursor.getColumnIndex("tendanhmuc")));
            cursor.moveToNext();
        }
        adapterDanhMucDialog.notifyDataSetChanged();
    }

    public void TinhSoDuVi()
    {
        int mavithem = arrMaViDialog.get(spinner_ViDialog.getSelectedItemPosition());
        Cursor cursor =  data.rawQuery("select* from tblvi where mavi = "+ mavithem,null);

        int sotienthuchi = Integer.parseInt(editText_SoTienThuChiDialog.getText().toString());
        int sotientuvi = cursor.getInt(cursor.getColumnIndex("sotienvi"));
        //Tinh
        int sodu = sotientuvi + sotienthuchi;
        // gan lai
        ContentValues values = new ContentValues();
        values.put("sotienvi",sodu);
        data.update("tblvi",values,"mavi="+ mavithem,null);
        //finish();
    }

    public void TruTienVi()
    {

    }
}

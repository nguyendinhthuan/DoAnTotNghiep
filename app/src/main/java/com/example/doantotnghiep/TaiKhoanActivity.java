package com.example.doantotnghiep;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.doantotnghiep.ui.QuanLyTaiKhoan.QuanLyTaiKhoanFragment;

public class TaiKhoanActivity extends AppCompatActivity {
    private SQLiteDatabase data;
    private Cursor cursor;
    private Activity a;

    private Button btnLuu,btnHuy;
    private EditText editText_HoTen, editText_DiaChi, editText_Email, editText_SoDienThoai;
    private TextView txt_TenTaiKhoan;
    private Animation animation;
    private String taikhoan ;

    Database database;
    private QuanLyTaiKhoanFragment quanLyTaiKhoanFragment;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_taikhoan);
        animation = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.animation_edittext);
        data = openOrCreateDatabase("data.db", a.MODE_PRIVATE, null);

        AnhXa();
        ThoatCapNhatTaiKhoan();
        LuuCapNhat();

        sharedPreferences = getSharedPreferences("tendangnhap",MODE_PRIVATE);
        taikhoan = sharedPreferences.getString("taikhoancanchuyen","khong tim thay");
        txt_TenTaiKhoan.setText(taikhoan);


    }

    public void AnhXa()
    {
        btnLuu = (Button) findViewById(R.id.btnLuuCapNhatTaiKhoan);
        btnHuy = (Button) findViewById(R.id.btnHuyCapNhatTaiKhoan);
        editText_HoTen = (EditText) findViewById(R.id.TextHoTen);
        editText_DiaChi = (EditText) findViewById(R.id.TextDiaChi);
        editText_Email = (EditText) findViewById(R.id.TextEmail);
        editText_SoDienThoai = (EditText) findViewById(R.id.TextSDT);
        txt_TenTaiKhoan = (TextView) findViewById(R.id.txtTenTaiKhoanCapNhat);
    }

    public void ThoatCapNhatTaiKhoan()
    {
        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent i = new Intent(TaiKhoanActivity.this, QuanLyTaiKhoanFragment.class);
//                startActivity(i);
                finish();
            }
        });
    }

    public void LuuCapNhat()
    {
        btnLuu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CapNhat();
            }
        });
    }



    public void CapNhat()
    {

        if (editText_HoTen.getText().toString().equals(""))
        {
            editText_HoTen.startAnimation(animation);
            Toast.makeText(this,"Bạn chưa nhập họ tên", Toast.LENGTH_SHORT).show();
        }else if (editText_DiaChi.getText().toString().equals(""))
        {
            editText_DiaChi.startAnimation(animation);
            Toast.makeText(this,"Bạn chưa nhập địa chỉ", Toast.LENGTH_SHORT).show();
        }else if (editText_Email.getText().toString().equals(""))
        {
            editText_Email.startAnimation(animation);
            Toast.makeText(this,"Bạn chưa nhập email", Toast.LENGTH_SHORT).show();
        }else if (editText_SoDienThoai.getText().toString().equals(""))
        {
            editText_SoDienThoai.startAnimation(animation);
            Toast.makeText(this,"Bạn chưa nhập số điện thoại", Toast.LENGTH_SHORT).show();
        }else {
            cursor = data.rawQuery("select tentaikhoan from tbltaikhoan",null);

        }
    }

}

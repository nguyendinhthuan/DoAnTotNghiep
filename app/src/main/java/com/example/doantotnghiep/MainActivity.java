package com.example.doantotnghiep;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActionBar;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.doantotnghiep.model.ArrayVi;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private SQLiteDatabase data;
    private SharedPreferences share;
    private LinearLayout layout;
    private EditText editText_TenTaiKhoanDangNhap, editText_MatKhauDangNhap, maso, matkhau1, matkhau2;
    private Button thaydoi, huy;
    private ImageView imglogo;
    private Animation animation;
    private CheckBox checkBox_GhiNho;
    Database database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        data = openOrCreateDatabase("data.db", MODE_PRIVATE, null);
        share = getSharedPreferences("taikhoan", MODE_PRIVATE);

        AnhXa();
        TaoBangCoSoDuLieu();
    }

    private void TaoBangCoSoDuLieu() {
        try {
            data.execSQL("create table if not exists tbltaikhoan(tentaikhoan text primary key, masobimat text, matkhau text);");
            data.execSQL("create table if not exists tblvi(mavi int primary key, tenvi text, motavi text, sotien text);");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void AnhXa() {
        database = new Database(this);

        imglogo = (ImageView) findViewById(R.id.imgLogo);
        animation = AnimationUtils.loadAnimation(this, R.anim.animation_logo);
        imglogo.startAnimation(animation);
        layout = (LinearLayout) findViewById(R.id.layoutDangnhap);
        layout.setVisibility(View.GONE);
        editText_TenTaiKhoanDangNhap = (EditText) findViewById(R.id.editText_TenTaiKhoanDangNhap);
        editText_MatKhauDangNhap = (EditText) findViewById(R.id.editText_MatKhauDangNhap);
        checkBox_GhiNho = (CheckBox) findViewById(R.id.checkBox_GhiNho);
        Handler h = new Handler();
        h.postDelayed(new Runnable() {
            @Override
            public void run() {
                imglogo.setVisibility(View.VISIBLE);
                layout.setVisibility(View.VISIBLE);
            }
        }, 2000);
    }

    public void DangKyTaiKhoan(View v) {
        XoaTrang();
        Intent intent = new Intent(this, DangKyActivity.class);
        startActivity(intent);
    }

    private void XoaTrang() {
        editText_TenTaiKhoanDangNhap.setText(null);
        editText_MatKhauDangNhap.setText(null);
    }

    public void DangNhap(View v) {
        animation = AnimationUtils.loadAnimation(this, R.anim.animation_edittext);
        boolean tk = false, mk = true;
        Cursor c = data.rawQuery("select * from tbltaikhoan", null);
        c.moveToFirst();
        while (c.isAfterLast() == false) {
            if (c.getString(c.getColumnIndex("tentaikhoan")).equals(editText_TenTaiKhoanDangNhap.getText().toString())) {
                tk = true;
                if (c.getString(c.getColumnIndex("matkhau")).equals(editText_MatKhauDangNhap.getText().toString())) {
                    Intent intent = new Intent(this, HomeActivity.class);
                    intent.putExtra("taikhoan", c.getString(c.getColumnIndex("tentaikhoan")));
                    startActivityForResult(intent, 2);
                    Toast.makeText(getApplicationContext(), "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                } else {
                    mk = false;
                }
            }
            c.moveToNext();
        }
        if (editText_TenTaiKhoanDangNhap.getText().toString().equals("")) {
            editText_TenTaiKhoanDangNhap.startAnimation(animation);
            Toast.makeText(getApplicationContext(), "Bạn chưa nhập tên tài khoản", Toast.LENGTH_SHORT).show();
        } else if (editText_MatKhauDangNhap.getText().toString().equals("")) {
            editText_MatKhauDangNhap.startAnimation(animation);
            Toast.makeText(getApplicationContext(), "Bạn chưa nhập mật khẩu", Toast.LENGTH_SHORT).show();
        } else if (tk == false) {
            Toast.makeText(getApplicationContext(), "Tài khoản này không tồn tại", Toast.LENGTH_SHORT).show();
            editText_TenTaiKhoanDangNhap.startAnimation(animation);
        } else if (mk == false) {
            Toast.makeText(getApplicationContext(), "Không đúng mật khẩu", Toast.LENGTH_SHORT).show();
            editText_MatKhauDangNhap.startAnimation(animation);
        }
    }

    public void QuenMatKhau(View v) {
        animation = AnimationUtils.loadAnimation(this, R.anim.animation_edittext);
        XoaTrang();
        final Dialog d = new Dialog(MainActivity.this);
        d.requestWindowFeature(Window.FEATURE_NO_TITLE);
        d.setContentView(R.layout.activity_quenmatkhau);
        d.getWindow().setLayout(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.WRAP_CONTENT);
        d.show();

        maso = (EditText) d.findViewById(R.id.txtNhapmasoquen);
        matkhau1 = (EditText) d.findViewById(R.id.txtNhapmatkhauquen);
        matkhau2 = (EditText) d.findViewById(R.id.txtNhapmatkhau2quen);
        thaydoi = (Button) d.findViewById(R.id.btnThaydoiquen);
        huy = (Button) d.findViewById((R.id.btnHuy));
        thaydoi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (DoiMatKhau()) {
                    d.dismiss();
                }
            }
        });
        huy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                d.dismiss();
            }
        });
    }

    public boolean DoiMatKhau() {
        String thongbao = "";
        boolean msbm = false;
        Cursor c = data.rawQuery("select * from tbltaikhoan", null);
        c.moveToFirst();
        while (c.isAfterLast() == false) {
            if (c.getString(c.getColumnIndex("masobimat")).equals(maso.getText().toString())) {
                msbm = true;
            }
            c.moveToNext();
        }
        if (maso.getText().toString().equals("")) {
            thongbao = "Bạn chưa nhập mã số bí mật";
            maso.startAnimation(animation);
        } else if (matkhau1.getText().toString().equals("")) {
            thongbao = "Bạn chưa nhập mật khẩu";
            matkhau1.startAnimation(animation);
        } else if (matkhau2.getText().toString().equals("")) {
            thongbao = "Bạn chưa nhập mật khẩu mới";
            matkhau2.startAnimation(animation);
        } else if (msbm == false) {
            thongbao = "Mã số bí mật sai";
            maso.startAnimation(animation);
        } else if (matkhau1.getText().toString().equals(matkhau2.getText().toString()) == false) {
            thongbao = "Mật khẩu không khớp";
            matkhau1.startAnimation(animation);
            matkhau2.startAnimation(animation);
        } else {
            ContentValues values = new ContentValues();
            values.put("matkhau", matkhau1.getText().toString());
            data.update("tbltaikhoan", values, "masobimat=?", new String[]{maso.getText().toString()});
            thongbao = "Lấy lại mật khẩu thành công";
            Toast.makeText(getApplicationContext(), thongbao, Toast.LENGTH_SHORT).show();
            return true;
        }
        Toast.makeText(getApplicationContext(), thongbao, Toast.LENGTH_SHORT).show();
        return false;
    }

    public void GhiNhoTaiKhoan() {
        SharedPreferences.Editor edit = share.edit();
        if (checkBox_GhiNho.isChecked()) {
            edit.putString("user", editText_TenTaiKhoanDangNhap.getText().toString());
            edit.putString("pass", editText_MatKhauDangNhap.getText().toString());
            edit.putBoolean("ghinho", checkBox_GhiNho.isChecked());
        } else {
            edit.clear();
        }
        edit.commit();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 2) {
            if (resultCode == 0) {
                GhiNhoTaiKhoan();
                finish();
            }
        }
    }

    @Override
    protected void onResume() {
        if (share.getBoolean("ghinho", false)) {
            editText_TenTaiKhoanDangNhap.setText(share.getString("user", null));
            editText_MatKhauDangNhap.setText(share.getString("pass", null));
            DangNhap(null);
        } else {
            editText_TenTaiKhoanDangNhap.setText(null);
            editText_MatKhauDangNhap.setText(null);
        }
        checkBox_GhiNho.setChecked(share.getBoolean("ghinho", false));
        super.onResume();
    }

    @Override
    protected void onPause() {
        GhiNhoTaiKhoan();
        super.onPause();
    }
}

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

public class MainActivity extends AppCompatActivity {
    private SQLiteDatabase data;
    private SharedPreferences share;
    private LinearLayout layout;
    private EditText username, password, taikhoan, maso, matkhau1, matkhau2;
    private Button thaydoi, huy;
    private ImageView imglogo;
    private Animation animation;
    private CheckBox ghinho;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        data = openOrCreateDatabase("data.db", MODE_PRIVATE, null);
        share = getSharedPreferences("taikhoan", MODE_PRIVATE);
        setWidget();
        createtable();
    }

    private void createtable() {
        try {
            data.execSQL("create table if not exists tbltaikhoan(mataikhoan text primary key, tentaikhoan text, matkhau text);");
            data.execSQL("create table if not exists tblphannhom(manhom int primary key, tennhom text, tenkhoan text, " +
                    "mataikhoan text constraint mataikhoan references tbltaikhoan(mataikhoan) on delete cascade);");
            data.execSQL("create table if not exists tblthuchi(mathuchi int primary key, loaitk text, sotien int, ngay date, " +
                    "tuan int, manhom int constraint manhom references tblphannhom(manhom) on delete cascade);");
            data.execSQL("create table if not exists tblgiaodich(magiaodich int primary key, lydo text, trangthai text, gio time, " +
                    "mathuchi int constraint mathuchi references tblthuchi(mathuchi) on delete cascade);");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void setWidget() {
        imglogo = (ImageView) findViewById(R.id.imgLogo);
        animation = AnimationUtils.loadAnimation(this, R.anim.animation_logo);
        imglogo.startAnimation(animation);
        layout = (LinearLayout) findViewById(R.id.layoutDangnhap);
        layout.setVisibility(View.GONE);
        username = (EditText) findViewById(R.id.txtNhaptendangnhapmain);
        password = (EditText) findViewById(R.id.txtNhapmatkhaumain);
        ghinho = (CheckBox) findViewById(R.id.chkGhinhomain);
        Handler h = new Handler();
        h.postDelayed(new Runnable() {
            @Override
            public void run() {
                imglogo.setVisibility(View.GONE);
                layout.setVisibility(View.VISIBLE);
            }
        }, 2000);
    }

    public void dangky(View v) {
        clearData();
        Intent intent = new Intent(this, DangkyActivity.class);
        startActivity(intent);
    }

    private void clearData() {
        username.setText(null);
        password.setText(null);
    }

    public void dangnhap(View v) {
        animation = AnimationUtils.loadAnimation(this, R.anim.animation_edittext);
        boolean tk = false, mk = true;
        Cursor c = data.rawQuery("select * from tbltaikhoan", null);
        c.moveToFirst();
        while (c.isAfterLast() == false) {
            if (c.getString(c.getColumnIndex("tentaikhoan")).equals(username.getText().toString())) {
                tk = true;
                if (c.getString(c.getColumnIndex("matkhau")).equals(password.getText().toString())) {
                    Intent intent = new Intent(this, HomeActivity.class);
                    intent.putExtra("matk", c.getString(c.getColumnIndex("mataikhoan")));
                    startActivityForResult(intent, 2);
                } else {
                    mk = false;
                }
            }
            c.moveToNext();
        }
        if (username.getText().toString().equals("")) {
            username.startAnimation(animation);
            Toast.makeText(getApplicationContext(), "Bạn chưa nhập username", Toast.LENGTH_SHORT).show();
        } else if (password.getText().toString().equals("")) {
            password.startAnimation(animation);
            Toast.makeText(getApplicationContext(), "Bạn chưa nhập password", Toast.LENGTH_SHORT).show();
        } else if (tk == false) {
            Toast.makeText(getApplicationContext(), "Tài khoản không tồn tại", Toast.LENGTH_SHORT).show();
            username.startAnimation(animation);
        } else if (mk == false) {
            Toast.makeText(getApplicationContext(), "Không đúng mật khẩu", Toast.LENGTH_SHORT).show();
            password.startAnimation(animation);
        }
    }

    public void quenmatkhau(View v) {
        animation = AnimationUtils.loadAnimation(this, R.anim.animation_edittext);
        clearData();
        final Dialog d = new Dialog(MainActivity.this);
        d.requestWindowFeature(Window.FEATURE_NO_TITLE);
        d.setContentView(R.layout.activity_quenmatkhau);
        d.getWindow().setLayout(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.WRAP_CONTENT);
        d.show();

        taikhoan = (EditText) d.findViewById(R.id.txtNhapusernamequen);
        maso = (EditText) d.findViewById(R.id.txtNhapmasoquen);
        matkhau1 = (EditText) d.findViewById(R.id.txtNhapmatkhauquen);
        matkhau2 = (EditText) d.findViewById(R.id.txtNhapmatkhau2quen);
        thaydoi = (Button) d.findViewById(R.id.btnThaydoiquen);
        huy = (Button) d.findViewById((R.id.btnHuy));
        thaydoi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (doimatkhau()) {
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

    public boolean doimatkhau() {
        String thongbao = "";
        boolean ms = false;
        Cursor c = data.rawQuery("select * from tbltaikhoan", null);
        c.moveToFirst();
        while (c.isAfterLast() == false) {
            if (c.getString(c.getColumnIndex("mataikhoan")).equals(maso.getText().toString())) {
                ms = true;
            }
            c.moveToNext();
        }
        if(taikhoan.getText().toString().equals("")) {
            thongbao = "Bạn chưa nhập tên tài khoản";
            taikhoan.startAnimation(animation);
        } else if (maso.getText().toString().equals("")) {
            thongbao = "Bạn chưa nhập mã số";
            maso.startAnimation(animation);
        } else if (matkhau1.getText().toString().equals("")) {
            thongbao = "Bạn chưa nhập password";
            matkhau1.startAnimation(animation);
        } else if (matkhau2.getText().toString().equals("")) {
            thongbao = "Bạn chưa nhập password 2";
            matkhau2.startAnimation(animation);
        } else if (ms == false) {
            thongbao = "Sai mã số bí mật";
            maso.startAnimation(animation);
        } else if (matkhau1.getText().toString().equals(matkhau2.getText().toString()) == false) {
            thongbao = "Mật khẩu không khớp";
            matkhau1.startAnimation(animation);
            matkhau2.startAnimation(animation);
        } else {
            ContentValues values = new ContentValues();
            values.put("matkhau", matkhau1.getText().toString());
            data.update("tbltaikhoan", values, "mataikhoan=?", new String[]{maso.getText().toString()});
            thongbao = "Lấy lại mật khẩu thành công";
            Toast.makeText(getApplicationContext(), thongbao, Toast.LENGTH_SHORT).show();
            return true;
        }
        Toast.makeText(getApplicationContext(), thongbao, Toast.LENGTH_SHORT).show();
        return false;
    }

    public void luutaikhoan() {
        SharedPreferences.Editor edit = share.edit();
        if (ghinho.isChecked()) {
            edit.putString("user", username.getText().toString());
            edit.putString("pass", password.getText().toString());
            edit.putBoolean("ghinho", ghinho.isChecked());
        } else {
            edit.clear();
        }
        edit.commit();
    }
}

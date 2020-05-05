package com.example.doantotnghiep;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.doantotnghiep.model.ArrayVi;

public class ViActivity extends AppCompatActivity {
    private SQLiteDatabase data;
    private Cursor cursor;
    private Activity a;

    private Button button_LuuVi, button_ThoatVi;
    private EditText editText_TenVi, editText_MoTaVi, editText_SoTienVi;
    private Animation animation;

    ArrayVi arrayVi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vi);
        animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.animation_edittext);
        data = openOrCreateDatabase("data.db", a.MODE_PRIVATE, null);

        AnhXa();
        ThemMoiVi();
        ThoatThemVi();
    }

    public void AnhXa() {
        button_LuuVi = (Button) findViewById(R.id.button_LuuVi);
        button_ThoatVi = (Button) findViewById(R.id.button_ThoatVi);

        editText_TenVi = (EditText) findViewById(R.id.editText_TenVi);
        editText_MoTaVi = (EditText) findViewById(R.id.editText_MoTaVi);
        editText_SoTienVi = (EditText) findViewById(R.id.editText_SoTienVi);
    }

    public void ThoatThemVi() {
        button_ThoatVi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void ThemMoiVi() {
        button_LuuVi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ThemVi();
            }
        });
    }

    public void ThemVi() {
        if (editText_TenVi.getText().toString().equals("")) {
            editText_TenVi.startAnimation(animation);
            Toast.makeText(this, "Bạn chưa nhập tên ví", Toast.LENGTH_SHORT).show();
        } else if (editText_MoTaVi.getText().toString().equals("")) {
            editText_MoTaVi.startAnimation(animation);
            Toast.makeText(this, "Bạn chưa nhập mô tả cho ví", Toast.LENGTH_SHORT).show();
        } else if (editText_SoTienVi.getText().toString().equals("")) {
            editText_SoTienVi.startAnimation(animation);
            Toast.makeText(this, "Bạn chưa nhập số tiền ban đầu cho ví", Toast.LENGTH_SHORT).show();
        } else {
            int ma = 1;
            cursor = data.rawQuery("select mavi from tblvi", null);
            if (cursor.moveToLast() == true) {
                ma = cursor.getInt(cursor.getColumnIndex("mavi")) + 1;
            }
                String thongbao = "";
                ContentValues values = new ContentValues();
                values.put("mavi", ma);
                values.put("tenvi", editText_TenVi.getText().toString());
                values.put("motavi", editText_MoTaVi.getText().toString());
                values.put("sotien", editText_SoTienVi.getText().toString());
                if (data.insert("tblvi", null, values) != -1) {
                    thongbao = "Thêm ví thành công";
                    finish();
                } else {
                    thongbao = "Thêm ví không thành công";
                }
                Toast.makeText(getApplicationContext(), thongbao, Toast.LENGTH_LONG).show();
            }
        }
    }


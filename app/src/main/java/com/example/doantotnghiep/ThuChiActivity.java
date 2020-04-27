package com.example.doantotnghiep;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class ThuChiActivity extends AppCompatActivity {
    private Button button_ThoatThuChi, button_LuuThuChi;
    private ImageButton imageButton_test;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thu_chi);

        //Anh xa
        button_ThoatThuChi = (Button) findViewById(R.id.button_ThoatThuChi);
        button_LuuThuChi = (Button) findViewById(R.id.button_LuuThuChi);
        imageButton_test = (ImageButton) findViewById(R.id.button4);

        imageButton_test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ThuChiActivity.this, MainActivity.class);
                startActivity(i);
            }
        });

        button_ThoatThuChi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }
}

package com.example.doantotnghiep;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ThuChiActivity extends AppCompatActivity {
    private Button button_ThoatThuChi, button_LuuThuChi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thu_chi);

        //Anh xa
        button_ThoatThuChi = (Button) findViewById(R.id.button_ThoatThuChi);
        button_LuuThuChi = (Button) findViewById(R.id.button_LuuThuChi);

        button_ThoatThuChi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }
}

package com.example.doantotnghiep;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ViActivity extends AppCompatActivity {
    private Button button_LuuVi, button_ThoatVi;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vi);

        anhxa();
        thoat();
    }

    public void thoat() {
        button_ThoatVi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void anhxa() {
        button_LuuVi = (Button) findViewById(R.id.button_LuuVi);
        button_ThoatVi = (Button) findViewById(R.id.button_ThoatVi);
    }
}

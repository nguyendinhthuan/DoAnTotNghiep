package com.example.doantotnghiep.ui.QuanLyTaiKhoan;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.doantotnghiep.MainActivity;
import com.example.doantotnghiep.R;
import com.example.doantotnghiep.TaiKhoanActivity;

import java.util.zip.Inflater;


public class QuanLyTaiKhoanFragment extends Fragment {

    private QuanLyTaiKhoanViewModel slideshowViewModel;
    private View myFragment;
    private Activity activity;
    private Animation animation;
    private SQLiteDatabase data;
    private String tentaikhoan;
    private Button button_CapNhatTaiKhoan, button_DangXuat;
    private TextView txt_TenTaiKhoanFrag, txt_TenNguoiDung, txt_DiaChi, txt_Email, txt_SDT;
    private String taikhoan;
    private SharedPreferences sharedPreferences;
//code Thuan
//    public View onCreateView(@NonNull LayoutInflater inflater,
//                             ViewGroup container, Bundle savedInstanceState) {
//        slideshowViewModel =
//                ViewModelProviders.of(this).get(QuanLyTaiKhoanViewModel.class);
//        View root = inflater.inflate(R.layout.fragment_quanlytaikhoan, container, false);
//        final TextView textView = root.findViewById(R.id.text_slideshow);
//        slideshowViewModel.getText().observe(this, new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });
//        return root;
//    }

//code Phat

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        myFragment = inflater.inflate(R.layout.fragment_quanlytaikhoan, container, false);
        return myFragment;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        activity = getActivity();
        data = activity.openOrCreateDatabase("data.db", activity.MODE_PRIVATE, null);
        animation = AnimationUtils.loadAnimation(getActivity(), R.anim.animation_edittext);

        AnhXa();
        CapNhatTaiKhoan();
        DangXuat();

        sharedPreferences = getActivity().getSharedPreferences("tendangnhap", Context.MODE_PRIVATE);
        taikhoan = sharedPreferences.getString("taikhoancanchuyen","khong tim thay");
        txt_TenTaiKhoanFrag.setText(taikhoan);

    }

    public void CapNhatTaiKhoan() {
        button_CapNhatTaiKhoan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), TaiKhoanActivity.class);
                startActivity(i);
            }
        });
    }

    public void AnhXa()
    {
        button_CapNhatTaiKhoan = (Button) myFragment.findViewById(R.id.btnCapNhatTaiKhoan);
        txt_TenTaiKhoanFrag = (TextView) myFragment.findViewById(R.id.txtTenTaiKhoan);
        txt_TenNguoiDung = (TextView) myFragment.findViewById(R.id.txtTenNguoiDung);
        txt_DiaChi = (TextView) myFragment.findViewById(R.id.txtDiaChi);
        txt_Email = (TextView) myFragment.findViewById(R.id.txtEmail);
        txt_SDT = (TextView) myFragment.findViewById(R.id.txtSoDienThoai);
        button_DangXuat = (Button) myFragment.findViewById(R.id.btnDangXuat);
    }

    public void DangXuat()
    {
        button_DangXuat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
            }
        });
    }

    public void LayTaiKhoan()
    {


//        Cursor cursor = data.query("tbltaikhoan",null,null,null,null,null,null);
//        cursor.moveToFirst();



    }
}
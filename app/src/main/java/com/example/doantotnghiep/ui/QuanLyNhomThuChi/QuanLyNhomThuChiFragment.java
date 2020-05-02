package com.example.doantotnghiep.ui.QuanLyNhomThuChi;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.doantotnghiep.R;

public class QuanLyNhomThuChiFragment extends Fragment {
    private QuanLyNhomThuChiViewModel slideshowViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        slideshowViewModel =
                ViewModelProviders.of(this).get(QuanLyNhomThuChiViewModel.class);
        View root = inflater.inflate(R.layout.fragment_quanlynhomthuchi, container, false);
        final TextView textView = root.findViewById(R.id.text_slideshow2);
        slideshowViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}

package com.example.doantotnghiep.ui.QuanLyVi;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.doantotnghiep.R;
import com.example.doantotnghiep.ViActivity;

public class QuanLyViFragment extends Fragment {

    private QuanLyViViewModel galleryViewModel;
    private ImageButton button_ThemVi;
    private View myFragment;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        myFragment = inflater.inflate(R.layout.fragment_quanlyvi, container, false);
        button_ThemVi = (ImageButton) myFragment.findViewById(R.id.button_ThemVi);
        button_ThemVi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), ViActivity.class);
                startActivity(i);
            }
        });
        return myFragment;
    }
}
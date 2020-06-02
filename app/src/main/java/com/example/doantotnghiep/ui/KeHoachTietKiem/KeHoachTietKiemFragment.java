package com.example.doantotnghiep.ui.KeHoachTietKiem;

import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.doantotnghiep.R;

public class KeHoachTietKiemFragment extends Fragment {

    private KeHoachTietKiemViewModel mViewModel;

    public static KeHoachTietKiemFragment newInstance() {
        return new KeHoachTietKiemFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_kehoachtietkiem, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(KeHoachTietKiemViewModel.class);
        // TODO: Use the ViewModel
    }

}

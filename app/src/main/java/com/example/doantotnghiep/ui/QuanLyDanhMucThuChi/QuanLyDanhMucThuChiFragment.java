package com.example.doantotnghiep.ui.QuanLyDanhMucThuChi;

import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.doantotnghiep.R;

public class QuanLyDanhMucThuChiFragment extends Fragment {
    private View myFragment;
    private Activity activity;
    private SQLiteDatabase data;
    private Animation animation;
    private String[] arrSpinner;
    private ArrayAdapter<String> adapterSpinner;
    private Spinner spinner_LoaiThuChi2, spinner_LoaiThuChi3;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        myFragment = inflater.inflate(R.layout.fragment_quanlydanhmucthuchi, container, false);
        return myFragment;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        activity = getActivity();
        data = activity.openOrCreateDatabase("data.db", activity.MODE_PRIVATE, null);
        animation = AnimationUtils.loadAnimation(getActivity(), R.anim.animation_edittext);

        AnhXa();
        LoadSpinner();
    }

    public void AnhXa() {
        spinner_LoaiThuChi2 = (Spinner) myFragment.findViewById(R.id.spinner_LoaiThuChi2);
        spinner_LoaiThuChi3 = (Spinner) myFragment.findViewById(R.id.spinner_LoaiThuChi3);
    }

    public void LoadSpinner() {
        //Spinner Loai thu chi
        arrSpinner = getResources().getStringArray(R.array.loaithuchi);
        adapterSpinner = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, arrSpinner);
        adapterSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_LoaiThuChi2.setAdapter(adapterSpinner);
        spinner_LoaiThuChi3.setAdapter(adapterSpinner);
    }
}

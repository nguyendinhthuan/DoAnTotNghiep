package com.example.doantotnghiep.ui.QuanLyThuChi;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class QuanLyThuChiViewModel extends ViewModel {

    private MutableLiveData<String> mText;
    public QuanLyThuChiViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is home fragment");

    }

    public LiveData<String> getText() {
        return mText;
    }
}
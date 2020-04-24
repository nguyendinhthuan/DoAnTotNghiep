package com.example.doantotnghiep.ui.QuanLyTaiKhoan;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class QuanLyTaiKhoanViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public QuanLyTaiKhoanViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is slideshow fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
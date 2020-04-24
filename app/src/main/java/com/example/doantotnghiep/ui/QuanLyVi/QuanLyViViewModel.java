package com.example.doantotnghiep.ui.QuanLyVi;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class QuanLyViViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public QuanLyViViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is gallery fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
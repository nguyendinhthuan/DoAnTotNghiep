package com.example.doantotnghiep.ui.QuanLyNhomThuChi;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class QuanLyNhomThuChiViewModel extends ViewModel {
    private MutableLiveData<String> mText;

    public QuanLyNhomThuChiViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is nhom thu chi fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}

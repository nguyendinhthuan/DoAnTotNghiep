package com.example.doantotnghiep.ui.QuanLyDanhMucThuChi;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class QuanLyDanhMucThuChiViewModel extends ViewModel {
    private MutableLiveData<String> mText;

    public QuanLyDanhMucThuChiViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is nhom thu chi fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}

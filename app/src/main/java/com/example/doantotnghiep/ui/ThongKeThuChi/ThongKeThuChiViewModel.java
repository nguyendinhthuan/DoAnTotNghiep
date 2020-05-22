package com.example.doantotnghiep.ui.ThongKeThuChi;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ThongKeThuChiViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public ThongKeThuChiViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is thong ke fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}

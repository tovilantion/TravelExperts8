package com.vlantion.travelexperts8.ui.Package;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class PackageViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public PackageViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Packages");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
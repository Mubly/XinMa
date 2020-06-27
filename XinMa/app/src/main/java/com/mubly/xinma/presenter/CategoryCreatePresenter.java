package com.mubly.xinma.presenter;

import com.mubly.xinma.base.BasePresenter;
import com.mubly.xinma.iview.ICategoryCreateView;

import androidx.lifecycle.MutableLiveData;

public class CategoryCreatePresenter extends BasePresenter<ICategoryCreateView> {
    MutableLiveData<String> categoryName = new MutableLiveData<>();

    public void init(String categoryId) {

    }
}

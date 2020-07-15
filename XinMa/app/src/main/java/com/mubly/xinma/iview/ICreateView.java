package com.mubly.xinma.iview;

import com.mubly.xinma.base.BaseMvpView;
import com.mubly.xinma.db.dao.CategoryInfoDao;
import com.mubly.xinma.model.CategoryInfoBean;

import java.util.List;

public interface ICreateView extends BaseMvpView {
    void choosePhoto();
    void customeParam();
    void createCustomerParam(List<CategoryInfoBean>categoryInfoBeans);

    void showHasPromat();
}

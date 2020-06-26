package com.mubly.xinma.presenter;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.mubly.xinma.base.BasePresenter;
import com.mubly.xinma.iview.IDepartAndStaffCreateView;
import com.mubly.xinma.model.CompanyBean;
import com.mubly.xinma.utils.AppConfig;

import androidx.lifecycle.MutableLiveData;

public class DepartAndStaffCreatePresenter extends BasePresenter<IDepartAndStaffCreateView> {
    private MutableLiveData<String> companyName = new MutableLiveData<>();
    private MutableLiveData<String> departName = new MutableLiveData<>();

    public MutableLiveData<String> getCompanyName() {
        return companyName;
    }

    public MutableLiveData<String> getDepartName() {
        return departName;
    }

    public void initDeportMentCreate() {
        try {
            String companyInfo = AppConfig.companyInfo.get();
            CompanyBean company = JSON.parseObject(companyInfo, CompanyBean.class);
            if (null != company)
                companyName.setValue(company.getCompany());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void initStaffCreate() {

    }
}

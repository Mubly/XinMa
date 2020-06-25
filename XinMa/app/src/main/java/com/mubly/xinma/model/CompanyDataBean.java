package com.mubly.xinma.model;

import com.alibaba.fastjson.JSON;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.mubly.xinma.base.BaseModel;
import com.mubly.xinma.common.CallBack;
import com.mubly.xinma.net.JsonCallback;
import com.mubly.xinma.net.URLConstant;
import com.mubly.xinma.utils.AppConfig;

import java.util.List;

public class CompanyDataBean extends BaseModel {
    List<CompanyBean>Company;
    public static void getCompanyInfo(final CallBack<CompanyDataBean> callBack) {
        OkGo.<CompanyDataBean>post(URLConstant.API_Company_SelectCompany_Url)
                .execute(new JsonCallback<CompanyDataBean>() {
                    @Override
                    public void onSuccess(final Response<CompanyDataBean> response) {
                        if (response.body().getCode() == 1) {
                            AppConfig.companyInfo.put(JSON.toJSONString(response.body().Company.get(0)));
                            callBack.callBack(response.body());
                        }
                    }
                });
    }
}

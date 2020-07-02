package com.mubly.xinma.model;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.mubly.xinma.base.BaseModel;
import com.mubly.xinma.common.CallBack;
import com.mubly.xinma.net.JsonCallback;

import java.util.List;

import static com.mubly.xinma.net.URLConstant.API_Template_ListTemplate_URL;

public class TemplateData extends BaseModel {
    private List<TemplateBean> Template;

    public List<TemplateBean> getTemplate() {
        return Template;
    }

    public void setTemplate(List<TemplateBean> template) {
        Template = template;
    }

    public static void getPrintModeData(CallBack<List<TemplateBean>>callBack) {
        OkGo.<TemplateData>post(API_Template_ListTemplate_URL)
                .execute(new JsonCallback<TemplateData>() {
                    @Override
                    public void onSuccess(Response<TemplateData> response) {
                        if (response.body().getCode()==1){
                            callBack.callBack(response.body().getTemplate());
                        }
                    }
                });
    }
}

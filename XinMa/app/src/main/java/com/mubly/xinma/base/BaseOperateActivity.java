package com.mubly.xinma.base;

import com.bigkoo.pickerview.view.TimePickerView;
import com.mubly.xinma.common.CallBack;
import com.mubly.xinma.utils.DialogUtils;

public abstract class BaseOperateActivity<P extends BasePresenter<V>, V extends BaseMvpView> extends BaseActivity<P, V> {
    public TimePickerView timePickerView;
    private String selectTime;
    private boolean timeSelectInit;

    public void initTimeSelect() {
        timePickerView = DialogUtils.getTimeSelect(this, new CallBack<String>() {
            @Override
            public void callBack(String obj) {
                selectTime=obj;
            }
        });
    }

    public void selectTime(CallBack<String> callBack) {
        if (timeSelectInit)
            timePickerView.show();
    }
}

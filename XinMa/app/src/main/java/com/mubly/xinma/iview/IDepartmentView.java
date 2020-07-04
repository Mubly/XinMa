package com.mubly.xinma.iview;

import com.mubly.xinma.adapter.SmartAdapter;
import com.mubly.xinma.base.BaseMvpView;
import com.mubly.xinma.model.StaffBean;

public interface IDepartmentView extends BaseMvpView {
    void showRv(SmartAdapter adapter);
    void editDepartMent();
    void deletDepartMent();
    void toStaffInfo(StaffBean staffBean);
}

package com.mubly.xinma.iview;

import com.mubly.xinma.adapter.SmartAdapter;
import com.mubly.xinma.base.BaseMvpView;
import com.mubly.xinma.model.ProcessBean;

public interface IOperateLogListView extends BaseMvpView {
    void showRv(SmartAdapter adapter);
    void toDesPage(String OperateID,String type);
    void toChangeView(ProcessBean processBean);
    void toCheckView(ProcessBean processBean);
}

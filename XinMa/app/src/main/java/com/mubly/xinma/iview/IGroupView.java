package com.mubly.xinma.iview;

import com.mubly.xinma.adapter.SmartAdapter;
import com.mubly.xinma.base.BaseMvpView;
import com.mubly.xinma.model.GroupBean;

public interface IGroupView extends BaseMvpView {
    void showRv(SmartAdapter adapter);
    void toDepartAct(GroupBean groupBean);
}

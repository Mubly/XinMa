package com.mubly.xinma.presenter;

import com.mubly.xinma.R;
import com.mubly.xinma.adapter.SmartAdapter;
import com.mubly.xinma.base.BaseMvpView;
import com.mubly.xinma.base.BasePresenter;
import com.mubly.xinma.common.CallBack;
import com.mubly.xinma.iview.IGroupView;
import com.mubly.xinma.model.GroupBean;
import com.mubly.xinma.model.GroupData;

import java.util.ArrayList;
import java.util.List;

public class GroupPresenter extends BasePresenter<IGroupView> {
    SmartAdapter<GroupBean> adapter = null;
    List<GroupBean> dataList = new ArrayList<>();

    public void init() {
        adapter = new SmartAdapter<GroupBean>(dataList) {
            @Override
            public int getLayout(int viewType) {
                return R.layout.item_group_layout;
            }

            @Override
            public void dealView(VH holder, GroupBean data, int position) {
                holder.setText(R.id.item_group_left_tv, data.getDepart());
                holder.setText(R.id.item_group_right_tv, "1");
            }
        };
        getMvpView().showRv(adapter);
        getData();
    }

    public void getData() {
        GroupData.getGroupData(new CallBack<GroupData>() {
            @Override
            public void callBack(GroupData obj) {
                if (null != obj.getDepart()) {
                    dataList.addAll(obj.getDepart());
                    adapter.notifyDataSetChanged();
                }
            }
        });
    }
}

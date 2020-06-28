package com.mubly.xinma.presenter;

import android.text.TextUtils;
import android.widget.TextView;

import com.mubly.xinma.R;
import com.mubly.xinma.adapter.SmartAdapter;
import com.mubly.xinma.base.BasePresenter;
import com.mubly.xinma.common.CallBack;
import com.mubly.xinma.iview.ICategoryInfoSelectView;
import com.mubly.xinma.utils.EditViewUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CategoryInfoSelectPresenter extends BasePresenter<ICategoryInfoSelectView> {
    SmartAdapter<String> adapter = null;
    List<String> dataList = new ArrayList<>();
    Map<Integer, String> mapParam = new HashMap<>();

    public void init() {
        dataList.add("");
        adapter = new SmartAdapter<String>(dataList) {
            @Override
            public int getLayout(int viewType) {
                return R.layout.item_edit_layout;
            }

            @Override
            public void dealView(VH holder, String data, final int position) {
                if (mapParam.containsKey(position)) {
                    holder.getEditText(R.id.edit_et).setText(mapParam.get(position));
                }else {
                    holder.getEditText(R.id.edit_et).getText().clear();
                }
                EditViewUtil.EditDatachangeLister(holder.getEditText(R.id.edit_et), new CallBack<String>() {
                    @Override
                    public void callBack(String obj) {
                        if (!TextUtils.isEmpty(obj)) {
                            mapParam.put(position, obj);
                        }
                    }
                });

            }
        };
        getMvpView().showRv(adapter);
    }

    public void addNew() {
        dataList.add("");
        adapter.notifyDataSetChanged();
    }
}

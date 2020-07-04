package com.mubly.xinma.presenter;

import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.mubly.xinma.R;
import com.mubly.xinma.adapter.SmartAdapter;
import com.mubly.xinma.base.BaseMvpView;
import com.mubly.xinma.base.BasePresenter;
import com.mubly.xinma.common.CallBack;
import com.mubly.xinma.db.DataBaseUtils;
import com.mubly.xinma.db.XinMaDatabase;
import com.mubly.xinma.iview.IGroupView;
import com.mubly.xinma.model.GroupBean;
import com.mubly.xinma.model.GroupData;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

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
            public void dealView(VH holder, final GroupBean data, int position) {
                holder.setText(R.id.item_group_left_tv, data.getDepart());
                TextView staffCount = (TextView) holder.getChildView(R.id.item_group_right_tv);
                if (data.getStaffCount() > 0) {
                    staffCount.setVisibility(View.VISIBLE);
                    staffCount.setText(String.valueOf(data.getStaffCount()));
                } else {
                    staffCount.setVisibility(View.GONE);
                }
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        getMvpView().toDepartAct(data);
                    }
                });
            }
        };
        getMvpView().showRv(adapter);
        getData();
    }

    public void getData() {
        dataList.clear();
        Observable.create(new ObservableOnSubscribe<List<GroupBean>>() {
            @Override
            public void subscribe(ObservableEmitter<List<GroupBean>> emitter) throws Exception {
                emitter.onNext(XinMaDatabase.getInstance().groupBeanDao().getAll());
            }
        }).subscribeOn(Schedulers.io())
                .map(new Function<List<GroupBean>, List<GroupBean>>() {
                    @Override
                    public List<GroupBean> apply(List<GroupBean> groupBeans) throws Exception {
                        if (null != groupBeans) {
                            for (GroupBean groupBean : groupBeans) {
                                groupBean.setStaffCount(XinMaDatabase.getInstance().staffBeanDao().getCountByDepartId(groupBean.getDepartID()));
                            }
                        }
                        return groupBeans;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<GroupBean>>() {
                    @Override
                    public void accept(List<GroupBean> groupBeans) throws Exception {
                        if (null != groupBeans) {
                            dataList.addAll(groupBeans);
                        }
                        adapter.notifyDataSetChanged();
                    }
                });
    }
}

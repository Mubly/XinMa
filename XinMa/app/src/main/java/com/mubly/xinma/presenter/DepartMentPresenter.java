package com.mubly.xinma.presenter;

import android.view.View;

import com.mubly.xinma.R;
import com.mubly.xinma.adapter.SmartAdapter;
import com.mubly.xinma.base.BasePresenter;
import com.mubly.xinma.db.XinMaDatabase;
import com.mubly.xinma.iview.IDepartmentView;
import com.mubly.xinma.model.StaffBean;
import com.mubly.xinma.utils.CommUtil;

import java.util.ArrayList;
import java.util.List;

import androidx.lifecycle.MutableLiveData;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class DepartMentPresenter extends BasePresenter<IDepartmentView> {
    private MutableLiveData<String> topTitle = new MutableLiveData<>();

    public MutableLiveData<String> getTopTitle() {
        return topTitle;
    }

    SmartAdapter<StaffBean> adapter;
    List<StaffBean> dataList = new ArrayList<>();

    public void init(String departId) {
        topTitle.setValue("暂无员工");
        adapter = new SmartAdapter<StaffBean>(dataList) {
            @Override
            public int getLayout(int viewType) {
                return R.layout.item_staff_layout;
            }

            @Override
            public void dealView(VH holder, StaffBean data, int position) {
                holder.setText(R.id.item_staff_name, data.getStaff());
                if ((position + 1) == dataList.size()) {
                    holder.getChildView(R.id.item_staff_bottom_line).setVisibility(View.GONE);
                } else {
                    holder.getChildView(R.id.item_staff_bottom_line).setVisibility(View.VISIBLE);
                }
            }
        };
        getMvpView().showRv(adapter);
        initData(departId);
    }

    public void initData(final String departId) {
        Observable.create(new ObservableOnSubscribe<List<StaffBean>>() {
            @Override
            public void subscribe(ObservableEmitter<List<StaffBean>> emitter) throws Exception {
                emitter.onNext(XinMaDatabase.getInstance().staffBeanDao().getAllByDepartId(departId));
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<StaffBean>>() {
                    @Override
                    public void accept(List<StaffBean> staffBeanList) throws Exception {
                        dataList.clear();
                        if (null != staffBeanList) {
                            if (staffBeanList.size()>0){
                                topTitle.setValue("员工列表");
                            }
                            dataList.addAll(staffBeanList);
                            adapter.notifyDataSetChanged();
                        }
                    }
                });
    }

    public void delectDepartMent() {
    }

    public void edtDepartMent() {
    }
}

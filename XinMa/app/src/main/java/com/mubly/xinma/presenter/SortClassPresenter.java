package com.mubly.xinma.presenter;

import android.view.View;

import com.mubly.xinma.R;
import com.mubly.xinma.adapter.SmartAdapter;
import com.mubly.xinma.base.BasePresenter;
import com.mubly.xinma.db.XinMaDatabase;
import com.mubly.xinma.iview.IReturnView;
import com.mubly.xinma.iview.ISortClassView;
import com.mubly.xinma.model.CategoryBean;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class SortClassPresenter extends BasePresenter<ISortClassView> {
    SmartAdapter<CategoryBean> adapter = null;
    List<CategoryBean> dataList = new ArrayList<>();

    public void init() {
        adapter = new SmartAdapter<CategoryBean>(dataList) {
            @Override
            public int getLayout(int viewType) {
                return R.layout.item_staff_layout;
            }

            @Override
            public void dealView(VH holder, final CategoryBean data, int position) {
                holder.setText(R.id.item_staff_name, data.getCategory());
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        getMvpView().toCreate(data.getCategoryID(),data.getCategory());
                    }
                });
            }
        };
        getMvpView().showRv(adapter);
        initData();
    }

    public void initData() {
        Observable.create(new ObservableOnSubscribe<List<CategoryBean>>() {
            @Override
            public void subscribe(ObservableEmitter<List<CategoryBean>> emitter) throws Exception {
                emitter.onNext(XinMaDatabase.getInstance().categoryDao().getAll());
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<CategoryBean>>() {
                    @Override
                    public void accept(List<CategoryBean> categoryBeans) throws Exception {
                        dataList.clear();
                        if (null!=categoryBeans){
                            dataList.addAll(categoryBeans);
                        }
                        adapter.notifyDataSetChanged();
                    }
                });
    }
}

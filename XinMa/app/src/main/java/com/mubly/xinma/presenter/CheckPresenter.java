package com.mubly.xinma.presenter;

import com.mubly.xinma.adapter.MyPageAdapter;
import com.mubly.xinma.base.BasePresenter;
import com.mubly.xinma.common.CallBack;
import com.mubly.xinma.db.XinMaDatabase;
import com.mubly.xinma.fragment.CheckListFragment;
import com.mubly.xinma.iview.ICheckView;
import com.mubly.xinma.iview.IReturnView;
import com.mubly.xinma.model.CheckBean;
import com.mubly.xinma.model.CheckData;

import java.util.ArrayList;
import java.util.List;

import androidx.fragment.app.Fragment;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class CheckPresenter extends BasePresenter<ICheckView> {
    List<String> tabStrList = new ArrayList<>();
    List<Fragment> fragmentList = new ArrayList<>();
    MyPageAdapter adapter = null;

    public void init() {
        Observable.create(new ObservableOnSubscribe<List<String>>() {
            @Override
            public void subscribe(ObservableEmitter<List<String>> emitter) throws Exception {
                List<String> countList = new ArrayList<>();
                countList.add("待盘点(" + XinMaDatabase.getInstance().checkBeanDao().getCountByStatus("0") + ")");
                countList.add("盘点中(" + XinMaDatabase.getInstance().checkBeanDao().getCountByStatus("1") + ")");
                countList.add("已完成(" + XinMaDatabase.getInstance().checkBeanDao().getCountByStatus("2") + ")");
                emitter.onNext(countList);
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<String>>() {
                    @Override
                    public void accept(List<String> tabs) throws Exception {
                        tabStrList.clear();
                        fragmentList.clear();
                        tabStrList.addAll(tabs);
                        fragmentList.add(CheckListFragment.getInstance("0"));
                        fragmentList.add(CheckListFragment.getInstance("1"));
                        fragmentList.add(CheckListFragment.getInstance("2"));
                        adapter = new MyPageAdapter(getMvpView().getFM(), tabStrList, fragmentList);
                        getMvpView().showView(adapter, tabStrList);
                    }
                });
    }
}

package com.mubly.xinma.base;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;

import com.bigkoo.pickerview.view.OptionsPickerView;
import com.bigkoo.pickerview.view.TimePickerView;
import com.mubly.xinma.common.CallBack;
import com.mubly.xinma.common.GroupSelectCallBack;
import com.mubly.xinma.db.XinMaDatabase;
import com.mubly.xinma.model.CategoryBean;
import com.mubly.xinma.model.GroupBean;
import com.mubly.xinma.model.GroupSelectBean;
import com.mubly.xinma.model.StaffBean;
import com.mubly.xinma.utils.CommUtil;
import com.mubly.xinma.utils.DialogUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public abstract class BaseOperateActivity<P extends BasePresenter<V>, V extends BaseMvpView> extends BaseActivity<P, V> {
    private TimePickerView timePickerView;
    private OptionsPickerView categoryDialog, groupDialog;
    //    private String selectTime;
//    private CategoryBean selectCategoryBean;
    private GroupSelectCallBack groupSelectCallBack;
    private CallBack<String> timeSelectCallBack;
    private CallBack<CategoryBean> categorySelectCallBack;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (isTimeSelectInit()) initTimeSelect();
        if (isCategorySelectInit()) initCategoryData();
        if (isGroupSelectInit()) initGroupData();
    }

    private void initGroupData() {
        Observable.create(new ObservableOnSubscribe<GroupSelectBean>() {
            @Override
            public void subscribe(ObservableEmitter<GroupSelectBean> emitter) throws Exception {
                GroupSelectBean groupSelectBean = new GroupSelectBean();
                List<GroupBean> groupBeanList = XinMaDatabase.getInstance().groupBeanDao().getAll();
                List<List<StaffBean>> staffArrData = new ArrayList<>();
                GroupBean groupBean = new GroupBean();
                groupBean.setDepart("无限制");
                groupBeanList.add(0, groupBean);
                for (GroupBean g : groupBeanList) {
                    List<StaffBean> staffBeanList = new ArrayList<>();
                    StaffBean staffBean = new StaffBean();
                    staffBean.setStaff("无限制");
                    staffBeanList.add(staffBean);
                    staffBeanList.addAll(XinMaDatabase.getInstance().staffBeanDao().getAllByDepartId(g.getDepartID()));
                    staffArrData.add(staffBeanList);
                }
                groupSelectBean.setGroupDataList(groupBeanList);
                groupSelectBean.setStaffDataList(staffArrData);
                emitter.onNext(groupSelectBean);

            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<GroupSelectBean>() {
                    @Override
                    public void accept(GroupSelectBean groupSelectBean) throws Exception {
                        initGroupDialog(groupSelectBean);
                    }
                });
    }


    private void initTimeSelect() {
        timePickerView = DialogUtils.getTimeSelect(this, showSecond(), new CallBack<Date>() {
            @Override
            public void callBack(Date obj) {
                if (showSecond()) {
                    timeSelectCallBack.callBack(CommUtil.getTimeHMS(obj));
                } else {

                    timeSelectCallBack.callBack(CommUtil.getTime(obj));
                }

            }
        });
    }

    public void showTimeSelectDialog(CallBack<String> callBack) {
        timePickerView.show();
        timeSelectCallBack = callBack;
    }

    protected void initCategoryData() {
        Observable.create(new ObservableOnSubscribe<List<CategoryBean>>() {
            @Override
            public void subscribe(ObservableEmitter<List<CategoryBean>> emitter) throws Exception {
                emitter.onNext(XinMaDatabase.getInstance().categoryDao().getAll());
            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<CategoryBean>>() {
                    @Override
                    public void accept(List<CategoryBean> categoryBeans) throws Exception {
                        initCategoryDialog(categoryBeans);
                    }
                });
    }

    private void initCategoryDialog(List<CategoryBean> categoryBeans) {
        categoryDialog = DialogUtils.categoryChooseDialog(this, new DialogUtils.SelectListener() {
            @Override
            public void selected(int index1, int index2, int index3, View v) {
                categorySelectCallBack.callBack(categoryBeans.get(index1));
            }
        });
        categoryDialog.setPicker(categoryBeans);
    }

    public void showCategorySelectDialog(CallBack<CategoryBean> callBack) {
        categoryDialog.show();
        categorySelectCallBack = callBack;
    }


    private void initGroupDialog(GroupSelectBean groupSelectBean) {
        groupDialog = DialogUtils.showSelectDialog(this, new DialogUtils.SelectListener() {
            @Override
            public void selected(int index1, int index2, int index3, View v) {
                groupSelectCallBack.callback(groupSelectBean.getGroupDataList().get(index1), groupSelectBean.getStaffDataList().get(index1).get(index2));
            }
        });
        groupDialog.setPicker(groupSelectBean.getGroupDataList(), groupSelectBean.getStaffDataList());
    }

    public void showGroupStaffDialog(GroupSelectCallBack callBack) {
        this.groupSelectCallBack = callBack;
        groupDialog.show();
    }

    public abstract boolean isTimeSelectInit();

    public abstract boolean showSecond();

    public abstract boolean isCategorySelectInit();

    public abstract boolean isGroupSelectInit();


}

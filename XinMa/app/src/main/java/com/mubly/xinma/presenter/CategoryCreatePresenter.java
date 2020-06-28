package com.mubly.xinma.presenter;

import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mubly.xinma.R;
import com.mubly.xinma.adapter.SmartAdapter;
import com.mubly.xinma.base.BasePresenter;
import com.mubly.xinma.db.XinMaDatabase;
import com.mubly.xinma.iview.ICategoryCreateView;
import com.mubly.xinma.model.CategoryInfoBean;

import java.util.ArrayList;
import java.util.List;

import androidx.lifecycle.MutableLiveData;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class CategoryCreatePresenter extends BasePresenter<ICategoryCreateView> {
    MutableLiveData<String> categoryName = new MutableLiveData<>();
    List<CategoryInfoBean> dataList = new ArrayList<>();
    SmartAdapter<CategoryInfoBean> adapter = null;
    private String categoryId;
    private String categoryNameStr;

    public MutableLiveData<String> getCategoryName() {
        return categoryName;
    }

    public void init(String categoryId, String categoryNameStr) {
        this.categoryId = categoryId;
        this.categoryNameStr = categoryNameStr;
        if (null != categoryNameStr) {
            categoryName.setValue(categoryNameStr);
        }
        dataList.add(new CategoryInfoBean("暂无参数", "prompt"));
        adapter = new SmartAdapter<CategoryInfoBean>(dataList) {
            @Override
            public int getLayout(int viewType) {
                return R.layout.item_categroy_create_edt_layout;
            }

            @Override
            public void dealView(VH holder, CategoryInfoBean data, int position) {
                TextView labelTv = (TextView) holder.getChildView(R.id.item_categroy_crate_label);
                EditText inputEt = holder.getEditText(R.id.item_categroy_crate_input_et);
                ImageView iconImg = (ImageView) holder.getChildView(R.id.item_categroy_crate_input_icon);
                if (data.getInfoType().equals("prompt")) {
                    labelTv.setText(data.getInfoName());
                    inputEt.setVisibility(View.INVISIBLE);
                    iconImg.setVisibility(View.INVISIBLE);
                    iconImg.setEnabled(false);
                } else if (data.getInfoType().equals("Text")) {
                    inputEt.setVisibility(View.VISIBLE);
                    iconImg.setVisibility(View.VISIBLE);
                    labelTv.setText("文本");
                    iconImg.setImageResource(R.mipmap.arrow_right_gray);
                    iconImg.setEnabled(false);
                } else if (data.getInfoType().equals("Date")) {
                    inputEt.setVisibility(View.VISIBLE);
                    iconImg.setVisibility(View.VISIBLE);
                    labelTv.setText("日期");
                    iconImg.setImageResource(R.mipmap.arrow_right_gray);
                    iconImg.setEnabled(false);
                } else if (data.getInfoType().equals("Select")) {
                    inputEt.setVisibility(View.VISIBLE);
                    iconImg.setVisibility(View.VISIBLE);
                    iconImg.setImageResource(R.drawable.ic_edit_icon);
                    labelTv.setText("选择项");
                    iconImg.setEnabled(true);
                }
                iconImg.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        getMvpView().openSelect();
                    }
                });
            }
        };
        getMvpView().showRv(adapter);
        initData(categoryId);
    }

    private void initData(final String categoryId) {
        Observable.create(new ObservableOnSubscribe<List<CategoryInfoBean>>() {
            @Override
            public void subscribe(ObservableEmitter<List<CategoryInfoBean>> emitter) throws Exception {
                emitter.onNext(XinMaDatabase.getInstance().categoryInfoDao().getAllById(categoryId));
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<CategoryInfoBean>>() {
                    @Override
                    public void accept(List<CategoryInfoBean> categoryInfoBeans) throws Exception {
                        if (null != categoryInfoBeans && categoryInfoBeans.size() > 0) {
                            dataList.clear();
                            dataList.addAll(categoryInfoBeans);
                            adapter.notifyDataSetChanged();
                        }
                    }
                });
    }

    public void creatText() {
        if (dataList.size() == 1 && dataList.get(0).getInfoType().equals("prompt")) {
            dataList.get(0).setInfoType("Text");
        } else {
            dataList.add(new CategoryInfoBean(categoryId, categoryNameStr, "Text"));
        }
        adapter.notifyDataSetChanged();
    }

    public void creatSelect() {
        if (dataList.size() == 1 && dataList.get(0).getInfoType().equals("prompt")) {
            dataList.get(0).setInfoType("Select");
        } else {
            dataList.add(new CategoryInfoBean(categoryId, categoryNameStr, "Select"));
        }
        adapter.notifyDataSetChanged();
    }

    public void creatTime() {
        if (dataList.size() == 1 && dataList.get(0).getInfoType().equals("prompt")) {
            dataList.get(0).setInfoType("Date");
        } else {
            dataList.add(new CategoryInfoBean(categoryId, categoryNameStr, "Date"));
        }
        adapter.notifyDataSetChanged();
    }
}

package com.mubly.xinma.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.mubly.xinma.R;
import com.mubly.xinma.adapter.SmartAdapter;
import com.mubly.xinma.base.BaseActivity;
import com.mubly.xinma.base.BasePresenter;
import com.mubly.xinma.databinding.ActivityCategoryCreateBinding;
import com.mubly.xinma.iview.ICategoryCreateView;
import com.mubly.xinma.presenter.CategoryCreatePresenter;
import com.mubly.xinma.utils.CommUtil;
import com.mubly.xinma.utils.LiveDataBus;
import com.shehuan.nicedialog.BaseNiceDialog;
import com.shehuan.nicedialog.NiceDialog;
import com.shehuan.nicedialog.ViewConvertListener;
import com.shehuan.nicedialog.ViewHolder;

/**
 * 创建类别/类别详情页
 */
public class CategoryCreateActivity extends BaseActivity<CategoryCreatePresenter, ICategoryCreateView> implements ICategoryCreateView {
    ActivityCategoryCreateBinding binding = null;
    String categoryId;
    String categoryName;
    private int currentIndex;

    @Override
    public void initView() {
        if (TextUtils.isEmpty(categoryId) && TextUtils.isEmpty(categoryName)) {
            setTitle("创建分类");
        } else {
            setTitle("分类");
            binding.categoryDelect.setVisibility(View.VISIBLE);
        }

        setRightTv("保存");
        binding.setVm(mPresenter);
        binding.setLifecycleOwner(this);
        mPresenter.init(categoryId, categoryName);
    }

    @Override
    public void onRightClickEvent(TextView rightTv) {
        super.onRightClickEvent(rightTv);
        categoryName = binding.categoryNameEt.getText().toString();
        if (TextUtils.isEmpty(categoryName)) {
            CommUtil.ToastU.showToast("请输入分类名称");
            return;
        }
        mPresenter.upDateAck(categoryId, categoryName);
    }

    @Override
    protected CategoryCreatePresenter createPresenter() {
        return new CategoryCreatePresenter();
    }

    @Override
    protected void getLayoutId() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_category_create);
        categoryId = getIntent().getStringExtra("categoryId");
        categoryName = getIntent().getStringExtra("categoryName");
    }

    @Override
    public void initEvent() {
        super.initEvent();
        LiveDataBus.get().with("selectParam", String.class).observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                mPresenter.reshSelectData(s, currentIndex);
            }
        });

    }

    @Override
    public void showRv(SmartAdapter adapter) {
        binding.paramRv.setLayoutManager(new LinearLayoutManager(this));
        binding.paramRv.setAdapter(adapter);
    }

    @Override
    public void openSelect(String InfoValues, int index) {
        this.currentIndex = index;
        Intent intent = new Intent(this, CategoryInfoSelectActivity.class);
        intent.putExtra("selectParam", InfoValues);
        startActivity(intent);
        startPage();
    }

    @Override
    public void delectParate() {
        forDelect();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LiveDataBus.get().with("SortClass").setValue(true);
    }


    private void forDelect() {
        NiceDialog.init().setLayoutId(R.layout.dialog_text_chose_promapt)
                .setConvertListener(new ViewConvertListener() {
                    @Override
                    protected void convertView(ViewHolder holder, BaseNiceDialog dialog) {
                        holder.setText(R.id.dialog_tittle_tv, "提示");
                        holder.setText(R.id.dialog_content_tv, "确定删除？");
                        holder.getView(R.id.dialog_promapt_cancle).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.dismiss();
                            }
                        });
                        holder.getView(R.id.dialog_promapt_ack).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.dismiss();
                                mPresenter.delectCategory();
                            }
                        });
                    }
                }).setMargin(60)
                .show(getSupportFragmentManager());
    }
}

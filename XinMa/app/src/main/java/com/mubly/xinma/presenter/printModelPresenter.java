package com.mubly.xinma.presenter;

import android.app.Activity;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.mubly.xinma.R;
import com.mubly.xinma.adapter.SmartAdapter;
import com.mubly.xinma.base.BasePresenter;
import com.mubly.xinma.base.CrossApp;
import com.mubly.xinma.common.CallBack;
import com.mubly.xinma.iview.IprintModelView;
import com.mubly.xinma.model.TemplateBean;
import com.mubly.xinma.model.TemplateData;

import java.util.ArrayList;
import java.util.List;

public class printModelPresenter extends BasePresenter<IprintModelView> {
    private List<TemplateBean> data = new ArrayList<>();
    SmartAdapter<TemplateBean> adapter = null;
    private Activity activity;
    private TemplateBean selectBean;

    public void init(Activity activity) {
        this.activity = activity;
        adapter = new SmartAdapter<TemplateBean>(data) {
            @Override
            public int getLayout(int viewType) {
                return R.layout.item_print_model_layout;
            }

            @Override
            public void dealView(VH holder, TemplateBean data, int position) {
                holder.setText(R.id.print_model_name, data.getTemplateName());
                ImageView imageView = (ImageView) holder.getChildView(R.id.print_model_img);
                RequestOptions options = new RequestOptions().placeholder(R.mipmap.img_defaut);
                options.disallowHardwareConfig();
                Glide.with(CrossApp.get()).asBitmap().load(ImageUrlPersenter.getPrintModeurl(data.getViews())).apply(options).into(imageView);
                if (position == getSelectIndex()) {
                    holder.getChildView(R.id.print_mode_layout).setSelected(true);
                } else {
                    holder.getChildView(R.id.print_mode_layout).setSelected(false);
                }
                holder.getChildView(R.id.print_mode_layout).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        selectIndex(position);
                    }
                });
            }
        };
        adapter.selectIndex(-1);
        getMvpView().showRv(adapter);
        initData();
    }

    private void initData() {
        TemplateData.getPrintModeData(new CallBack<List<TemplateBean>>() {
            @Override
            public void callBack(List<TemplateBean> obj) {
                data.clear();
                if (null != obj) {
                    data.addAll(obj);
                }
                adapter.notifyDataSetChanged();
            }
        });
    }

    public TemplateBean getSelectBean() {

        return data.get(adapter.getSelectIndex());
    }
}

package com.mubly.xinma.adapter;

import android.view.View;

import com.mubly.xinma.R;
import com.mubly.xinma.common.CallBack;
import com.mubly.xinma.databinding.ItemHomeMenuLayoutBinding;
import com.mubly.xinma.model.HomeMenuBean;

import java.util.List;

import androidx.annotation.NonNull;

public class HomeMenuAdapter extends NBaseBindingAdapter<List<HomeMenuBean>, ItemHomeMenuLayoutBinding> {
    CallBack<HomeMenuBean> callBack = null;

    public HomeMenuAdapter(List<HomeMenuBean> list, CallBack<HomeMenuBean> callBack) {
        super(list);
        this.callBack = callBack;
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_home_menu_layout;
    }

    @Override
    public void onBindViewHolder(@NonNull BaseHolder holder, final int position) {
        ((ItemHomeMenuLayoutBinding) holder.bind).setBean(data.get(position));
        holder.bind.executePendingBindings();
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (null!=callBack)
                callBack.callBack(data.get(position));
            }
        });
    }
}

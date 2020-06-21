package com.mubly.xinma.adapter;

import com.mubly.xinma.R;
import com.mubly.xinma.databinding.ItemHomeMenuLayoutBinding;
import com.mubly.xinma.model.HomeMenuBean;

import java.util.List;

import androidx.annotation.NonNull;

public class HomeMenuAdapter extends NBaseBindingAdapter<List<HomeMenuBean>, ItemHomeMenuLayoutBinding> {
    public HomeMenuAdapter(List<HomeMenuBean> list) {
        super(list);
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_home_menu_layout;
    }

    @Override
    public void onBindViewHolder(@NonNull BaseHolder holder, int position) {
        ((ItemHomeMenuLayoutBinding) holder.bind).setBean(data.get(position));
        holder.bind.executePendingBindings();
    }
}

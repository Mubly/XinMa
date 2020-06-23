package com.mubly.xinma.adapter;

import com.mubly.xinma.R;
import com.mubly.xinma.databinding.ItemAssetsLayoutBinding;
import com.mubly.xinma.model.AssetBean;
import com.mubly.xinma.presenter.ImageUrlPersenter;

import java.util.List;

import androidx.annotation.NonNull;

public class AssetsListAdapter extends NBaseBindingAdapter<List<AssetBean>, ItemAssetsLayoutBinding> {

    public AssetsListAdapter(List<AssetBean> list) {
        super(list);
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_assets_layout;
    }

    @Override
    public void onBindViewHolder(@NonNull BaseHolder holder, int position) {
        ((ItemAssetsLayoutBinding) holder.getBind()).setBean(data.get(position));
        ((ItemAssetsLayoutBinding) holder.getBind()).setImgPersent(new ImageUrlPersenter());
        holder.getBind().executePendingBindings();
    }
}

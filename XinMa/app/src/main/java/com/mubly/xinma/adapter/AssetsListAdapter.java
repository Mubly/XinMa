package com.mubly.xinma.adapter;

import android.view.View;

import com.mubly.xinma.R;
import com.mubly.xinma.databinding.ItemAssetsLayoutBinding;
import com.mubly.xinma.model.AssetBean;
import com.mubly.xinma.presenter.ImageUrlPersenter;

import java.util.List;

import androidx.annotation.NonNull;

public class AssetsListAdapter extends NBaseBindingAdapter<List<AssetBean>, ItemAssetsLayoutBinding> {
    OnItemClickListener onItemClickListener = null;

    public AssetsListAdapter(List<AssetBean> list) {
        super(list);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_assets_layout;
    }

    @Override
    public void onBindViewHolder(@NonNull BaseHolder holder, final int position) {
        ((ItemAssetsLayoutBinding) holder.getBind()).setBean(data.get(position));
        ((ItemAssetsLayoutBinding) holder.getBind()).setImgPersent(new ImageUrlPersenter());
        holder.getBind().executePendingBindings();
        ((ItemAssetsLayoutBinding) holder.getBind()).toAssetsLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemClickListener.itemClick(data.get(position), true, position);
            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (null != onItemClickListener)
                    onItemClickListener.itemClick(data.get(position), false, position);
            }
        });
    }

    public static interface OnItemClickListener {
        void itemClick(AssetBean data, boolean isLog, int index);
    }
}

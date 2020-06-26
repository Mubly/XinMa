package com.mubly.xinma.adapter;

import android.view.View;

import com.mubly.xinma.R;
import com.mubly.xinma.databinding.ItemAssetsCallbackLayoutBinding;
import com.mubly.xinma.databinding.ItemAssetsLayoutBinding;
import com.mubly.xinma.model.AssetBean;
import com.mubly.xinma.presenter.ImageUrlPersenter;

import java.util.List;

import androidx.annotation.NonNull;

public class AssetsListCallBackAdapter extends NBaseBindingAdapter<List<AssetBean>, ItemAssetsCallbackLayoutBinding> {
    OnItemClickListener onItemClickListener = null;

    public AssetsListCallBackAdapter(List<AssetBean> list) {
        super(list);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_assets_callback_layout;
    }

    @Override
    public void onBindViewHolder(@NonNull BaseHolder holder, final int position) {
        ((ItemAssetsCallbackLayoutBinding) holder.getBind()).setBean(data.get(position));
        ((ItemAssetsCallbackLayoutBinding) holder.getBind()).setImgPersent(new ImageUrlPersenter());
        holder.getBind().executePendingBindings();
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (null != onItemClickListener)
                    onItemClickListener.itemClick(data.get(position), position);
            }
        });
    }

    public static interface OnItemClickListener {
        void itemClick(AssetBean data, int index);
    }
}

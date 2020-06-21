package com.mubly.xinma.adapter;

import android.content.Context;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

public abstract class NBaseBindingAdapter<DATA extends List, BIND extends ViewDataBinding> extends RecyclerView.Adapter<NBaseBindingAdapter.BaseHolder> {
    protected DATA data;
    protected Context mContext;

    public NBaseBindingAdapter(DATA list) {
        this.data = list;
    }

    @Override
    public BaseHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        BIND bind = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), getLayoutId(), parent, false);
        return new BaseHolder(bind);
    }

    public abstract int getLayoutId();

    @Override
    public int getItemCount() {
        return data.size();
    }

    public static class BaseHolder<BIND extends ViewDataBinding> extends RecyclerView.ViewHolder {
        public BIND bind;

        public BaseHolder(@NonNull BIND bind) {
            super(bind.getRoot());
            this.bind = bind;
        }

        public BIND getBind() {
            return bind;
        }
    }
}

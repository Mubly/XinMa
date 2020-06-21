package com.mubly.xinma.adapter;

import java.util.List;

/**
 * Created by PC on 2018/12/28.
 */

public abstract class SmartAdapter<T> extends BaseRecyclerViewAdapter<T> {
    private int selectIndex = 0;

    public SmartAdapter(List<T> data) {
        super(data);
    }

    @Override
    public int getLayoutId(int viewType) {
        return getLayout(viewType);
    }

    @Override
    public void convert(VH holder, T data, int position) {
        dealView(holder, data, position);
    }

    @Override
    public int getItemViewTypes(int poition, List<T> data) {
        return poition;
    }

    public abstract int getLayout(int viewType);

    public abstract void dealView(VH holder, T data, int position);

    public void selectIndex(int selectIndex) {
        this.selectIndex = selectIndex;
        notifyDataSetChanged();
    }

    public int getSelectIndex() {
        return selectIndex;
    }
}

package com.mubly.xinma.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mubly.xinma.R;
import com.mubly.xinma.activity.CheckDetialActivity;
import com.mubly.xinma.adapter.SmartAdapter;
import com.mubly.xinma.databinding.FragmentCheckListBinding;
import com.mubly.xinma.db.XinMaDatabase;
import com.mubly.xinma.model.CheckBean;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class CheckListFragment extends Fragment {
    FragmentCheckListBinding binding = null;
    SmartAdapter<CheckBean> adapter = null;
    List<CheckBean> dataList = new ArrayList<>();
    private String type;

    public static CheckListFragment getInstance(String type) {
        CheckListFragment fragment = new CheckListFragment();
        Bundle bundle = new Bundle();
        bundle.putString("type", type);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_check_list, container, false);
        binding = DataBindingUtil.bind(view);
        type = getArguments().getString("type");
        init();
        return view;
    }

    private void init() {
        adapter = new SmartAdapter<CheckBean>(dataList) {
            @Override
            public int getLayout(int viewType) {
                return R.layout.item_check_layout;
            }

            @Override
            public void dealView(VH holder, CheckBean data, int position) {
                String statusName=data.getStatus().equals("0")?"待盘点":(data.getStatus().equals("1")?"盘点中":"已完成");
                holder.setText(R.id.item_check_tv, statusName + "：" + data.getItems() + "条");
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent=new Intent(getContext(), CheckDetialActivity.class);
                        intent.putExtra("checkId",data.getCheckID());
                        intent.putExtra("checkTime",data.getCreateTime());
                        startActivity(intent);
                    }
                });
            }
        };
        binding.checkListRv.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.checkListRv.setAdapter(adapter);
        initData();
    }

    private void initData() {
        Observable.create(new ObservableOnSubscribe<List<CheckBean>>() {
            @Override
            public void subscribe(ObservableEmitter<List<CheckBean>> emitter) throws Exception {
                emitter.onNext(XinMaDatabase.getInstance().checkBeanDao().getAllByStatus(type));
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<CheckBean>>() {
                    @Override
                    public void accept(List<CheckBean> checkBeanList) throws Exception {
                        dataList.clear();
                        if (null != checkBeanList) {
                            dataList.addAll(checkBeanList);
                            adapter.notifyDataSetChanged();
                        }
                        if (dataList.size() > 0)
                            binding.tvEmptyPromapt.setVisibility(View.GONE);
                        else
                            binding.tvEmptyPromapt.setVisibility(View.VISIBLE);
                    }
                });
    }
}

package com.mubly.xinma.works;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.mubly.xinma.common.CallBack;
import com.mubly.xinma.db.XinMaDatabase;
import com.mubly.xinma.model.SynDataFastData;

public class SynFastWork extends Worker {
    public SynFastWork(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        SynDataFastData.synData(new CallBack<SynDataFastData>() {
            @Override
            public void callBack(SynDataFastData obj) {
         new Thread(new Runnable() {
             @Override
             public void run() {
                 if (null != obj.getAsset())
                     XinMaDatabase.getInstance().assetBeanDao().insertAll(obj.getAsset());
                 if (null!=obj.getAssetInfo())
                     XinMaDatabase.getInstance().assetInfoBeanDao().insertAll(obj.getAssetInfo());
                 if (null!=obj.getOperate())
                     XinMaDatabase.getInstance().operateBeanDao().insertAll(obj.getOperate());
                 if (null!=obj.getProcess())
                     XinMaDatabase.getInstance().processBeanDao().insertAll(obj.getProcess());
             }
         }).start();
            }
        });
        return Result.success();
    }
}

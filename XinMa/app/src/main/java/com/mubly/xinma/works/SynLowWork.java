package com.mubly.xinma.works;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.mubly.xinma.common.CallBack;
import com.mubly.xinma.db.XinMaDatabase;
import com.mubly.xinma.model.SynDataLowData;

public class SynLowWork extends Worker {
    public SynLowWork(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        SynDataLowData.synData(new CallBack<SynDataLowData>() {
            @Override
            public void callBack(SynDataLowData obj) {
                if (null != obj.getCategory())
                    XinMaDatabase.getInstance().categoryDao().insertAll(obj.getCategory());
                if (null!=obj.getCategoryInfo())
                    XinMaDatabase.getInstance().categoryInfoDao().insertAll(obj.getCategoryInfo());
                if (null!=obj.getCheck())
                    XinMaDatabase.getInstance().checkBeanDao().insertAll(obj.getCheck());
                if (null!=obj.getDepart())
                    XinMaDatabase.getInstance().groupBeanDao().insertAll(obj.getDepart());
                if (null!=obj.getInventory())
                    XinMaDatabase.getInstance().inventoryBeanDao().insertAll(obj.getInventory());
                if (null!=obj.getProperty())
                    XinMaDatabase.getInstance().propertyBeanDao().insertAll(obj.getProperty());
                if (null!=obj.getStaff())
                    XinMaDatabase.getInstance().staffBeanDao().insertAll(obj.getStaff());
            }
        });
        return null;
    }
}

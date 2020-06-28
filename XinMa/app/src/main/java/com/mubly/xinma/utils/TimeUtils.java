package com.mubly.xinma.utils;

/**
 * Created by PC on 2018/11/13.
 */


import android.os.Handler;
import android.os.Message;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

/**
 * 倒计时
 */
public class TimeUtils {

    private int time = 60;
    private Timer mTimer;
    private TimerTask mTimerTask;
    private TextView mTvSendCode;
    private String mSendPrompt;//重新发送提示


    public TimeUtils(TextView tvSendCode, String sendPrompt) {
        super();
        this.mTvSendCode = tvSendCode;
        this.mSendPrompt = sendPrompt;
    }

    public void runTimer() {
        mTimer = new Timer();
        mTimerTask = new TimerTask() {
            @Override
            public void run() {
                time--;
                Message msg = handler.obtainMessage();
                msg.what = 1;
                handler.sendMessage(msg);
            }
        };
        mTimer.schedule(mTimerTask, 100, 1000);
    }

    public void cancelTimer() {
        if (mTimerTask != null)
            mTimerTask.cancel();
        if (mTimer != null)
            mTimer.cancel();
        mTimerTask = null;
        mTimer = null;
    }

    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    if (time > 0) {
                        mTvSendCode.setEnabled(false);
                        mTvSendCode.setText(time + "s");
//                        mTvSendCode.setText(mSendPrompt + "(" + time + "s)");
                    } else {
                        mTvSendCode.setText(mSendPrompt);
                        mTvSendCode.setEnabled(true);
                        cancelTimer();
                        time = 60;
                    }
                    break;
                default:
                    break;
            }
        }

        ;
    };


}




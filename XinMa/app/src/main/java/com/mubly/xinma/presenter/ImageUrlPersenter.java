package com.mubly.xinma.presenter;

import android.text.TextUtils;
import android.util.Log;

import com.mubly.xinma.R;
import com.mubly.xinma.common.Constant;

public class ImageUrlPersenter {
    public String getAssetListUrl(String headUrl) {

        return TextUtils.isEmpty(headUrl) ? null : Constant.ASSET_HRADIMG_LITTLE_URL + headUrl;
    }

    public static String getPrintModeurl(String imgUrl) {
//        return "https://dss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=2534506313,1688529724&fm=26&gp=0.jpg";
        return TextUtils.isEmpty(imgUrl) ? null : Constant.PRINT_MODE_IMG_SCAN_URL + imgUrl;
    }

    public int getAssetIcon(String status) {
        if (TextUtils.isEmpty(status)) return -1;
        if (status.equals("1")) {
            return R.drawable.ic_ldle_icon_bg;
        } else if (status.equals("3")) {
            return R.drawable.ic_using_icon_bg;
        } else if (status.equals("5")) {
            return R.drawable.ic_brrorow_icon_bg;
        } else if (status.equals("6")) {
            return R.drawable.ic_repair_icon_bg;
        } else if (status.equals("8")) {
            return R.drawable.ic_dispose_icon_bg;
        }
        return -1;
    }
}

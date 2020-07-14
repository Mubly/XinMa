package com.mubly.xinma.presenter;

import android.text.TextUtils;
import android.util.Log;

import com.mubly.xinma.R;
import com.mubly.xinma.common.Constant;
import com.mubly.xinma.utils.AppConfig;

public class ImageUrlPersenter {
    private String companyId;

    public ImageUrlPersenter(String companyId) {
        this.companyId = companyId;
    }

    public ImageUrlPersenter() {
        companyId = AppConfig.companyId.get();
    }

    public String getAssetListUrl(String headUrl) {
        Log.i("imgUrl", Constant.ASSET_HRADIMG_LITTLE_URL + headUrl);
        return TextUtils.isEmpty(headUrl) ? null : Constant.ASSET_HRADIMG_LITTLE_URL + companyId + "/" + headUrl;
    }
    public String getAssetListBigUrl(String headUrl) {
        Log.i("imgUrl", Constant.ASSET_HRADIMG_LITTLE_URL + headUrl);
        return TextUtils.isEmpty(headUrl) ? null : Constant.ASSET_HRADIMG_BIG_URL + companyId + "/" + headUrl;
    }
    public static String getPrintModeurl(String imgUrl) {
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

package com.mubly.xinma.presenter;

import android.util.Log;

import com.mubly.xinma.common.Constant;

public class ImageUrlPersenter {
    public String getAssetListUrl(String headUrl) {
        Log.i("ImgUrlLog", Constant.ASSET_HRADIMG_LITTLE_URL + headUrl);
        return Constant.ASSET_HRADIMG_LITTLE_URL + headUrl;
    }
}

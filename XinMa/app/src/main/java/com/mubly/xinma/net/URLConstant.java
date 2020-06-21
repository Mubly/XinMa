package com.mubly.xinma.net;

import com.mubly.xinma.common.Constant;

/**
 * Created by admin on 2016/6/13.
 */
public class URLConstant {

    //主地址
    private static String MAIN_URL = "";

    //基址
    public static String BASE_URL = "";
    //进入后台
    public static String ENTER_SERVICE = "";
    //聊天服务器
    public static String WEB_SOCKET = "";
    public static String WEB_BASEURL = "http://baoku-app.51kupai.com/#/";

    static {
        if (EnvironmentType.DEVELOP.equal(Constant.flag)) {
            MAIN_URL = "172.29.12.97:18080";
            BASE_URL = "http://" + MAIN_URL;
        } else if (EnvironmentType.TEST.equal(Constant.flag)) {
            MAIN_URL = "api.ezc365.cn";
            BASE_URL = "http://" + MAIN_URL;
        } else if (EnvironmentType.ONLINE.equal(Constant.flag)) {
            MAIN_URL = "qomolangma.hoecan.com";
            BASE_URL = "https://" + MAIN_URL;
            WEB_BASEURL = "https://lhotse.hoecan.com/#";
            WEB_SOCKET = "wss://qomolangma.hoecan.com";
        }
    }

    //体验登录
    public static final String EXPERIENCE_LOGIN_URL = BASE_URL + "/AppUser/ExperienceLogin";
    //资产数据
    public static final String ASSET_DATA_DOWNLOAD_URL = BASE_URL + "/API_Asset/ListAsset";
}

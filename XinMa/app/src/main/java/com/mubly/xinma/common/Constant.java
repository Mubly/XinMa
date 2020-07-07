package com.mubly.xinma.common;


import com.mubly.xinma.R;
import com.mubly.xinma.net.EnvironmentType;
import com.mubly.xinma.net.URLConstant;
import com.mubly.xinma.utils.AppConfig;
import com.mubly.xinma.utils.StringUtils;

import java.util.List;

/**
 * Created by admin on 2016/6/13.
 */
public class Constant {

    //0代表开发环境,1代表测试环境,2代表线上环境
    public static final EnvironmentType flag = EnvironmentType.TEST;
    public static final String APP_ID = "";//
//    资产缩略图
    public static final String ASSET_HRADIMG_LITTLE_URL = URLConstant.BASE_IMG_URL + "Headimg/s/"+ AppConfig.companyId.get()+"/";
//    模板缩略图
    public static final String PRINT_MODE_IMG_SCAN_URL = URLConstant.PRINT_MODE_BASE_URL + "img/Template/";
    public static String[] menuName = {"资产", "创建", "盘点", "领用", "借用", "归还", "维修", "处置", "分析报表", "资产分类", "组织架构","设置"};
    public static int[] menuIconResId = {R.mipmap.menu_asset, R.mipmap.menu_create, R.mipmap.menu_check, R.mipmap.menu_get_use,
            R.mipmap.menu_borrow, R.mipmap.menu_return, R.mipmap.menu_repair, R.mipmap.menu_handle,
            R.mipmap.menu_analysis, R.mipmap.menu_class, R.mipmap.menu_group,R.drawable.ic_setting_blue_icon
    };

}

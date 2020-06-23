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

    public static String[] menuName = {"资产", "创建", "盘点", "领用", "借用", "归还", "维修", "处置", "分析报表", "资产分类", "组织架构"};
    public static int[] menuIconResId = {R.drawable.menu_asset, R.drawable.menu_create, R.drawable.menu_check, R.drawable.menu_get_use,
            R.drawable.menu_borrow, R.drawable.menu_return, R.drawable.menu_repair, R.drawable.menu_handle,
            R.drawable.menu_analysis, R.drawable.menu_class, R.drawable.menu_group
    };

}

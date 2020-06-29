package com.mubly.xinma.net;

import android.util.Printer;

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
    //图片服务器
    public static String BASE_IMG_URL = "http://asset.ymade.cn/";

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
        }
    }

    //app启动
    public static final String APP_START_URL = BASE_URL + "/APP/Index";
    //    注册
    public static final String REGISTER_URL = BASE_URL + "/AppUser/Reg";

    //    登录
    public static final String LOGIN_URL = BASE_URL + "/AppUser/Login";

    //    重复注册校验
    public static final String CHECK_REPEAT_URL = BASE_URL + "/AppUser/CheckRepeat";
    //    手机验证码(注册)
    public static final String REGISTER_GAIN_PHONE_CODE_URL = BASE_URL + "/APP/SendCode";

    //    手机验证码（忘记密码）
    public static final String FORGET_GAIN_PHONE_CODE_URL = BASE_URL + "/APP/ForgetCode";

    //体验登录
    public static final String EXPERIENCE_LOGIN_URL = BASE_URL + "/AppUser/ExperienceLogin";

    //    退出登录
    public static final String EXPERIENCE_LOGIN_OUT_URL = BASE_URL + "/AppUser/Logout";


    //用户查询
    public static final String GAIN_USERINFO_URL = BASE_URL + "/AppUser/SelectUser";

    //资产数据
    public static final String ASSET_DATA_DOWNLOAD_URL = BASE_URL + "/API_Asset/ListAsset";

    //资产添加修改
    public static final String ASSET_DATA_UpdateAsset_URL = BASE_URL + "/API_Asset/UpdateAsset";

    //资产操作日志
    public static final String API_Process_SelectProcess_URL = BASE_URL + "/API_Process/SelectProcess";

    //操作资产列表
    public static final String API_Process_SelectOperate_URL = BASE_URL + "/API_Process/SelectOperate";

    //操作数据
    public static final String API_Operate_ListOperate_Url = BASE_URL + "/API_Operate/ListOperate";

    //操作执行
    public static final String API_Operate_InsertOperate_Url = BASE_URL + "/API_Operate/InsertOperate";

    //变更创建
    public static final String API_Change_InsertChange_Url = BASE_URL + "/API_Change/InsertChange";


    //盘点数据
    public static final String API_Check_ListCheck_URL = BASE_URL + "/API_Check/ListCheck";

    //盘点创建
    public static final String API_Check_InsertCheck_URL = BASE_URL + "/API_Check/InsertCheck";

    //盘点操作
    public static final String API_Check_UpdateInventory_URL = BASE_URL + "/API_Check/UpdateInventory";

    //    主题数据
    public static final String API_Company_SelectCompany_Url = BASE_URL + "/API_Company/SelectCompany";

    //    主题修改
    public static final String API_Company_UpdateCompany_Url = BASE_URL + "/API_Company/UpdateCompany";

    //部门列表
    public static final String API_Depart_ListDepart_URL = BASE_URL + "/API_Depart/ListDepart";

    //部门删除
    public static final String API_Depart_DELETE_URL = BASE_URL + "/API_Depart/UpdateDepart";

    //部门新增修改
    public static final String API_Depart_UPDATE_URL = BASE_URL + "/API_Depart/UpdateDepart";

    //分类删除
    public static final String API_Category_DelCategory = BASE_URL + "/API_Category/DelCategory";

    //分类新增修改
    public static final String API_Category_UpdateCategory = BASE_URL + "/API_Category/UpdateCategory";

    //分类列表
    public static final String API_Category_ListCategory = BASE_URL + "/API_Category/ListCategory";

    //员工列表
    public static final String API_Staff_ListStaff_URL = BASE_URL + "/API_Staff/ListStaff";

    //员工新增修改
    public static final String API_Staff_UpdateStaff_URL = BASE_URL + "/API_Staff/UpdateStaff";

    //员工删除
    public static final String API_Staff_DelStaff_URL = BASE_URL + "/API_Staff/DelStaff";

    //属性列表
    public static final String API_Property_ListProperty_URL = BASE_URL + "/API_Property/ListProperty";

    //参数数据
    public static final String API_SYNC_PARAM_URL = BASE_URL + "/API_Sync/Param";
    //模板列表
    public static final String API_Template_ListTemplate_URL = BASE_URL + "/API_Template/ListTemplate";

    //模板打印记录
    public static final String API_Template_Select_URL = BASE_URL + "/API_Template/Select";

    //高频同步数据
    public static final String API_Sync_SYNC_URL = BASE_URL + "/API_Sync/Sync";

    //低频同步数据
    public static final String API_Sync_LOWSYNC_URL = BASE_URL + "/API_Sync/LowSync";

    //同步用户数据
    public static final String APPUSER_SYNCDATA_URL = BASE_URL + "/AppUser/SyncData";

    //打印机记录
    public static final String APP_Printer_url = BASE_URL + "/APP/Printer";


}

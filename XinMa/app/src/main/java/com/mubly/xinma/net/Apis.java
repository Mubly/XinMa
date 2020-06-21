package com.mubly.xinma.net;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.convert.Converter;
import com.lzy.okrx2.adapter.ObservableBody;


import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.Response;


public class Apis {

    private static final String TAG = "Apis";

    private static Gson gson = new Gson();

    // 支付测试
//    public static Observable<ResponseData<OrderCashierBean>> getPayInfo() {
//        return OkGo.<ResponseData<OrderCashierBean>>get(URLConstant.getPayInfo)
//                .converter(new Converter<ResponseData<OrderCashierBean>>() {
//                    @Override
//                    public ResponseData<OrderCashierBean> convertResponse(Response response) throws Throwable {
//                        Type type = new TypeToken<ResponseData<OrderCashierBean>>() {
//                        }.getType();
//                        return gson.fromJson(response.body().string(), type);
//                    }
//                })
//                .adapt(new ObservableBody<ResponseData<OrderCashierBean>>());
//
//    }
//
//    public static Observable<ResponseData<OrderCashierBean>> checkPhoneCode(String phoneNo) {
//        return OkGo.<ResponseData<OrderCashierBean>>get(URLConstant.CHECK_PHONE_CODE_URL + phoneNo + "/valid")
//                .converter(new Converter<ResponseData<OrderCashierBean>>() {
//                    @Override
//                    public ResponseData<OrderCashierBean> convertResponse(Response response) throws Throwable {
//                        Type type = new TypeToken<ResponseData<OrderCashierBean>>() {
//                        }.getType();
//                        return gson.fromJson(response.body().string(), type);
//                    }
//                })
//                .adapt(new ObservableBody<ResponseData<OrderCashierBean>>());
//
//    }
//
//    public static Observable<SendPhoneCodeRes> sendPhoneCode(String phoneNo) {
//        Map<String, String> params = new HashMap<>();
//        params.put("driver", CommUtil.getMyUUID(CrossApp.get()));
//        params.put("digest", MD5Utils.MD5(phoneNo + "-" + CommUtil.getMyUUID(CrossApp.get())));
//        String jsonParams=new Gson().toJson(params);
//        return OkGo.<SendPhoneCodeRes>put(URLConstant.SEND_PHONE_CODE_URL + phoneNo)
//                .upJson(jsonParams)
//                .converter(new Converter<SendPhoneCodeRes>() {
//                    @Override
//                    public SendPhoneCodeRes convertResponse(Response response) throws Throwable {
//                        Type type = new TypeToken<ResponseData<OrderCashierBean>>() {
//                        }.getType();
//                        return gson.fromJson(response.body().string(), type);
//                    }
//                })
//                .adapt(new ObservableBody<SendPhoneCodeRes>());
//
//    }
//    public static Observable<ResponseData<BaseModel>> checkTest() {
//
//        return OkGo.<ResponseData<BaseModel>>put(URLConstant.CHECK_TEST_URL)
//                .converter(new Converter<ResponseData<BaseModel>>() {
//                    @Override
//                    public ResponseData<BaseModel> convertResponse(Response response) throws Throwable {
//                        Type type = new TypeToken<ResponseData<BaseModel>>() {
//                        }.getType();
//                        return gson.fromJson(response.body().string(), type);
//                    }
//                })
//                .adapt(new ObservableBody<ResponseData<BaseModel>>());
//
//    }
//    public static Observable<PayOrderInfo> getOrderInfo() {
//        Map<String, String> params = new HashMap<>();
//        params.put("deliveryMode", "TAKE_THEIR");
//        params.put("productId", "121");
//        params.put("buyerName", "xx");
//        params.put("buyerPhone", "11111111111");
//        params.put("buyerAddress", "zz");
//        params.put("describe", "mmm");
//        params.put("formId", "mmm");
//        params.put("total", "1");
//        String jsonObject = new Gson().toJson(params);
//        return OkGo.<PayOrderInfo>get(URLConstant.orderInfo_Url)
//                .converter(new Converter<PayOrderInfo>() {
//                    @Override
//                    public PayOrderInfo convertResponse(Response response) throws Throwable {
//                        Type type = new TypeToken<PayOrderInfo>() {
//                        }.getType();
//                        return gson.fromJson(response.body().string(), type);
//                    }
//                })
//                .adapt(new ObservableBody<PayOrderInfo>());
//
//    }
//    // 登录
//    public static Observable<ResponseData<LoginResBean>> login(String phone, String verCode) {
//        return OkGo.<ResponseData<LoginResBean>>post(loginUrl)
//                .params("phone", phone)
//                .params("code", verCode)
//                .converter(new Converter<ResponseData<LoginResBean>>() {
//                    @Override
//                    public ResponseData<LoginResBean> convertResponse(Response response) throws Throwable {
//                        Type type = new TypeToken<ResponseData<LoginResBean>>() {
//                        }.getType();
//                        return gson.fromJson(response.body().string(), type);
//                    }
//                })
//                .adapt(new ObservableBody<ResponseData<LoginResBean>>());
//
//    }
//
//    //发送验证码
//    public static Observable<ResponseData<BaseModel>> getCode(String phone) {
//        return OkGo.<ResponseData<BaseModel>>post(GET_CODE_URL)
//                .params("phone", phone)
//                .converter(new Converter<ResponseData<BaseModel>>() {
//                    @Override
//                    public ResponseData<BaseModel> convertResponse(Response response) throws Throwable {
//                        Type type = new TypeToken<ResponseData<BaseModel>>() {
//                        }.getType();
//                        return gson.fromJson(response.body().string(), type);
//                    }
//                })
//                .adapt(new ObservableBody<ResponseData<BaseModel>>());
//
//    }
//
//    //首页信息获取
//    public static Observable<ResponseData<List<HomeBean>>> getHomeData(int status, int page) {
//        return OkGo.<ResponseData<List<HomeBean>>>post(HOME_INFO_URL)
//                .params("status", status)
//                .params("page", page)
//                .converter(new Converter<ResponseData<List<HomeBean>>>() {
//                    @Override
//                    public ResponseData<List<HomeBean>> convertResponse(Response response) throws Throwable {
//
//                        Type type = new TypeToken<ResponseData<List<HomeBean>>>() {
//                        }.getType();
////                        return gson.fromJson(response.body().string(), type);
//                        ResponseData<List<HomeBean>> data = gson.fromJson(response.body().string(), type);
//
//                        return data;
//                    }
//                })
//                .adapt(new ObservableBody<ResponseData<List<HomeBean>>>());
//
//    }
//
//    // 启动页
//    public static Observable<ResponseData<List<StartBean>>> getStartImage() {
//        return OkGo.<ResponseData<List<StartBean>>>post(START_IMAGE_URL)
//                .converter(new Converter<ResponseData<List<StartBean>>>() {
//                    @Override
//                    public ResponseData<List<StartBean>> convertResponse(Response response) throws Throwable {
//                        Type type = new TypeToken<ResponseData<List<StartBean>>>() {
//                        }.getType();
//                        return gson.fromJson(response.body().string(), type);
//                    }
//                })
//                .adapt(new ObservableBody<ResponseData<List<StartBean>>>());
//
//    }
//
//    //用户注册
//    public static Observable<ResponseData<LoginResBean>> register(String phone, String userName, String tagArr, String sex, File imgFile) {
//        return OkGo.<ResponseData<LoginResBean>>post(REGISTERED_URL)
//                .params("user_name", userName)
//                .params("phone", phone)
//                .params("cate_id", tagArr)
//                .params("sex", sex)
//                .params("header_img", imgFile)
//                .converter(new Converter<ResponseData<LoginResBean>>() {
//                    @Override
//                    public ResponseData<LoginResBean> convertResponse(Response response) throws Throwable {
//                        Type type = new TypeToken<ResponseData<LoginResBean>>() {
//                        }.getType();
//                        return gson.fromJson(response.body().string(), type);
//                    }
//                })
//                .adapt(new ObservableBody<ResponseData<LoginResBean>>());
//
//    }
//
//    //注册页面标签获取
//    public static Observable<ResponseData<List<CategoryVo>>> getTagData() {
//        return OkGo.<ResponseData<List<CategoryVo>>>get(GETCATEGORY_URL)
//                .converter(new Converter<ResponseData<List<CategoryVo>>>() {
//                    @Override
//                    public ResponseData<List<CategoryVo>> convertResponse(Response response) throws Throwable {
//                        Type type = new TypeToken<ResponseData<List<CategoryVo>>>() {
//                        }.getType();
//                        return gson.fromJson(response.body().string(), type);
//                    }
//                })
//                .adapt(new ObservableBody<ResponseData<List<CategoryVo>>>());
//
//    }
//
//    //    帖子详情
//    public static Observable<ResponseData<TopicInfoVo>> getTieInfo(int post_id, int type) {
//        return OkGo.<ResponseData<TopicInfoVo>>post(TIE_INFO_URL)
//                .params("post_id", post_id)
//                .params("type", type)
//                .converter(new Converter<ResponseData<TopicInfoVo>>() {
//                    @Override
//                    public ResponseData<TopicInfoVo> convertResponse(Response response) throws Throwable {
//
//                        Type type = new TypeToken<ResponseData<TopicInfoVo>>() {
//                        }.getType();
//                        ResponseData<TopicInfoVo> data = gson.fromJson(response.body().string(), type);
//
//                        return data;
//                    }
//                })
//                .adapt(new ObservableBody<ResponseData<TopicInfoVo>>());
//
//    }
//
//    // 视频上传
//    public static Observable<ResponseData<BaseModel>> videoUpload(String post_info, String location, String through, String weft, String sign, File video, File img) {
//        return OkGo.<ResponseData<BaseModel>>post(VIDEO_UPLOAD_URL)
//                .params("post_info", post_info)//文章内容
////                .params("cate_id", cate_id)//分类Id
//                .params("location", location)//地址
//                .params("through", through)//经度
//                .params("weft", weft)//维度
//                .params("video", video)//视频
////                .params("img", img)//封面图
//                .params("sign", sign)//标签
//                .converter(new Converter<ResponseData<BaseModel>>() {
//                    @Override
//                    public ResponseData<BaseModel> convertResponse(Response response) throws Throwable {
//                        Type type = new TypeToken<ResponseData<BaseModel>>() {
//                        }.getType();
//                        return gson.fromJson(response.body().string(), type);
//                    }
//                })
//                .adapt(new ObservableBody<ResponseData<BaseModel>>());
//
//    }
//
//    // 视频上传七牛
//    public static Observable<ResponseData<BaseModel>> videoUpload2(String post_info, String location, String through, String weft, String sign, String video, String img) {
//        return OkGo.<ResponseData<BaseModel>>post(VIDEO_UPLOAD_URL2)
//                .params("post_info", post_info)//文章内容
////                .params("cate_id", cate_id)//分类Id
//                .params("location", location)//地址
//                .params("through", through)//经度
//                .params("weft", weft)//维度
//                .params("video", video)//视频
//                .params("img", img)//封面图
//                .params("sign", sign)//标签
//                .converter(new Converter<ResponseData<BaseModel>>() {
//                    @Override
//                    public ResponseData<BaseModel> convertResponse(Response response) throws Throwable {
//                        Type type = new TypeToken<ResponseData<BaseModel>>() {
//                        }.getType();
//                        return gson.fromJson(response.body().string(), type);
//                    }
//                })
//                .adapt(new ObservableBody<ResponseData<BaseModel>>());
//
//    }
//
//    // 图片上传
//    public static Observable<ResponseData<BaseModel>> imageUpload(String post_info, String cate_id, String location, String through, String weft, String sign, File video, File img) {
//        return OkGo.<ResponseData<BaseModel>>post(IMAGE_UPLOAD_URL)
//                .params("post_info", post_info)//文章内容
//                .params("cate_id", cate_id)//分类Id
//                .params("location", location)//地址
//                .params("through", through)//经度
//                .params("weft", weft)//维度
//                .params("video", video)//视频
////                .params("img", img)//封面图
//                .params("sign", sign)//标签
//                .converter(new Converter<ResponseData<BaseModel>>() {
//                    @Override
//                    public ResponseData<BaseModel> convertResponse(Response response) throws Throwable {
//                        Type type = new TypeToken<ResponseData<BaseModel>>() {
//                        }.getType();
//                        return gson.fromJson(response.body().string(), type);
//                    }
//                })
//                .adapt(new ObservableBody<ResponseData<BaseModel>>());
//
//    }
//
//    //七牛多图上传
//    public static Observable<ResponseData<BaseModel>> imageUploadMore(final String post_info, final String location, final String through, final String weft, final String sign, final String files) {
//        return OkGo.<ResponseData<BaseModel>>post(MUIL_IMAGE_UPLOAD_QINIU_URL)
//                .params("post_info", post_info)
//                .params("location", location)
//                .params("through", through)
//                .params("sign", sign)
//                .params("weft", weft)
//                .params("img", files)
//                .converter(new Converter<ResponseData<BaseModel>>() {
//                    @Override
//                    public ResponseData<BaseModel> convertResponse(Response response) throws Throwable {
//                        Type type = new TypeToken<ResponseData<BaseModel>>() {
//                        }.getType();
//                        return gson.fromJson(response.body().string(), type);
//                    }
//                })
//                .adapt(new ObservableBody<ResponseData<BaseModel>>());
//
//    }
//
//    public static Observable<ResponseData<BaseModel>> imageVideoUpload(String common_id, String cate_id, String location, String through, String weft, String sign, File video, File img) {
//        return OkGo.<ResponseData<BaseModel>>post(IMG_VIDEO_UPLOAD_URL)
//                .params("common_id", common_id)//回复或者是评论的id(文章上传成功后返回此id)
//                .params("universal", cate_id)//图片或者视频
//                .params("type", location)//1:图片 2:视频
//                .params("status", sign)//1:评论 2:回复
//                .converter(new Converter<ResponseData<BaseModel>>() {
//                    @Override
//                    public ResponseData<BaseModel> convertResponse(Response response) throws Throwable {
//                        Type type = new TypeToken<ResponseData<BaseModel>>() {
//                        }.getType();
//                        return gson.fromJson(response.body().string(), type);
//                    }
//                })
//                .adapt(new ObservableBody<ResponseData<BaseModel>>());
//
//    }
//
//    public static Observable<ResponseData<BaseModel>> imageUpload2(String id, ArrayList<File> files) {
//        return OkGo.<ResponseData<BaseModel>>post(IMAGE_UPLOAD_URL)
//                .params("id", id)//文章Id
//                .addFileParams("img", files)//图片
//                .converter(new Converter<ResponseData<BaseModel>>() {
//                    @Override
//                    public ResponseData<BaseModel> convertResponse(Response response) throws Throwable {
//                        Type type = new TypeToken<ResponseData<BaseModel>>() {
//                        }.getType();
//                        return gson.fromJson(response.body().string(), type);
//                    }
//                })
//                .adapt(new ObservableBody<ResponseData<BaseModel>>());
//
//    }
//
//    // 获取评论列表
//    public static Observable<ResponseData<List<CommentInfo>>> getCommentInfoById(int post_id) {
//        return OkGo.<ResponseData<List<CommentInfo>>>post(COMMENT_INFO_URL)
//                .params("post_id", post_id)
//                .converter(new Converter<ResponseData<List<CommentInfo>>>() {
//                    @Override
//                    public ResponseData<List<CommentInfo>> convertResponse(Response response) throws Throwable {
//
//                        Type type = new TypeToken<ResponseData<List<CommentInfo>>>() {
//                        }.getType();
//
//                        return gson.fromJson(response.body().string(), type);
//                    }
//                })
//                .adapt(new ObservableBody<ResponseData<List<CommentInfo>>>());
//    }
//
//    // 回复评论
//    public static Observable<ResponseData<BaseModel>> replyComment(int post_id, String content, int type, int commentId, int userId) {
//        return OkGo.<ResponseData<BaseModel>>post(REPLY_COMMENT_URL)
//                .params("post_id", post_id)
//                .params("content", content)
//                .params("type", type)
//                .params("common_id", commentId)
//                .params("user_id", userId)
//                .converter(new Converter<ResponseData<BaseModel>>() {
//                    @Override
//                    public ResponseData<BaseModel> convertResponse(Response response) throws Throwable {
//                        Type type = new TypeToken<ResponseData<BaseModel>>() {
//                        }.getType();
//                        return gson.fromJson(response.body().string(), type);
//                    }
//                })
//                .adapt(new ObservableBody<ResponseData<BaseModel>>());
//    }
//
//    // 发表评论
//    public static Observable<ResponseData<BaseModel>> sendReplyComment(int post_id, String content, int type) {
//        return OkGo.<ResponseData<BaseModel>>post(SEND_REPLY_COMMENT_URL)
//                .params("post_id", post_id)
//                .params("content", content)
//                .params("type", type)
//                .converter(new Converter<ResponseData<BaseModel>>() {
//                    @Override
//                    public ResponseData<BaseModel> convertResponse(Response response) throws Throwable {
//                        Type type = new TypeToken<ResponseData<BaseModel>>() {
//                        }.getType();
//                        return gson.fromJson(response.body().string(), type);
//                    }
//                })
//                .adapt(new ObservableBody<ResponseData<BaseModel>>());
//    }
//
//    // 发表评论
//    public static Observable<ResponseData<BaseModel>> attentionSomeOne(int user_id) {
//        return OkGo.<ResponseData<BaseModel>>post(SEND_REPLY_COMMENT_URL)
//                .params("user_id", user_id)
//                .converter(new Converter<ResponseData<BaseModel>>() {
//                    @Override
//                    public ResponseData<BaseModel> convertResponse(Response response) throws Throwable {
//                        Type type = new TypeToken<ResponseData<BaseModel>>() {
//                        }.getType();
//                        return gson.fromJson(response.body().string(), type);
//                    }
//                })
//                .adapt(new ObservableBody<ResponseData<BaseModel>>());
//    }
//
//    // 点赞或取消点赞
//    public static Observable<ResponseData<SmartBeanVo>> doPraise(int post_id) {
//        return OkGo.<ResponseData<SmartBeanVo>>post(PRAISE_URL)
//                .params("post_id", post_id)
//                .converter(new Converter<ResponseData<SmartBeanVo>>() {
//                    @Override
//                    public ResponseData<SmartBeanVo> convertResponse(Response response) throws Throwable {
//                        Type type = new TypeToken<ResponseData<SmartBeanVo>>() {
//                        }.getType();
//                        return gson.fromJson(response.body().string(), type);
//                    }
//                })
//                .adapt(new ObservableBody<ResponseData<SmartBeanVo>>());
//    }
//
//    // 点赞或取消点赞
//    public static Observable<ResponseData<SmartBeanVo>> doCollection(int post_id) {
//        return OkGo.<ResponseData<SmartBeanVo>>post(COLLECTION_URL)
//                .params("post_id", post_id)
//                .converter(new Converter<ResponseData<SmartBeanVo>>() {
//                    @Override
//                    public ResponseData<SmartBeanVo> convertResponse(Response response) throws Throwable {
//                        Type type = new TypeToken<ResponseData<SmartBeanVo>>() {
//                        }.getType();
//                        return gson.fromJson(response.body().string(), type);
//                    }
//                })
//                .adapt(new ObservableBody<ResponseData<SmartBeanVo>>());
//    }
//
//    // 关注/取关
//    public static Observable<ResponseData<SmartBeanVo>> doAttention(int user_id) {
//        return OkGo.<ResponseData<SmartBeanVo>>post(ATTENTION_SOMEBODAY_URL)
//                .params("user_id", user_id)
//                .converter(new Converter<ResponseData<SmartBeanVo>>() {
//                    @Override
//                    public ResponseData<SmartBeanVo> convertResponse(Response response) throws Throwable {
//                        Type type = new TypeToken<ResponseData<SmartBeanVo>>() {
//                        }.getType();
//                        return gson.fromJson(response.body().string(), type);
//                    }
//                })
//                .adapt(new ObservableBody<ResponseData<SmartBeanVo>>());
//    }
//
//    // 搜索页一级分类
//    public static Observable<ResponseData<List<CategoryVo>>> getSearchTab1(int page) {
//        return OkGo.<ResponseData<List<CategoryVo>>>post(SEARCH_ONE_CATEGARY_URL)
//                .params("page", page)
//                .converter(new Converter<ResponseData<List<CategoryVo>>>() {
//                    @Override
//                    public ResponseData<List<CategoryVo>> convertResponse(Response response) throws Throwable {
//                        Type type = new TypeToken<ResponseData<List<CategoryVo>>>() {
//                        }.getType();
//                        return gson.fromJson(response.body().string(), type);
//                    }
//                })
//                .adapt(new ObservableBody<ResponseData<List<CategoryVo>>>());
//    }
//
//    // 搜索页二级分类（列表内容）
//    public static Observable<ResponseData<List<CategoryVo>>> getSearchTab2(int cate_id, int page) {
//        return OkGo.<ResponseData<List<CategoryVo>>>post(SEARCH_TWO_CATEGARY_URL)
//                .params("cate_id", cate_id)
//                .params("page", page)
//                .converter(new Converter<ResponseData<List<CategoryVo>>>() {
//                    @Override
//                    public ResponseData<List<CategoryVo>> convertResponse(Response response) throws Throwable {
//                        Type type = new TypeToken<ResponseData<List<CategoryVo>>>() {
//                        }.getType();
//                        return gson.fromJson(response.body().string(), type);
//                    }
//                })
//                .adapt(new ObservableBody<ResponseData<List<CategoryVo>>>());
//    }
//    // 搜索页二级分类（推荐列表内容）
//    public static Observable<ResponseData<List<CategoryVo>>> getSearchTabRecom( int page) {
//        return OkGo.<ResponseData<List<CategoryVo>>>post(SEARCH_TWO_CATEGARY_RECOM_URL)
//                .params("page", page)
//                .converter(new Converter<ResponseData<List<CategoryVo>>>() {
//                    @Override
//                    public ResponseData<List<CategoryVo>> convertResponse(Response response) throws Throwable {
//                        Type type = new TypeToken<ResponseData<List<CategoryVo>>>() {
//                        }.getType();
//                        return gson.fromJson(response.body().string(), type);
//                    }
//                })
//                .adapt(new ObservableBody<ResponseData<List<CategoryVo>>>());
//    }
//
//
//    //搜索模块视频列表
//    public static Observable<ResponseData<List<SearchVideoVo>>> getSearchVideo(int cate_id) {
//        return OkGo.<ResponseData<List<SearchVideoVo>>>post(SEARCH_VIDEO_LIST_URL)
//                .params("cate_id", cate_id)
//                .converter(new Converter<ResponseData<List<SearchVideoVo>>>() {
//                    @Override
//                    public ResponseData<List<SearchVideoVo>> convertResponse(Response response) throws Throwable {
//                        Type type = new TypeToken<ResponseData<List<SearchVideoVo>>>() {
//                        }.getType();
//                        return gson.fromJson(response.body().string(), type);
//                    }
//                })
//                .adapt(new ObservableBody<ResponseData<List<SearchVideoVo>>>());
//    }
//
//    //
////    // 版本更新
////    public static Observable<ResponseData<VersionBean>> getVersion() {
////        return OkGo.<ResponseData<VersionBean>>get(GetVersionUrl)
////                .converter(new Converter<ResponseData<VersionBean>>() {
////                    @Override
////                    public ResponseData<VersionBean> convertResponse(Response response) throws Throwable {
////                        Type type = new TypeToken<ResponseData<VersionBean>>() {
////                        }.getType();
////                        return gson.fromJson(response.body().string(), type);
////                    }
////                })
////                .adapt(new ObservableBody<ResponseData<VersionBean>>());
////    }
////
////    // 加入群聊
////    // todo AddCommentBean为空返回
////    public static Observable<ResponseData<AddCommentBean>> joinGroup(String tid) {
////        return OkGo.<ResponseData<AddCommentBean>>post(JoinGroupUrl)
////                .params("tid", tid)
////                .converter(new Converter<ResponseData<AddCommentBean>>() {
////                    @Override
////                    public ResponseData<AddCommentBean> convertResponse(Response response) throws Throwable {
////                        Type type = new TypeToken<ResponseData<AddCommentBean>>() {
////                        }.getType();
////                        return gson.fromJson(response.body().string(), type);
////                    }
////                })
////                .adapt(new ObservableBody<ResponseData<AddCommentBean>>());
////    }
////
//    //修改用户信息
//    public static Observable<ResponseData<BaseModel>> editUserInfo(String user_name, String signature, String birthday, String location, String sex, String school) {
//        return OkGo.<ResponseData<BaseModel>>post(EDIT_USER_INFO_URL)
//                .params("user_name", user_name)
//                .params("signature", signature)
//                .params("birthday", birthday)
//                .params("location", location)
//                .params("sex", sex)
//                .params("school", school)
//                .converter(new Converter<ResponseData<BaseModel>>() {
//                    @Override
//                    public ResponseData<BaseModel> convertResponse(Response response) throws Throwable {
//                        Type type = new TypeToken<ResponseData<BaseModel>>() {
//                        }.getType();
//                        return gson.fromJson(response.body().string(), type);
//                    }
//                })
//                .adapt(new ObservableBody<ResponseData<BaseModel>>());
//    }
////
//
//    //修改用户头像
//    public static Observable<ResponseData<BaseModel>> updateAvatar(String avatar) {
//        return OkGo.<ResponseData<BaseModel>>post(CHANGE_AVTAR_IMG_URL)
//                .params("img", avatar)
//                .converter(new Converter<ResponseData<BaseModel>>() {
//                    @Override
//                    public ResponseData<BaseModel> convertResponse(Response response) throws Throwable {
//                        Type type = new TypeToken<ResponseData<BaseModel>>() {
//                        }.getType();
//                        return gson.fromJson(response.body().string(), type);
//                    }
//                })
//                .adapt(new ObservableBody<ResponseData<BaseModel>>());
//    }
//
//    //修改用户背景图
//    public static Observable<ResponseData<BaseModel>> userInfoBg(String avatar) {
//        return OkGo.<ResponseData<BaseModel>>post(CHANGE_USERINFO_BG_URL)
//                .params("img", avatar)
//                .converter(new Converter<ResponseData<BaseModel>>() {
//                    @Override
//                    public ResponseData<BaseModel> convertResponse(Response response) throws Throwable {
//                        Type type = new TypeToken<ResponseData<BaseModel>>() {
//                        }.getType();
//                        return gson.fromJson(response.body().string(), type);
//                    }
//                })
//                .adapt(new ObservableBody<ResponseData<BaseModel>>());
//    }
//
//    //我的帖子，关注
//    public static Observable<ResponseData<List<UserPostVo>>> mytopAndFocus(int type, int page) {
//        return OkGo.<ResponseData<List<UserPostVo>>>post(MY_TOPIC_AND_FOCUS_URL)
//                .params("type", type)
//                .params("page", page)
//                .converter(new Converter<ResponseData<List<UserPostVo>>>() {
//                    @Override
//                    public ResponseData<List<UserPostVo>> convertResponse(Response response) throws Throwable {
//                        Type type = new TypeToken<ResponseData<List<UserPostVo>>>() {
//                        }.getType();
//                        return gson.fromJson(response.body().string(), type);
//                    }
//                })
//                .adapt(new ObservableBody<ResponseData<List<UserPostVo>>>());
//    }
//
//    //获取用户信息
//    public static Observable<ResponseData<UserInfoVo>> getEditUserInfo() {
//        return OkGo.<ResponseData<UserInfoVo>>post(GET_USERINFO_URL)
//                .converter(new Converter<ResponseData<UserInfoVo>>() {
//                    @Override
//                    public ResponseData<UserInfoVo> convertResponse(Response response) throws Throwable {
//                        Type type = new TypeToken<ResponseData<UserInfoVo>>() {
//                        }.getType();
//                        return gson.fromJson(response.body().string(), type);
//                    }
//                })
//                .adapt(new ObservableBody<ResponseData<UserInfoVo>>());
//    }
//
//    //获取用户信息
//    public static Observable<ResponseData<UserInfoVo>> getUserInfo() {
//        return OkGo.<ResponseData<UserInfoVo>>post(GET_USER_INFO_URL)
//                .converter(new Converter<ResponseData<UserInfoVo>>() {
//                    @Override
//                    public ResponseData<UserInfoVo> convertResponse(Response response) throws Throwable {
//                        Type type = new TypeToken<ResponseData<UserInfoVo>>() {
//                        }.getType();
//                        return gson.fromJson(response.body().string(), type);
//                    }
//                })
//                .adapt(new ObservableBody<ResponseData<UserInfoVo>>());
//    }
//
//    //确认修改用户信息
//    public static Observable<ResponseData<BaseModel>> ackEditUserInfo(UserInfoVo userInfoVo) {
//        return OkGo.<ResponseData<BaseModel>>post(ACK_EDIT_USER_INFO_URL)
//                .params("user_name", userInfoVo.getUser_name())
//                .params("signature", userInfoVo.getSignature())
//                .params("birthday", userInfoVo.getBirthday())
//                .params("location", userInfoVo.getLocation())
//                .params("sex", userInfoVo.getSex())
//                .params("school", userInfoVo.getSchool())
//                .converter(new Converter<ResponseData<BaseModel>>() {
//                    @Override
//                    public ResponseData<BaseModel> convertResponse(Response response) throws Throwable {
//                        Type type = new TypeToken<ResponseData<BaseModel>>() {
//                        }.getType();
//                        return gson.fromJson(response.body().string(), type);
//                    }
//                })
//                .adapt(new ObservableBody<ResponseData<BaseModel>>());
//    }
//
//    //    七牛token获取
//    public static Observable<ResponseData<SmartBeanVo>> getUpLoadToken() {
//        return OkGo.<ResponseData<SmartBeanVo>>post(GET_UPLOAD_TOKEN_URL)
//                .converter(new Converter<ResponseData<SmartBeanVo>>() {
//                    @Override
//                    public ResponseData<SmartBeanVo> convertResponse(Response response) throws Throwable {
//                        Type type = new TypeToken<ResponseData<SmartBeanVo>>() {
//                        }.getType();
//                        return gson.fromJson(response.body().string(), type);
//                    }
//                })
//                .adapt(new ObservableBody<ResponseData<SmartBeanVo>>());
//    }
//
//    //商户认证
//    public static Observable<ResponseData<BaseModel>> storeCertification(StoreCertificationReq storeCertificationReq) {
//        return OkGo.<ResponseData<BaseModel>>post(ACK_EDIT_USER_INFO_URL)
//                .params("shop_name", storeCertificationReq.shop_name)
//                .params("shop_phone", storeCertificationReq.shop_phone)
//                .params("shop_mobile", storeCertificationReq.shop_mobile)
//                .params("shop_type", storeCertificationReq.shop_type)
//                .params("shop_type_scope", storeCertificationReq.shop_type_scope)
//                .params("shop_all_name", storeCertificationReq.shop_all_name)
//                .params("shop_commercial_type", storeCertificationReq.shop_commercial_type)
//                .params("shop_logo", storeCertificationReq.shop_logo)
//                .params("shop_business_license", storeCertificationReq.shop_business_license)
//                .params("shop_address", storeCertificationReq.shop_address)
//                .params("shop_facade_img", storeCertificationReq.shop_facade_img)
//                .params("shop_inner_img", storeCertificationReq.shop_inner_img)
//                .params("shop_weft", storeCertificationReq.shop_weft)
//                .params("shop_through", storeCertificationReq.shop_through)
//                .converter(new Converter<ResponseData<BaseModel>>() {
//                    @Override
//                    public ResponseData<BaseModel> convertResponse(Response response) throws Throwable {
//                        Type type = new TypeToken<ResponseData<BaseModel>>() {
//                        }.getType();
//                        return gson.fromJson(response.body().string(), type);
//                    }
//                })
//                .adapt(new ObservableBody<ResponseData<BaseModel>>());
//    }
}
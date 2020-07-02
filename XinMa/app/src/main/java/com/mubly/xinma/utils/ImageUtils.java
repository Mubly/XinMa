package com.mubly.xinma.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.media.MediaScannerConnection;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.mubly.xinma.R;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class ImageUtils {







    /**
     * glide加载base64图片
     * @param context
     * @param imageView 载入iv
     * @param url 加载的url地址
     * @param isCircle 是否圆形显示
     * @param isRoundConner 是否圆角图片
     * @param phResId  默认图片
     */
    public static void glideLoadUrl(Context context, ImageView imageView, String url, boolean isCircle, boolean isRoundConner, int phResId){
        if(context==null)return;
        RequestOptions options = new RequestOptions().placeholder(R.mipmap.img_defaut);
        options.disallowHardwareConfig();
        if(isCircle){
            options.circleCrop();
        }
        if(isRoundConner){
//            RoundedCorners roundedCorners = new RoundedCorners(6);
            //与centerCrop冲突，改用GlideRoundedCornersTransform
//            RoundedCornersTransform  roundedCornersTransform = new RoundedCornersTransform (context,6);
//            options.optionalTransform(roundedCornersTransform);
        }
        Glide.with(context).asBitmap().load(url).apply(options).into(imageView);
    }

    public static void glideLoadUrl(Context context, ImageView imageView, String url, boolean isCircle, int phResId){
        glideLoadUrl(context,imageView,url,isCircle,false,phResId);
    }

    public static void glideLoadUrlWithRoundConner(Context context, ImageView imageView, String url, int phResId){
        glideLoadUrl(context,imageView,url,false,true,phResId);
    }

    /**
     * 将图片转换成Base64编码的字符串
     */
    public static String imageToBase64(String path){
        if(TextUtils.isEmpty(path)){
            return null;
        }
        InputStream is = null;
        byte[] data = null;
        String result = null;
        try{
            is = new FileInputStream(path);
            //创建一个字符流大小的数组。
            data = new byte[is.available()];
            //写入数组
            is.read(data);
            //用默认的编码格式进行编码
            result = Base64.encodeToString(data,Base64.DEFAULT);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(null !=is){
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
        return result;
    }
}

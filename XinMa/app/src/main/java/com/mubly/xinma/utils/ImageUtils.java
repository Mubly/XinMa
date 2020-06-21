package com.mubly.xinma.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.media.MediaScannerConnection;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;

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
        RequestOptions options = new RequestOptions().placeholder(phResId);
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


}

package com.mubly.xinma.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.Dialog;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.media.MediaMetadataRetriever;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Environment;
import android.telephony.TelephonyManager;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.mubly.xinma.R;
import com.mubly.xinma.base.CrossApp;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InvalidClassException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 所有的工具类集合
 */
public class CommUtil {

    /**
     * 判断是否有网络
     *
     * @param context
     * @return
     */
    public static boolean isNetWorkConnected(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
            if (mNetworkInfo != null) {
                return mNetworkInfo.isAvailable() && mNetworkInfo.isConnected();
            }
        }
        return false;
    }

    public static String addZeroPrefix(int hours) {
        return String.valueOf(hours).length() > 1 ? String.valueOf(hours) : "0" + String.valueOf(hours);
    }

    /**
     * 获取mipmap图标
     *
     * @param context
     * @param mipmapId
     * @return
     */
    public static Drawable getDrawableToMipmap(Context context, int mipmapId) {
        return context.getResources().getDrawable(mipmapId);
    }


    /**
     * 不带后缀的文件夹名字
     *
     * @return 返回路径（全路径）
     * @dirName 文件夹名称
     */
    public static File getFilesDir(String dirName) {
        File file = new File(getFilesDirSystem() + File.separator + dirName);
        if (!file.exists())
            file.mkdir();

        return file;
    }

    public static File getFilesDirSystem() {
        String externalStorageState;
        Context context = CrossApp.get();
        try {
            externalStorageState = Environment.getExternalStorageState();
        } catch (NullPointerException var5) {
            externalStorageState = "";
        } catch (IncompatibleClassChangeError var6) {
            externalStorageState = "";
        }
        File folder = null;
        if (Environment.MEDIA_MOUNTED.equals(externalStorageState) && context != null
                && PackageManager.PERMISSION_GRANTED == context.checkCallingOrSelfPermission("android.permission.WRITE_EXTERNAL_STORAGE")
                && PackageManager.PERMISSION_GRANTED == context.checkCallingOrSelfPermission("android.permission.READ_EXTERNAL_STORAGE")) {
            folder = context.getExternalCacheDir();
        }

        if (folder == null || !folder.exists()) {
            folder = CrossApp.get().getFilesDir();
        }
        return folder;
    }

    /**
     * 获取XML中字符串String.xml
     *
     * @return
     */
    public static String getStringToStringXml(Context context, int stringId) {
        if (stringId == 0) return "";
        return context.getResources().getString(stringId);
    }

    public static int getPxToDimenXml(Context context, int dimenId) {
        return context.getResources().getDimensionPixelSize(dimenId);
    }

    public static int getPxToColorXml(Context context, int colorId) {
        return context.getResources().getColor(colorId);
    }


    /**
     * 设置TextView文本
     *
     * @param textView
     * @param tv
     */
    public static void textViewTv(TextView textView, String tv) {
        textView.setText(tv);
    }

    /**
     * 根据Xml设置TextView文本
     *
     * @param context
     * @param textView
     * @param tvToXml
     */
    public static void textTextToStringXml(Context context, TextView textView, int tvToXml) {
        textView.setText(getStringToStringXml(context, tvToXml));
    }

    /**
     * 设置ImageView图标
     *
     * @param imageView
     * @param ivToMipmapXml
     */
    public static void imageViewIcon(ImageView imageView, int ivToMipmapXml) {
        if (imageView.getVisibility() == View.GONE) {
            imageView.setVisibility(View.VISIBLE);
        }
        imageView.setImageResource(ivToMipmapXml);
    }


    /**
     * 数组、判断数组大小
     *
     * @param list
     * @param <T>
     * @return
     */
    public static <T> boolean listSize(List<T> list) {
        if (list == null || list.size() == 0)
            return true;
        return false;
    }

    public static String strLess(String count, int i) {
        return String.valueOf(Integer.parseInt(count) - i);
    }

    public static int currentOffset;

    public static SpannableString getAuctionInfoSpannableStr(String lotNum, String browse, String bidNum) {
        String data = lotNum + "拍品·" + browse + "围观·" + bidNum + "出价";
        SpannableString spannableString = new SpannableString(data);
        StyleSpan styleSpan_B1 = new StyleSpan(Typeface.BOLD);
        StyleSpan styleSpan_B2 = new StyleSpan(Typeface.BOLD);
        StyleSpan styleSpan_B3 = new StyleSpan(Typeface.BOLD);
        ForegroundColorSpan colorSpan1 = new ForegroundColorSpan(Color.parseColor("#666666"));
        ForegroundColorSpan colorSpan2 = new ForegroundColorSpan(Color.parseColor("#666666"));
        ForegroundColorSpan colorSpan3 = new ForegroundColorSpan(Color.parseColor("#666666"));
        spannableString.setSpan(styleSpan_B1, 0, lotNum.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(styleSpan_B2, lotNum.length() + 3, data.indexOf("围"), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(styleSpan_B3, data.lastIndexOf("·") + 1, data.lastIndexOf("·") + bidNum.length() + 1, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);

        spannableString.setSpan(colorSpan1, 0, lotNum.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(colorSpan2, lotNum.length() + 3, data.indexOf("围"), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(colorSpan3, data.lastIndexOf("·") + 1, data.lastIndexOf("·") + bidNum.length() + 1, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        return spannableString;
    }

    public static SpannableString getAuctionPriceStr(String leftStr, String price) {
        if (!price.contains(".")) {
            price = price + ".00";
        }
        String data = leftStr + "￥" + price;

        SpannableString spannableString = new SpannableString(data);
        StyleSpan styleSpan_B1 = new StyleSpan(Typeface.BOLD);
        AbsoluteSizeSpan ab16 = new AbsoluteSizeSpan(16, true);
        AbsoluteSizeSpan ab14 = new AbsoluteSizeSpan(14, true);
        spannableString.setSpan(ab16, leftStr.length() + 1, data.indexOf("."), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(ab14, data.indexOf("."), data.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(styleSpan_B1, leftStr.length() + 1, data.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        return spannableString;
    }

    public static SpannableString getAuctionInfoSpannableStr2(String browse, String bidNum) {
        String data = browse + "围观·" + bidNum + "出价";
        SpannableString spannableString = new SpannableString(data);
        StyleSpan styleSpan_B1 = new StyleSpan(Typeface.BOLD);
        StyleSpan styleSpan_B2 = new StyleSpan(Typeface.BOLD);
        ForegroundColorSpan colorSpan1 = new ForegroundColorSpan(Color.parseColor("#666666"));
        ForegroundColorSpan colorSpan2 = new ForegroundColorSpan(Color.parseColor("#666666"));
        spannableString.setSpan(styleSpan_B1, 0, browse.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(colorSpan1, 0, browse.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(styleSpan_B2, data.indexOf("·") + 1, data.indexOf("·") + bidNum.length() + 1, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(colorSpan2, data.indexOf("·") + 1, data.indexOf("·") + bidNum.length() + 1, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        return spannableString;
    }

    public static void toSearchView(Context mContext, int searchArtist) {

    }


    /**
     * Toast多次点击只显示一次
     */
    public static class ToastU {
        private static Context context = null;
        private static Toast toast = null;

        public static void showToast(Context context, String text) {
            if (toast == null) {
                toast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
            } else {
                toast.setText(text);
                toast.setDuration(Toast.LENGTH_SHORT);
            }
            toast.show();
        }

        public static void showToast(String text) {
            if (toast == null) {
                toast = Toast.makeText(CrossApp.get(), text, Toast.LENGTH_SHORT);
            } else {
                toast.setText(text);
                toast.setDuration(Toast.LENGTH_SHORT);
            }
            toast.show();
        }

        //            * 将Toast封装在一个方法中，在其它地方使用时直接输入要弹出的内容即可
//      */
        public static void ToastMessage(Context context, String messages) {
            //LayoutInflater的作用：对于一个没有被载入或者想要动态载入的界面，都需要LayoutInflater.inflate()来载入，LayoutInflater是用来找res/layout/下的xml布局文件，并且实例化
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();//调用Activity的getLayoutInflater()
            View view = inflater.inflate(R.layout.toast_style_view, null); //加載layout下的布局
            TextView text = view.findViewById(R.id.progress_message);
            text.setText(messages); //toast内容
            Toast toast = new Toast(CrossApp.get());
            toast.setGravity(Gravity.CENTER, 12, 20);//setGravity用来设置Toast显示的位置，相当于xml中的android:gravity或android:layout_gravity
            toast.setDuration(Toast.LENGTH_LONG);//setDuration方法：设置持续时间，以毫秒为单位。该方法是设置补间动画时间长度的主要方法
            toast.setView(view); //添加视图文件
            toast.show();
        }
    }


    /**
     * 获取屏幕宽度
     *
     * @param context
     * @return
     */
    public static int getScreenWidth1(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;         // 屏幕宽度（像素）
        //int height = dm.heightPixels;       // 屏幕高度（像素）
        float density = dm.density;         // 屏幕密度（0.75 / 1.0 / 1.5）
        //int densityDpi = dm.densityDpi;     // 屏幕密度dpi（120 / 160 / 240）
        // 屏幕宽度算法:屏幕宽度（像素）/屏幕密度
        int screenWidth = (int) (width / density);  // 屏幕宽度(dp)
        //int screenHeight = (int) (height / density);// 屏幕高度(dp)
        return screenWidth;
    }

    public static int getScreenWidth() {
        return getDisplayMetrics().widthPixels;
    }

    /**
     * Returns a valid DisplayMetrics object
     *
     * @param
     * @return DisplayMetrics object
     */
    private static DisplayMetrics getDisplayMetrics() {
        final WindowManager
                windowManager =
                (WindowManager) CrossApp.get().getSystemService(Context.WINDOW_SERVICE);
        final DisplayMetrics metrics = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(metrics);
        return metrics;
    }

    /**
     * 获取屏幕高度
     *
     * @param context
     * @return
     */
    public static int getScreenHeight(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        //int width = dm.widthPixels;         // 屏幕宽度（像素）
        int height = dm.heightPixels;       // 屏幕高度（像素）
        float density = dm.density;         // 屏幕密度（0.75 / 1.0 / 1.5）
        //int densityDpi = dm.densityDpi;     // 屏幕密度dpi（120 / 160 / 240）
        // 屏幕宽度算法:屏幕宽度（像素）/屏幕密度
        //int screenWidth = (int) (width / density);  // 屏幕宽度(dp)
        int screenHeight = (int) (height / density);// 屏幕高度(dp)
        return screenHeight;
    }

    /**
     * 日期转换
     *
     * @param str
     * @return
     */
    public static String formatDate(String str) {
        SimpleDateFormat sf1 = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sf2 = new SimpleDateFormat("MM-dd");
        String formatStr = "";
        try {
            formatStr = sf2.format(sf1.parse(str));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return formatStr;
    }

    /**
     * 编辑对话框
     *
     * @param context
     * @param layoutId
     * @param bgColor
     * @return
     */
    public static Dialog showEditDialog(Context context, int layoutId, int bgColor) {
        View view = LayoutInflater.from(context).inflate(layoutId, null);
        Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(view);
        dialog.getWindow().setBackgroundDrawableResource(bgColor);
        //dialog.show();
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        int displayWidth = dm.widthPixels;
        int displayHeight = dm.heightPixels;
        WindowManager.LayoutParams p = dialog.getWindow().getAttributes();  //获取对话框当前的参数值
        p.width = (int) (displayWidth * 0.90);    //宽度设置为屏幕的0.5
        p.height = (int) (displayHeight * 0.38);    //宽度设置为屏幕的0.5
        p.height = WindowManager.LayoutParams.WRAP_CONTENT;
        dialog.setCanceledOnTouchOutside(false);// 设置点击屏幕Dialog不消失
        dialog.getWindow().setAttributes(p);     //设置生效
        return dialog;
    }


    /**
     * 对话框
     *
     * @param context
     * @param layoutId
     * @param bgColor
     * @param widthPercent
     * @param heightPercent
     * @return
     */
    public static Dialog showEditDialog(Context context, int layoutId, int bgColor, double widthPercent, double heightPercent) {
        View view = LayoutInflater.from(context).inflate(layoutId, null);
        Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(view);
        if (bgColor != 0) {
            dialog.getWindow().setBackgroundDrawableResource(bgColor);
        }
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        int displayWidth = dm.widthPixels;
        int displayHeight = dm.heightPixels;
        WindowManager.LayoutParams p = dialog.getWindow().getAttributes();  //获取对话框当前的参数值
        p.width = (int) (displayWidth * widthPercent);    //宽度设置为屏幕的0.5
        p.height = (int) (displayHeight * heightPercent);    //宽度设置为屏幕的0.5
        p.height = WindowManager.LayoutParams.WRAP_CONTENT;
        dialog.setCanceledOnTouchOutside(false);// 设置点击屏幕Dialog不消失
        dialog.getWindow().setAttributes(p);     //设置生效
        return dialog;
    }

    public static Dialog showEditDialog(Context context, int layoutId, int bgColor, double widthPercent, double heightPercent, boolean isCancel) {
        View view = LayoutInflater.from(context).inflate(layoutId, null);
        Dialog dialog = new Dialog(context);
        dialog.setCancelable(isCancel);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(view);
        dialog.getWindow().setBackgroundDrawableResource(bgColor);
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        int displayWidth = dm.widthPixels;
        int displayHeight = dm.heightPixels;
        WindowManager.LayoutParams p = dialog.getWindow().getAttributes();  //获取对话框当前的参数值
        p.width = (int) (displayWidth * widthPercent);    //宽度设置为屏幕的0.5
        p.height = (int) (displayHeight * heightPercent);    //宽度设置为屏幕的0.5
        p.height = WindowManager.LayoutParams.WRAP_CONTENT;
        dialog.setCanceledOnTouchOutside(false);// 设置点击屏幕Dialog不消失
        dialog.getWindow().setAttributes(p);     //设置生效
        return dialog;
    }

    public static Dialog showEditDialog(Context context, int layoutId, int bgColor, boolean isCancel) {
        View view = LayoutInflater.from(context).inflate(layoutId, null);
        Dialog dialog = new Dialog(context);
        dialog.setCancelable(isCancel);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(view);
        dialog.getWindow().setBackgroundDrawableResource(bgColor);
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        int displayWidth = dm.widthPixels;
        int displayHeight = dm.heightPixels;
        WindowManager.LayoutParams p = dialog.getWindow().getAttributes();  //获取对话框当前的参数值
        dialog.setCanceledOnTouchOutside(false);// 设置点击屏幕Dialog不消失
        dialog.getWindow().setAttributes(p);     //设置生效
        return dialog;
    }

//    public static void showSingleListDialog(Context context, int indexDef, String titleStr, final List<String> dataList, final CallBackObject callBackObject) {
//        AlertDialog.Builder builder = new AlertDialog.Builder(context);
//        if (!TextUtils.isEmpty(titleStr)) {
//            builder.setTitle(titleStr);
//        }
//        final int[] index = {0};
//        String[] singleList = new String[dataList.size()];
//        dataList.toArray(singleList);
//        builder.setSingleChoiceItems(singleList, indexDef, new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                index[0] = which;
//            }
//        });
//        builder.setNegativeButton("取消", null);
//        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                dialog.dismiss();
//                if (null != callBackObject) {
//                    callBackObject.callBack(index[0]);
//                }
//            }
//        });
//        AlertDialog dialog = builder.create();
//        dialog.show();
//    }

    /**
     * 省市区对话框
     *
     * @param context
     * @param layoutId
     * @param bgColor
     * @param widthPercent
     * @param heightPercent
     * @return
     */
    public static Dialog showProvCityAreaWheelDialog(Context context, int layoutId, int bgColor, double widthPercent, double heightPercent) {
        View view = LayoutInflater.from(context).inflate(layoutId, null);
        Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(view);
        dialog.getWindow().setBackgroundDrawableResource(bgColor);
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        int displayWidth = dm.widthPixels;
        int displayHeight = dm.heightPixels;
        WindowManager.LayoutParams p = dialog.getWindow().getAttributes();  //获取对话框当前的参数值
        p.gravity = Gravity.BOTTOM;
        p.width = (int) (displayWidth * widthPercent);    //宽度设置为屏幕的0.5
        p.height = (int) (displayHeight * heightPercent);    //宽度设置为屏幕的0.5
        dialog.setCanceledOnTouchOutside(false);// 设置点击屏幕Dialog不消失
        dialog.getWindow().setAttributes(p);     //设置生效
        return dialog;
    }

    /**
     * 设置TextView 左
     *
     * @param context
     * @param textView
     * @param mipmapId
     */
    public static void setLeftCompoundDrawables(Context context, TextView textView, int mipmapId) {
        Drawable drawableLeft = CommUtil.getDrawableToMipmap(context, mipmapId);
        textView.setCompoundDrawablesWithIntrinsicBounds(drawableLeft, null, null, null);
    }

    /**
     * 存储对象
     * /data/data/<package name>/files
     *
     * @param context
     * @param ser
     * @param file
     * @return
     */
    public static boolean saveObject(Context context, Serializable ser, String file) {
        FileOutputStream fos = null;
        ObjectOutputStream oos = null;
        try {
            fos = context.openFileOutput(file, Context.MODE_PRIVATE);
            oos = new ObjectOutputStream(fos);
            oos.writeObject(ser);
            oos.flush();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                oos.close();
                fos.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 读取对象
     *
     * @param context
     * @param file
     * @return
     */
    public static Serializable readObject(Context context, String file) {
        if (!isExistDataCache(context, file))
            return null;
        FileInputStream fis = null;
        ObjectInputStream ois = null;
        try {
            fis = context.openFileInput(file);
            ois = new ObjectInputStream(fis);
            return (Serializable) ois.readObject();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
            // 反序列化失败 - 删除缓存文件
            if (e instanceof InvalidClassException) {
                File data = context.getFileStreamPath(file);
                data.delete();
            }
        } finally {
            try {
                ois.close();
                fis.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 判断缓存是否存在
     *
     * @param cachefile
     * @return
     */
    public static boolean isExistDataCache(Context context, String cachefile) {
        if (context == null)
            return false;
        boolean exist = false;
        File data = context.getFileStreamPath(cachefile);
        if (data.exists())
            exist = true;
        return exist;
    }

    /**
     * 验证手机号码是否合法 * 176, 177, 178; * 180, 181, 182, 183, 184, 185, 186, 187, 188, 189; * 145, 147; * 130, 131, 132, 133, 134, 135, 136, 137, 138, 139; * 150, 151, 152, 153, 155, 156, 157, 158, 159;
     * * * "13"代表前两位为数字13, * "[0-9]"代表第二位可以为0-9中的一个, * "[^4]" 代表除了4 * "\\d{8}"代表后面是可以是0～9的数字, 有8位。
     */
    public static boolean isMobileNumber(String mobiles) {
        String telRegex = "^((13[0-9])|(15[^4])|(18[0-9])|(17[0-8])|(147,145))\\d{8}$";
        return !isEmpty(mobiles) && mobiles.matches(telRegex);
    }

    /**
     * 字符串为空判断
     *
     * @param s
     * @return
     */
    public static boolean isEmpty(String s) {
        return s == null || "".equals(s) || "null".equals(s) || s.trim().length() == 0;
    }

    public static String getStr(String s) {
        try {
            if (isEmpty(s)) return " ";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return s;
    }

    /**
     * 手机号码用空格隔开，例如：133 2569 7536
     *
     * @return
     */
    public static String dealPhoneNumber(String phoneNum) {
        StringBuilder builder = new StringBuilder();
        if (!isEmpty(phoneNum)) {
            int phoneNumLength = phoneNum.length();
            for (int i = 0; i < phoneNumLength; i++) {
                builder.append(phoneNum.charAt(i));
                if (i == 2 || i == 6) {
                    builder.append(" ");
                }
            }
        }
        return builder.toString();
    }

    /**
     * 当前字符串是否匹配当前正则表达式
     *
     * @param matchStr
     * @param regex
     * @return
     */
    public static boolean isMatch(String matchStr, String regex) {
        return !isEmpty(matchStr) && matchStr.matches(regex);
    }

    /**
     * String类型转成Int类型
     *
     * @param s
     * @return
     */
    public static int stringToInt(String s) {
        return Integer.valueOf(s).intValue();
    }

    /**
     * String类型转成Double类型
     *
     * @param s
     * @return
     */
    public static double stringToDouble(String s) {
        return Double.parseDouble(s);
    }

    /**
     * 银行卡替换，保留后四位
     * <p>
     * 如果银行卡号为空 或者 null ,返回null ；否则，返回替换后的字符串；
     *
     * @param bankCard 银行卡号
     * @return
     */
    public static String bankCardReplaceWith(String bankCard) {

        if (bankCard.isEmpty() || bankCard == null) {
            return null;
        } else {
            return replaceAction(bankCard, "(?<=\\d{0})\\d(?=\\d{4})");
        }
    }

    /**
     * 银行卡替换
     *
     * @param username username
     * @param regular  正则
     * @return
     */
    private static String replaceAction(String username, String regular) {
        return username.replaceAll(regular, "*");
    }

    /**
     * 一种格式日期转换成另外一种格式日期字符串
     * 例如：201-10-18转成10月10日
     *
     * @param timeFormat
     * @param timeS
     * @return
     */
    public static String timeFormat(String timeFormat, String otherTimeFormat, String timeS) {
        try {
            SimpleDateFormat format = new SimpleDateFormat(otherTimeFormat);
            Date date = new Date(dateToStamp(timeS, timeFormat));
            return format.format(date).toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 日期转时间戳
     *
     * @param s
     * @return
     */
    public static long dateToStamp(String s, String timeFormat) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(timeFormat);
        Date date = null;
        try {
            date = simpleDateFormat.parse(s);
            return date.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
            return -1;
        }
    }

    /**
     * 时间戳转日期
     *
     * @param s
     * @return
     */
    public static String stampTodate(int s) {
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("mm:ss");
        long lt = s;
        Date date = new Date(lt);
        res = simpleDateFormat.format(date);
        return res;
    }

    public static String dateToStr(Date date) {
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        res = simpleDateFormat.format(date);
        return res;
    }

    /**
     * 保留2位小数
     *
     * @param s
     * @return
     */
    public static String keepTwo(String s) {
        DecimalFormat decimalFormat = new DecimalFormat("0.00");//构造方法的字符格式这里如果小数不足2位,会以0补足.
        String distanceString = decimalFormat.format(Double.parseDouble(s));//format 返回的是字符串
        return distanceString;
    }

    public static String keepOne(String s) {
        DecimalFormat decimalFormat = new DecimalFormat("0.0");//构造方法的字符格式这里如果小数不足2位,会以0补足.
        String distanceString = decimalFormat.format(Double.parseDouble(s));//format 返回的是字符串
        return distanceString;
    }


    /**
     * 将每三个数字加上逗号处理（通常使用金额方面的编辑）
     * 价格、每隔3位加入逗号（，号）
     *
     * @param str 需要处理的字符串
     * @return 处理完之后的字符串
     */

    public static String priceAddComma(String str) {
        DecimalFormat decimalFormat = new DecimalFormat(",###");
        return decimalFormat.format(Double.parseDouble(str));
    }


    public static void showKeyboard(View view) {
        InputMethodManager imm = (InputMethodManager) view.getContext()
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            view.requestFocus();
            imm.showSoftInput(view, 0);
        }
    }

    public static void hideKeyboard(View view) {
        InputMethodManager imm = (InputMethodManager) view.getContext()
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    public static void toggleSoftInput(View view) {
        InputMethodManager imm = (InputMethodManager) view.getContext()
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.toggleSoftInput(0, InputMethodManager.SHOW_FORCED);
        }
    }

    /**
     * 获取应用版本号
     *
     * @param
     * @return
     */
    public static int getPackageCode() {
        PackageManager manager = CrossApp.get().getPackageManager();
        int code = 0;
        try {
            PackageInfo info = manager.getPackageInfo(CrossApp.get().getPackageName(), 0);
            code = info.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return code;
    }

    /**
     * 获取应用版本号
     *
     * @param context
     * @return
     */
    public static String getPackageName(Context context) {
        PackageManager manager = context.getPackageManager();
        String name = "";
        try {
            PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
            name = info.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return name;
    }

    /**
     * 获取版本号内容
     *
     * @param content
     * @return
     */
    public static String getUpateApkContent(String content) {
        try {
            String[] s = content.split("#");
            StringBuffer sb = new StringBuffer();
            int sLength = s.length;
            for (int i = 0; i < sLength; i++) {
                sb.append("-");
                sb.append(s[i]);
                if (i < sLength - 1)
                    sb.append("\n");
            }
            return sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * List集合大小
     *
     * @param tList
     * @param <T>
     * @return
     */
    public static <T> boolean listEmpty(List<T> tList) {
        if (tList == null || tList.size() == 0)
            return true;
        return false;
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }


//    public static void dealFlipperView(ViewFlipper mVp, final List<HomeIndexBean.NoticeListBean> mList, final Context mContext, final CallBackObj callBack) {
//        for (int i = 0; i < mList.size(); i++) {
//            TextView view = new TextView(mContext);
//            ViewFlipper.LayoutParams params = new ViewFlipper.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//            params.gravity = Gravity.LEFT | Gravity.CENTER_VERTICAL;
//            view.setLayoutParams(params);
//            view.setText(mList.get(i).getNoticeTitle());
//            view.setTextSize(12);
//            view.setEllipsize(TextUtils.TruncateAt.END);
//            view.setMaxEms(20);
//            view.setSingleLine();
//            view.setTextColor(mContext.getResources().getColor(R.color.color_999999));
//            final int finalI = i;
//            view.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    callBack.callBack(mList.get(finalI));
//                }
//            });
//            mVp.addView(view);
//        }
//        //是否自动开始滚动
//        mVp.setAutoStart(true);
//        //滚动时间
//        mVp.setFlipInterval(2000);
//        if (mList.size() > 1) {
//            //开始滚动
//            mVp.startFlipping();
//        }
//        //出入动画
//        mVp.setOutAnimation(mContext, R.anim.push_up_out);
//        mVp.setInAnimation(mContext, R.anim.push_down_in);
//    }


    /**
     * 方法描述 隐藏手机号中间位置字符，显示前三后三个字符
     *
     * @param phoneNo
     * @return
     * @author yaomy
     * @date 2018年4月3日 上午10:38:51
     */
    public static String hidePhoneNo(String phoneNo) {
        /*if(StringUtils.isBlank(phoneNo)) {
            return phoneNo;
        }*/

        int length = phoneNo.length();
        int beforeLength = 3;
        int afterLength = 4;
        //替换字符串，当前使用“*”
        String replaceSymbol = "*";
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            if (i < beforeLength || i >= (length - afterLength)) {
                sb.append(phoneNo.charAt(i));
            } else {
                sb.append(replaceSymbol);
            }
        }

        return sb.toString();
    }

//    // 获取渠道号
//    public static String getChannel(Context mContext) {
//        String channel = "未知";
//        if (null == mContext) {
//            mContext = App.getAppContext();
//        }
//        try {
//            if (null == mContext) {
//                return channel;
//            }
//            PackageManager packageManager = mContext.getPackageManager();
//            if (null != packageManager) {
//                ApplicationInfo info = packageManager.getApplicationInfo(mContext.getPackageName(), PackageManager.GET_META_DATA);
//                if (info != null && info.metaData != null) {
//                    String metaData = info.metaData.getString("UMENG_CHANNEL");
//                    if (null != metaData) {
//                        channel = metaData;
//                    }
//                }
//            }
//
//        } catch (PackageManager.NameNotFoundException e) {
//            e.printStackTrace();
//            channel = "未知";
//        }
//        return channel;
//    }


    public static String getDateFromDetailTimeAndDate(String string) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        String nowDate = null;
        try {
            date = dateFormat.parse(string);
            GregorianCalendar calendar = new GregorianCalendar();
            calendar.setTime(date);
            nowDate = calendar.get(Calendar.YEAR) + "-" + (calendar.get(Calendar.MONTH) + 1) + "-" + calendar.get(Calendar.DAY_OF_MONTH);
        } catch (ParseException e) {
            e.printStackTrace();
            nowDate = string;
        }

        return nowDate;

    }

    /*
     * 如果是小数，保留两位，非小数，保留整数
     * @param number
     */
    public static String getDoubleString(double number) {
        String numberStr;
        if (((int) number * 1000) == (int) (number * 1000)) {
            //如果是一个整数
            numberStr = String.valueOf((int) number);
        } else {
            DecimalFormat df = new DecimalFormat("######");
            numberStr = df.format(number);
        }
        return numberStr;
    }

    public static String getDouble2String(String numStr) {
        double number = Double.valueOf(numStr);
        DecimalFormat df = new DecimalFormat("0.00");
        return df.format(number);
    }
//    判断字符串是否为数字
    public static boolean isNumeric(String str){
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher isNum = pattern.matcher(str);
        if( !isNum.matches() ){
            return false;
        }
        return true;
    }
    public static String getImgOfVideo(String path) {
        return bitmap2File(getVideoThumb(path), System.currentTimeMillis() + "");
    }

    /**
     * 获取视频文件截图
     *
     * @param path 视频文件的路径
     * @return Bitmap 返回获取的Bitmap
     */
    public static Bitmap getVideoThumb(String path) {
        MediaMetadataRetriever media = new MediaMetadataRetriever();
        media.setDataSource(path);
        return media.getFrameAtTime();
    }

    public static String bitmap2File(Bitmap bitmap, String name) {
        File f = new File(Environment.getExternalStorageDirectory() + name + ".jpg");
        if (f.exists()) f.delete();
        FileOutputStream fOut = null;
        try {
            fOut = new FileOutputStream(f);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fOut);
            fOut.flush();
            fOut.close();
        } catch (IOException e) {
            return null;
        }
        return f.getAbsolutePath();
    }

    public void getTagTextStr(String content) {
        List<String> tagList = getTaglist(content);

    }


    private List<String> getTaglist(String content) {
        List<String> tagList = new ArrayList<>();
        if (content.contains("")) {
            String[] contentSplit = content.split("#");
            for (int i = 1; i < contentSplit.length; i++) {
                String continKeyValue = contentSplit[i];
                if (continKeyValue.length() > 0 && continKeyValue.endsWith(" ")) {
                    String tagKey = "#" + continKeyValue.substring(0, continKeyValue.indexOf(" ")) + " ";
                    tagList.add(tagKey);
                }
            }
        }
        return tagList;
    }

//    public static String object2Json(Object object) {
//        return new Gson().toJson(object);
//    }

//    public static boolean isLogin(Context context) {
//        if (TextUtils.isEmpty(AppConfig.token.get())) {
//            context.startActivity(new Intent(context, PhoneLoginActivity.class));
//            return false;
//        }
//        return true;
//    }


//    public static void getLocal(Context context, final CallBackObject callBackObject) {
//
//        final AMapLocationClient mlocationClient;
////声明mLocationOption对象
//        AMapLocationClientOption mLocationOption = null;
//        mlocationClient = new AMapLocationClient(context.getApplicationContext());
////初始化定位参数
//        mLocationOption = new AMapLocationClientOption();
//        AMapLocationListener aMapLocationListener = new AMapLocationListener() {
//            @Override
//            public void onLocationChanged(AMapLocation aMapLocation) {
//                mlocationClient.stopLocation();
//                if (null != callBackObject&&null!=aMapLocation) {
//                    callBackObject.callBack(aMapLocation);
//                }
//
//            }
//        };
////设置定位监听
//        mlocationClient.setLocationListener(aMapLocationListener);
////设置定位模式为高精度模式，Battery_Saving为低功耗模式，Device_Sensors是仅设备模式
//        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
//        mLocationOption.setOnceLocation(true);
////获取最近3s内精度最高的一次定位结果：
////设置setOnceLocationLatest(boolean b)接口为true，启动定位时SDK会返回最近3s内精度最高的一次定位结果。如果设置其为true，setOnceLocation(boolean b)接口也会被设置为true，反之不会，默认为false。
//        mLocationOption.setOnceLocationLatest(true);
////设置定位参数
//        mlocationClient.setLocationOption(mLocationOption);
//        mlocationClient.startLocation();
//    }

    @SuppressLint("MissingPermission")
    public static String getMyUUID(Context context) {
        final TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        final String tmDevice, tmSerial, tmPhone, androidId;
//        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
//            // TODO: Consider calling
//            //    ActivityCompat#requestPermissions
//            // here to request the missing permissions, and then overriding
//            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//            //                                          int[] grantResults)
//            // to handle the case where the user grants the permission. See the documentation
//            // for ActivityCompat#requestPermissions for more details.
//            return TODO;
//        }
        tmDevice = "" + tm.getDeviceId();
        tmSerial = "" + tm.getSimSerialNumber();
        androidId = "" + android.provider.Settings.Secure.getString(context.getContentResolver(), android.provider.Settings.Secure.ANDROID_ID);
        UUID deviceUuid = new UUID(androidId.hashCode(), ((long) tmDevice.hashCode() << 32) | tmSerial.hashCode());
        String uniqueId = deviceUuid.toString();
        return uniqueId;
    }

    @SuppressLint("MissingPermission")
    public static String getMyUUID() {
        final TelephonyManager tm = (TelephonyManager) CrossApp.get().getSystemService(Context.TELEPHONY_SERVICE);
        final String tmDevice, tmSerial, tmPhone, androidId;
//        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
//            // TODO: Consider calling
//            //    ActivityCompat#requestPermissions
//            // here to request the missing permissions, and then overriding
//            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//            //                                          int[] grantResults)
//            // to handle the case where the user grants the permission. See the documentation
//            // for ActivityCompat#requestPermissions for more details.
//            return TODO;
//        }
        tmDevice = "" + tm.getDeviceId();
        tmSerial = "" + tm.getSimSerialNumber();
        androidId = "" + android.provider.Settings.Secure.getString(CrossApp.get().getContentResolver(), android.provider.Settings.Secure.ANDROID_ID);
        UUID deviceUuid = new UUID(androidId.hashCode(), ((long) tmDevice.hashCode() << 32) | tmSerial.hashCode());
        String uniqueId = deviceUuid.toString();
        return uniqueId;
    }

    /**
     * 判断应用是否已经启动
     *
     * @param context 一个context
     * @param
     * @return boolean
     */
    public static boolean isAppAlive(Context context) {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> list = am.getRunningTasks(100);
        for (ActivityManager.RunningTaskInfo info : list) {
            if (info.topActivity.getPackageName().equals("com.sina.shihui.baoku") && info.baseActivity.getPackageName().equals("com.sina.shihui.baoku")) {
                return true;
            }
        }
        return false;

//        100 表示取的最大的任务数，info.topActivity表示当前正在运行的Activity，info.baseActivity表系统后台有此进程在运行，
//        具体要做如何判断就看自已的业务需求。这个类还有更多的方法可以取得系统运行的服务、内存使用情况等的方法，请各位自行查找。
    }
}

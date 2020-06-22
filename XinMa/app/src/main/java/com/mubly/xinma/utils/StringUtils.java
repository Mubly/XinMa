package com.mubly.xinma.utils;

import java.util.regex.Pattern;

/**
 * String工具
 * Created by dan.y on 2019/12/24.
 */
public class StringUtils {

    /**
     * 定义下划线
     */
    private static final char UNDERLINE = '_';

    /**
     * null转为""
     * @param str
     * @return
     */
    public static String notNull(String str){
        return str == null ? "" : str;
    }

    public static String notNull2(String str){
        return str == null ? "--" : str;
    }

    public static String notNullf0(String str){
        return str == null ? "0" : str;
    }

    /**
     * String为空判断(不允许空格)
     * @param str
     * @return
     */
    public static boolean isBlank(String str){
        return str == null || "".equals(str.trim());
    }

    /**
     * String不为空判断(不允许空格)
     * @param str
     * @return
     */
    public static boolean isNotBlank(String str){
        return !isBlank(str);
    }

    /**
     * Byte数组为空判断
     * @param bytes
     * @return
     */
    public static boolean isNull(byte[] bytes){
        return bytes.length == 0 || bytes == null;
    }

    /**
     * Byte数组不为空判断
     * @param bytes
     * @return
     */
    public static boolean isNotNull(byte[] bytes){return !isNull(bytes);}

    /**
     * 驼峰转下划线工具
     * @param param
     * @return
     */
    public static String camelToUnderline(String param){
        if(isNotBlank(param)){
            int len = param.length();
            StringBuilder sb = new StringBuilder(len);
            for (int i = 0; i < len; i++) {
                char c = param.charAt(i);
                if(Character.isUpperCase(c)){
                    sb.append(UNDERLINE);
                    sb.append(Character.toLowerCase(c));
                }else{
                    sb.append(c);
                }
            }
            return sb.toString();
        }else{
            return "";
        }
    }

    /**
     * 下划线转驼峰工具
     * @param param
     * @return
     */
    public static String underlineToCamel(String param){
        if(isNotBlank(param)){
            int len = param.length();
            StringBuilder sb = new StringBuilder(len);
            for (int i = 0; i < len; i++) {
                char c = param.charAt(i);
                if(c == 95){
                    ++i;
                    if(i < len){
                        sb.append(Character.toUpperCase(param.charAt(i)));
                    }
                }else {
                    sb.append(c);
                }
            }
            return sb.toString();
        }else {
            return "";
        }
    }

    /**
     * 在字符串两周添加‘’
     * @param param
     * @return
     */
    public static String addSingleQuotes(String param){
        return "\'" + param + "\'";
    }

    /**
     * 判断字符且包含数字、字母、符号中的任意2种
     */
    public static boolean checkStringHasSNCTwo(String source){
        boolean success = false;
        // 数字
         Pattern patternNum = Pattern.compile("^[0-9]$");
        // 字母
        Pattern patternLetter = Pattern.compile("^[A-Za-z]$");
        // 标点
         Pattern patternChar = Pattern.compile("[^\\w\\s]+");
        // EmoJi
         Pattern patternEmoJi = Pattern.compile("[\ud83c\udc00-\ud83c\udfff]|[\ud83d\udc00-\ud83d\udfff]|[\u2600-\u27ff]", Pattern.UNICODE_CASE | Pattern.CASE_INSENSITIVE);
        // 英文标点
         Pattern patternEn = Pattern.compile("^[`~!@#\\$%^&*()_\\-+=<>?:\"{},.\\\\/;'\\[\\]]$");
        // 中文标点
         Pattern patternCn = Pattern.compile("^[·！#￥（——）：；“”‘、，|《。》？、【】\\[\\]]$");

        char[] chars = source.toCharArray();
        int numCount = 0;//数字个数
        int letterCount = 0;//字母个数
        int symbolCount = 0;//符号个数
        for (char c: chars){
            String cs = c+"";
            if(patternNum.matcher(cs).matches()){
                numCount++;
            }else if(patternLetter.matcher(cs).matches()){
                letterCount++;
            }else if(patternChar.matcher(cs).matches() || patternEn.matcher(cs).matches() ||
                    patternCn.matcher(cs).matches() || patternEmoJi.matcher(cs).matches()){
                symbolCount++;
            }
        }
        if(numCount == 0 && letterCount >= 1 && symbolCount >=1){
            success = true;
        }else if(letterCount == 0 && numCount >=1 && symbolCount >=1){
            success = true;
        }else if(symbolCount == 0 && numCount >=1 && letterCount >=1){
            success = true;
        }else {
            success = false;
        }
        return success;
    }

    /**
     * 判断输入是中文.或英文.
     */
    public static boolean checkNameStringLegal(String source){
        boolean success = false;
        // 字母
        Pattern patternChar = Pattern.compile("^[A-Za-z]$");
        // 中文
        Pattern patternChinese = Pattern.compile("^[\u4E00-\u9FA5]$");
        // 点
        Pattern patternPoint = Pattern.compile("^[.]$");

        char[] chars = source.toCharArray();
        int letterCount = 0;//字母个数
        int chineseCount = 0;//汉字个数
        int pointCount = 0;//符号.个数
        for (char c: chars){
            String cs = String.valueOf(c);
            if(patternChar.matcher(cs).matches()){
                letterCount++;
            }else if(patternChinese.matcher(cs).matches()){
                chineseCount++;
            }else if(patternPoint.matcher(cs).matches()){
                pointCount++;
            }
        }
        if(letterCount == 0 && chineseCount >= 2 && pointCount <=1){
            success = true;
        }else if(chineseCount == 0 && letterCount >=2 && pointCount <=1){
            success = true;
        }else {
            success = false;
        }
        return success;
    }

    /**
     * 判断字符串中是否包含全角字符
     * @param source
     * @return
     */
    public static boolean isContainQuanjiaoChar(String source){
        boolean isContain = false;
        // 首先将汉字用空格替换掉
        String temp = source.replaceAll("[\u4e00-\u9fa5]", "");
        char[] chars = temp.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            int charValue = (int) chars[i];
            // 判断是全角字符
            if (charValue >= 65281 && charValue <= 65374 || charValue == 12288) {
                System.out.println("全角  " + (char) charValue);
                isContain = true;
                break;
            }
//            // 判断是半角字符
//            else if (charValue >= 33 && charValue <= 126 || charValue == 32) {
//                System.out.println("半角  " + (char) charValue);
//            }
            else {
                continue;
            }
        }
        return isContain;
    }

//    public static void main(String[] args) {
//        System.out.println(isContainQuanjiaoChar("jfkds你好3454爱的发的￥%……&ad！@#"));
//    }

}

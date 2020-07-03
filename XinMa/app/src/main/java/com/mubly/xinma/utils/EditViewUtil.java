package com.mubly.xinma.utils;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import com.mubly.xinma.common.CallBack;


/**
 * Created by PC on 2018/12/15.
 */

public class EditViewUtil {
    public static void EditDatachangeLister(EditText editText, final CallBack<String> callBack) {

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (null == s) {
                    callBack.callBack("");
                } else {
                    callBack.callBack(s.toString());
                }
            }
        });
    }

    public static boolean passwordCheck(String password, int left, int right) {
        if (password.isEmpty()) {
            return false;
        }
        return password.matches("([0-9]+[a-zA-Z]+|[a-zA-Z]+[0-9]+)[0-9a-zA-Z]*") && password.length() > left && password.length() < right;
    }



    public static void setInputType(EditText editText, String starKey, String endKey) {
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
}

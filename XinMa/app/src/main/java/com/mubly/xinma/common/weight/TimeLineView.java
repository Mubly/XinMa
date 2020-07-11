package com.mubly.xinma.common.weight;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.mubly.xinma.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class TimeLineView extends FrameLayout {
    int color;
    View view = null;

    public TimeLineView(@NonNull Context context) {
        super(context);
        init(context);
    }

    public TimeLineView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.MyViewAttr);
        String str = typedArray.getString(R.styleable.MyViewAttr_bgColor);
        color = Color.parseColor(str);
        init(context);
    }

    public TimeLineView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.MyViewAttr);
        String str = typedArray.getString(R.styleable.MyViewAttr_bgColor);
        color = Color.parseColor(str);
        init(context);
    }

    private void init(Context context) {
         view = LayoutInflater.from(context).inflate(R.layout.layout, this);
        view.findViewById(R.id.circle_view).getBackground().setTint(color);
        view.findViewById(R.id.line_view).getBackground().setTint(color);
        FrameLayout.LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        view.setLayoutParams(layoutParams);
    }

    public void setBgColor(int color) {
        view.findViewById(R.id.circle_view).getBackground().setTint(color);
        view.findViewById(R.id.line_view).getBackground().setTint(color);
    }
}

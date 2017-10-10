package com.ytsh.zhanghao.yourexclusive;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by hasee on 2017/9/21.
 */

public class Calendar_day_textView extends TextView {
    public String isTodat="";
    private Paint paint=new Paint();
    public Calendar_day_textView(Context context) {
        super(context);
    }

    public Calendar_day_textView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        iniControl(context);
    }

    public Calendar_day_textView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        iniControl(context);
    }

    private void iniControl(Context context) {
        paint.setStyle(Paint.Style.STROKE);
        paint.setAntiAlias(true);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if(isTodat.equals("生日")){
            paint.setColor(Color.parseColor("#fba2b6"));
            canvas.translate(getWidth()/2,getHeight()/2);
            canvas.drawCircle(0,0,getWidth()/2,paint);
        }else if(isTodat.equals("今天")){
            paint.setColor(Color.parseColor("#ff0000"));
            canvas.translate(getWidth()/2,getHeight()/2);
            canvas.drawCircle(0,0,getWidth()/2,paint);
        }


    }
}

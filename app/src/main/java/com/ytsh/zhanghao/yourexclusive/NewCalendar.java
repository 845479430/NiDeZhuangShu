package com.ytsh.zhanghao.yourexclusive;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by hasee on 2017/9/21.
 */

public class NewCalendar extends LinearLayout {
    private SimpleDateFormat Year;
    private SimpleDateFormat Month;
    private SimpleDateFormat Day;
    private ImageView btnPrev;
    private ImageView btnNext;
    private TextView txtDeta;
    private int[] i;
    private GridView grid;
    private String displayFormat;
    private LuarCalendar luarCalendar;
    public NewCalendarListener newCalendarListener;
    private Calendar curDate=Calendar.getInstance();
    public NewCalendar(Context context) {
        super(context);
    }

    public NewCalendar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initControl(context,attrs);
    }

    public NewCalendar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initControl(context,attrs);
    }
    private void initControl(Context context,AttributeSet attrs){
        Year = new SimpleDateFormat("yyyy");
        Month=new SimpleDateFormat("MMM");
        Day=new SimpleDateFormat("dd");
        bindControl(context);
        bindControlEvent();


        TypedArray ta=getContext().obtainStyledAttributes(attrs,R.styleable.NewCalendar);

        try {
            String format=ta.getString(R.styleable.NewCalendar_dateFormat);
            displayFormat=format;
            if(displayFormat==null){
                displayFormat="yyyy年 MMM";
            }
        }finally {
            ta.recycle();
        }

        renderCalendar();
    }



    private void bindControl(Context context) {
        LayoutInflater inflater=LayoutInflater.from(context);
        inflater.inflate(R.layout.calendar_view,this);
        btnPrev= (ImageView) findViewById(R.id.btnPrev);
        btnNext= (ImageView) findViewById(R.id.btnNext);
        txtDeta= (TextView) findViewById(R.id.textData);
        grid= (GridView) findViewById(R.id.calendar_grid);
        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(newCalendarListener==null){
                    return;
                }else {
                    newCalendarListener.onItemPress((Date) parent.getItemAtPosition(position));
                }
            }
        });
    }

    private void bindControlEvent() {
        btnPrev.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                curDate.add(Calendar.MONTH,-1);
                renderCalendar();
            }


        });
        btnNext.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                curDate.add(Calendar.MONTH,1);
                renderCalendar();

            }
        });
    }


    private void renderCalendar() {
        SimpleDateFormat sdf=new SimpleDateFormat(displayFormat);
        txtDeta.setText(sdf.format(curDate.getTime()));

        ArrayList<Date> cells=new ArrayList<>();
        Calendar calendar= (Calendar) curDate.clone();
        calendar.set(Calendar.DAY_OF_MONTH,1);
        int prevDays=calendar.get(Calendar.DAY_OF_WEEK)-1;
        calendar.add(Calendar.DAY_OF_MONTH,-prevDays);
        int maxCellCount=6*7;
        while(cells.size()<maxCellCount){
            cells.add(calendar.getTime());
            calendar.add(Calendar.DAY_OF_MONTH,1);
        }
        luarCalendar=new LuarCalendar();
        grid.setAdapter(new CAlendarAdapter(getContext(),cells));
    }
    private class CAlendarAdapter extends ArrayAdapter<Date> {
        LayoutInflater inflater;
        public CAlendarAdapter(@NonNull Context context,ArrayList<Date> days) {
            super(context, R.layout.candar_text_day,days);
            inflater=LayoutInflater.from(context);
        }


        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            Date date=getItem(position);
            if(convertView==null){
                convertView= inflater.inflate(R.layout.candar_text_day,parent,false);
            }
            int day=date.getDate();
            ((TextView)convertView).setText(String.valueOf(day));
            String subMonth=Month.format(date.getTime()).substring(0,Month.format(date.getTime()).length()-1);

            i = LuarCalendar.solarToLunar(Integer.valueOf(Year.format(curDate.getTime())),Integer.valueOf(subMonth),day);

            Date now=new Date();

            if(date.getMonth() == curDate.getTime().getMonth()){
                ((TextView)convertView).setTextColor(Color.parseColor("#000000"));
            }else {
                ((TextView)convertView).setTextColor(Color.parseColor("#eeeeee"));
            }



            if(now.getDate()==date.getDate()&&now.getMonth()==date.getMonth()
                    &&now.getYear()==date.getYear()){

                if(i[1]==8&&i[2]==18){
                    ((TextView)convertView).setTextColor(Color.parseColor("#fba2b6"));
                    ((Calendar_day_textView)convertView).isTodat="生日";
                }else {
                    ((TextView)convertView).setTextColor(Color.parseColor("#ff0000"));
                    ((Calendar_day_textView)convertView).isTodat="今天";
                }

            }
            if(i[1]==8&&i[2]==18){
                ((TextView)convertView).setTextColor(Color.parseColor("#fba2b6"));
                ((Calendar_day_textView)convertView).isTodat="生日";
            }
            return convertView;
        }
    }
    public interface NewCalendarListener{
        void onItemPress(Date day);
    }
}

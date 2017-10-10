package com.ytsh.zhanghao.yourexclusive;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity implements NewCalendar.NewCalendarListener{
    public Toolbar toolBar;
    private NewCalendar newCalendar;
    private int[] i;
    private LuarCalendar luarCalendar;
    private SimpleDateFormat Year;
    private SimpleDateFormat Month;
    private SimpleDateFormat Day;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolBar = (Toolbar) findViewById(R.id.toolBar);
        toolBar.setTitle("你的专属日历");
        newCalendar = (NewCalendar) findViewById(R.id.NewCalendar);
        newCalendar.newCalendarListener=this;
        luarCalendar = new LuarCalendar();
        Year = new SimpleDateFormat("yyyy");
        Month=new SimpleDateFormat("MMM");
        Day=new SimpleDateFormat("dd");
    }
//kjbkjbjhb
    @Override
    public void onItemPress(Date day) {
        String subMonth=Month.format(day).substring(0,Month.format(day).length()-1);

        i = LuarCalendar.solarToLunar(Integer.valueOf(Year.format(day)),Integer.valueOf(subMonth),Integer.valueOf(Day.format(day)));
        Log.d("qwe",i[0]+" "+i[1]+" "+i[2]);
        Log.d("qwe",Year.format(day)+" "+Month.format(day)+" "+Day.format(day));
        Date now=new Date();
        if(now.getDate()==day.getDate()&&now.getMonth()==day.getMonth()
                &&now.getYear()==day.getYear()){

            if(i[1]==8&&i[2]==18){
                Toast.makeText(this,"今天你生日，忘了吗。祝你生日快乐",Toast.LENGTH_LONG).show();
            }else {
                Toast.makeText(this,"就是今天啊",Toast.LENGTH_LONG).show();
            }

        }else if (i[1]==8&&i[2]==18){
            Toast.makeText(this,"你生日，忘了吗",Toast.LENGTH_LONG).show();
        }
        else {

            Toast.makeText(this,"今天是"+Day.format(day)+"号是什么特别的日子呢？",Toast.LENGTH_LONG).show();
        }

    }

}

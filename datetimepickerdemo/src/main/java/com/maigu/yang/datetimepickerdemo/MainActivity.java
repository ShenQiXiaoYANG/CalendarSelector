package com.maigu.yang.datetimepickerdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.maigu.yang.datetimepickerdemo.timepicker.SelectDateTimePop;
import com.maigu.yang.datetimepickerdemo.timepicker.SelectTimePop;
import com.maigu.yang.datetimepickerdemo.timepicker.SelectYearTimePop;
import com.maigu.yang.datetimepickerdemo.util.DateUtils;

import java.text.ParseException;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btn_date_type_one;
    private Button btn_date_type_two;
    private Button btn_date_type_three;
    private Button btn_date_type_four;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        btn_date_type_one = (Button) findViewById(R.id.btn_date_type_one);
        btn_date_type_one.setOnClickListener(this);
        btn_date_type_two = (Button) findViewById(R.id.btn_date_type_two);
        btn_date_type_two.setOnClickListener(this);
        btn_date_type_three = (Button) findViewById(R.id.btn_date_type_three);
        btn_date_type_three.setOnClickListener(this);
        btn_date_type_four = (Button) findViewById(R.id.btn_date_type_four);
        btn_date_type_four.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_date_type_one:
                SelectTimePop selectTimePop = new SelectTimePop(MainActivity.this, btn_date_type_one);
                selectTimePop.setOnDateTimeSetListener(new SelectTimePop.OnDateTimeSetListener() {
                    @Override
                    public void OnDateTimeSet(long date) {
                        String time = null;
                        try {
                            time = DateUtils.longToString(date, "MM月dd日 HH时:mm分");
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        btn_date_type_one.setText(time);
                    }
                });
                break;
            case R.id.btn_date_type_two:
                SelectYearTimePop selectYearTimePop = new SelectYearTimePop(MainActivity.this, btn_date_type_two, true);
                selectYearTimePop.setOnDateTimeSetListener(new SelectYearTimePop.OnDateTimeSetListener() {
                    @Override
                    public void OnDateTimeSet(long date, int i, View v) throws ParseException {
                        String time = null;
                        try {
                            time = DateUtils.longToString(date, "yyyy年");
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        btn_date_type_two.setText(time);
                    }
                });
                break;
            case R.id.btn_date_type_three:
                SelectDateTimePop selectDateTimePop = new SelectDateTimePop(MainActivity.this, btn_date_type_three, true, false);
                selectDateTimePop.setOnDateTimeSetListener(new SelectDateTimePop.OnDateTimeSetListener() {
                    @Override
                    public void OnDateTimeSet(long date, int i, View v) throws ParseException {
                        String time = null;
                        try {
                            time = DateUtils.longToString(date, "yyyy年MM月dd日");
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        btn_date_type_three.setText(time);
                    }
                });
                break;
            case R.id.btn_date_type_four:
                SelectDateTimePop selectDateTimePopLast = new SelectDateTimePop(MainActivity.this, btn_date_type_three, true, true);
                selectDateTimePopLast.setOnDateTimeSetListener(new SelectDateTimePop.OnDateTimeSetListener() {
                    @Override
                    public void OnDateTimeSet(long date, int i, View v) throws ParseException {
                        String time = null;
                        try {
                            time = DateUtils.longToString(date, "yyyy年MM月dd日");
                            if (i == 0) {
                                btn_date_type_four.setText(time + " 上午");
                            } else {
                                btn_date_type_four.setText(time + " 下午");
                            }
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                });
                break;
            default:
                break;
        }
    }

}

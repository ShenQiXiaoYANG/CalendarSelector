package com.maigu.yang.datetimepickerdemo.timepicker;

import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.PopupWindow;

import com.maigu.yang.datetimepickerdemo.R;

import java.util.Calendar;

public class SelectTimePop extends PopupWindow implements OnClickListener {

    private Button btnCancel, btnConfirm;

    private DateTimePicker mDateTimePicker;

    private Calendar mDate = Calendar.getInstance();

    private OnDateTimeSetListener mOnDateTimeSetListener;

    private Activity context;

    public SelectTimePop(final Activity context, View parent) {
        super(context);
        this.context = context;
        mDateTimePicker = new DateTimePicker(context);
        mDateTimePicker.setOnDateTimeChangedListener(new DateTimePicker.OnDateTimeChangedListener() {
            @Override
            public void onDateTimeChanged(DateTimePicker view, int year, int month, int day, int hour, int minute) {
                mDate.set(Calendar.YEAR, year);
                mDate.set(Calendar.MONTH, month);
                mDate.set(Calendar.DAY_OF_MONTH, day);
                mDate.set(Calendar.HOUR_OF_DAY, hour);
                mDate.set(Calendar.MINUTE, minute);
                mDate.set(Calendar.SECOND, 0);
            }
        });
        btnCancel = (Button) mDateTimePicker.findViewById(R.id.btn_cancel);
        btnConfirm = (Button) mDateTimePicker.findViewById(R.id.btn_ok);
        btnCancel.setOnClickListener(new OnClickListener() {

            public void onClick(View v) {
                dismiss();
            }
        });
        btnConfirm.setOnClickListener(this);
        // 设置SelectTimePop的View
        this.setContentView(mDateTimePicker);
        // 设置SelectTimePop弹出窗体的宽
        this.setWidth(LayoutParams.MATCH_PARENT);
        // 设置SelectTimePop弹出窗体的高
        this.setHeight(LayoutParams.WRAP_CONTENT);
        // 设置SelectTimePop弹出窗体可点击
        this.setFocusable(true);
        // 设置SelectTimePop弹出窗体动画效果
        this.setAnimationStyle(R.style.animation);
        // 设置SelectTimePop弹出窗体的背景
        ColorDrawable dw = new ColorDrawable(0xffffffff);
        this.setBackgroundDrawable(dw);
        // 在指定位置的弹出窗口中显示内容视图。
        showAtLocation(parent, Gravity.BOTTOM, 0, 0);
        anseBg();
        this.setOnDismissListener(new OnDismissListener() {

            @Override
            public void onDismiss() {
                baiseBg();
            }
        });
    }

    private void anseBg() {
        WindowManager.LayoutParams params = context.getWindow()
                .getAttributes();
        params.alpha = 0.7f;

        context.getWindow().setAttributes(params);

    }

    private void baiseBg() {
        WindowManager.LayoutParams params = context.getWindow()
                .getAttributes();
        params.alpha = 1.0f;

        context.getWindow().setAttributes(params);
    }

    @Override
    public void onClick(View v) {
        this.dismiss();
        if (mOnDateTimeSetListener != null) {
            mOnDateTimeSetListener.OnDateTimeSet(mDate.getTimeInMillis());
        }
    }

    public interface OnDateTimeSetListener {
        void OnDateTimeSet(long date);
    }

    public void setOnDateTimeSetListener(OnDateTimeSetListener callBack) {
        mOnDateTimeSetListener = callBack;
    }

}
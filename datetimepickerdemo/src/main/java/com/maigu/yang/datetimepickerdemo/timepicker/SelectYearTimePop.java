package com.maigu.yang.datetimepickerdemo.timepicker;

import android.app.Activity;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import com.maigu.yang.datetimepickerdemo.R;

import java.text.ParseException;
import java.util.Calendar;

public class SelectYearTimePop extends PopupWindow implements View.OnClickListener {

    private Button btnCancel, btnConfirm;
    private DateTimeOnlyYear mDateTimePicker;
    private Calendar mDate = Calendar.getInstance();
    private SelectYearTimePop.OnDateTimeSetListener mOnDateTimeSetListener;
    private Activity context;
    private View parent;
    private LinearLayout title;
    private RelativeLayout bottom;
    private int AP = 0;

    /**
     * show：显示标题(窗口居中)；hide：隐藏标题（窗口位于底部）
     */
    public SelectYearTimePop(final Activity context, View parent, boolean show) {
        this.context = context;
        this.parent = parent;
        mDateTimePicker = new DateTimeOnlyYear(context);
        mDateTimePicker.setOnDateTimeChangedListener(new DateTimeOnlyYear.OnDateTimeChangedListener() {

            @Override
            public void onDateTimeChanged(DateTimeOnlyYear view, int year, int month, int day) {
                mDate.set(Calendar.YEAR, year);
                mDate.set(Calendar.MONTH, month);
                mDate.set(Calendar.DAY_OF_MONTH, day);
                mDate.set(Calendar.SECOND, 0);
            }

        });
        btnCancel = (Button) mDateTimePicker.findViewById(R.id.btn_cancel);
        btnConfirm = (Button) mDateTimePicker.findViewById(R.id.btn_ok);
        title = (LinearLayout) mDateTimePicker.findViewById(R.id.ll_title);
        bottom = (RelativeLayout) mDateTimePicker.findViewById(R.id.rl_btn);
        btnConfirm.setOnClickListener(this);
        //取消按钮
        btnCancel.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                //销毁弹出框
                dismiss();
            }
        });
        if (show) {
            title.setVisibility(View.VISIBLE);
        } else {
            title.setVisibility(View.GONE);
        }
        //设置SelectYearTimePop的View
        this.setContentView(mDateTimePicker);
        //设置SelectYearTimePop弹出窗体的宽
        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        //设置SelectYearTimePop弹出窗体的高
        this.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        //设置SelectYearTimePop弹出窗体可点击
        this.setFocusable(true);
        //设置SelectYearTimePop弹出窗体动画效果
        this.setAnimationStyle(R.style.animation);
        showAtLocation(parent, Gravity.BOTTOM, 0, 0);
        translucenceBg();
        this.setOnDismissListener(new OnDismissListener() {

            @Override
            public void onDismiss() {
                whiteBg();
            }
        });


    }

    private void translucenceBg() {
        WindowManager.LayoutParams params = context.getWindow()
                .getAttributes();
        params.alpha = 0.7f;

        context.getWindow().setAttributes(params);

    }

    private void whiteBg() {
        WindowManager.LayoutParams params = context.getWindow()
                .getAttributes();
        params.alpha = 1.0f;

        context.getWindow().setAttributes(params);
    }

    @Override
    public void onClick(View v) {
        this.dismiss();
        if (mOnDateTimeSetListener != null) {
            try {
                mOnDateTimeSetListener.OnDateTimeSet(mDate.getTimeInMillis(), AP, parent);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }

    public interface OnDateTimeSetListener {
        /**
         * 您选择的时间
         *
         * @param date
         */
        void OnDateTimeSet(long date, int i, View v) throws ParseException;
    }

    public void setOnDateTimeSetListener(SelectYearTimePop.OnDateTimeSetListener callBack) {
        mOnDateTimeSetListener = callBack;
    }
}


package com.maigu.yang.datetimepickerdemo.timepicker;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.NumberPicker;
import android.widget.NumberPicker.OnValueChangeListener;
import android.widget.TextView;

import com.maigu.yang.datetimepickerdemo.R;

import java.lang.reflect.Field;
import java.util.Calendar;

public class DateTimeOnlyYear extends FrameLayout {
    private final NumberPicker mYearSpinner;
    private final NumberPicker mMonthSpinner;
    private final NumberPicker mDaySpinner;
    private TextView tvtype;
    private Calendar mDate;
    private int mYear, mMonth;
    private int map = 0;
    private String[] mDateDisplayValues = new String[7];
    private String[] mYearDisplayValues = new String[101];
    private String[] mMonthDisplayValues = new String[12];
    private String[] mAPDisplayValues = new String[2];
    private OnDateTimeChangedListener mOnDateTimeChangedListener;

    public DateTimeOnlyYear(Context context) {
        super(context);
        mDate = Calendar.getInstance();
        mYear = mDate.get(Calendar.YEAR);
        mMonth = mDate.get(Calendar.MONTH);
        inflate(context, R.layout.dialog_yeardialog, this);
        tvtype = (TextView) this.findViewById(R.id.tv_type);
        tvtype.setText("选择时间");
        mYearSpinner = (NumberPicker) this.findViewById(R.id.np_year);
        setNumberPickerDividerColor(mYearSpinner);
        mYearSpinner.getChildAt(0).setFocusable(false);
        mYearSpinner.setMinValue(mYear - 50);
        mYearSpinner.setMaxValue(mYear + 50);
        updateYearControl();
        mYearSpinner.setValue(mYear);
        mYearSpinner.setWrapSelectorWheel(false);//设置为不可循环
        mYearSpinner.setOnValueChangedListener(mOnYearChangedListener);

        mMonthSpinner = (NumberPicker) this.findViewById(R.id.np_month);
        setNumberPickerDividerColor(mMonthSpinner);
        mMonthSpinner.getChildAt(0).setFocusable(false);
        mMonthSpinner.setMaxValue(12);
        mMonthSpinner.setMinValue(1);
        updateMonthControl();
        mMonthSpinner.setWrapSelectorWheel(false);
        mMonthSpinner.setOnValueChangedListener(mOnMonthChangedListener);

        mDaySpinner = (NumberPicker) this.findViewById(R.id.np_day);
        setNumberPickerDividerColor(mDaySpinner);
        mDaySpinner.getChildAt(0).setFocusable(false);
        mDaySpinner.setMaxValue(6);
        mDaySpinner.setMinValue(0);
        updateDateControl();
        mDaySpinner.setWrapSelectorWheel(false);
        mDaySpinner.setOnValueChangedListener(mOnDayChangedListener);
        mMonthSpinner.setVisibility(GONE);
        mDaySpinner.setVisibility(GONE);
    }

    private OnValueChangeListener mOnDayChangedListener = new OnValueChangeListener() {
        @Override
        public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
            mDate.add(Calendar.DAY_OF_MONTH, newVal - oldVal);
            mDate.set(mYear, mMonth, mDate.get(Calendar.DAY_OF_MONTH));
            updateDateControl();
            onDateTimeChanged();
        }
    };

    private OnValueChangeListener mOnYearChangedListener = new OnValueChangeListener() {
        @Override
        public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
            mYear = mYearSpinner.getValue();
            mDate.set(mYear, mMonth, mDate.get(Calendar.DAY_OF_MONTH));
            onDateTimeChanged();
        }
    };

    private OnValueChangeListener mOnMonthChangedListener = new OnValueChangeListener() {
        @Override
        public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
            mMonth = mMonthSpinner.getValue() - 1;
            mDate.set(mYear, mMonth, mDate.get(Calendar.DAY_OF_MONTH));
            onDateTimeChanged();
        }
    };

    private void updateYearControl() {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(mDate.getTimeInMillis());
        /*
        add
    public abstract void add(int field,
                             int amount)根据日历的规则，为给定的日历字段添加或减去指定的时间量。例如，要从当前日历时间减去 5 天，可以通过调用以下方法做到这一点：
    add(Calendar.DAY_OF_MONTH, -5)。
    参数：
    field - 日历字段。
    amount - 为字段添加的日期或时间量。
         */
        cal.add(Calendar.YEAR, 1);
        mYearSpinner.setDisplayedValues(null);
        for (int i = 50; i >= 0; --i) {
            cal.add(Calendar.YEAR, -1);
            mYearDisplayValues[i] = (String) DateFormat.format("yyyy年", cal);
        }
        cal.setTimeInMillis(mDate.getTimeInMillis());
        cal.add(Calendar.YEAR, 0);
        for (int i = 51; i < 101; ++i) {
            cal.add(Calendar.YEAR, 1);
            mYearDisplayValues[i] = (String) DateFormat.format("yyyy年", cal);
        }

        mYearSpinner.setDisplayedValues(mYearDisplayValues);
        mYearSpinner.setValue(mYear);
        mYearSpinner.invalidate();
    }

    private void updateMonthControl() {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(mDate.getTimeInMillis());
        cal.add(Calendar.MONTH, -1);
        mMonthSpinner.setDisplayedValues(null);
        for (int i = 0; i < 12; ++i) {
            if (i < 9) {
                mMonthDisplayValues[i] = "0" + (i + 1) + "月";
            } else {
                mMonthDisplayValues[i] = (i + 1) + "月";
            }
        }
        mMonthSpinner.setDisplayedValues(mMonthDisplayValues);
        mMonthSpinner.setValue(mMonth + 1);
        mMonthSpinner.invalidate();
    }

    private void updateDateControl() {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(mDate.getTimeInMillis());
        cal.add(Calendar.DAY_OF_MONTH, -7 / 2 - 1);
        mDaySpinner.setDisplayedValues(null);
        for (int i = 0; i < 7; ++i) {
            cal.add(Calendar.DAY_OF_MONTH, 1);
            mDateDisplayValues[i] = (String) DateFormat.format("dd日", cal);
//            mDateDisplayValues[i] = (String) DateFormat.format("dd日  EEEE", cal);
        }
        mDaySpinner.setDisplayedValues(mDateDisplayValues);
        mDaySpinner.setValue(7 / 2);
        mDaySpinner.invalidate();
    }


    public interface OnDateTimeChangedListener {
        void onDateTimeChanged(DateTimeOnlyYear view, int year, int month, int day);
    }

    public void setOnDateTimeChangedListener(OnDateTimeChangedListener callback) {
        mOnDateTimeChangedListener = callback;
    }

    private void onDateTimeChanged() {
        if (mOnDateTimeChangedListener != null) {
            mOnDateTimeChangedListener.onDateTimeChanged(this, mYear,
                    mMonth, mDate.get(Calendar.DAY_OF_MONTH));
        }
    }

    private void setNumberPickerDividerColor(NumberPicker numberPicker) {
        NumberPicker picker = numberPicker;
        Field[] pickerFields = NumberPicker.class.getDeclaredFields();
        for (Field pf : pickerFields) {
            if (pf.getName().equals("mSelectionDivider")) {
                pf.setAccessible(true);
                try {
                    //设置分割线的颜色值
                    pf.set(picker, new ColorDrawable(this.getResources().getColor(R.color.green)));
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                } catch (Resources.NotFoundException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
                break;
            }
        }
    }

    public static boolean setNumberPickerTextColor(NumberPicker numberPicker, int color) {
        final int count = numberPicker.getChildCount();
        for (int i = 0; i < count; i++) {
            View child = numberPicker.getChildAt(i);
            if (child instanceof EditText) {
                Field selectorWheelPaintField;
                try {
                    selectorWheelPaintField = numberPicker.getClass().getDeclaredField("mSelectorWheelPaint");
                    selectorWheelPaintField.setAccessible(true);
                    try {
                        ((Paint) selectorWheelPaintField.get(numberPicker)).setColor(color);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                    ((EditText) child).setTextColor(color);
                    numberPicker.invalidate();
                    return true;
                } catch (NoSuchFieldException e) {
                    e.printStackTrace();
                }
            }
        }
        return false;
    }
}

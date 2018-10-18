package com.maigu.yang.datetimepickerdemo.timepicker;

import java.util.Calendar;

import android.content.Context;
import android.text.format.DateFormat;
import android.widget.FrameLayout;
import android.widget.NumberPicker;
import android.widget.NumberPicker.OnValueChangeListener;

import com.maigu.yang.datetimepickerdemo.R;

public class DateTimePicker extends FrameLayout {

    private final NumberPicker mDateSpinner;
    private final NumberPicker mHourSpinner;
    private final NumberPicker mMinuteSpinner;

    private Calendar mDate;

    private int mHour, mMinute;

    private String[] mDateDisplayValues = new String[7];

    private OnDateTimeChangedListener mOnDateTimeChangedListener;

    public DateTimePicker(Context context) {
        super(context);
        mDate = Calendar.getInstance();

        mHour = mDate.get(Calendar.HOUR_OF_DAY);
        mMinute = mDate.get(Calendar.MINUTE);

        inflate(context, R.layout.dialog_datedialog, this);

        mDateSpinner = (NumberPicker) this.findViewById(R.id.np_date);
        (mDateSpinner.getChildAt(0)).setFocusable(false);
        // Sets the min value of the picker.——设置最小值
        mDateSpinner.setMinValue(0);
        // Sets the max value of the picker.——设置最大值
        mDateSpinner.setMaxValue(6);
        updateDateControl();
        // Sets the listener to be notified on change of the current value.——设置要在更改当前值时通知监听器。
        mDateSpinner.setOnValueChangedListener(mOnDateChangedListener);

        mHourSpinner = (NumberPicker) this.findViewById(R.id.np_hour);
        (mHourSpinner.getChildAt(0)).setFocusable(false);
        mHourSpinner.setMaxValue(23);
        mHourSpinner.setMinValue(0);
        // Set the current value for the number picker.——设置数字选择器的当前值。
        mHourSpinner.setValue(mHour);
        mHourSpinner.setOnValueChangedListener(mOnHourChangedListener);

        mMinuteSpinner = (NumberPicker) this.findViewById(R.id.np_minute);
        (mMinuteSpinner.getChildAt(0)).setFocusable(false);
        mMinuteSpinner.setMaxValue(59);
        mMinuteSpinner.setMinValue(0);
        mMinuteSpinner.setValue(mMinute);
        mMinuteSpinner.setOnValueChangedListener(mOnMinuteChangedListener);
    }

    private OnValueChangeListener mOnDateChangedListener = new OnValueChangeListener() {
        @Override
        public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
            mDate.add(Calendar.DAY_OF_MONTH, newVal - oldVal);
            updateDateControl();
            onDateTimeChanged();
        }
    };

    private OnValueChangeListener mOnHourChangedListener = new OnValueChangeListener() {
        @Override
        public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
            mHour = mHourSpinner.getValue();
            onDateTimeChanged();
        }
    };

    private OnValueChangeListener mOnMinuteChangedListener = new OnValueChangeListener() {
        @Override
        public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
            mMinute = mMinuteSpinner.getValue();
            onDateTimeChanged();
        }
    };

    private void updateDateControl() {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(mDate.getTimeInMillis());
        cal.add(Calendar.DAY_OF_YEAR, -7 / 2 - 1);
        mDateSpinner.setDisplayedValues(null);
        for (int i = 0; i < 7; ++i) {
            cal.add(Calendar.DAY_OF_YEAR, 1);
            mDateDisplayValues[i] = (String) DateFormat.format("MM月dd日", cal);
        }
        mDateSpinner.setDisplayedValues(mDateDisplayValues);
        // Sets the values to be displayed.——设置要显示的值。
        mDateSpinner.setValue(7 / 2);
        mDateSpinner.invalidate();
    }

    public interface OnDateTimeChangedListener {
        void onDateTimeChanged(DateTimePicker view, int year, int month, int day, int hour, int minute);
    }

    public void setOnDateTimeChangedListener(OnDateTimeChangedListener callback) {
        mOnDateTimeChangedListener = callback;
    }

    private void onDateTimeChanged() {
        if (mOnDateTimeChangedListener != null) {
            mOnDateTimeChangedListener.onDateTimeChanged(this, mDate.get(Calendar.YEAR),
                    mDate.get(Calendar.MONTH), mDate.get(Calendar.DAY_OF_MONTH), mHour, mMinute);
        }
    }

}


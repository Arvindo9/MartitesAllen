package com.maritesallen.almanac2020.core.dialogs.date;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;
import android.widget.NumberPicker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.maritesallen.almanac2020.R;
import com.maritesallen.almanac2020.utils.AppConstants;
import com.maritesallen.almanac2020.utils.util.General;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Objects;

/**
 * Author       : Arvindo Mondal
 * Created on   : 24-06-2019
 * Email        : arvindomondal@gmail.com
 * Designation  : Programmer
 * About        : I am a human can only think, I can't be a person like machine which have lots of memory and knowledge.
 * Quote        : No one can measure limit of stupidity but stupid things bring revolutions
 * Strength     : Never give up
 * Motto        : To be known as great Mathematician
 * Skills       : Algorithms and logic
 */
public class DateDialog extends DialogFragment
        implements android.app.DatePickerDialog.OnDateSetListener {

    public static final String TAG = DateDialog.class.getSimpleName();
    private static DateInterface dateInterface;

    private boolean single = false;
    private boolean showInputDate = false;
    private int[] calendarDate;

    public void setShowDateSingle(boolean showInputDate, int[] calendarDate) {
        this.showInputDate = showInputDate;
        this.calendarDate = calendarDate;
    }

    public interface DateInterface {

        /**
         *
         * @param date date will return in string format
         * @param params date will return in int format
         */
        void date(String date, String... params);
    }

    public void setSingle(boolean value){
        single = value;
    }

    public static DateDialog newInstance(DateInterface object) {
        dateInterface = object;
        DateDialog fragment = new DateDialog();
        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);
        return fragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current date as the default date in the picker
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        if(single && showInputDate && calendarDate!= null && calendarDate[2]  > 1){
            return singleYearInputDate(AppConstants.DEFAULT_YEAR);
        }
        else if(single) {
//            return singleYear(c, year, month, day);
            return singleYear(c, AppConstants.DEFAULT_YEAR, month, day);
        }
        DatePickerDialog dialog = new DatePickerDialog(Objects.requireNonNull(getContext()), R.style.DateDialog,
                this, year, month, day);
            dialog.getDatePicker().setMaxDate(c.getTimeInMillis());

        return dialog;
    }

    private Dialog singleYear(Calendar cal, int year, int month, int day) {
        AlertDialog.Builder builder = new AlertDialog.Builder(Objects.requireNonNull(getActivity()), R.style.AlertDialog);
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();

        View dialog = inflater.inflate(R.layout.date_picker_dialog, null);
        final NumberPicker datePicker = dialog.findViewById(R.id.picker_date);
        final NumberPicker monthPicker = dialog.findViewById(R.id.picker_month);
        final NumberPicker yearPicker = dialog.findViewById(R.id.picker_year);

        datePicker.setMinValue(1);
        datePicker.setMaxValue(cal.getActualMaximum(Calendar.DAY_OF_MONTH));
        datePicker.setValue(cal.get(Calendar.DATE));

        monthPicker.setMinValue(0);
        monthPicker.setMaxValue(11);
        monthPicker.setValue(cal.get(Calendar.MONTH));
        monthPicker.setDisplayedValues(getResources().getStringArray(R.array.months));

        monthPicker.setOnValueChangedListener((picker, oldVal, newVal) -> {
            // Create a calendar object and set year and month
            Calendar mycal = new GregorianCalendar(year, newVal, 1);

            datePicker.setValue(cal.get(Calendar.DATE));
            datePicker.setMaxValue(mycal.getActualMaximum(Calendar.DAY_OF_MONTH));
        });

//        yearPicker.setMinValue(year);     //use this
        yearPicker.setMinValue(year);       //comment this
        yearPicker.setMaxValue(year);
        yearPicker.setValue(year);

        builder.setMessage(getString(R.string.select_date));
        builder.setView(dialog)
                // Add action buttons
                .setPositiveButton(R.string.ok, (dialog12, id) -> {

//                    cal.set(Calendar.YEAR, year);                     //use this
                    cal.set(Calendar.YEAR, yearPicker.getValue());         //comment this
//                    cal.set(Calendar.MONTH, month);
                    cal.set(Calendar.MONTH, monthPicker.getValue());
//                    cal.set(Calendar.DAY_OF_MONTH, day);
                    cal.set(Calendar.DAY_OF_MONTH, datePicker.getValue());

                    String dateReadable = General.getDateReadable(cal.getTime());
                    String sendDate = General.getDateSend(cal.getTime());

                    int dateInt = General.getDateInt(cal.getTime());

                    dateInterface.date(dateReadable, sendDate, String.valueOf(dateInt), String.valueOf(cal.get(Calendar.DATE)),
                            String.valueOf(cal.get(Calendar.MONTH)), String.valueOf(cal.get(Calendar.YEAR)));
                })
                .setNegativeButton(R.string.cancel, (dialog1, id) -> {
//                        MonthYearPickerDialog.this.getDialog().cancel();
                });
        return builder.create();
    }

    private Dialog singleYearInputDate(final int year) {
        final Calendar cal = Calendar.getInstance();
        int month;
        int day;
//        int year;
        if(calendarDate[0]  > 1 ) {
//            year = calendarDate[2];
            month = calendarDate[1];
            day = calendarDate[0];

            cal.set(Calendar.YEAR, year);         //comment this
            cal.set(Calendar.MONTH, month);
            cal.set(Calendar.DAY_OF_MONTH, day);
        }
        else{
//            year = cal.get(Calendar.YEAR);
            month = cal.get(Calendar.MONTH);
            day = cal.get(Calendar.DAY_OF_MONTH);
        }



        AlertDialog.Builder builder = new AlertDialog.Builder(Objects.requireNonNull(getActivity()), R.style.AlertDialog);
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();

        View dialog = inflater.inflate(R.layout.date_picker_dialog, null);
        final NumberPicker datePicker = dialog.findViewById(R.id.picker_date);
        final NumberPicker monthPicker = dialog.findViewById(R.id.picker_month);
        final NumberPicker yearPicker = dialog.findViewById(R.id.picker_year);

        datePicker.setMinValue(1);
        datePicker.setMaxValue(cal.getActualMaximum(Calendar.DAY_OF_MONTH));
        datePicker.setValue(cal.get(Calendar.DATE));

        monthPicker.setMinValue(0);
        monthPicker.setMaxValue(11);
        monthPicker.setValue(cal.get(Calendar.MONTH));
        monthPicker.setDisplayedValues(getResources().getStringArray(R.array.months));

        monthPicker.setOnValueChangedListener((picker, oldVal, newVal) -> {
            // Create a calendar object and set year and month
            Calendar mycal = new GregorianCalendar(year, newVal, 1);

            datePicker.setValue(cal.get(Calendar.DATE));
            datePicker.setMaxValue(mycal.getActualMaximum(Calendar.DAY_OF_MONTH));
        });

//        yearPicker.setMinValue(year);     //use this
        yearPicker.setMinValue(year);       //comment this
        yearPicker.setMaxValue(year);
        yearPicker.setValue(year);

        builder.setMessage(getString(R.string.select_date));
        builder.setView(dialog)
                // Add action buttons
                .setPositiveButton(R.string.ok, (dialog12, id) -> {

//                    cal.set(Calendar.YEAR, year);                     //use this
                    cal.set(Calendar.YEAR, yearPicker.getValue());         //comment this
//                    cal.set(Calendar.MONTH, month);
                    cal.set(Calendar.MONTH, monthPicker.getValue());
//                    cal.set(Calendar.DAY_OF_MONTH, day);
                    cal.set(Calendar.DAY_OF_MONTH, datePicker.getValue());

                    String dateReadable = General.getDateReadable(cal.getTime());
                    String sendDate = General.getDateSend(cal.getTime());

                    int dateInt = General.getDateInt(cal.getTime());

                    dateInterface.date(dateReadable, sendDate, String.valueOf(dateInt), String.valueOf(cal.get(Calendar.DATE)),
                            String.valueOf(cal.get(Calendar.MONTH)), String.valueOf(cal.get(Calendar.YEAR)));
                })
                .setNegativeButton(R.string.cancel, (dialog1, id) -> {
//                        MonthYearPickerDialog.this.getDialog().cancel();
                });
        return builder.create();
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int day) {
        // Do something with the date chosen by the user
        Calendar myCalendar = Calendar.getInstance();
        myCalendar.set(Calendar.YEAR, year);
        myCalendar.set(Calendar.MONTH, month);
        myCalendar.set(Calendar.DAY_OF_MONTH, day);

        String dateReadable = General.getDateReadable(myCalendar.getTime());
        String sendDate = General.getDateSend(myCalendar.getTime());

        int dateInt = General.getDateInt(myCalendar.getTime());

        dateInterface.date(dateReadable, sendDate, String.valueOf(dateInt), String.valueOf(day),
                String.valueOf(month), String.valueOf(year));

        this.dismiss();
    }
}

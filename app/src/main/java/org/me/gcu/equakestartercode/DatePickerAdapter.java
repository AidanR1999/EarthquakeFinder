package org.me.gcu.equakestartercode;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import java.util.Calendar;

public class DatePickerAdapter extends DialogFragment {

    public static final int FLAG_START_DATE = 0;
    public static final int FLAG_END_DATE = 1;

    private int flag = 0;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog dialog = new DatePickerDialog(
                getActivity(),
                (DatePickerDialog.OnDateSetListener)getActivity(),
                year,
                month,
                dayOfMonth
        );

        dialog.getDatePicker().setMaxDate(System.currentTimeMillis() + 1000);
        return dialog;
    }

    public void setFlag(int i) {
        flag = i;
    }

    public int getFlag() {
        return flag;
    }

}

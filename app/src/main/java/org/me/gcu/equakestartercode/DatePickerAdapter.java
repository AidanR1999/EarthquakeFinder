//Aidan Rooney - S1911669
package org.me.gcu.equakestartercode;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import java.util.Calendar;

//adapter for creating date picker dialog
public class DatePickerAdapter extends DialogFragment {

    //declare flags for setting date picker values
    public static final int FLAG_START_DATE = 0;
    public static final int FLAG_END_DATE = 1;
    private int flag = 0;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        //set values using calender instance
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

        //display dialog
        DatePickerDialog dialog = new DatePickerDialog(
                getActivity(),
                (DatePickerDialog.OnDateSetListener) getTargetFragment(),
                year,
                month,
                dayOfMonth
        );

        //set maximum date so user cant select future
        dialog.getDatePicker().setMaxDate(System.currentTimeMillis() + 1000);
        return dialog;
    }

    //attaches context to dialog
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }

    //flag setter
    public void setFlag(int i) {
        flag = i;
    }

    //flag getter
    public int getFlag() {
        return flag;
    }

}

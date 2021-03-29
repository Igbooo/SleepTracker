package com.example.sleeptracker.ui.sleep;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.sleeptracker.ITimePicker;
import com.example.sleeptracker.MainActivity;
import com.example.sleeptracker.R;

import java.util.Calendar;

public class SleepFragment extends Fragment implements ITimePicker {
    TextView textView;
    Button button;
    int day, month, year, hour, minute;
    int dDay, dMonth, dYear, dHour, dMinute;

    private SleepViewModel homeViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(SleepViewModel.class);
        View root = inflater.inflate(R.layout.fragment_sleep, container, false);

        textView = root.findViewById(R.id.txtView);
        textView = root.findViewById(R.id.txtView2);

        button = root.findViewById(R.id.btnPick);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                year = calendar.get(Calendar.YEAR);
                month = calendar.get(Calendar.MONTH);
                day = calendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), (DatePickerDialog.OnDateSetListener) getActivity(), year, month, day);
                datePickerDialog.show();
            }
        });
        return root;
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        dYear = year;
        dDay = day;
        dMonth = month;
        Calendar c = Calendar.getInstance();
        hour = c.get(Calendar.HOUR);
        minute = c.get(Calendar.MINUTE);
        TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity(),
                (TimePickerDialog.OnTimeSetListener) getActivity(), hour, minute,
                DateFormat.is24HourFormat(getActivity()));
        timePickerDialog.show();
    }


    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        dHour = hourOfDay;
        dMinute = minute;
        textView.setText("Year:" +dYear+"\n" +"Month:" + dMonth +
                "\n" + "Day:" + dDay +
                "\n" + "Hour:" + dHour +
                "\n" + "Minute" + dMinute);
    }
}

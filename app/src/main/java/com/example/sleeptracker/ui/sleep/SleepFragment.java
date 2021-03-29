package com.example.sleeptracker.ui.sleep;

import android.app.Activity;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.sleeptracker.MainActivity;
import com.example.sleeptracker.R;

import android.text.format.DateFormat;
import java.util.Calendar;

public class SleepFragment extends Fragment {
    Button buttonBedtime, buttonWakeUp;
    TextView tvBedtime, tvWakeUp;
    int bedHour, bedMinute, wakeHour, wakeMinute;

    private SleepViewModel homeViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(SleepViewModel.class);
        View root = inflater.inflate(R.layout.fragment_sleep, container, false);

        tvBedtime = root.findViewById(R.id.bedtimeTimeText);
        tvWakeUp = root.findViewById(R.id.wakeUpTimeText);
        buttonBedtime = root.findViewById(R.id.downArrowBedtime);
        buttonWakeUp = root.findViewById(R.id.downArrowWakeUp);

        buttonBedtime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(
                        getActivity(),
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                bedHour = hourOfDay;
                                bedMinute = minute;
                                Calendar calendar = Calendar.getInstance();
                                calendar.set(0,0,0,bedHour,bedMinute);
                                tvBedtime.setText(DateFormat.format("hh:mm aa", calendar));
                            }
                        },12,0,false
                );
                timePickerDialog.updateTime(bedHour,bedMinute);

                timePickerDialog.show();
            }
        });

        buttonWakeUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(
                        getActivity(),
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                wakeHour = hourOfDay;
                                wakeMinute = minute;
                                Calendar calendar = Calendar.getInstance();
                                calendar.set(0,0,0,wakeHour,wakeMinute);
                                tvWakeUp.setText(DateFormat.format("hh:mm aa", calendar));
                            }
                        },12,0,false
                );
                timePickerDialog.updateTime(wakeHour,wakeMinute);

                timePickerDialog.show();
            }
        });
        return root;
    }

}

package com.example.sleeptracker.ui.sleep;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;

import com.example.sleeptracker.R;

import android.text.format.DateFormat;
import android.widget.Toast;


import java.util.Calendar;
import java.util.concurrent.TimeUnit;

public class SleepFragment extends Fragment {
    Button buttonBedtime, buttonWakeUp, buttonSave,buttonSleepDate, buttonWakeDate;
    TextView tvBedtime, tvWakeUp, tvBedDate, tvWakeDate;
    int bedHour, bedMinute, wakeHour, wakeMinute;
    int dYear,  dMonth,  day;

    private SleepViewModel homeViewModel;

    public void CalculateTime(int bedHour, int bedMinute, int wakeHour, int wakeMinute)
    {
        long Hduration = bedHour - wakeHour;
        long Mduration = bedMinute - wakeMinute;
        long diffInHr = TimeUnit.MICROSECONDS.toHours(Hduration);
        long diffInMin = TimeUnit.MICROSECONDS.toMinutes(Mduration);

        System.out.println(diffInHr + "Hrs and " + diffInMin  + "Minutes");
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(SleepViewModel.class);
        View root = inflater.inflate(R.layout.fragment_sleep, container, false);

        tvBedtime = root.findViewById(R.id.bedtimeTimeText);
        tvWakeUp = root.findViewById(R.id.wakeUpTimeText);
        buttonBedtime = root.findViewById(R.id.downArrowBedtime);
        buttonWakeUp = root.findViewById(R.id.downArrowWakeUpTime);
        buttonSave = root.findViewById(R.id.saveInputButton);
        buttonSleepDate = root.findViewById(R.id.downArrowBedDate);
        buttonWakeDate = root.findViewById(R.id.downArrowWakeDate);

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
        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_navigation_sleep_to_navigation_profile);
                Toast toast = Toast.makeText(getContext(),
                        "Saved!",
                        Toast.LENGTH_LONG);
                toast.setGravity(Gravity.BOTTOM, 0, 250);
                toast.show();
            }
        });

        buttonSleepDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        getActivity(),
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                dYear = year;
                                day = dayOfMonth;
                                dMonth = month;
                                Calendar calendar = Calendar.getInstance();
                                calendar.set(dYear, dMonth, day);
                                tvBedtime.setText(DateFormat.format("dd:mm:yyyy", calendar));
                            }
                        }, 2010, 1, 1
                );
                datePickerDialog.updateDate(dYear, dMonth, day);
                datePickerDialog.show();
            }
        });

        buttonWakeDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        getActivity(),
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                dYear = year;
                                day = dayOfMonth;
                                dMonth = month;
                                Calendar calendar = Calendar.getInstance();
                                calendar.set(dYear, dMonth, day);
                                tvWakeUp.setText(DateFormat.format("dd-mm-yyyy", calendar));
                            }
                        }, 2010, 1, 1
                );
                datePickerDialog.updateDate(dYear, dMonth, day);
                datePickerDialog.show();
            }
        });

        return root;

    }
}

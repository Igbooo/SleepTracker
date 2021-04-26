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
import com.example.sleeptracker.SleepRecord;

import android.text.format.DateFormat;
import android.widget.Toast;


import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;

import static com.example.sleeptracker.MainActivity.sleepDatabase;

public class SleepFragment extends Fragment {
    Button buttonBedtime, buttonWakeUp, buttonSave,buttonSleepDate, buttonWakeDate;
    TextView tvBedtimeTime, tvWakeUpTime, tvBedDate, tvWakeDate;
    int bedHour, bedMinute, wakeHour, wakeMinute;
    int dYear,  dMonth,  day;
    private SleepRecord sleep;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        SleepViewModel homeViewModel = ViewModelProviders.of(this).get(SleepViewModel.class);
        View root = inflater.inflate(R.layout.fragment_sleep, container, false);

        sleep = new SleepRecord();
        tvBedtimeTime = root.findViewById(R.id.bedtimeTimeText);
        tvWakeUpTime = root.findViewById(R.id.wakeUpTimeText);
        tvBedDate = root.findViewById(R.id.bedtimeDateText);
        tvWakeDate = root.findViewById(R.id.wakeUpDateText);
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
                                tvBedtimeTime.setText(DateFormat.format("hh:mm aa", calendar));
                                sleep.setStartTime(DateFormat.format("HH:mm", calendar).toString());
                            }
                        },LocalTime.now().getHour(), LocalTime.now().getMinute(),false
                );
                //timePickerDialog.updateTime(bedHour,bedMinute);
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
                                tvWakeUpTime.setText(DateFormat.format("hh:mm aa", calendar));
                                sleep.setEndTime(DateFormat.format("HH:mm", calendar).toString());
                            }
                        }, LocalTime.now().getHour(), LocalTime.now().getMinute(),false
                );
                //timePickerDialog.updateTime(wakeHour,wakeMinute);
                timePickerDialog.show();
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
                                tvBedDate.setText(DateFormat.format("yyyy-MM-dd", calendar));
                                sleep.setStartDate(DateFormat.format("yyyy-MM-dd", calendar).toString());
                            }
                        }, LocalDate.now().getYear(), LocalDate.now().getMonthValue() - 1, LocalDate.now().getDayOfMonth()
                );
                //datePickerDialog.updateDate(dYear, dMonth, day);
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
                                tvWakeDate.setText(DateFormat.format("yyyy-MM-dd", calendar));
                                sleep.setEndDate(DateFormat.format("yyyy-MM-dd", calendar).toString());
                            }
                        }, LocalDate.now().getYear(), LocalDate.now().getMonthValue() - 1, LocalDate.now().getDayOfMonth()
                );
                //datePickerDialog.updateDate(dYear, dMonth, day);
                datePickerDialog.show();
            }
        });
        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_navigation_sleep_to_navigation_profile);
                sleepDatabase.sleepDAO().addRecord(sleep);
                sleep = new SleepRecord(); //reset the sleep record
                Toast toast = Toast.makeText(getContext(),
                        "Saved!",
                        Toast.LENGTH_LONG);
                toast.setGravity(Gravity.BOTTOM, 0, 250);
                toast.show();
            }
        });

        return root;
    }
}

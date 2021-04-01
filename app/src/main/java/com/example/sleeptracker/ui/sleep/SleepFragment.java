package com.example.sleeptracker.ui.sleep;

import android.app.TimePickerDialog;
import android.content.Intent;
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
import androidx.navigation.Navigation;

import com.example.sleeptracker.R;

import android.text.format.DateFormat;
import android.widget.Toast;


import java.util.Calendar;
import java.util.concurrent.TimeUnit;

public class SleepFragment extends Fragment {
    Button buttonBedtime, buttonWakeUp, buttonSave;
    TextView tvBedtime, tvWakeUp;
    int bedHour, bedMinute, wakeHour, wakeMinute;

    private SleepViewModel homeViewModel;

    public void CalculateTime(int bedHour, int bedMinute, int wakeHour, int wakeMinute)
    {

        long duration = bedHour - wakeHour;
        long diffInHr = TimeUnit.MICROSECONDS.toHours(duration);
        long diffInMin = TimeUnit.MICROSECONDS.toMinutes(duration);



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
        buttonWakeUp = root.findViewById(R.id.downArrowWakeUp);
        buttonSave = root.findViewById(R.id.saveInputButton);


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



                Toast.makeText(getContext(),
                        "Saved!",
                        Toast.LENGTH_LONG).show();

            }

        });
        return root;

    }

}

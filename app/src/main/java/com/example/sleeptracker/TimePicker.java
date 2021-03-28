package com.example.sleeptracker;

import android.widget.DatePicker;

import androidx.appcompat.app.AppCompatActivity;

public abstract class TimePicker extends AppCompatActivity {
    public abstract void onDateSet(DatePicker view, int year, int month, int dayOfMonth);

    public abstract void onTimeSet(android.widget.TimePicker view, int hourOfDay, int minute);
}

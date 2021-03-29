package com.example.sleeptracker;

import android.widget.DatePicker;

public interface ITimePicker {
    public abstract void onDateSet(DatePicker view, int year, int month, int dayOfMonth);

    public abstract void onTimeSet(android.widget.TimePicker view, int hourOfDay, int minute);
}

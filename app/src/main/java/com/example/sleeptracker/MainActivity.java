package com.example.sleeptracker;

import android.app.ActionBar;
import android.app.DatePickerDialog;
import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import java.util.Calendar;
import android.app.TimePickerDialog;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.V;


public class MainActivity extends com.example.sleeptracker.TimePicker {
    TextView textView;
    Button button;
    int day, month, year, hour, minute;
    int dDay, dMonth, dYear, dHour,dMinute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_sleep, R.id.navigation_stats, R.id.navigation_profile)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);
        textView = findViewById(R.id.txtView);
        textView = findViewById(R.id.txtView2);

        //button = findViewById(R.id.btnPick);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                year = calendar.get(Calendar.YEAR);
                month = calendar.get(Calendar.MONTH);
                day = calendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(MainActivity.this, (DatePickerDialog.OnDateSetListener) MainActivity.this,year, month,day);
                datePickerDialog.show();
            }
        });
    }


    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        dYear = year;
        dDay = day;
        dMonth = month;
        Calendar c = Calendar.getInstance();
        hour = c.get(Calendar.HOUR);
        minute = c.get(Calendar.MINUTE);
        TimePickerDialog timePickerDialog = new TimePickerDialog(MainActivity.this,
                (TimePickerDialog.OnTimeSetListener) MainActivity.this, hour, minute,
                DateFormat.is24HourFormat(this));
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

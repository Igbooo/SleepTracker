package com.example.sleeptracker;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.room.Room;

public class MainActivity extends AppCompatActivity {
    public static SleepDatabase sleepDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //db init
        sleepDatabase = Room.databaseBuilder(getApplicationContext(), SleepDatabase.class, "sleepdb").allowMainThreadQueries().build();
        //disable this for release
        sleepDatabase.clearAllTables();
        boolean dbWorks = dbTest();

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
    }

    private boolean dbTest() {
        try {
            SleepRecord sleep = new SleepRecord();
            sleep.setStartTime("00:00");
            sleep.setEndTime("03:00");
            sleep.setStartDate("01-01-2021");
            sleep.setEndDate("01-01-2021");
            sleepDatabase.sleepDAO().addRecord(sleep);
            //sleepDatabase.sleepDAO().deleteRecord(sleep);

            SleepRecord sleep1 = new SleepRecord();
            sleep1.setStartTime("00:00");
            sleep1.setEndTime("03:00");
            sleep1.setStartDate("02-01-2021");
            sleep1.setEndDate("02-01-2021");
            sleepDatabase.sleepDAO().addRecord(sleep1);

            SleepRecord sleep2 = new SleepRecord();
            sleep2.setStartTime("00:00");
            sleep2.setEndTime("01:00");
            sleep2.setStartDate("03-01-2021");
            sleep2.setEndDate("03-01-2021");
            sleepDatabase.sleepDAO().addRecord(sleep2);

            SleepRecord sleep3 = new SleepRecord();
            sleep3.setStartTime("00:00");
            sleep3.setEndTime("01:00");
            sleep3.setStartDate("04-01-2021");
            sleep3.setEndDate("04-01-2021");
            sleepDatabase.sleepDAO().addRecord(sleep3);

            SleepRecord sleep4 = new SleepRecord();
            sleep4.setStartTime("00:00");
            sleep4.setEndTime("03:00");
            sleep4.setStartDate("05-01-2021");
            sleep4.setEndDate("05-01-2021");
            sleepDatabase.sleepDAO().addRecord(sleep4);

            SleepRecord sleep5 = new SleepRecord();
            sleep5.setStartTime("00:00");
            sleep5.setEndTime("01:00");
            sleep5.setStartDate("06-01-2021");
            sleep5.setEndDate("06-01-2021");
            sleepDatabase.sleepDAO().addRecord(sleep5);

            SleepRecord sleep6 = new SleepRecord();
            sleep6.setStartTime("00:00");
            sleep6.setEndTime("01:00");
            sleep6.setStartDate("07-01-2021");
            sleep6.setEndDate("07-01-2021");
            sleepDatabase.sleepDAO().addRecord(sleep6);

            Toast.makeText(this, "DEBUG: Database inserted & deleted data successfully!", Toast.LENGTH_SHORT).show();

            return true;
        }
        catch (Exception e) {
            return false;
        }
    }
}

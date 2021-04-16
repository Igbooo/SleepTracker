package com.example.sleeptracker;

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
        //boolean dbWorks = dbTest();

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
            sleep.setStartTime("12:00AM");
            sleep.setEndTime("01:00AM");
            sleep.setStartDate("1970-01-01");
            sleep.setEndDate("1970-01-01");
            sleepDatabase.sleepDAO().addRecord(sleep);
            //sleepDatabase.sleepDAO().deleteRecord(sleep);

            Toast.makeText(this, "DEBUG: Database inserted data successfully!", Toast.LENGTH_SHORT).show();

            return true;
        }
        catch (Exception e) {
            return false;
        }
    }
}

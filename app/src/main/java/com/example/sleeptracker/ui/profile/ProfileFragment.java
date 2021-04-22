package com.example.sleeptracker.ui.profile;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.sleeptracker.R;
import com.example.sleeptracker.SleepRecord;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import static com.example.sleeptracker.MainActivity.sleepDatabase;
import static java.time.format.TextStyle.FULL;
import static java.time.temporal.ChronoUnit.HOURS;
import static java.time.temporal.ChronoUnit.MINUTES;

public class ProfileFragment extends Fragment implements DatePickerDialog.OnDateSetListener {
    List<SleepRecord> sleeps = sleepDatabase.sleepDAO().getDataFromDB();
    public static LocalDate currentViewedDate;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ProfileViewModel profileViewModel = ViewModelProviders.of(this).get(ProfileViewModel.class);
        View root = inflater.inflate(R.layout.fragment_profile, container, false);

        //get the current sleep record for today
        loadNewDate(root, LocalDate.now().toString());

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        view.findViewById(R.id.ProfileDate).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                calendar.set(currentViewedDate.getYear(), (currentViewedDate.getMonthValue() - 1),
                        currentViewedDate.getDayOfMonth());
                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), ProfileFragment.this, calendar
                        .get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.setOnDateSetListener(ProfileFragment.this);
                datePickerDialog.show();
            }
        });
        view.findViewById(R.id.PreviousButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentViewedDate = currentViewedDate.minusDays(1);
                loadNewDate(getView(), currentViewedDate.toString());
            }
        });
        view.findViewById(R.id.NextButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentViewedDate = currentViewedDate.plusDays(1);
                loadNewDate(getView(), currentViewedDate.toString());
            }
        });
    }

    private SleepRecord getSleepFromDay(List<SleepRecord> sleeps, String date) {
        for (SleepRecord s : sleeps) {
            if (s.getStartDate().equals(date)) {
                return s;
            }
        }
        return null;
    }

    private void loadNewDate(View view, String date) {
        //here we set texts :D
        final TextView dayOfWeekTV = (TextView) view.findViewById(R.id.ProfileDate);
        final TextView wentToBedTV = (TextView) view.findViewById(R.id.wentToBedText);
        final TextView wokeUpTV = (TextView) view.findViewById(R.id.wokeUpText);
        final TextView timeSleptTV = (TextView) view.findViewById(R.id.timeSleptText);

        sleeps = sleepDatabase.sleepDAO().getDataFromDB();
        SleepRecord currentSleep;

        //set the current viewed date to enable navigation
        currentViewedDate = LocalDate.parse(date);

        //next we get the LocalDate so we can know the day of the week
        LocalDate lDate = LocalDate.parse(date);
        String dayOfWeek = lDate.getDayOfWeek().getDisplayName(FULL, Locale.ENGLISH);
        dayOfWeekTV.setText(dayOfWeek + "\n" + lDate.getDayOfMonth() + "/" + lDate.getMonthValue() + "/" + lDate.getYear());

        //calculate the time slept
        if ((currentSleep = getSleepFromDay(sleeps, date)) != null) {
            LocalTime startTime = LocalTime.parse(currentSleep.getStartTime());
            LocalTime endTime = LocalTime.parse(currentSleep.getEndTime());
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm a");
            String startTimeFormat = startTime.format(formatter);
            String endTimeFormat = endTime.format(formatter);
            Long hoursSlept = HOURS.between(startTime, endTime);
            Long minutesSlept = MINUTES.between(startTime, endTime) % 60;

            //failsafe for time logic
            if (hoursSlept < 0) { hoursSlept += 12; }

            wentToBedTV.setText(getString(R.string.profile_to_bed_text) + " " + startTimeFormat);
            wokeUpTV.setText(getString(R.string.profile_woke_up_text) + " " + endTimeFormat);
            timeSleptTV.setText(getString(R.string.profile_time_slept_text) + " " + hoursSlept + "h " + minutesSlept + "m");
        } else { //if the current date doesn't have a sleeprecord
            wentToBedTV.setText(getString(R.string.profile_to_bed_text) + " ");
            wokeUpTV.setText(getString(R.string.profile_woke_up_text) + " ");
            timeSleptTV.setText(getString(R.string.profile_time_slept_text) + " ");
            Toast.makeText(getContext(), "There is no data for this date!!", Toast.LENGTH_SHORT).show();
        }
    }

    public void onDateSet(DatePicker view, int year, int month, int day) {
        month++; //change 0-11 dates to 1-12
        currentViewedDate = LocalDate.of(year, month, day);
        loadNewDate(getView(), currentViewedDate.toString());
    }
}
package com.example.sleeptracker.ui.stats;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.sleeptracker.R;
import com.example.sleeptracker.SleepRecord;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.ValueDependentColor;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import static com.example.sleeptracker.MainActivity.sleepDatabase;
import static java.time.format.TextStyle.FULL;
import static java.time.temporal.ChronoUnit.HOURS;
import static java.time.temporal.ChronoUnit.MINUTES;

public class StatsFragment extends Fragment implements DatePickerDialog.OnDateSetListener {
    List<SleepRecord> sleeps = sleepDatabase.sleepDAO().getDataFromDB();
    BarGraphSeries<DataPoint> series;
    public static LocalDate currentViewedDate;
    Double maxSleepTime;
    GraphView graph;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_stats, container, false);
        StatsViewModel statsViewModel = ViewModelProviders.of(this).get(StatsViewModel.class);

        graph = (GraphView) root.findViewById(R.id.graph);

        //load data starting at the current date
        DataPoint[] dataPoints = loadNewDate(root, LocalDate.now().toString());
        series = new BarGraphSeries<>(dataPoints);

        //graph related setup
        graph.addSeries(series);
        series.setValueDependentColor(new ValueDependentColor<DataPoint>() {
            @Override
            public int get(DataPoint data) {
                return Color.rgb(255, 128, 0);
            }
        });
        series.setSpacing(50);
        //graph.getGridLabelRenderer().setNumHorizontalLabels(7);
        graph.getGridLabelRenderer().setHorizontalAxisTitle(getString(R.string.horizontal_axis_title));
        graph.getGridLabelRenderer().setVerticalAxisTitle(getString(R.string.vertical_axis_title));
        graph.getGridLabelRenderer().setPadding(75);

        graph.getViewport().setXAxisBoundsManual(true);
        graph.getViewport().setMinX(0.5);
        graph.getViewport().setMaxX(7.5);

        graph.getViewport().setYAxisBoundsManual(true);
        graph.getViewport().setMinY(0);
        graph.getViewport().setMaxY(maxSleepTime + 1);

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        view.findViewById(R.id.statsDate).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                calendar.set(currentViewedDate.getYear(), (currentViewedDate.getMonthValue() - 1),
                        currentViewedDate.getDayOfMonth());
                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), StatsFragment.this, calendar
                        .get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.setOnDateSetListener(StatsFragment.this);
                datePickerDialog.show();
            }
        });

        view.findViewById(R.id.statsCalendar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                calendar.set(currentViewedDate.getYear(), (currentViewedDate.getMonthValue() - 1),
                        currentViewedDate.getDayOfMonth());
                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), StatsFragment.this, calendar
                        .get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.setOnDateSetListener(StatsFragment.this);
                datePickerDialog.show();
            }
        });

        view.findViewById(R.id.previousStatsButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                series.resetData(loadNewDate(getView(), currentViewedDate.minusDays(7).toString()));
            }
        });

        view.findViewById(R.id.nextStatsButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                series.resetData(loadNewDate(getView(), currentViewedDate.plusDays(7).toString()));
            }
        });
    }

    private DataPoint[] loadNewDate(View view, String date) {
        final TextView dayOfWeekTV = (TextView) view.findViewById(R.id.statsDate);
        sleeps = sleepDatabase.sleepDAO().getDataFromDB();
        List<Double> timesSlept = new ArrayList<Double>();

        currentViewedDate = LocalDate.parse(date);
        LocalDate lDate = LocalDate.parse(date);
        String dayOfWeek = lDate.getDayOfWeek().getDisplayName(FULL, Locale.ENGLISH);

        dayOfWeekTV.setText(lDate.getDayOfMonth() + "/" + lDate.getMonthValue() + "/" + lDate.getYear());

        //get a week of dates into a list
        ArrayList<LocalDate> localDates = new ArrayList<LocalDate>();
        for (int i=0; i<7; i++) {
            localDates.add(lDate.plusDays(i));
        }

        for (LocalDate searchDate : localDates) { //for all dates this week
            boolean found = false;

            //for every entry in the DB
            for (SleepRecord s : sleeps) {
                LocalDate startDate = LocalDate.parse(s.getStartDate());
                //if the sleep's date matches one we're trying to display
                if (searchDate.equals(startDate)) {
                    //work out the time slept and add it to a list of times
                    LocalTime startTime = LocalTime.parse(s.getStartTime());
                    LocalTime endTime = LocalTime.parse(s.getEndTime());
                    LocalDate endDate = LocalDate.parse(s.getEndDate());
                    LocalDateTime startDT = LocalDateTime.of(startDate, startTime);
                    LocalDateTime endDT = LocalDateTime.of(endDate, endTime);
                    
                    Long timeSlept = MINUTES.between(startDT, endDT);

                    double hrsSlept = (timeSlept / 60.0) + ((timeSlept % 60.0) / 60.0);
                    timesSlept.add(hrsSlept);
                    found = true;
                }
            }

            if (!found) { //if we never find a matching entry, set the time slept for that date to 0
                timesSlept.add(0D);
            }
        }

        //find the largest time slept so we can set the Y value of the graph
        maxSleepTime = 0D;
        for (Double t : timesSlept) {
            if (t > maxSleepTime) {
                maxSleepTime = t;
            }
        }

        //set the datapoints for the graph to use
        DataPoint[] values = new DataPoint[7];
        for (int i=0; i<7; i++) {
            double x = i + 1;
            double y = timesSlept.get(i);
            DataPoint v = new DataPoint(x, y);
            values[i] = v;
        }
        return values;
    }

    public void onDateSet(DatePicker view, int year, int month, int day) {
        month++; //change 0-11 dates to 1-12
        currentViewedDate = LocalDate.of(year, month, day);

        //change the graph values
        series.resetData(loadNewDate(getView(), currentViewedDate.toString()));
        graph.getViewport().setMaxY(maxSleepTime + 1); //set the y value
    }
}

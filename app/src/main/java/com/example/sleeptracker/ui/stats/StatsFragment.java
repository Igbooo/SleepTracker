package com.example.sleeptracker.ui.stats;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.sleeptracker.R;
import com.example.sleeptracker.SleepRecord;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.ValueDependentColor;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static com.example.sleeptracker.MainActivity.sleepDatabase;
import static java.time.temporal.ChronoUnit.HOURS;
import static java.time.temporal.ChronoUnit.MINUTES;

public class StatsFragment extends Fragment {

    private StatsViewModel statsViewModel;
    List<SleepRecord> sleeps = sleepDatabase.sleepDAO().getDataFromDB();
    List<Long> timesSlept = new ArrayList<Long>();

    @RequiresApi(api = Build.VERSION_CODES.O)
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        for (int i=0; i<7; i++) {
            SleepRecord currentSleep = sleeps.get(i);
            LocalTime startTime = LocalTime.parse(currentSleep.getStartTime());
            LocalTime endTime = LocalTime.parse(currentSleep.getEndTime());
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm a");
            String startTimeFormat = startTime.format(formatter);
            String endTimeFormat = endTime.format(formatter);
            Long timeSlept = MINUTES.between(startTime, endTime) / 60;

            timesSlept.add(timeSlept);
        }

        statsViewModel =
                ViewModelProviders.of(this).get(StatsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_stats, container, false);
        GraphView graph = (GraphView) root.findViewById(R.id.graph);
        BarGraphSeries<DataPoint> series = new BarGraphSeries<>(new DataPoint[] {
                new DataPoint(1, timesSlept.get(0)),
                new DataPoint(2, timesSlept.get(1)),
                new DataPoint(3, timesSlept.get(2)),
                new DataPoint(4, timesSlept.get(3)),
                new DataPoint(5, timesSlept.get(4)),
                new DataPoint(6, timesSlept.get(5)),
                new DataPoint(7, timesSlept.get(6))
        });
        graph.addSeries(series);
        series.setValueDependentColor(new ValueDependentColor<DataPoint>() {
            @Override
            public int get(DataPoint data) {
                return Color.rgb(255, 128, 0);
            }
        });
        series.setSpacing(50);
        graph.getGridLabelRenderer().setHorizontalAxisTitle(getString(R.string.horizontal_axis_title));
        graph.getGridLabelRenderer().setVerticalAxisTitle(getString(R.string.vertical_axis_title));
        graph.getGridLabelRenderer().setPadding(75);

        graph.getViewport().setXAxisBoundsManual(true);
        graph.getViewport().setMinX(0.5);
        graph.getViewport().setMaxX(7.5);

        graph.getViewport().setYAxisBoundsManual(true);
        graph.getViewport().setMinY(0);
        return root;
    }
}

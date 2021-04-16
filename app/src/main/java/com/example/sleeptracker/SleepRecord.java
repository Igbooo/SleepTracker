package com.example.sleeptracker;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

@Entity(tableName = "sleepy")
public class SleepRecord {

    @PrimaryKey(autoGenerate = true)
    private int recordID;

    @ColumnInfo(name="start_time")
    private String startTime;

    @ColumnInfo(name="end_time")
    private String endTime;

    @ColumnInfo(name="start_date")
    private String startDate;

    @ColumnInfo(name="end_date")
    private String endDate;

    public int getRecordID() {
        return recordID;
    }

    public void setRecordID(int recordID) {
        this.recordID = recordID;
    }

    public String getStartTime() {
        return startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
}

package com.example.sleeptracker;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface SleepDAO {

    @Insert
    public void addRecord(SleepRecord sleep);

    @Delete
    void deleteRecord (SleepRecord sleep);

    @Query("SELECT * FROM sleepy")
    public List<SleepRecord> getDataFromDB();
}

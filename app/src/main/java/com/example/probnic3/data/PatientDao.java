package com.example.probnic3.data;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.RawQuery;
import androidx.room.Update;

import com.example.probnic3.Patient;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface PatientDao {

    @Query("SELECT * FROM patient")
    List<Patient> getAll();
    @Update
    void update(Patient patient);
    @Insert
    void insertAll(Patient... patients);
    @Delete
    void delete(Patient patient);
}

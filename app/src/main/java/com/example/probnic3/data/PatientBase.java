package com.example.probnic3.data;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.probnic3.Patient;

@Database(entities = {Patient.class}, version = 1)
public abstract class PatientBase extends RoomDatabase {
    public abstract PatientDao patientDao();
}

package com.example.probnic3;

import android.content.Context;

import androidx.room.Room;

import com.example.probnic3.data.PatientBase;

import java.util.List;

public class PatientRepository {
    public PatientBase roomdatabase;
    private Doctor doctor;
    private static PatientRepository instance = null;
    public static PatientRepository getInstance(Context context) {
        if (instance == null) instance = new PatientRepository(context);
        return instance;
    }

    public PatientRepository(Context context){
        roomdatabase = Room.databaseBuilder(context, PatientBase.class, "roompatientbase").allowMainThreadQueries().build();
       /* roomdatabase.patientDao().insertAll(
                new Patient("Железнодорожная, 1", "Потеря сознания, 50 лет", "Петров Владимир Георгиевич"),
                new Patient("Площадь Маркса, 2", "Боли в животе, 80 лет", "Борисова Людмила Петровна"),
                new Patient("Комсомольская, 3", "Предположительно перелом ноги, 12 лет", "Иванов Иван"));*/
    }

    public List<Patient> getPatient(){
        return roomdatabase.patientDao().getAll();
    }

    public void addPatient(Patient patient){
        //doctor = new Doctor();
        roomdatabase.patientDao().insertAll(patient);
        //doctor.ManagerPoint();
    }

    public void removeByPosition(Patient patient) {
        roomdatabase.patientDao().delete(patient);
    }

    public void updatePatient(Patient patient) {
        roomdatabase.patientDao().update(patient);
    }
}

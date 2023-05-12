package com.example.probnic3;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Objects;
@Entity
public class Patient {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name="id")
    private long id;
    @ColumnInfo(name="street")
    private  String street;
    @ColumnInfo(name="description")
    private  String description;
    @ColumnInfo(name="fio")
    private  String fio;

    public Patient(String street, String description, String fio) {
        this.street = street;
        this.description = description;
        this.fio = fio;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getStreet() {
        return street;
    }

    public String getDescription() {
        return description;
    }

    public String getFio() {
        return fio;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setFio(String fio) {
        this.fio = fio;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Patient patient = (Patient) o;
        return Objects.equals(street, patient.street) && Objects.equals(description, patient.description) && Objects.equals(fio, patient.fio);
    }

    @Override
    public int hashCode() {
        return Objects.hash(street, description, fio);
    }

    @Override
    public String toString() {
        return street + " " + description + " " + fio;
    }
}

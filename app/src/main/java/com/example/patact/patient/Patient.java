package com.example.patact.patient;

import android.os.Parcel;
import android.os.Parcelable;

public class Patient implements Parcelable {
    private final long patientId;
    private final int cardNum;
    private final int histNum;
    private final long visitId;
    private final long visitRootId;
    private final String agr;
    private String fio;
    private String age;
    private final int bedId;
    private String bed;
    private final Integer profId;
    private final String prof;
    private final String doctor;
    private final String dateCome;
    private final String dateFrom;
    private final String dateTo;

    // Геттеры и сеттеры
    public String getFio() {
        return fio;
    }

    public void setFio(String fio) {
        this.fio = fio;
    }

    public String getAge() {
        return age;
    }


    public String getBed() {
        return bed;
    }


    public long getVisitRootId() {
        return visitRootId;
    }


    public String getProf() {
        return prof;
    }

    public String getDateCome(){
        return dateCome;
    }

    public String getDateFrom(){
        return dateFrom;
    }

    public String getDoctor(){
        return doctor;
    }

    // Parcelable implementation
    protected Patient(Parcel in) {
        // Read the values from the Parcel in the same order as you wrote them
        patientId = in.readLong();
        cardNum = in.readInt();
        histNum = in.readInt();
        visitId = in.readLong();
        visitRootId = in.readLong();
        agr = in.readString();
        fio = in.readString();
        age = in.readString();
        bedId = in.readInt();
        bed = in.readString();
        profId = in.readInt();
        prof = in.readString();
        doctor = in.readString();
        dateCome = in.readString();
        dateFrom = in.readString();
        dateTo = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        // Write the values to the Parcel in the same order
        dest.writeLong(patientId);
        dest.writeInt(cardNum);
        dest.writeInt(histNum);
        dest.writeLong(visitId);
        dest.writeLong(visitRootId);
        dest.writeString(agr);
        dest.writeString(fio);
        dest.writeString(age);
        dest.writeInt(bedId);
        dest.writeString(bed);
        dest.writeInt(profId);
        dest.writeString(prof);
        dest.writeString(doctor);
        dest.writeString(dateCome);
        dest.writeString(dateFrom);
        dest.writeString(dateTo);
    }

    @Override
    public int describeContents() {
        return 0;
    }


    public static final Creator<Patient> CREATOR = new Creator<Patient>() {
        @Override
        public Patient createFromParcel(Parcel in) {
            return new Patient(in);
        }

        @Override
        public Patient[] newArray(int size) {
            return new Patient[size];
        }
    };
}



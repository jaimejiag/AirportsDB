package com.jaime.airports.pojo;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

/**
 * Created by jaime on 27/05/2017.
 */

public class Airport implements Parcelable {
    private int id;
    private String code;
    private String country;
    private Date date;
    private String notes;


    public Airport(int id, String code, String country, Date date, String notes) {
        this.id = id;
        this.code = code;
        this.country = country;
        this.date = date;
        this.notes = notes;
    }


    public Airport(String code, String country, Date date, String notes) {
        this.code = code;
        this.country = country;
        this.date = date;
        this.notes = notes;
    }


    protected Airport(Parcel in) {
        id = in.readInt();
        code = in.readString();
        country = in.readString();
        date = new Date(in.readLong());
        notes = in.readString();
    }


    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(code);
        dest.writeString(country);
        dest.writeLong(date.getTime());
        dest.writeString(notes);
    }


    @Override
    public int describeContents() {
        return 0;
    }


    public static final Creator<Airport> CREATOR = new Creator<Airport>() {
        @Override
        public Airport createFromParcel(Parcel in) {
            return new Airport(in);
        }

        @Override
        public Airport[] newArray(int size) {
            return new Airport[size];
        }
    };


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

}

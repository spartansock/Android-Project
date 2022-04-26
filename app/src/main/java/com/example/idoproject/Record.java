package com.example.idoproject;

public class Record {
    private String record_trackName;
    private Time record_time;

    public Record(String record_trackName, Time record_time) {
        this.record_trackName = record_trackName;
        this.record_time = record_time;
    }

    public Record() {

    }

    public Record(String record_trackName) {
        this.record_trackName = record_trackName;
        record_time = new Time();
    }

    public String getRecord_trackName() {
        return record_trackName;
    }

    public void setRecord_trackName(String record_trackName) {
        this.record_trackName = record_trackName;
    }

    public Time getRecord_time() {
        return record_time;
    }

    public void setRecord_time(Time record_time) {
        this.record_time = record_time;
    }
}

package com.example.idoproject;

public class Time
{
    private int minutes;
    private int seconds;
    private int milliseconds;

    public Time() {
        this.minutes = 0;
        this.seconds = 0;
        this.milliseconds = 0;
    }

    public Time(int minutes, int seconds, int milliseconds) {
        this.minutes = minutes;
        this.seconds = seconds;
        this.milliseconds = milliseconds;
    }

    public int getMinutes() {
        return minutes;
    }

    public void setMinutes(int minutes) {
        this.minutes = minutes;
    }

    public int getSeconds() {
        return seconds;
    }

    public void setSeconds(int seconds) {
        this.seconds = seconds;
    }

    public int getMilliseconds() {
        return milliseconds;
    }

    public void setMilliseconds(int milliseconds) {
        this.milliseconds = milliseconds;
    }

    @Override
    public String toString() {
        if (seconds < 10)
            return minutes + ":0" + seconds + "." + milliseconds;
        else
            return minutes + ":" + seconds + "." + milliseconds;
    }
}

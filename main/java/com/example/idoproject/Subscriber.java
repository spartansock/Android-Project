package com.example.idoproject;

import java.util.ArrayList;

public class Subscriber

{
    private String subName;
    private String subPassword;
    private String leagueName;
    private ArrayList<Record> arrYourRecords;
    private Boolean isFirstM = false;
    private Boolean isFirstS = false;
    private Boolean isFirstB = false;

    ArrayList<String> tracks = new ArrayList<String>();

    public Subscriber() {
    }

    public Subscriber(String subName, String subPassword) {
        this.subName = subName;
        this.subPassword = subPassword;
        tracks.add("Monza");
        tracks.add("Spa");
        tracks.add("Barcelona");
        arrYourRecords = new ArrayList<>();
        arrYourRecords.add(new Record("Monza"));
        arrYourRecords.add(new Record("Spa"));
        arrYourRecords.add(new Record("Barcelona"));

    }

    public Boolean getFirst(int track) {
        if (track == 0)
            return isFirstM;

        if (track == 1)
            return isFirstS;

        if (track == 2)
            return isFirstB;

        return false;
    }


    public void setFirst(boolean isFirst, int track)
    {
        if (track == 0)
            isFirstM = isFirst;

        if (track == 1)
            isFirstS = isFirst;

        if (track == 2)
            isFirstB = isFirst;

        else
            return;

    }



    public int compareRecords(Subscriber sub2, int trackIndex)
    {
        Time time1 = this.getArrYourRecords().get(trackIndex).getRecord_time();
        Time time2 = sub2.getArrYourRecords().get(trackIndex).getRecord_time();

        if (time1.getMinutes() > time2.getMinutes())
            return -1;

        if (time1.getMinutes() < time2.getMinutes())
            return 1;

        else
        {
            if (time1.getSeconds() > time2.getSeconds())
                return -1;

            if (time1.getMinutes() < time2.getMinutes())
                return 1;

            else
            {
                if (time1.getMilliseconds() > time2.getMilliseconds())
                    return  -1;

                if (time1.getMilliseconds() < time2.getMilliseconds())
                    return 1;
            }
        }

        return -1;
    }

    public ArrayList<Record> getArrYourRecords() {
        return arrYourRecords;
    }

    public void setArrYourRecords(ArrayList<Record> arrYourRecords) {
        this.arrYourRecords = arrYourRecords;
    }

    public void addRecord(Record record)
    {
        arrYourRecords.add(record);
    }

    public String getLeagueName() {
        return leagueName;
    }

    public void setLeagueName(String leagueName) {
        this.leagueName = leagueName;
    }

    public String getSubName() {
        return subName;
    }

    public String getSubPassword() {
        return subPassword;
    }

    public void setSubName(String subName) {
        this.subName = subName;
    }

    public void setSubPassword(String subPassword) {
        this.subPassword = subPassword;
    }
}


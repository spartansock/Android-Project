package com.example.idoproject;

import java.util.ArrayList;

public class Subscriber

{
    private String subName;
    private String subPassword;
    private String leagueName;
    private ArrayList<Record> arrYourRecords;
    private Boolean isFirst = false;


    Record empty = new Record();
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


    public Boolean getIsFirst() {
        return isFirst;
    }

    public void setIsFirst(Boolean isFirst) {
        this.isFirst = isFirst;
    }

    public int compareRecords(Subscriber sub2, int trackIndex)
    {




        Time time1 = this.getArrYourRecords().get(trackIndex).getRecord_time();
        Time time2 = sub2.getArrYourRecords().get(trackIndex).getRecord_time();

        return time2.toString().compareTo(time1.toString());


    }

    public ArrayList<Subscriber> arrSort(ArrayList<Subscriber> arr, String trackName)
    {


        Subscriber temp = new Subscriber();
        int trackIndex = 0;
        for (int i = 0 ; i < tracks.size() ; i++)
        {
            if (tracks.get(i).equals(trackName))
                trackIndex = i;
        }

        for (int i = 0; i < arr.size() - 1; i++)
        {
            for (int j = 0; j < arr.size() - i - 1; j++)
            {
                if (arr.get(j).compareRecords(arr.get(j+1), trackIndex) == 1)
                {
                    // swap arr[j+1] and arr[j]
                    temp = arr.get(j);
                    arr.set(j, arr.get(j + 1));
                    arr.set(j + 1, temp);
                }
            }

        }

        return arr;
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


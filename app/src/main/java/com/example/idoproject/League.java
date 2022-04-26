package com.example.idoproject;

import java.util.ArrayList;

public class League {

    public int leagueNumber;
    public String leagueName;
    public ArrayList<String> arrSub;

    public League() {
        arrSub = new ArrayList<String>();
    }

    public League(int leagueNumber, String leagueName, String subName) {
        arrSub = new ArrayList<String>();

        this.leagueNumber = leagueNumber;
        this.leagueName = leagueName;
        this.arrSub.add(subName);
    }


}

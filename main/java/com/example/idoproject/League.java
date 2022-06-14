package com.example.idoproject;

import java.util.ArrayList;

public class League {

    public String leagueName;
    public ArrayList<Subscriber> arrSub;

    public League() {
        arrSub = new ArrayList<Subscriber>();
    }

    public League(String leagueName, Subscriber subName) {
        arrSub = new ArrayList<Subscriber>();
        this.leagueName = leagueName;
        this.arrSub.add(subName);
    }


}

package com.example.idoproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Comparator;


public class LeagueMembers extends MenuActivity {
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference();

    LeagueMembersAdapter myListAdapter;
    ListView listView_to_show_to_user;
    TextView leagueName_tv;

    String userleagueName;
    String trackName;
    ArrayList<Subscriber> arrSub1  =new ArrayList<Subscriber>();
    boolean toDisplay2 = true;

    public static ArrayList<Subscriber> arrSort(ArrayList<Subscriber> arr, String trackName)
    {
        ArrayList<String> tracks = new ArrayList<String>();
        Subscriber temp;
        int trackIndex = 0;
        tracks.add("Monza");
        tracks.add("Spa");
        tracks.add("Barcelona");
        if (tracks.get(0).compareTo(trackName) == 0) // getting the track index
        {
            trackIndex = 0;
        }

        else if (tracks.get(1).compareTo(trackName) == 0)
        {
            trackIndex = 1;
        }

        else if (tracks.get(2).compareTo(trackName) == 0)
        {
            trackIndex = 2;
        }

        for (int i = 0; i < arr.size() - 1; i++) // bubble sort
        {
            for (int j = 0; j < arr.size() - i - 1; j++)
            {
                if (arr.get(j).compareRecords(arr.get(j+1), trackIndex) < 0)
                {

                    temp = arr.get(j);
                    arr.set(j, arr.get(j + 1));
                    arr.set(j + 1, temp);
                }
            }
        }
        return arr;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_league_members);
        trackName  =(String) getIntent().getExtras().get("trackName");
        leagueName_tv = findViewById(R.id.leagueName_tv);
        listView_to_show_to_user = (ListView) findViewById(R.id.leagueMembers_lv);

        TextView track = findViewById(R.id.trackName_tv);
        track.setText(trackName);

        userleagueName = MainActivity.nowConnected.getLeagueName();
        leagueName_tv.setText(userleagueName);

        Query q = myRef.child("Leagues").orderByValue();
        q.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (toDisplay2) {
                    for (DataSnapshot dst : snapshot.getChildren()) {
                        if (dst.getKey().compareTo(userleagueName) == 0) {
                            arrSub1 = dst.getValue(League.class).arrSub;
                            toDisplay2 = false;
                        }
                    }
                }

                arrSub1 = arrSort(arrSub1, trackName);
                myListAdapter = new LeagueMembersAdapter(LeagueMembers.this, R.layout.league_members_one_line, arrSub1, trackName);
                listView_to_show_to_user.setAdapter(myListAdapter);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}
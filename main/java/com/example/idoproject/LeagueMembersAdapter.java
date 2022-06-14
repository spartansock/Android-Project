package com.example.idoproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class LeagueMembersAdapter extends ArrayAdapter<Subscriber>
{
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference();
    Query q;

    Context context;
    ArrayList<Subscriber> object;
    String trackName;
    ArrayList<String> tracks = new ArrayList<String>();
    int trackIndex = 0;

    public LeagueMembersAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Subscriber> objects,
                                String trackName) {
        super(context, resource, objects);
        this.context = context;
        this.object = objects;
        this.trackName = trackName;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View view, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(getContext());
        view = layoutInflater.inflate(R.layout.league_members_one_line, parent, false);
        TextView position_tv = (TextView) view.findViewById(R.id.position_tv);
        TextView member_tv = (TextView) view.findViewById(R.id.member_tv);
        TextView record1_tv = (TextView) view.findViewById(R.id.record1_tv);

        tracks.clear();
        tracks.add("Monza");
        tracks.add("Spa");
        tracks.add("Barcelona");

        for (int i = 0 ; i < tracks.size() ; i++) // getting the track index
        {
            if (tracks.get(i).compareTo(trackName) == 0)
                trackIndex = i;
        }

        // presenting the subscriber on the screen
        position_tv.setText("" +(position + 1));
        member_tv.setText(this.getItem(position).getSubName());
        record1_tv.setText(this.getItem(position).getArrYourRecords().get(trackIndex).getRecord_time().toString());
        return view;
    }
}

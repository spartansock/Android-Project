package com.example.idoproject;

import android.app.Activity;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PersonalRecordsAdapter extends ArrayAdapter<Record>
{
        Context context;
        ArrayList<Record> object;
        private HashMap<String, String> textValues = new HashMap<String, String>();
        EditText et_trackRecord;
        String[] trackNames = {"Monza", "Spa", "Barcelona"};

        public PersonalRecordsAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Record> objects) {
        super(context, resource, objects);
        this.context = context;
        this.object = objects;
        }

        @NonNull
        @Override
        public View getView(final int position, @Nullable View view, @NonNull ViewGroup parent) {
                LayoutInflater layoutInflater = LayoutInflater.from(getContext());
                view = layoutInflater.inflate(R.layout.track_register_one_line, parent, false);
                TextView track_tv = (TextView) view.findViewById(R.id.track_tv);
                TextView record_tv = (TextView) view.findViewById(R.id.record_tv);
                track_tv.setText(track_tv.getText().toString()+trackNames[position]);
                record_tv.setText(this.getItem(position).getRecord_time().toString());
                return view;
        }







}
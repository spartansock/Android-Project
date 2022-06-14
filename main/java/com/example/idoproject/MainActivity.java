package com.example.idoproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Random;

public class MainActivity extends MenuActivity implements View.OnClickListener {
    LowBatteryReceiver lowBatteryReceiver = new LowBatteryReceiver();
    NoInternetReceiver noInternetReceiver = new NoInternetReceiver();

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference();

    Button signup_btn;
    Button login_btn;

    EditText userName_et;
    EditText password_et;

    static String userName;
    Intent notificationService;

    public static Subscriber nowConnected = new Subscriber();

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(noInternetReceiver);
        unregisterReceiver(lowBatteryReceiver);
    }

    @Override
    protected void onStart() {
        super.onStart();

        // initiating the receivers
        IntentFilter filter1 = new IntentFilter(Intent.ACTION_BATTERY_LOW);
        IntentFilter filter2 = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(noInternetReceiver, filter2);
        registerReceiver(lowBatteryReceiver, filter1);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        notificationService = new Intent(MainActivity.this, PassingNotificationService.class);
        startService(notificationService);

        signup_btn = (Button)findViewById(R.id.signup_btn);
        login_btn = (Button)findViewById(R.id.login_btn);
        userName_et = (EditText) findViewById(R.id.userName_et);
        password_et = (EditText) findViewById(R.id.password_et);

        login_btn.setOnClickListener(this);
        signup_btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(userName_et.getText().toString().trim().length()==0 || password_et.getText().toString().trim().length()==0)
            Toast.makeText(MainActivity.this, "Please enter all your details",Toast.LENGTH_LONG).show();

        else if(view == signup_btn) {
            Query q = myRef.child("App Users").orderByValue();
            q.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    boolean userTaken = false;
                    for (DataSnapshot dst : snapshot.getChildren()) {
                        Subscriber sub = dst.getValue(Subscriber.class);
                        if (sub.getSubName().equals(userName_et.getText().toString())){
                            Toast.makeText(MainActivity.this, "Username already taken, choose a different one",Toast.LENGTH_LONG).show();
                            userTaken = true;
                        }
                    }

                    if (!userTaken)
                    {
                        nowConnected = new Subscriber(userName_et.getText().toString(),
                                                            password_et.getText().toString());
                        myRef.child("App Users").child(userName_et.getText().toString()).setValue(nowConnected);
                        Toast.makeText(MainActivity.this, "Registered successfully",Toast.LENGTH_LONG).show();
                        userName = nowConnected.getSubName();
                        Intent i1 = new Intent(MainActivity.this, RegisterRecords.class);
                        startActivity(i1);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }


        else if(view == login_btn){
            Query q = myRef.child("App Users").orderByValue();
            q.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    boolean userFound = false;
                    for (DataSnapshot dst : snapshot.getChildren()) {
                        Subscriber sub = dst.getValue(Subscriber.class);
                        if (sub.getSubName().equals(userName_et.getText().toString()) && sub.getSubPassword().equals(password_et.getText().toString())) {
                            Toast.makeText(MainActivity.this, "registered successfully", Toast.LENGTH_LONG).show();
                            userFound = true;
                            nowConnected = sub;
                        }
                    }

                    if (userFound) {
                        userName = nowConnected.getSubName();
                        Intent i1 = new Intent(MainActivity.this, RegisterRecords.class);
                        startActivity(i1);
                    }
                    else
                        Toast.makeText(MainActivity.this, "username or password are incorrect", Toast.LENGTH_LONG).show();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
    }
}
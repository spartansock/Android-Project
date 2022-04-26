package com.example.idoproject;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class PassingNotificationService extends Service {

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference();
    Query q;
    NotificationCompat.Builder builder;
    NotificationManager manager;

    ArrayList<String> arrNames = new ArrayList<String>();
    ArrayList<Subscriber> arrSub1 = new ArrayList<Subscriber>();

    ArrayList<String> tracks = new ArrayList<String>();

    boolean toDisplay1 = true;


    public PassingNotificationService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        builder = new NotificationCompat.Builder(this);
        builder.setSmallIcon(R.drawable.spa_image2);
        builder.setContentTitle("Title");
        builder.setContentText("text of notification");
        builder.setDefaults(NotificationCompat.DEFAULT_ALL);
        builder.setPriority(NotificationCompat.PRIORITY_MAX);

        Intent notification_intent = new Intent(this,MainActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(this,0,notification_intent,PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(contentIntent);
        manager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);

        tracks.add("Monza");
        tracks.add("Spa");
        tracks.add("Barcelona");

        q = myRef.orderByKey();
        q.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot dst : snapshot.child("Leagues").getChildren())
                {
                    arrNames = dst.getValue(League.class).arrSub;
                    arrSub1 = new ArrayList<Subscriber>();
                    if (arrNames.contains(MainActivity.userName))
                    {
                        // sort
                        toDisplay1 = true;
                        Query q1 = myRef.child("App Users").orderByValue();
                        q1.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                if (toDisplay1) {
                                    for (DataSnapshot dst1 : snapshot.getChildren()) {

                                        if (arrNames.contains(dst1.getKey())) {
                                            arrSub1.add(dst1.getValue(Subscriber.class));
                                            toDisplay1 = false;
                                        }


                                    }
                                    Subscriber curr = null;
                                    for (int i = 0 ; i < arrSub1.size() ; i++)
                                    {
                                        if (arrSub1.get(i).getSubName().equals(MainActivity.userName))
                                            curr = arrSub1.get(i);
                                    }


                                    if (curr != null && curr.getLeagueName() != null)
                                    {
                                        for (int i = 0 ; i < tracks.size() ; i++)
                                        {
                                            arrSub1 = LeagueMembers.arrSort(arrSub1, tracks.get(i));
                                            boolean isFirstNow = arrSub1.get(0).getSubName().equals(MainActivity.userName);


                                            if (curr != null && !isFirstNow && curr.getIsFirst())
                                            {
                                                curr.setIsFirst(false);
                                                myRef.child("App Users").child(MainActivity.userName).setValue(curr);
                                                builder.setContentTitle("F1 Leaderboard");
                                                builder.setContentText("Someone just knocked you off 1st place!");
                                                manager.notify(0,builder.build());
                                            }

                                            else if (curr != null && isFirstNow && !curr.getIsFirst())
                                            {
                                                curr.setIsFirst(true);
                                                myRef.child("App Users").child(MainActivity.userName).setValue(curr);
                                            }


                                        }
                                    }












                                }


                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });



                    }


                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return Service.START_NOT_STICKY;

    }
    @Override
    public void onDestroy() {
    }
    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }
    public void onStop(){
    }


    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
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

    League nowLeague;
    boolean leagueFound;
    boolean isFirstNow;

    ArrayList<Subscriber> arrSub = new ArrayList<Subscriber>();
    ArrayList<String> tracks = new ArrayList<String>();

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

          q = myRef.child("Leagues").orderByKey();
          q.addValueEventListener(new ValueEventListener() {
              @Override
              public void onDataChange(@NonNull DataSnapshot snapshot) {
                  nowLeague = new League();
                  leagueFound = false;
                  for (DataSnapshot dst : snapshot.getChildren())
                  {
                      if (dst.getKey().equals(MainActivity.nowConnected.getLeagueName()) && MainActivity.nowConnected.getLeagueName() != null)
                      {
                          nowLeague = dst.getValue(League.class);
                          leagueFound = true;
                      }
                  }

                  if (leagueFound)
                  {
                      arrSub = nowLeague.arrSub;

                      for (int i = 0 ; i < tracks.size() ; i++) // checking for each track
                      {
                          arrSub = LeagueMembers.arrSort(arrSub, tracks.get(i));
                          if (arrSub.isEmpty())
                              isFirstNow = true;

                          else
                            isFirstNow = arrSub.get(0).getSubName().equals(MainActivity.nowConnected.getSubName());

                          if (!isFirstNow && MainActivity.nowConnected.getFirst(i)) // if the connected subscriber is not first but
                                                                                    // he use to be first
                          {
                              MainActivity.nowConnected.setFirst(false, i);
                              myRef.child("App Users").child(MainActivity.nowConnected.getSubName()).setValue(MainActivity.nowConnected);
                              builder.setContentTitle("F1 Leaderboard");
                              builder.setContentText("Someone just knocked you off 1st place!");
                              manager.notify(0,builder.build());
                          }

                          if (isFirstNow && !MainActivity.nowConnected.getFirst(i)) // if the connected subscriber is now first but wasn't
                          {
                              MainActivity.nowConnected.setFirst(true, i);
                              myRef.child("App Users").child(MainActivity.userName).setValue(MainActivity.nowConnected);
                          }
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
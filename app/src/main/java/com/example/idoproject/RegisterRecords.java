package com.example.idoproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.animation.ObjectAnimator;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.view.animation.AccelerateInterpolator;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class RegisterRecords extends MenuActivity {
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference();
    boolean toDisplay1 = true;
    boolean toDisplay2 = true;
    boolean toDisplay3 = true;
    boolean toDisplay4 = true;
    String currentSubName;
    private ArrayList<Record> stam_text_data_array = new ArrayList<>();
    PersonalRecordsAdapter myListAdapter;
    ListView listView_to_show_to_user;
    Button btn_my_personal_records;
    Button createLeague_btn;
    Button joinLeague_btn;
    Button monza_btn;
    Button spa_btn;
    Button barcelona_btn;
    TextView subName_tv;
    String[] trackNames = {"Monza", "Spa", "Barcelona"};
    Record record1 = new Record(trackNames[0], new Time());
    Record record2 = new Record(trackNames[1], new Time());
    Record record3 = new Record(trackNames[2], new Time());
    Subscriber currSub = new Subscriber();
    boolean flag = false;
    League newLeague = new League();
    League temp1 = new League();
    String userLeague;
    ImageView raceCar_iv;
    ImageView raceCar2_iv;

    ObjectAnimator animator;
    ObjectAnimator animator2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_records);
        currentSubName = (String) getIntent().getExtras().get("currentSub");
        subName_tv = findViewById(R.id.subName_tv);
        subName_tv.setText(currentSubName);
        listView_to_show_to_user = (ListView) findViewById(R.id.records_lv);
        btn_my_personal_records = (Button) findViewById(R.id.editRecords_btn);
        createLeague_btn = findViewById(R.id.createLeague_btn);
        joinLeague_btn = findViewById(R.id.joinLeague_btn);
        monza_btn = findViewById(R.id.monza_btn);
        spa_btn = findViewById(R.id.spa_btn);
        barcelona_btn = findViewById(R.id.barcelona_btn);
        raceCar_iv = findViewById(R.id.raceCar_iv);
        raceCar_iv.setImageResource(R.drawable.racecar);
        raceCar_iv.setVisibility(View.INVISIBLE);

        raceCar2_iv = findViewById(R.id.raceCar2_iv);
        raceCar2_iv.setImageResource(R.drawable.racecar2);
        raceCar2_iv.setVisibility(View.INVISIBLE);


        animator = ObjectAnimator.ofFloat(raceCar_iv, "translationX", 1000f);
        animator.setInterpolator(new AccelerateInterpolator());
        animator.setDuration(2000);

        animator2 = ObjectAnimator.ofFloat(raceCar2_iv, "translationX", 1000f);
        animator2.setInterpolator(new AccelerateInterpolator());
        animator2.setDuration(4000);


        Query q = myRef.child("App Users").orderByValue();
        q.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Subscriber temp = new Subscriber();

                if (toDisplay1) {
                    for (DataSnapshot dst : snapshot.getChildren()) {
                        if (dst.getKey().compareTo(currentSubName) == 0) {
                            toDisplay1 = false;
                            temp = dst.getValue(Subscriber.class);
                            if (temp.getArrYourRecords() == null)
                                temp.setArrYourRecords(new ArrayList<Record>());
                            if (temp.getArrYourRecords().isEmpty())
                                flag = true;
                            else
                            {

                                stam_text_data_array = temp.getArrYourRecords();
                            }

                        }

                    }

                    if (flag) {
                        Record record4 = new Record(trackNames[0], new Time());
                        Record record5 = new Record(trackNames[1], new Time());
                        Record record6 = new Record(trackNames[2], new Time());
                        stam_text_data_array.add(record4);
                        stam_text_data_array.add(record5);
                        stam_text_data_array.add(record6);

                        myListAdapter = new PersonalRecordsAdapter(RegisterRecords.this, R.layout.track_register_one_line, stam_text_data_array);
                        listView_to_show_to_user.setAdapter(myListAdapter);
                    }
                    else {
                        myListAdapter = new PersonalRecordsAdapter(RegisterRecords.this, R.layout.track_register_one_line, stam_text_data_array);
                        listView_to_show_to_user.setAdapter(myListAdapter);
                    }

                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        btn_my_personal_records.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {
                   AlertDialog.Builder builder = new AlertDialog.Builder(RegisterRecords.this);
//you should edit this to fit your needs
                   builder.setTitle("Edit your Records");
                   TextView tv1 = new TextView(RegisterRecords.this);
                   tv1.setText(trackNames[0]);
                   TextView tv2 = new TextView(RegisterRecords.this);
                   tv2.setText(trackNames[1]);
                   TextView tv3 = new TextView(RegisterRecords.this);
                   tv3.setText(trackNames[2]);

                   final EditText etM1 = new EditText(RegisterRecords.this);
                   etM1.setHint("Minutes");
                   final EditText etM2 = new EditText(RegisterRecords.this);
                   etM2.setHint("Seconds");
                   final EditText etM3 = new EditText(RegisterRecords.this);
                   etM3.setHint("Milliseconds");

                   final EditText etS1 = new EditText(RegisterRecords.this);
                   etS1.setHint("Minutes");
                   final EditText etS2 = new EditText(RegisterRecords.this);
                   etS2.setHint("Seconds");
                   final EditText etS3 = new EditText(RegisterRecords.this);
                   etS3.setHint("Milliseconds");

                   final EditText etB1 = new EditText(RegisterRecords.this);
                   etB1.setHint("Minutes");
                   final EditText etB2 = new EditText(RegisterRecords.this);
                   etB2.setHint("Seconds");
                   final EditText etB3 = new EditText(RegisterRecords.this);
                   etB3.setHint("Milliseconds");

                   etM1.setInputType(InputType.TYPE_CLASS_NUMBER);
                   etM2.setInputType(InputType.TYPE_CLASS_NUMBER);
                   etM3.setInputType(InputType.TYPE_CLASS_NUMBER);

                   etS1.setInputType(InputType.TYPE_CLASS_NUMBER);
                   etS2.setInputType(InputType.TYPE_CLASS_NUMBER);
                   etS3.setInputType(InputType.TYPE_CLASS_NUMBER);

                   etB1.setInputType(InputType.TYPE_CLASS_NUMBER);
                   etB2.setInputType(InputType.TYPE_CLASS_NUMBER);
                   etB3.setInputType(InputType.TYPE_CLASS_NUMBER);

                   LinearLayout layMain = new LinearLayout(RegisterRecords.this);
                   LinearLayout lay1 = new LinearLayout(RegisterRecords.this);
                   LinearLayout lay2 = new LinearLayout(RegisterRecords.this);
                   LinearLayout lay3 = new LinearLayout(RegisterRecords.this);

                   lay1.setOrientation(LinearLayout.HORIZONTAL);
                   lay1.addView(tv1);
                   lay1.addView(etM1);
                   lay1.addView(etM2);
                   lay1.addView(etM3);

                   lay2.setOrientation(LinearLayout.HORIZONTAL);
                   lay2.addView(tv2);
                   lay2.addView(etS1);
                   lay2.addView(etS2);
                   lay2.addView(etS3);

                   lay3.setOrientation(LinearLayout.HORIZONTAL);
                   lay3.addView(tv3);
                   lay3.addView(etB1);
                   lay3.addView(etB2);
                   lay3.addView(etB3);


                   layMain.setOrientation(LinearLayout.VERTICAL);
                   layMain.addView(lay1);
                   layMain.addView(lay2);
                   layMain.addView(lay3);



                   builder.setView(layMain);

// Set up the buttons
                   builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                       public void onClick(DialogInterface dialog, int whichButton) {
                           stam_text_data_array.clear();

                           int input1, input2, input3;
                           int input4, input5, input6;
                           int input7, input8, input9;

                           if (etM1.getText().toString().trim().length() != 0 &&
                               etM2.getText().toString().trim().length() != 0 &&
                               etM3.getText().toString().trim().length() != 0)
                           {
                               input1 = Integer.parseInt(etM1.getText().toString());
                               input2 = Integer.parseInt(etM2.getText().toString());
                               input3 = Integer.parseInt(etM3.getText().toString());
                               record1.setRecord_time(new Time(input1, input2, input3));
                               stam_text_data_array.add(0, record1);
                           }

                           if (etS1.getText().toString().trim().length() != 0 &&
                               etS2.getText().toString().trim().length() != 0 &&
                               etS3.getText().toString().trim().length() != 0)
                           {
                               input4 = Integer.parseInt(etS1.getText().toString());
                               input5 = Integer.parseInt(etS2.getText().toString());
                               input6 = Integer.parseInt(etS3.getText().toString());
                               record2.setRecord_time(new Time(input4, input5, input6));
                               stam_text_data_array.add(1, record2);
                           }

                           if (etB1.getText().toString().trim().length() != 0 &&
                               etB2.getText().toString().trim().length() != 0 &&
                               etB3.getText().toString().trim().length() != 0)
                           {
                               input7 = Integer.parseInt(etB1.getText().toString());
                               input8 = Integer.parseInt(etB2.getText().toString());
                               input9 = Integer.parseInt(etB3.getText().toString());
                               record3.setRecord_time(new Time(input7, input8, input9));
                               stam_text_data_array.add(2, record3);
                           }

//                           myRef.child("App Users").child(MainActivity.userName).

                           toDisplay2 = true;
                           Query q = myRef.child("App Users").orderByValue();
                           q.addListenerForSingleValueEvent(new ValueEventListener() {
                               @Override
                               public void onDataChange(@NonNull DataSnapshot snapshot) {
                                   Subscriber tempSub;

                                   if (toDisplay2) {
                                       for (DataSnapshot dst : snapshot.getChildren()) {
                                           tempSub = dst.getValue(Subscriber.class);
                                           if (tempSub.getSubName().equals(currentSubName)) {
                                               toDisplay2 = false;
                                               if (tempSub.getArrYourRecords() == null)
                                                   tempSub.setArrYourRecords(new ArrayList<Record>());

                                               tempSub.setArrYourRecords(stam_text_data_array);
                                               myRef.child("App Users").child(currentSubName).setValue(tempSub);
                                           }

                                       }

                                   }


                               }

                               @Override
                               public void onCancelled(@NonNull DatabaseError error) {

                               }
                           });
                           myListAdapter = new PersonalRecordsAdapter(RegisterRecords.this, R.layout.track_register_one_line,

                                   stam_text_data_array);

                           listView_to_show_to_user.setAdapter(myListAdapter);
                       }
                   });
                   builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                       public void onClick(DialogInterface dialog, int whichButton) {
                           dialog.cancel();
                       }
                   });
                   builder.show();

               }
           });

        createLeague_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                AlertDialog.Builder builder = new AlertDialog.Builder(RegisterRecords.this);

                builder.setTitle("Create A New League");
                final EditText et1 = new EditText(RegisterRecords.this);
                et1.setHint("League Name");//optional

                et1.setInputType(InputType.TYPE_CLASS_TEXT);

                LinearLayout lay = new LinearLayout(RegisterRecords.this);
                lay.setOrientation(LinearLayout.VERTICAL);
                lay.addView(et1);

                builder.setView(lay);

// Set up the buttons
                AlertDialog.Builder builder1 = builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {

                        Query q = myRef.child("Leagues").orderByKey();
                        q.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                int desiredLeague = (int) snapshot.getChildrenCount();
                                Toast.makeText(RegisterRecords.this, "League " + (desiredLeague) + " was created", Toast.LENGTH_LONG).show();

                                newLeague.leagueNumber = desiredLeague;
                                newLeague.leagueName = et1.getText().toString();
                                userLeague = et1.getText().toString();
                                newLeague.arrSub.add(currentSubName);
                                myRef.child("Leagues").child("League " + (newLeague.leagueNumber)).setValue(newLeague);
                                Query q1 = myRef.child("App Users").orderByValue();
                                q1.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        if (toDisplay4) {
                                            for (DataSnapshot dst : snapshot.getChildren()) {
                                                if (dst.getKey().equals(currentSubName)) {
                                                    Subscriber temptemp = dst.getValue(Subscriber.class);
                                                    temptemp.setLeagueName(userLeague);
                                                    toDisplay4 = false;
                                                    myRef.child("App Users").child(currentSubName).setValue(temptemp);

                                                }


                                            }


                                        }


                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {

                                    }
                                });

                            }


                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                        raceCar_iv.setVisibility(View.VISIBLE);
                        raceCar2_iv.setVisibility(View.VISIBLE);
                        animator.start();
                        animator2.start();
                        new CountDownTimer(5000, 1000) {

                            public void onTick(long millisUntilFinished) {
                                //here you can have your logic to set text to edittext
                            }

                            public void onFinish() {
                                raceCar_iv.setVisibility(View.INVISIBLE);
                                raceCar2_iv.setVisibility(View.INVISIBLE);
                            }

                        }.start();
                    }





                });
                builder.show();




            }

        });

        joinLeague_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(RegisterRecords.this);
//you should edit this to fit your needs
                builder.setTitle("Join A League");
                final EditText et1 = new EditText(RegisterRecords.this);
                et1.setHint("League Name");//optional

                et1.setInputType(InputType.TYPE_CLASS_TEXT);

                LinearLayout lay = new LinearLayout(RegisterRecords.this);
                lay.setOrientation(LinearLayout.VERTICAL);
                lay.addView(et1);

                builder.setView(lay);

// Set up the buttons
                builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {


                        Query q = myRef.child("Leagues").orderByKey();
                        q.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                if (toDisplay3)
                                {

                                    for (DataSnapshot dst : snapshot.getChildren()) {
                                        if (dst.getValue(League.class).leagueName.compareTo(et1.getText().toString()) == 0) {
                                            toDisplay3 = false;
                                            temp1 = dst.getValue(League.class);
                                            if (temp1.arrSub == null)
                                                temp1.arrSub = new ArrayList<String>();
                                            else
                                            {
                                                toDisplay4 = true;
                                                temp1.arrSub.add(currentSubName);
                                                userLeague = et1.getText().toString();
                                                myRef.child("Leagues").child(dst.getKey()).setValue(temp1);

                                                Query q1 = myRef.child("App Users").orderByValue();
                                                q1.addValueEventListener(new ValueEventListener() {
                                                    @Override
                                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                        if (toDisplay4) {
                                                            for (DataSnapshot dst1 : snapshot.getChildren()) {
                                                                if (dst1.getKey().equals(currentSubName))
                                                                {
                                                                    Subscriber temptemp = dst1.getValue(Subscriber.class);
                                                                    temptemp.setLeagueName(userLeague);
                                                                    toDisplay4 = false;
                                                                    myRef.child("App Users").child(currentSubName).setValue(temptemp);
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
                                }

//                                Intent i1 = new Intent(RegisterRecords.this, LeagueMembers.class);
//                                Bundle b = new Bundle();
//                                b.putSerializable("leagueName", temp1.leagueName);
//                                b.putSerializable("currentSub", currentSubName);
//                                i1.putExtras(b);
//                                startActivity(i1);
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                    }
                });
                builder.show();

            }
        });

        monza_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i1 = new Intent(RegisterRecords.this, LeagueMembers.class);
                Bundle b = new Bundle();

                b.putSerializable("currentSub", currentSubName);
                b.putSerializable("trackName", monza_btn.getText().toString());
                i1.putExtras(b);
                startActivity(i1);
            }
        });

        spa_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i1 = new Intent(RegisterRecords.this, LeagueMembers.class);
                Bundle b = new Bundle();

                b.putSerializable("currentSub", currentSubName);
                b.putSerializable("trackName", spa_btn.getText().toString());
                i1.putExtras(b);
                startActivity(i1);
            }
        });

        barcelona_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i1 = new Intent(RegisterRecords.this, LeagueMembers.class);
                Bundle b = new Bundle();

                b.putSerializable("currentSub", currentSubName);
                b.putSerializable("trackName", barcelona_btn.getText().toString());
                i1.putExtras(b);
                startActivity(i1);
            }
        });


    }

}
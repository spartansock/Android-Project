package com.example.idoproject;

import static com.example.idoproject.R.id.spa_iv;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class MenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        ActionBar myActionBar = getSupportActionBar();
        myActionBar.setDisplayShowHomeEnabled(true);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @SuppressLint("ResourceType")
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.item_about)
        {
            Intent i1 = new Intent(getApplicationContext(), AppActivity.class);
            startActivity(i1);
        }

        if (item.getItemId() == R.id.item_developer)
        {
            Intent i2 = new Intent(getApplicationContext(), DeveloperActivity.class);
            startActivity(i2);
        }

        if (item.getItemId() == R.id.item_spa)
        {
            ImageView image = new ImageView(this);

            image.setImageResource(R.drawable.spa_image2);
            AlertDialog.Builder builder = new AlertDialog.Builder(MenuActivity.this);

            builder.setPositiveButton("Close", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {

                }
            });
            builder.setTitle("About Circuit de Spa-Francorchamps");
            builder.setMessage("The Circuit de Spa-Francorchamps, " +
                    "frequently referred to as 'Spa', is a 7.004 km (4.352 mi) motor-racing circuit located in Stavelot, " +
                    "Belgium. It is the current venue of the Formula One Belgian Grand Prix, hosting its first Grand Prix in 1925, " +
                    "and has held a Grand Prix every year since 1985 (except 2003 and 2006).");
            builder.setView(image);
            builder.show();
        }

        if (item.getItemId() == R.id.item_monza)
        {
            ImageView image = new ImageView(this);
            image.setImageResource(R.drawable.monza);
            AlertDialog.Builder builder = new AlertDialog.Builder(MenuActivity.this);

            builder.setPositiveButton("Close", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {

                }
            });
            builder.setTitle("About Monza Circuit");
            builder.setMessage("The Monza Circuit (Italian: Autodromo Nazionale di Monza," +
                    " 'National Motor Racetrack of Monza') is a historic 5.793 km (3.600 mi) race track near the city of Monza, " +
                    "north of Milan, in Italy. Built in 1922, it was the world's third purpose-built motor racing circuit after Brooklands and Indianapolis and the oldest in mainland Europe." +
                    " The circuit's biggest event is the Italian Grand Prix. With the exception of the 1980 running, " +
                    "the race has been hosted there since 1949.");
            builder.setView(image);
            builder.show();
        }

        if (item.getItemId() == R.id.item_barcelona)
        {
            ImageView image = new ImageView(this);
            image.setImageResource(R.drawable.barcelona);
            AlertDialog.Builder builder = new AlertDialog.Builder(MenuActivity.this);

            builder.setPositiveButton("Close", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {

                }
            });
            builder.setTitle("About Circuit de Barcelona-Catalunya");
            builder.setMessage("The Circuit de Barcelona-Catalunya is a 4.675 km (2.905 mi) motorsport race track in Montmeló, Catalonia, " +
                    "Spain. With long straights and a variety of corners, " +
                    "the Circuit de Barcelona-Catalunya is seen as an all-rounder circuit. " +
                    "The track has stands with a capacity of 140,700. The circuit has FIA Grade 1 license." +
                    "Until 2013 the track was known only as the Circuit de Catalunya, before a sponsorship deal with Barcelona City Council added Barcelona to the track's title.");
            builder.setView(image);
            builder.show();
        }
        return super.onOptionsItemSelected(item);
    }
}
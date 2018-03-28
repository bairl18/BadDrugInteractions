package com.example.linnea.baddruginteractions2;

import android.content.Intent;
import android.content.res.AssetManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.*;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


import android.util.Log;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    static Boolean programOpens = false;
    private String text = " ";
    private UserProfileHandler profile;
    private DrugInteractionsHandler interactions;
    private UserRemindersHandler userReminders;

    // Button Definitions
    public ImageButton meds;
    public ImageButton search;
    public ImageButton settings;
    public ImageButton reminders;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        openProgramTest();

        // Buttons
        meds = (ImageButton)findViewById(R.id.medsButton);
        search = (ImageButton)findViewById(R.id.searchButton);
        settings = (ImageButton)findViewById(R.id.settingsButton);
        reminders = (ImageButton)findViewById(R.id.remindersButton);

        // Button onClickListeners
        meds.setOnClickListener(this);
        search.setOnClickListener(this);
        settings.setOnClickListener(this);
        reminders.setOnClickListener(this);

        // Create databases
        Log.d("MainActivity", "Opening User medication DB");
        profile = new UserProfileHandler(this);
        interactions = new DrugInteractionsHandler(this);

        userReminders = new UserRemindersHandler(this);
        
        // set to true if you want to reset db next time app opens
        boolean resetProfile = false;
        if (resetProfile) {
            profile.reset();
            interactions.reset(); // reset interactions too
        }

    }

    //onClick method corresponds to onClickListeners: contains instructions
    //for implementing what happens when each button is clicked
    @Override
    public void onClick(View view)
    {
        //String viewStr = view.toString();
        switch(view.getId())
        {
            case R.id.medsButton:
                Intent intent = new Intent(MainActivity.this, MedicationsActivity.class);
                startActivity(intent);
                break;
            case R.id.searchButton:
                intent = new Intent(MainActivity.this, SearchActivity.class);
                startActivity(intent);
                break;
            case R.id.settingsButton:
                intent = new Intent(MainActivity.this, SettingsActivity.class);
                startActivity(intent);
                break;
            case R.id.remindersButton:
                intent = new Intent(MainActivity.this, RemindersActivity.class);
                startActivity(intent);
                break;
            default:
                // Do nothing
        }
    }

    public static Boolean openProgramTest()
    {
        programOpens = true;
        return programOpens;
    }

}

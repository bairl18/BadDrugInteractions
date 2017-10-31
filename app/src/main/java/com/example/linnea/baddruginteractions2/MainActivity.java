package com.example.linnea.baddruginteractions2;

import android.content.Intent;
import android.content.res.AssetManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
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
    private DBHandler db;

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

        //Log.d("DBHandler: ", "Opening DB");
        //db = new DBHandler(this);

        // false if setting up new DB. Change to true if you don't want to wait on startup.
        //boolean fastStart = false;

        ///if (!fastStart) {
            //db.resetDB();
            //parseFdaDatabase();
        //}
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
                //intent = new Intent(MainActivity.this, SettingsActivity.class);
                //startActivity(intent);
                break;
            case R.id.remindersButton:
                //intent = new Intent(MainActivity.this, RemindersActivity.class);
                //startActivity(intent);
                break;
            default:
                //do nothing
        }
    }


        public String getMedInfo(int index)
    {
        String info = "No Medication Found";
        Drug drug = db.getDrug(index);
        info = "Drug Name: " + drug.getDrug_name()
                + "\nActive Ingredient: " + drug.getActive_ingredient()
                + "\nForm: " + drug.getForm()
                + "\nStrength: " + drug.getStrength();
        return info;
    }

    public static Boolean openProgramTest()
    {
        programOpens = true;
        return programOpens;
    }

    public void parseFdaDatabase()
    {

        String csvFile = "Products.txt";
        BufferedReader br = null;
        String line = "";
        String csvSplitBy = "\t";

        try {

            br = new BufferedReader( new InputStreamReader( getAssets().open(csvFile) ) );

            int keyIndex = 1;

            // skip top line
            line = br.readLine();

            while ((line = br.readLine()) != null) {

                // use comma as separator
                String[] data = line.split(csvSplitBy);

                String entry[] = new String[8];
                for (int i = 0; i < entry.length; i++) {
                    entry[i] = "N/A";
                }

                for (int i = 0; i < data.length && i < entry.length; i++) {
                    entry[i] = data[i];
                }

                Log.d("DBHandler: ", "adding " + Integer.toString(keyIndex));

                Drug drug = new Drug(keyIndex, entry[0], entry[1], entry[2], entry[3], entry[4], entry[5], entry[6], entry[7]);

                db.addDrug(drug);

                keyIndex++;



            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


}

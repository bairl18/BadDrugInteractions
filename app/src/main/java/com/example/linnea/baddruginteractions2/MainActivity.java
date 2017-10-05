package com.example.linnea.baddruginteractions2;

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

public class MainActivity extends AppCompatActivity {

    static Boolean programOpens = false;
    String text = " ";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        openProgramTest();

        final TextView medField = (TextView)findViewById(R.id.textView);

        final Button search = (Button)findViewById(R.id.button);

        search.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                text = getMedName();
                medField.setText(text);
            }
        });

        Log.d("DBHandler: ", "Creating...");

        DBHandler db = new DBHandler(this);

    }

    public static String getMedName()
    {
        String name = "Your Medication";
        return name;
    }

    public static Boolean openProgramTest()
    {
        programOpens = true;
        return programOpens;
    }

    public static void parseFdaDatabase()
    {
        SQLiteDatabase drugs;
    }


}

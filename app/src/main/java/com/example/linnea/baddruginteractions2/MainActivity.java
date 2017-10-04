package com.example.linnea.baddruginteractions2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

import android.util.Log;

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

}

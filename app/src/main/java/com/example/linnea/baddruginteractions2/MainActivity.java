package com.example.linnea.baddruginteractions2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

public class MainActivity extends AppCompatActivity {

    static Boolean programOpens = false;
    static String text = " ";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        programOpens = true;

        final TextView medField = (TextView)findViewById(R.id.textView);

        final Button search = (Button)findViewById(R.id.button);

        search.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                text = "Your Medication";
                medField.setText(text);
            }
        });
    }

}

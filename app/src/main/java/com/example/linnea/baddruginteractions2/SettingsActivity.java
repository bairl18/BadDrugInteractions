package com.example.linnea.baddruginteractions2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.view.View;

public class SettingsActivity extends AppCompatActivity implements View.OnClickListener{

    Button reset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        reset = (Button)findViewById(R.id.reset);
        reset.setOnClickListener(this);
    }

    @Override
    public void onClick(View view)
    {
        //String viewStr = view.toString();
        switch(view.getId())
        {
            case R.id.reset:
                // Reset all

            default:
                // Do nothing
        }
    }
}

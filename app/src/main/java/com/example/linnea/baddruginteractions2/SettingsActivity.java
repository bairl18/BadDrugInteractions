package com.example.linnea.baddruginteractions2;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.view.View;
import android.widget.Switch;
import android.widget.TextView;

import static android.R.color.white;

public class SettingsActivity extends AppCompatActivity implements View.OnClickListener{

    Button reset;
    Switch darkSwitch;
    TextView activityTitle, fontSize, darkTheme, clearText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ThemeUtils.onActivityCreateSetTheme(this);
        setContentView(R.layout.activity_settings);

        reset = (Button)findViewById(R.id.reset);
        reset.setOnClickListener(this);

        darkSwitch = (Switch)findViewById(R.id.switch1);
        darkSwitch.setOnClickListener(this);

        activityTitle = (TextView)findViewById(R.id.sets);
        fontSize = (TextView)findViewById(R.id.fontSize);
        darkTheme = (TextView)findViewById(R.id.darkTheme);
        clearText = (TextView)findViewById(R.id.clearText);

        if ((ThemeUtils.getTheme()).equals("dark"))
        {
            activityTitle.setTextColor(getResources().getColor(white));
            fontSize.setTextColor(getResources().getColor(white));
            darkTheme.setTextColor(getResources().getColor(white));
            clearText.setTextColor(getResources().getColor(white));
        }

        if (ThemeUtils.checked == 1)
        {
            darkSwitch.setChecked(true);
        }

    }

    @Override
    public void onClick(View view)
    {
        //String viewStr = view.toString();
        switch(view.getId())
        {
            case R.id.reset:
                // Reset all


            case R.id.switch1:

                if(darkSwitch.isChecked())
                {

                    Intent i = getBaseContext().getPackageManager()
                            .getLaunchIntentForPackage( getBaseContext().getPackageName() );
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(i);
                    ThemeUtils.changeToTheme(this, ThemeUtils.THEME_DARK);
                    break;
                }
                else
                {
                    Intent i = getBaseContext().getPackageManager()
                            .getLaunchIntentForPackage( getBaseContext().getPackageName() );
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(i);
                    ThemeUtils.changeToTheme(this, ThemeUtils.THEME_DEFAULT);
                    break;
                }


            default:
                // Do nothing
        }
    }
}

package com.example.linnea.baddruginteractions2;

import android.app.Activity;
import android.content.Intent;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.view.View;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import static android.R.color.white;

import java.util.List;

public class SettingsActivity extends AppCompatActivity implements View.OnClickListener{

    Button reset;
    Switch darkSwitch;
    TextView activityTitle, fontSize, darkTheme, clearText;

    private UserProfileHandler up = new UserProfileHandler(this);
    private DrugInteractionsHandler di = new DrugInteractionsHandler(this);
    private UserRemindersHandler ur = new UserRemindersHandler(this);

    AlarmManager alarmManager;
    Intent myIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ThemeUtils.onActivityCreateSetTheme(this);
        setContentView(R.layout.activity_settings);

        alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
        myIntent = new Intent(SettingsActivity.this, AlertReceiver.class);

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
                resetWarning(); // warn user

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

    private void resetAll() {

        up.reset(); // reset user medication table
        di.reset(); // reset drug interaction table

        List<Reminder> remList = ur.asList();
        /* cancel notifaction alarms */
        for (Reminder rem : remList) {

            PendingIntent p = PendingIntent.getBroadcast(SettingsActivity.this, rem.getId(), myIntent, 0);
            p.cancel();
            alarmManager.cancel(p);
        }

        ur.reset(); // reset user reminder tables

        String message = "Cleared user medications and reminders";
        Toast.makeText(SettingsActivity.this, message, Toast.LENGTH_LONG).show();
    }

    private void resetWarning() {

        final AlertDialog alertDialog = new AlertDialog.Builder(SettingsActivity.this).create();

        alertDialog.setMessage("Delete all saved medications and reminders?");

        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "YES" ,
                new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        resetAll();
                        dialog.dismiss();
                        finish();
                    }
                });

        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "CANCEL" ,
                new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();

        // Increase text size on first dialog
        TextView dialogTextView1 = (TextView)alertDialog.findViewById(android.R.id.message);
        dialogTextView1.setTextSize(22);
        TextView dialogYesButton1 = (TextView)alertDialog.findViewById(android.R.id.button1);
        dialogYesButton1.setTextSize(20);
        TextView dialogCancelButton1 = (TextView)alertDialog.findViewById(android.R.id.button3);
        dialogCancelButton1.setTextSize(20);
    }
}

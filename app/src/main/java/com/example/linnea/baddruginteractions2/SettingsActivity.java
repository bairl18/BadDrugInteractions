package com.example.linnea.baddruginteractions2;

import android.content.Intent;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import java.util.List;
import static android.R.color.white;

public class SettingsActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener{




    Button reset;
    ImageButton save;
    Switch darkSwitch;
    TextView activityTitle, fontSize, darkTheme, clearText;
    Spinner fontSpinner;
    EditText phoneNum;
    public static String physNumber = "";

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

        save = (ImageButton)findViewById(R.id.save);
        save.setOnClickListener(this);

        phoneNum = (EditText)findViewById(R.id.number);
        if (!physNumber.isEmpty())
        {
            phoneNum.setText(physNumber);
        }

        fontSpinner = (Spinner)findViewById(R.id.spinner);
        fontSpinner.setOnItemSelectedListener(this);
        String[] fonts = {"Small", "Medium", "Large"};

        ArrayAdapter<String> adapter = new ArrayAdapter<String>
                (this, android.R.layout.simple_spinner_item, fonts);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        fontSpinner.setAdapter(adapter);


        activityTitle = (TextView)findViewById(R.id.sets);
        fontSize = (TextView)findViewById(R.id.fontSize);
        darkTheme = (TextView)findViewById(R.id.darkTheme);
        clearText = (TextView)findViewById(R.id.clearText);

        if (ThemeUtils.sTheme == 1 || ThemeUtils.sTheme == 5 || ThemeUtils.sTheme == 6 || ThemeUtils.sTheme == 7 )
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

        switch (ThemeUtils.fontSize)
        {
            case "small": fontSpinner.setSelection(0);
                          break;
            case "med": fontSpinner.setSelection(1);
                        break;
            case "large": fontSpinner.setSelection(2);
                          break;
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

                    if (ThemeUtils.fontSize.equals("med"))
                    {
                        ThemeUtils.changeToTheme(this, ThemeUtils.THEME_DARK);
                    }
                    else if (ThemeUtils.fontSize.equals("small"))
                    {
                        ThemeUtils.changeToTheme(this, ThemeUtils.THEME_DARK_SMALL);
                    }
                    else if (ThemeUtils.fontSize.equals("large"))
                    {
                        ThemeUtils.changeToTheme(this, ThemeUtils.THEME_DARK_LARGE);
                    }


                    break;
                }
                else
                {
                    Intent i = getBaseContext().getPackageManager()
                            .getLaunchIntentForPackage( getBaseContext().getPackageName() );
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(i);

                    if (ThemeUtils.fontSize.equals("med"))
                    {
                        ThemeUtils.changeToTheme(this, ThemeUtils.THEME_DEFAULT);
                    }
                    else if (ThemeUtils.fontSize.equals("small"))
                    {
                        ThemeUtils.changeToTheme(this, ThemeUtils.THEME_SMALL);
                    }
                    else if (ThemeUtils.fontSize.equals("large"))
                    {
                        ThemeUtils.changeToTheme(this, ThemeUtils.THEME_LARGE);
                    }
                    break;
                }

            case R.id.save:

                String num = phoneNum.getText().toString();
                String regex = "\\d{10}|(?:\\d{3}-){2}\\d{4}|\\(\\d{3}\\)\\d{3}-?\\d{4}";

                if (num.matches(regex))
                {
                    physNumber = num;
                    Toast.makeText(SettingsActivity.this, "Phone number saved", Toast.LENGTH_LONG).show();
                }
                else
                {
                    Toast.makeText(SettingsActivity.this, "Enter a valid phone number", Toast.LENGTH_LONG).show();
                }

                break;

            default:
                // Do nothing
                break;
        }
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View v, int position,
                               long id)
    {
        switch (position)
        {
            case 0:
                // small
                if (!(ThemeUtils.fontSize).equals("small"))
                {
                    Intent i = getBaseContext().getPackageManager()
                            .getLaunchIntentForPackage( getBaseContext().getPackageName() );
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(i);

                    if (ThemeUtils.checked == 0)
                    {
                        ThemeUtils.changeToTheme(this, ThemeUtils.THEME_SMALL);
                    }
                    else
                    {
                        ThemeUtils.changeToTheme(this, ThemeUtils.THEME_DARK_SMALL);
                    }
                }

                break;
            case 1:
                // med
                if (!(ThemeUtils.fontSize).equals("med"))
                {
                    Intent i = getBaseContext().getPackageManager()
                            .getLaunchIntentForPackage( getBaseContext().getPackageName() );
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(i);

                    if (ThemeUtils.checked == 0)
                    {
                        ThemeUtils.changeToTheme(this, ThemeUtils.THEME_DEFAULT);
                    }
                    else
                    {
                        ThemeUtils.changeToTheme(this, ThemeUtils.THEME_DARK);
                    }
                }
                break;
            case 2:
                // large
                if (!(ThemeUtils.fontSize).equals("large"))
                {
                    Intent i = getBaseContext().getPackageManager()
                            .getLaunchIntentForPackage( getBaseContext().getPackageName() );
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(i);

                    if (ThemeUtils.checked == 0)
                    {
                        ThemeUtils.changeToTheme(this, ThemeUtils.THEME_LARGE);
                    }
                    else
                    {
                        ThemeUtils.changeToTheme(this, ThemeUtils.THEME_DARK_LARGE);
                    }
                }
                break;
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0) {

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

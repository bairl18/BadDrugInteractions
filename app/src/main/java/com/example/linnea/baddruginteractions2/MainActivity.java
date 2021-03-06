package com.example.linnea.baddruginteractions2;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.*;
import android.util.Log;
import java.util.List;

import static android.Manifest.permission.CALL_PHONE;
import static android.R.attr.textSize;
import static android.content.DialogInterface.BUTTON_NEUTRAL;
import static android.content.DialogInterface.BUTTON_POSITIVE;
import static com.example.linnea.baddruginteractions2.R.color.colorPrimary;
import static com.example.linnea.baddruginteractions2.R.drawable.dark_logo;
import static com.example.linnea.baddruginteractions2.SettingsActivity.physNumber;

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

    public ImageView logo;
    private static final int REQUEST_PHONE_CALL = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ThemeUtils.onActivityCreateSetTheme(this);
        setContentView(R.layout.activity_main);

        openProgramTest();

        logo = (ImageView) findViewById(R.id.logo);
        logo.bringToFront();

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
        logo.setOnClickListener(this);

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

        if (ThemeUtils.sTheme == 1 || ThemeUtils.sTheme == 5 || ThemeUtils.sTheme == 6 || ThemeUtils.sTheme == 7 )
        {
            logo.setBackgroundResource(dark_logo);
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
            case R.id.logo:
                // Create pop-up with all interactions
                final List<Interaction> ints = interactions.asList();
                final String[] intStrings = new String[ints.size()];

                for (int i = 0; i < ints.size(); i++)
                {
                    Interaction currentInt = ints.get(i);
                    String drug1 = currentInt.getDrugName1();
                    String drug2 = currentInt.getDrugName2();
                    String sev = currentInt.getSeverity();

                    String intString = drug1 + " and " + drug2;
                    intStrings[i] = intString;
                }

                boolean empty = true;
                for (int i=0; i< intStrings.length; i++) {
                    if (intStrings[i] != null) {
                        empty = false;
                        break;
                    }
                }

                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                TextView title = new TextView(getApplicationContext());

                if(empty)
                {
                    title.setText("You have no drug interactions!");
                }
                else
                {
                    title.setText("Your Drug Interactions");
                    builder.setItems(intStrings, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int item) {
                            // Do something with the selection
                            Interaction i = ints.get(item);
                            String description = i.getDescription();
                            String severity = i.getSeverity();

                            final AlertDialog alertDialog2 = new AlertDialog.Builder(MainActivity.this).create();

                            TextView dialogTitle2 = new TextView(getApplicationContext());
                            dialogTitle2.setText(severity);
                            dialogTitle2.setTextColor(Color.parseColor("#c70039"));
                            dialogTitle2.setTypeface(dialogTitle2.getTypeface(), Typeface.BOLD);
                            dialogTitle2.setTextSize(TypedValue.COMPLEX_UNIT_SP, 22);
                            dialogTitle2.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                            alertDialog2.setCustomTitle(dialogTitle2);

                            alertDialog2.setMessage(description);

                            alertDialog2.setButton(BUTTON_NEUTRAL, "OK" ,
                                    new DialogInterface.OnClickListener()
                                    {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which)
                                        {
                                            dialog.dismiss();
                                            alertDialog2.show();
                                        }
                                    });

                            alertDialog2.setButton(BUTTON_POSITIVE, "CONTACT PHYSICIAN" ,
                                    new DialogInterface.OnClickListener()
                                    {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which)
                                        {

                                            if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CALL_PHONE)
                                                    != PackageManager.PERMISSION_GRANTED) {
                                                // Permission is not granted
                                                ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.CALL_PHONE},REQUEST_PHONE_CALL);

                                            }
                                            else
                                            {
                                                if (!physNumber.isEmpty())
                                                {
                                                    Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+physNumber));
                                                    startActivity(intent);
                                                }
                                                else
                                                {
                                                    Toast.makeText(MainActivity.this, "Add a phone number on the Settings page", Toast.LENGTH_LONG).show();
                                                }

                                            }
                                        }
                                    });

                            alertDialog2.show();
                            TextView dialogTextView2 = (TextView)alertDialog2.findViewById(android.R.id.message);
                            dialogTextView2.setTextSize(20);
                            TextView buttonTextView = (TextView)alertDialog2.findViewById(android.R.id.button1);
                            buttonTextView.setTextSize(18);
                            TextView buttonTextView2 = (TextView)alertDialog2.findViewById(android.R.id.button3);
                            buttonTextView2.setTextSize(18);

                        }
                    });
                }
                title.setTextSize(TypedValue.COMPLEX_UNIT_SP, 24);

                if(ThemeUtils.sTheme == 1 || ThemeUtils.sTheme == 5 || ThemeUtils.sTheme == 6 || ThemeUtils.sTheme == 7 )
                {
                    title.setTextColor(getResources().getColor(colorPrimary));
                }
                title.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                builder.setCustomTitle(title);

                AlertDialog alert = builder.create();

                if (!empty)
                {
                    ListView alertListView = alert.getListView();
                    alertListView.setDivider(new ColorDrawable(Color.LTGRAY));
                    alertListView.setDividerHeight(2);
                }

                alert.show();

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

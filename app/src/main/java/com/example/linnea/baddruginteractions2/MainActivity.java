package com.example.linnea.baddruginteractions2;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.*;
import android.util.Log;
import java.util.List;

import static com.example.linnea.baddruginteractions2.R.color.colorPrimary;
import static com.example.linnea.baddruginteractions2.R.drawable.dark_logo;

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

        if ((ThemeUtils.getTheme()).equals("dark"))
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

                //ListView intsListView;
                //ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, intsListView, intStrings);

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

                            AlertDialog alertDialog2 = new AlertDialog.Builder(MainActivity.this).create();

                            TextView dialogTitle2 = new TextView(getApplicationContext());
                            dialogTitle2.setText(severity);
                            dialogTitle2.setTextColor(Color.parseColor("#c70039"));
                            dialogTitle2.setTypeface(dialogTitle2.getTypeface(), Typeface.BOLD);
                            dialogTitle2.setTextSize(TypedValue.COMPLEX_UNIT_SP, 22);
                            dialogTitle2.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                            alertDialog2.setCustomTitle(dialogTitle2);

                            alertDialog2.setMessage(description);

                            alertDialog2.setButton(AlertDialog.BUTTON_NEUTRAL, "OK" ,
                                    new DialogInterface.OnClickListener()
                                    {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which)
                                        {
                                            dialog.dismiss();
                                            //alertDialog.show();
                                        }
                                    });

                            alertDialog2.show();
                            TextView dialogTextView2 = (TextView)alertDialog2.findViewById(android.R.id.message);
                            dialogTextView2.setTextSize(20);
                        }
                    });
                }
                title.setTextSize(TypedValue.COMPLEX_UNIT_SP, 24);

                if(ThemeUtils.getTheme().equals("dark"))
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

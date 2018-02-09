package com.example.linnea.baddruginteractions2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;

import java.util.List;

public class RemindersActivity extends AppCompatActivity implements View.OnClickListener{

    UserProfileHandler up = new UserProfileHandler(this);

    Drug drug;
    int day, month, year, timeHour, timeMinutes;
    String frequency;

    List<Reminder> remList;

    public ImageButton add;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminders);

        final ListView remsListView = (ListView) findViewById(R.id.remsList);

        // Buttons
        add = (ImageButton)findViewById(R.id.add);

        // Button onClickListeners
        add.setOnClickListener(this);
    }

    //onClick method corresponds to onClickListeners: contains instructions
    //for implementing what happens when each button is clicked
    @Override
    public void onClick(View view)
    {
        //String viewStr = view.toString();
        switch(view.getId())
        {
            case R.id.add:

                // Pop-up list of reminder attributes



            default:
                // Do nothing
        }
    }

    private void populateMedsList() {

        String[] remInfo;

        if(remList.isEmpty()) // Is empty
        {
            remInfo = new String[1];
            remInfo[0] = "You have no reminders.";
        }
        else // Not empty
        {
            remInfo = new String[remList.size()];
            for (int i = 0; i < remList.size(); i++) {
                remInfo[i] = remList.get(i).getDrug() + "\n"  + remList.get(i).getStartMonth()
                        + remList.get(i).getStartDay() + ", " + remList.get(i).getStartYear()
                        + "\n" + remList.get(i).getTimeHour() + ":" + remList.get(i).getTimeMinutes()
                        + " | " + remList.get(i).getFrequency();
            }
        }

        // Build adapter
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.list_items_layout, remInfo);

        // Configure list view
        ListView remsListView = (ListView) findViewById(R.id.remsList);
        remsListView.setAdapter(adapter);
    }

    public void addReminder(Drug drug, int day, int month, int year, int timeHour, int timeMinutes, String frequency)
    {
        Reminder rem = new Reminder(drug, day, month, year, timeHour, timeMinutes, frequency);
        remList.add(rem);
    }

}

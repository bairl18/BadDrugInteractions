package com.example.linnea.baddruginteractions2;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;
import java.util.List;
import java.text.DecimalFormat;

import static android.R.color.white;
import static com.example.linnea.baddruginteractions2.R.layout.frequency_picker;
import static com.example.linnea.baddruginteractions2.R.layout.reminder_attrs_list;

public class RemindersActivity extends AppCompatActivity{

    UserRemindersHandler ur = new UserRemindersHandler(this);

    UserProfileHandler up = new UserProfileHandler(this);
    List<UserDrug> drugList;

    int day, month, year, hour, minute;
    int hour24;
    public static String drug;
    String frequency, minuteStr, amPm;

    List<Reminder> remList;

    private ImageButton add;
    private Button drugButton, startDateButton, timeButton, freqButton;
    private Button save, cancel, okay, cancel2;
    private RadioButton onceButton, dailyButton, weeklyButton, monthlyButton;

    private LinearLayout linearLayout;
    int selected;
    private PopupWindow popupWindow;
    private LayoutInflater layoutInflater;
    private ViewGroup container;
    TextView activityTitle;

    // Arraylist of pending intents that correspond to each reminder
    PendingIntent intentArray[];
    AlarmManager alarmManager;
    Intent myIntent;

    Boolean saved;

    private View.OnClickListener addOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            editReminder("", 0, 0, 0, 0, "", "", "");
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ThemeUtils.onActivityCreateSetTheme(this);
        setContentView(R.layout.activity_reminders);

        intentArray = new PendingIntent[20];

        linearLayout = (LinearLayout) findViewById(R.id.LinearLayout);
        final ListView remsListView = (ListView) findViewById(R.id.remsList);

        activityTitle = (TextView)findViewById(R.id.RemsTitle);

        if ((ThemeUtils.getTheme()).equals("dark"))
        {
            activityTitle.setTextColor(getResources().getColor(white));
        }

        populateRemsList();
        registerClickCallback();

        // Buttons
        add = (ImageButton)findViewById(R.id.add);
        add.setOnClickListener(addOnClickListener);
    }

    // User may edit and save reminder attributes
    private void editReminder(String initDrug, int initDay, int initMonth, int initYear, int initHour, String initMin, String initAmPm, String initFreq)
    {
        saved = false;
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(RemindersActivity.this);

        // Inflate reminder attrs dialog layout so we can access its buttons
        View remAttrsLayout = getLayoutInflater().inflate(reminder_attrs_list, null);

        // Buttons in reminder attrs dialog
        drugButton = (Button)remAttrsLayout.findViewById(R.id.drugButton);
        startDateButton = (Button)remAttrsLayout.findViewById(R.id.dateButton);
        timeButton = (Button)remAttrsLayout.findViewById(R.id.timeButton);
        freqButton = (Button)remAttrsLayout.findViewById(R.id.freqButton);
        save = (Button)remAttrsLayout.findViewById(R.id.save);
        cancel = (Button)remAttrsLayout.findViewById(R.id.cancel);

        // Initialize button text
        if (!initDrug.equals(""))
        {
            drugButton.setText(initDrug);
            drug = initDrug;
        }
        if (initDay != 0 && initMonth != 0 && initYear != 0)
        {
            startDateButton.setText(initMonth+1 + "/" + initDay + "/" + initYear);
            day = initDay;
            month = initMonth;
            year = initYear;
        }
        if(!(initAmPm.equals("")))
        {
            amPm = initAmPm;
        }
        if (initHour != 0 && initMin != null)
        {
            timeButton.setText(initHour + ":" + initMin + " " + amPm);
            hour = initHour;
            minuteStr = initMin;
        }
        if (!initFreq.equals(""))
        {
            freqButton.setText(initFreq);
            frequency = initFreq;
        }

        freqButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                AlertDialog.Builder mBuilder2 = new AlertDialog.Builder(RemindersActivity.this);
                mBuilder2.setIcon(R.drawable.repeat);

                // Inflate frequency picker so we can access its buttons
                View freqPickerLayout = getLayoutInflater().inflate(frequency_picker, null);

                onceButton = (RadioButton)freqPickerLayout.findViewById(R.id.once);
                dailyButton = (RadioButton)freqPickerLayout.findViewById(R.id.daily);
                weeklyButton = (RadioButton)freqPickerLayout.findViewById(R.id.weekly);
                monthlyButton = (RadioButton)freqPickerLayout.findViewById(R.id.monthly);
                okay = (Button)freqPickerLayout.findViewById(R.id.okayButton);
                cancel2 = (Button)freqPickerLayout.findViewById(R.id.cancelButton);

                mBuilder2.setView(freqPickerLayout);
                final AlertDialog freqPickerDialog = mBuilder2.create();
                freqPickerDialog.show();

                okay.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View view)
                    {
                        if(onceButton.isChecked())
                        {
                            frequency = "once";
                            freqButton.setText("once");
                            freqPickerDialog.dismiss();
                        }
                        else if(dailyButton.isChecked())
                        {
                            frequency = "daily";
                            freqButton.setText("daily");
                            freqPickerDialog.dismiss();
                        }
                        else if(weeklyButton.isChecked())
                        {
                            frequency = "weekly";
                            freqButton.setText("weekly");
                            freqPickerDialog.dismiss();
                        }
                        else if(monthlyButton.isChecked())
                        {
                            frequency = "monthly";
                            freqButton.setText("monthly");
                            freqPickerDialog.dismiss();
                        }
                        else
                        {
                            String message = "Please select a frequency";
                            Toast.makeText(RemindersActivity.this, message, Toast.LENGTH_LONG).show();
                        }
                    }
                });

                // Close dialog when user presses cancel
                cancel2.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View view)
                    {
                        freqPickerDialog.dismiss();
                    }
                });
            }
        });

        startDateButton.setOnClickListener(new View.OnClickListener()
        {

            Calendar cal = Calendar.getInstance();
            int currentDay = cal.get(Calendar.DAY_OF_MONTH);
            int currentMonth = cal.get(Calendar.MONTH);
            int currentYear = cal.get(Calendar.YEAR);

            @Override
            public void onClick(View view)
            {

                DatePickerDialog datePickerDialog = new DatePickerDialog(RemindersActivity.this, new DatePickerDialog.OnDateSetListener()
                {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2)
                    {
                        year = i;
                        month = i1+1;
                        day = i2;

                        startDateButton.setText(i1+1 + "/" + i2 + "/" + i);
                    }
                }, currentYear, currentMonth, currentDay);

                datePickerDialog.show();

            }
        });

        timeButton.setOnClickListener(new View.OnClickListener()
        {

            Calendar cal = Calendar.getInstance();
            int currentHour = cal.get(Calendar.HOUR_OF_DAY);
            int currentMinute = cal.get(Calendar.MINUTE);

            @Override
            public void onClick(View view)
            {

                TimePickerDialog timePickerDialog = new TimePickerDialog(RemindersActivity.this, new TimePickerDialog.OnTimeSetListener()
                {
                    @Override
                    public void onTimeSet(TimePicker TimePicker, int i, int i1)
                    {
                        hour = i;
                        hour24 = i;
                        minute = i1;

                        if(hour >= 12)
                        {
                            amPm = "PM";

                            if(hour != 12)
                            {
                                hour = hour-12;
                            }
                        }
                        else
                        {
                            amPm = "AM";
                        }

                        if(minute < 10)
                        {
                            // Make minute times less than 10 still appear with two numbers
                            DecimalFormat df = new DecimalFormat("00");
                            minuteStr = df.format(i1);
                            timeButton.setText(hour + ":" + minuteStr + " " + amPm);
                        }
                        else
                        {
                            timeButton.setText(hour + ":" + minute + " " + amPm);
                        }
                    }
                }, currentHour, currentMinute, false);

                timePickerDialog.show();
            }
        });

        drugButton.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View view)
            {
                AlertDialog.Builder mBuilder3 = new AlertDialog.Builder(RemindersActivity.this);

                final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(RemindersActivity.this, android.R.layout.select_dialog_singlechoice);

                drugList = up.asList();

                if (drugList.isEmpty())
                {
                    arrayAdapter.add("You have no medications");
                }
                else
                {
                    // Add all med names to the list
                    for (int i = 0; i < drugList.size(); i++)
                    {
                        String name = drugList.get(i).getDrug_name();
                        arrayAdapter.add(name);
                    }
                }

                mBuilder3.setNegativeButton("cancel", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        dialog.dismiss();
                    }
                });

                mBuilder3.setAdapter(arrayAdapter, new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        final String strName = arrayAdapter.getItem(which);

                        if (!strName.equals("You have no medications"))
                        {
                            AlertDialog.Builder builderInner = new AlertDialog.Builder(RemindersActivity.this);
                            builderInner.setMessage(strName);
                            builderInner.setTitle("You Selected: ");
                            builderInner.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    drugButton.setText(strName);
                                    drug = strName;
                                    dialog.dismiss();
                                }
                            });
                            builderInner.show();
                        }
                    }
                });
                mBuilder3.show();
            }

        });

        mBuilder.setView(remAttrsLayout);
        final AlertDialog remAttrsDialog = mBuilder.create();
        remAttrsDialog.show();

        // Close dialog when user presses cancel
        cancel.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                remAttrsDialog.dismiss();
            }
        });

        save.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if(drug != null && day != 0 && month != 0 && year != 0 && hour != 0
                        && (minuteStr != null || minute != 0) && amPm != null && frequency != null)
                {
                    addReminder(drug, day, month, year, hour, minute, amPm, frequency);
                    setNotification();
                    saved = true;
                    remAttrsDialog.dismiss();
                    populateRemsList();
                }
                else
                {
                    String message = "Please complete all fields";
                    Toast.makeText(RemindersActivity.this, message, Toast.LENGTH_LONG).show();
                }
            }

        });
    }



    // Put all reminders into the ListView
    private void populateRemsList()
    {
        remList = ur.asList();
        String[] remInfo;

        if(remList.isEmpty()) // Is empty
        {
            remInfo = new String[1];
            remInfo[0] = "You have no reminders.";
        }
        else // Not empty
        {
            remInfo = new String[remList.size()];
            for (int i = 0; i < remList.size(); i++)
            {
                remInfo[i] = remList.get(i).getDrugName() + "\n"  + remList.get(i).getStartMonth()
                        + "/" + remList.get(i).getStartDay() + "/" + remList.get(i).getStartYear()
                        + "\n" + remList.get(i).getTimeHour() + ":" + remList.get(i).getTimeMinutes()
                        + " " + remList.get(i).getAmPm() + ", " + remList.get(i).getFrequency();
            }
        }

        // Build adapter
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.list_items_layout, remInfo);

        // Configure list view
        ListView remsListView = (ListView) findViewById(R.id.remsList);
        remsListView.setAdapter(adapter);
    }

    // Returns the current maximum reminder id
    public int getNewId()
    {
        int maxId = ur.getMaxId();
        return maxId;
    }

    // Creates a reminder object and adds it to the userRemindersHandler database
    public void addReminder(String drug, int day, int month, int year, int timeHour, int timeMinutes, String amPm, String frequency)
    {
        // Find maximum id #, then set the new reminder's id # to the number after it.
        int maxId = getNewId();
        Reminder rem = new Reminder(maxId+1, drug, day, month, year, timeHour, timeMinutes, amPm, frequency);
        ur.addRem(rem);
    }

    // Create a new notification at the time and date selected by the user
    public void setNotification()
    {
        // Create alarm
        alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
        myIntent = new Intent(RemindersActivity.this, AlertReceiver.class);

        // Set the pending intent's request code to the id of the new reminder we've just created.
        // This way, the reminder id corresponds to the notification request code.
        PendingIntent pendingIntent = PendingIntent.getBroadcast(RemindersActivity.this, getNewId(), myIntent, 0);

        // Add pending intent to intent array at the index of the new id.
        intentArray[getNewId()] = pendingIntent;

        // Create an instance of the calendar and set its attributes to the time chosen by the user
        Calendar calendar = Calendar.getInstance();

        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.HOUR_OF_DAY, hour24);

        if(amPm.equals("AM"))
        {
            calendar.set(Calendar.AM_PM, Calendar.AM);
        }
        else if(amPm.equals("PM"))
        {
            calendar.set(Calendar.AM_PM, Calendar.PM);
        }

        // Set the alarm manager so it will show the notification at the time specified in our calendar instance
        if(frequency.equals("once"))
        {
            alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
        }
        if(frequency.equals("daily"))
        {
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), alarmManager.INTERVAL_DAY , pendingIntent);
        }
        else if(frequency.equals("weekly"))
        {
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), alarmManager.INTERVAL_DAY * 7 , pendingIntent);
        }
        else if(frequency.equals("monthly"))
        {
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), alarmManager.INTERVAL_DAY * 30 , pendingIntent);
        }

    }

    // On Click method for the reminders ListView
    private void registerClickCallback()
    {
        final ListView remsList = (ListView) findViewById(R.id.remsList);
        remsList.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> paret, final View viewClicked, final int position, long id)
            {

                TextView textView = (TextView) viewClicked;
                String textInView = textView.getText().toString();
                if(!textInView.equals("You have no reminders."))
                {

                    remsList.setSelector(android.R.color.darker_gray);
                    selected = position;

                    int[] locationOfClickedView = new int[2];
                    viewClicked.getLocationOnScreen(locationOfClickedView);
                    int x = locationOfClickedView[0];
                    int y = locationOfClickedView[1];

                    layoutInflater = (LayoutInflater) getApplicationContext().getSystemService(LAYOUT_INFLATER_SERVICE);
                    container = (ViewGroup) layoutInflater.inflate(R.layout.activity_delete_edit_reminder, null);

                    popupWindow = new PopupWindow(container, 570, 450, true);
                    popupWindow.setAnimationStyle(-1);
                    popupWindow.showAtLocation(linearLayout, Gravity.NO_GRAVITY, x + 200, y);

                    ImageButton delete = (ImageButton) container.findViewById(R.id.delete);
                    delete.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            // Check if the reminder is in the rems list
                            if (ur.searchReminder(remList.get(selected).getId()) != null)
                            {
                                // Delete the reminder
                                remsList.setSelector(android.R.color.transparent);
                                ur.deleteRem(remList.get(selected));

                                // Delete the corresponding notification
                                PendingIntent pIntent = intentArray[remList.get(selected).getId()];

                                if(alarmManager != null)
                                {
                                    alarmManager.cancel(pIntent);
                                }

                                if(intentArray[remList.get(selected).getId()] != null)
                                {
                                    intentArray[remList.get(selected).getId()] = null;
                                }

                                popupWindow.dismiss();
                                populateRemsList();

                                if (selected == 0) //If only one rem in the list
                                {
                                    // Restart the activity
                                    finish();
                                    startActivity(getIntent());
                                }
                            }
                        }
                    });

                    ImageButton edit = (ImageButton) container.findViewById(R.id.edit);
                    edit.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            // Check if the reminder is in the rems list
                            if (ur.searchReminder(remList.get(selected).getId()) != null)
                            {
                                String thisDrug = remList.get(selected).getDrugName();
                                int thisDay = remList.get(selected).getStartDay();
                                int thisMonth = remList.get(selected).getStartMonth();
                                int thisYear = remList.get(selected).getStartYear();
                                int thisHour = remList.get(selected).getTimeHour();
                                String thisAmPm = remList.get(selected).getAmPm();
                                String thisMin = remList.get(selected).getTimeMinutes();
                                String thisFreq = remList.get(selected).getFrequency();

                                // Create a new edited reminder
                                editReminder(thisDrug, thisDay, thisMonth, thisYear, thisHour, thisMin, thisAmPm, thisFreq);

                                if(saved == true) // Check if a reminder was actually added
                                {
                                    // Delete the reminder
                                    remsList.setSelector(android.R.color.transparent);

                                    if(remList.get(selected) != null)
                                    {
                                        ur.deleteRem(remList.get(selected));
                                    }

                                    // Delete the corresponding notification
                                    PendingIntent pIntent = intentArray[remList.get(selected).getId()];

                                    if(alarmManager != null)
                                    {
                                        alarmManager.cancel(pIntent);
                                    }

                                    if(intentArray[remList.get(selected).getId()] != null)
                                    {
                                        intentArray[remList.get(selected).getId()] = null;
                                    }

                                    populateRemsList();

                                }

                                popupWindow.dismiss();
                            }
                        }
                    });

                    // Make window disappear after user touches another location on screen
                    container.setOnTouchListener(new View.OnTouchListener() {
                        @Override
                        public boolean onTouch(View v, MotionEvent event) {

                            //Change color of selected item back to normal?

                            popupWindow.dismiss();
                            return true;
                        }
                    });

                }
            }
        });
    }

}

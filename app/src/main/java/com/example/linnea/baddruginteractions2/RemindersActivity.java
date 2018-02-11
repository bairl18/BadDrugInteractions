package com.example.linnea.baddruginteractions2;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
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

import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;
import java.text.DecimalFormat;

import static com.example.linnea.baddruginteractions2.R.layout.frequency_picker;
import static com.example.linnea.baddruginteractions2.R.layout.reminder_attrs_list;

public class RemindersActivity extends AppCompatActivity{

    UserProfileHandler up = new UserProfileHandler(this);
    List<UserDrug> drugList;

    int day, month, year, hour, minute;
    String drug, frequency, minuteStr;

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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminders);

        linearLayout = (LinearLayout) findViewById(R.id.LinearLayout);
        final ListView remsListView = (ListView) findViewById(R.id.remsList);

        populateRemsList();
        registerClickCallback();

        // Buttons
        add = (ImageButton)findViewById(R.id.add);
        add.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
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

                                startDateButton.setText(i1+1 + "." + i2 + "." + i);
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
                                minute = i1;

                                String amPm;

                                if(hour >= 12)
                                {
                                    amPm = "PM";
                                    i = i-12;
                                }
                                else
                                {
                                    amPm = "am";
                                }

                                if(minute < 10)
                                {
                                    DecimalFormat df = new DecimalFormat("00");
                                    minuteStr = df.format(i1);
                                    timeButton.setText(i + ":" + minuteStr + " " + amPm);
                                }
                                else
                                {
                                    timeButton.setText(i + ":" + i1 + " " + amPm);
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
                        if(drug != null && day != 0 && month != 0 && year != 0 && hour != 0 && (minuteStr != null || minute != 0) && frequency != null)
                        {
                            addReminder(drug, day, month, year, hour, minute, frequency);
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
        });
    }


    private void populateRemsList() {

        remList = up.getRemList();
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
                        + "." + remList.get(i).getStartDay() + "." + remList.get(i).getStartYear()
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

    public void addReminder(String drug, int day, int month, int year, int timeHour, int timeMinutes, String frequency)
    {
        Reminder rem = new Reminder(drug, day, month, year, timeHour, timeMinutes, frequency);
        up.addReminder(rem);
    }

    private void registerClickCallback()
    {
        final ListView remsList = (ListView) findViewById(R.id.remsList);
        remsList.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> paret, final View viewClicked, int position, long id)
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
                    container = (ViewGroup) layoutInflater.inflate(R.layout.activity_delete_pop_up, null);

                    popupWindow = new PopupWindow(container, 500, 450, true);
                    popupWindow.setAnimationStyle(-1);
                    popupWindow.showAtLocation(linearLayout, Gravity.NO_GRAVITY, x + 200, y);

                    ImageButton delete = (ImageButton) container.findViewById(R.id.delete);
                    delete.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            // Check if the reminder is in the rems list
                            if (up.searchRem(remList.get(selected).getDrugName()) != null)
                            {
                                // Delete the reminder
                                remsList.setSelector(android.R.color.transparent);
                                up.deleteReminder(remList.get(selected));
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

                    // Make window disappear after user touches another location on screen
                    container.setOnTouchListener(new View.OnTouchListener() {
                        @Override
                        public boolean onTouch(View v, MotionEvent event) {
                            popupWindow.dismiss();
                            return true;
                        }
                    });

                }
            }
        });
    }

}

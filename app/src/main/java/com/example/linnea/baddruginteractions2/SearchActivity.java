package com.example.linnea.baddruginteractions2;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;
import java.util.List;

import static android.R.color.white;
import static java.sql.Types.NULL;

public class SearchActivity extends AppCompatActivity {

    AWSConnector aws = new AWSConnector();
    UserProfileHandler up = new UserProfileHandler(this);
    DrugInteractionsHandler di = new DrugInteractionsHandler(this);
    int selected = -1;

    Boolean interactionFound = false;

    private LinearLayout linearLayout;
    private PopupWindow popupWindow;
    private LayoutInflater layoutInflater;
    private ViewGroup container;
    EditText searchField;

    List<String> drugList;
    ListView fdaMedsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ThemeUtils.onActivityCreateSetTheme(this);
        setContentView(R.layout.activity_search);

        linearLayout = (LinearLayout) findViewById(R.id.linearlayout0);

        searchField = (EditText)findViewById(R.id.searchField);

        if ((ThemeUtils.getTheme()).equals("dark"))
        {
            searchField.setTextColor(getResources().getColor(white));
        }

        fdaMedsList = (ListView) findViewById(R.id.fdaMedsList);
        final Button search = (Button) findViewById(R.id.search);
        final TextView searchField = (TextView) findViewById(R.id.searchField);

        populateMedsList("a-");

        searchField.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN)
                {
                    switch (keyCode)
                    {
                        case KeyEvent.KEYCODE_DPAD_CENTER:
                        case KeyEvent.KEYCODE_ENTER:
                            String searchText = searchField.getText().toString();
                            if (searchText.isEmpty())
                            {
                                //toast that says enter a valid medication
                                String message = "Please enter a valid medication.";
                                Toast.makeText(SearchActivity.this, message, Toast.LENGTH_LONG).show();
                            }
                            else
                            {
                                populateMedsList(searchText);
                                selected = -1;
                            }
                            return true;
                        default:
                            break;
                    }
                }
                return false;
            }
        });

        search.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                String searchText = searchField.getText().toString();

                if (searchText.isEmpty())
                {
                    //toast that says enter a valid medication
                    String message = "Please enter a valid medication.";
                    Toast.makeText(SearchActivity.this, message, Toast.LENGTH_LONG).show();
                }
                else
                {
                    populateMedsList(searchText);
                    selected = -1;
                }
            }
        });

        registerClickCallback();

        searchField.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                searchField.setText("");
            }
        });

    }

    private void populateMedsList(String searchText)
    {
        // Returns a list of drugs that correspond with the search criteria
        drugList = aws.findSimilarDrugNames(searchText);

        // Create array of drug name strings
        String[] drugNames = new String[drugList.size()]; // String array of drug names

        // Add all FDA drug names to the array
        for (int i = 0; i < drugList.size(); i++)
        {
            drugNames[i] = drugList.get(i);
        }

        // Build adapter
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.list_items_layout, drugNames);

        // Configure list view
        ListView fdaMedsList = (ListView) findViewById(R.id.fdaMedsList);
        fdaMedsList.setAdapter(adapter);

        if(drugList.isEmpty())
        {
            String message = "No Medications Found.";
            Toast.makeText(SearchActivity.this, message, Toast.LENGTH_LONG).show();
        }
    }


    private void registerClickCallback()
    {
        final ListView fdaMedsList = (ListView) findViewById(R.id.fdaMedsList);

        // OnClickListener for when a drug in the ListView is clicked
        fdaMedsList.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> paret, View viewClicked, int position, long id)
            {
                fdaMedsList.setSelector(android.R.color.darker_gray);;
                selected = position;

                int [] locationOfClickedView = new int[2];
                viewClicked.getLocationOnScreen(locationOfClickedView);
                int x = locationOfClickedView[0];
                int y = locationOfClickedView[1];

                layoutInflater = (LayoutInflater) getApplicationContext().getSystemService(LAYOUT_INFLATER_SERVICE);
                container = (ViewGroup) layoutInflater.inflate(R.layout.activity_add_pop_up, null);

                popupWindow = new PopupWindow(container, 550, 520, true);
                popupWindow.setAnimationStyle(-1);
                popupWindow.showAtLocation(linearLayout, Gravity.NO_GRAVITY, x+180, y);

                // Add selected medication to medications list
                ImageButton add = (ImageButton)container.findViewById(R.id.add);
                add.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View view)
                    {
                        if(selected >= 0)
                        {
                            popupWindow.dismiss();
                            fdaMedsList.setSelector(android.R.color.transparent);

                            // Create new UserDrug by getting corresponding drug at the selected position
                            final UserDrug ud = new UserDrug(drugList.get(selected));

                            final String message = "Drug saved";

                            if (up.searchDrug(ud.getDrug_name()) == null) // Drug isn't already in user medications list
                            {
                                // Check for interactions between this drug and the other saved drugs
                                // Display warning message if there is an interaction
                                List<UserDrug> savedDrugs = up.asList();

                                if(!savedDrugs.isEmpty())
                                {
                                    for (UserDrug d : savedDrugs)
                                    {
                                        // ud is drug selected by user, d is a drug already in the user meds list
                                        final Interaction interaction = di.lookupInteractionForDrugs(ud, d);

                                        // Interaction is found
                                        if (interaction != null)
                                        {
                                            interactionFound = true;

                                            // Warning message to user
                                            final AlertDialog alertDialog = new AlertDialog.Builder(SearchActivity.this).create();

                                            TextView dialogTitle1 = new TextView(getApplicationContext());
                                            dialogTitle1.setText("Warning! \n Interaction found.");
                                            dialogTitle1.setTextSize(TypedValue.COMPLEX_UNIT_SP, 22);
                                            dialogTitle1.setTextColor(Color.parseColor("#c70039"));
                                            dialogTitle1.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                                            dialogTitle1.setTypeface(dialogTitle1.getTypeface(), Typeface.BOLD);
                                            dialogTitle1.setPadding(0,30,0,0);
                                            alertDialog.setCustomTitle(dialogTitle1);

                                            alertDialog.setMessage("\nTaking " + ud.getDrug_name() + " and " + d.getDrug_name() + " may result in a " + interaction.getSeverity() + ".\n" +
                                                    "Do you still want to save this drug?");

                                            final String description = interaction.getDescription();

                                            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "MORE INFO" ,
                                                    new DialogInterface.OnClickListener()
                                                    {
                                                        @Override
                                                        public void onClick(DialogInterface dialog, int which)
                                                        {
                                                            AlertDialog alertDialog2 = new AlertDialog.Builder(SearchActivity.this).create();

                                                            TextView dialogTitle2 = new TextView(getApplicationContext());
                                                            dialogTitle2.setText("Interaction Description");
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
                                                                            alertDialog.show();
                                                                        }
                                                                    });

                                                            alertDialog2.show();
                                                            TextView dialogTextView2 = (TextView)alertDialog2.findViewById(android.R.id.message);
                                                            dialogTextView2.setTextSize(20);
                                                        }
                                                    });

                                            alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "YES" ,
                                                    new DialogInterface.OnClickListener()
                                                    {
                                                        @Override
                                                        public void onClick(DialogInterface dialog, int which)
                                                        {
                                                            up.addDrug(ud); // Add selected drug to user medications list
                                                            // Add interaction to database
                                                            di.addInteraction(interaction);
                                                            dialog.dismiss();
                                                            // Go to meds list
                                                            finish();
                                                            Intent intent = new Intent(SearchActivity.this, MedicationsActivity.class);
                                                            startActivity(intent);
                                                            Toast.makeText(SearchActivity.this, message, Toast.LENGTH_LONG).show();
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
                                            TextView dialogMoreInfoButton1 = (TextView)alertDialog.findViewById(android.R.id.button2);
                                            dialogMoreInfoButton1.setTextSize(20);
                                            TextView dialogCancelButton1 = (TextView)alertDialog.findViewById(android.R.id.button3);
                                            dialogCancelButton1.setTextSize(20);
                                        }
                                    }

                                    // No interactions were found
                                    if (interactionFound == false)
                                    {
                                        up.addDrug(ud); // Add selected drug to user medications list
                                        // Go to meds list
                                        finish();
                                        Intent intent = new Intent(SearchActivity.this, MedicationsActivity.class);
                                        startActivity(intent);
                                        Toast.makeText(SearchActivity.this, message, Toast.LENGTH_LONG).show();
                                    }
                                }
                                else  // User meds list is empty
                                {
                                    up.addDrug(ud); // Add selected drug to user medications list
                                    // Go to meds list
                                    finish();
                                    Intent intent = new Intent(SearchActivity.this, MedicationsActivity.class);
                                    startActivity(intent);
                                    Toast.makeText(SearchActivity.this, message, Toast.LENGTH_LONG).show();
                                }
                            }
                            else // Already in user medications list
                            {
                                Toast.makeText(SearchActivity.this, "This medication is already saved.", Toast.LENGTH_LONG).show();
                            }
                        }
                    }
                });

                // Make window disappear after user touches another location on screen
                container.setOnTouchListener(new View.OnTouchListener()
                {
                    @Override
                    public boolean onTouch(View v, MotionEvent event)
                    {
                        popupWindow.dismiss();
                        return true;
                    }
                });


                TextView textView = (TextView) viewClicked;
            }
        });
    }
}

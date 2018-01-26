package com.example.linnea.baddruginteractions2;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
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

import static java.sql.Types.NULL;

public class SearchActivity extends AppCompatActivity {

    DBHandler db = new DBHandler(this);
    UserProfileHandler up = new UserProfileHandler(this);
    int selected = -1;

    private LinearLayout linearLayout;
    private PopupWindow popupWindow;
    private LayoutInflater layoutInflater;
    private ViewGroup container;

    List<Drug> drugList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        linearLayout = (LinearLayout) findViewById(R.id.linearlayout0);

        final ListView fdaMedsList = (ListView) findViewById(R.id.fdaMedsList);
        final Button search = (Button) findViewById(R.id.search);
        final TextView searchField = (TextView) findViewById(R.id.searchField);

        search.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                String searchText = searchField.getText().toString();

                if (searchText.equals(NULL))
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
        drugList = db.getDrugList(searchText);

        // Create array of drug name strings
        if(!drugList.isEmpty()) //If there are drugs in drugList
        {
            String[] drugNames = new String[drugList.size()]; // String array of drug names

            // Add all FDA drug names to the array
            for (int i = 0; i < drugList.size(); i++)
            {
                drugNames[i] = drugList.get(i).getDrug_name();
            }

            // Build adapter
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.list_items_layout, drugNames);

            // Configure list view
            ListView fdaMedsList = (ListView) findViewById(R.id.fdaMedsList);
            fdaMedsList.setAdapter(adapter);
        }
        else
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
                fdaMedsList.setSelector(android.R.color.darker_gray);
                selected = position;

                int [] locationOfClickedView = new int[2];
                viewClicked.getLocationOnScreen(locationOfClickedView);
                int x = locationOfClickedView[0];
                int y = locationOfClickedView[1];

                layoutInflater = (LayoutInflater) getApplicationContext().getSystemService(LAYOUT_INFLATER_SERVICE);
                container = (ViewGroup) layoutInflater.inflate(R.layout.activity_add_pop_up, null);

                popupWindow = new PopupWindow(container, 500, 450, true);
                popupWindow.setAnimationStyle(-1);
                popupWindow.showAtLocation(linearLayout, Gravity.NO_GRAVITY, x+200, y);

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
                            UserDrug ud = new UserDrug(drugList.get(selected));
                            String message = "Drug saved";

                            if (up.searchDrug(ud.getId()) == null) //Drug isn't already in user medications list
                            {
                                up.addDrug(ud); // Add selected drug to user medications list
                            }
                            else // Already in user medications list
                            {
                                message = "Drug already saved";
                            }

                            Toast.makeText(SearchActivity.this, message, Toast.LENGTH_LONG).show();
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
                //String message = "You clicked " + textView.getText().toString();
                //Toast.makeText(SearchActivity.this, message, Toast.LENGTH_LONG).show();
            }
        });
    }
}

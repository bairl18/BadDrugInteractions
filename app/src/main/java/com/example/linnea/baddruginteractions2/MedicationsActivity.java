package com.example.linnea.baddruginteractions2;

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
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import static android.R.id.list;
import static com.example.linnea.baddruginteractions2.R.color.colorPrimaryDark;

public class MedicationsActivity extends AppCompatActivity
{

    UserProfileHandler up = new UserProfileHandler(this);
    int selected;

    private LinearLayout linearLayout;
    private PopupWindow popupWindow;
    private LayoutInflater layoutInflater;
    private ViewGroup container;

    List<UserDrug> drugList;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medications);

        final ListView userMedsList = (ListView) findViewById(R.id.userMedsList);
        linearLayout = (LinearLayout) findViewById(R.id.linearLayout);

        populateMedsList();
        registerClickCallback();
    }

    private void populateMedsList() {

        drugList = up.asList();

        // Create list of meds
        String[] drugNames;

        if(drugList.isEmpty()) // Is empty
        {
            drugNames = new String[1];
            drugNames[0] = "You have no medications.";
        }
        else // Not empty
        {
            drugNames = new String[drugList.size()];

            for (int i = 0; i < drugList.size(); i++)
            {
                drugNames[i] = drugList.get(i).getDrug_name();
            }
        }

        // Build adapter
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.list_items_layout, drugNames);

        // Configure list view
        ListView userMedsList = (ListView) findViewById(R.id.userMedsList);
        userMedsList.setAdapter(adapter);

    }

    private void registerClickCallback()
    {
        final ListView userMedsList = (ListView) findViewById(R.id.userMedsList);
        userMedsList.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> paret, final View viewClicked, int position, long id)
            {

            TextView textView = (TextView) viewClicked;
            String textInView = textView.getText().toString();
            if(!textInView.equals("You have no medications."))
            {
                userMedsList.setSelector(android.R.color.darker_gray);;
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

                        if (up.searchDrug(drugList.get(selected).getDrug_name()) != null) ;
                        {
                            userMedsList.setSelector(android.R.color.transparent);
                            up.deleteDrug(drugList.get(selected));
                            popupWindow.dismiss();

                            populateMedsList();

                            if (selected == 0) //If only one med in the list
                            {
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

                //String message = "You clicked " + textView.getText().toString();
                //Toast.makeText(MedicationsActivity.this, message, Toast.LENGTH_LONG).show();

            }
            }
        });
    }
}




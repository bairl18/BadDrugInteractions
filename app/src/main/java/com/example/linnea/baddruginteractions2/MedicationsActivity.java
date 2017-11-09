package com.example.linnea.baddruginteractions2;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import static android.R.id.list;

public class MedicationsActivity extends AppCompatActivity
{

    UserProfileHandler up = new UserProfileHandler(this);
    int selected;

    List<UserDrug> drugList;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medications);

        ImageButton delete = (ImageButton) findViewById(R.id.deleteButton);

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (up.searchDrug(drugList.get(selected).getId()) != null);
                {

                    up.deleteDrug(drugList.get(selected));

                    populateMedsList();
                }
            }
        });

        populateMedsList();
        registerClickCallback();
    }

    private void populateMedsList() {

        drugList = up.asList();
        // Create list of meds

        if(!drugList.isEmpty()) {
            String[] drugInfo = new String[drugList.size()];
            for (int i = 0; i < drugList.size(); i++) {
                drugInfo[i] = drugList.get(i).getDrug_name() + " | "
                                + drugList.get(i).getActive_ingredient() + " | "
                                + drugList.get(i).getForm();
            }

            // Build adapter
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.list_items_layout, drugInfo);

            // Configure list view
            ListView userMedsList = (ListView) findViewById(R.id.userMedsList);
            userMedsList.setAdapter(adapter);
        }
    }

    private void registerClickCallback()
    {
        final ListView userMedsList = (ListView) findViewById(R.id.userMedsList);
        userMedsList.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> paret, View viewClicked, int position, long id)
            {

                selected = position;

                TextView textView = (TextView) viewClicked;
                String message = "You clicked #" + position + ", which is string: " + textView.getText().toString();
                Toast.makeText(MedicationsActivity.this, message, Toast.LENGTH_LONG).show();
            }
        });
    }
}




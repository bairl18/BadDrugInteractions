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

import static android.R.id.list;

public class MedicationsActivity extends AppCompatActivity
{

    UserProfileHandler up = new UserProfileHandler(this);
    UserDrug selectedDrug;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medications);

        ImageButton delete = (ImageButton) findViewById(R.id.deleteButton);

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //if() its in up
                //{
                    up.deleteDrug(selectedDrug);
                //}
            }
        });

        populateMedsList();
        registerClickCallback();
    }

    private void populateMedsList() {
        // Create list of meds
        String[] userMeds = {"Tylenol", "Xanax", "Penicillin", "Dimetapp"};

        // Build adapter
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.list_items_layout, userMeds);

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
            public void onItemClick(AdapterView<?> paret, View viewClicked, int position, long id)
            {

                selectedDrug = (UserDrug)userMedsList.getItemAtPosition(position);

                TextView textView = (TextView) viewClicked;
                String message = "You clicked # " + position + ", which is string: " + textView.getText().toString();
                Toast.makeText(MedicationsActivity.this, message, Toast.LENGTH_LONG).show();
            }
        });
    }
}




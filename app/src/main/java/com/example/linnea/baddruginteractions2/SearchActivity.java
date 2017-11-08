package com.example.linnea.baddruginteractions2;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import static java.sql.Types.NULL;

public class SearchActivity extends AppCompatActivity {

    DBHandler db = new DBHandler(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        final TextView searchField = (TextView) findViewById(R.id.searchField);
        searchField.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                String search = searchField.getText().toString();

                if (search.equals(NULL))
                {
                    //toast that says enter a valid medication
                    String message = "Please enter a valid medication.";
                    Toast.makeText(SearchActivity.this, message, Toast.LENGTH_LONG).show();
                }
                else
                {
                   //query
                }
            }
        });

        populateMedsList();
        registerClickCallback();
    }



    private void populateMedsList()
    {
        // Create list of meds
        Drug[] fdaMeds = {};

        // Build adapter
        ArrayAdapter<Drug> adapter = new ArrayAdapter<Drug>(this, R.layout.list_items_layout, fdaMeds);

        // Configure list view
        ListView fdaMedsList = (ListView) findViewById(R.id.fdaMedsList);
        fdaMedsList.setAdapter(adapter);
    }

    private void registerClickCallback()
    {
        final ListView fdaMedsList = (ListView) findViewById(R.id.fdaMedsList);
        fdaMedsList.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> paret, View viewClicked, int position, long id)
            {
                Drug selectedDrug = (Drug)fdaMedsList.getItemAtPosition(position);

                TextView textView = (TextView) viewClicked;
                String message = "You clicked # " + position + ", which is string: " + textView.getText().toString();
                Toast.makeText(SearchActivity.this, message, Toast.LENGTH_LONG).show();
            }
        });
    }
}

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

public class SearchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        populateMedsList();
        registerClickCallback();
    }

    private void populateMedsList() {
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
        ListView fdaMedsList = (ListView) findViewById(R.id.fdaMedsList);
        fdaMedsList.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> paret, View viewClicked, int position, long id)
            {
                TextView textView = (TextView) viewClicked;
                String message = "You clicked # " + position + ", which is string: " + textView.getText().toString();
                Toast.makeText(SearchActivity.this, message, Toast.LENGTH_LONG).show();
            }
        });
    }
}

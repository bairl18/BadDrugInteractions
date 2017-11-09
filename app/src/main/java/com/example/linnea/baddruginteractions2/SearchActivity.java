package com.example.linnea.baddruginteractions2;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import static java.sql.Types.NULL;

public class SearchActivity extends AppCompatActivity {

    DBHandler db = new DBHandler(this);
    UserProfileHandler up = new UserProfileHandler(this);
    UserDrug selectedDrug;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

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
                }
            }
        });

        registerClickCallback();

        ImageButton add = (ImageButton)findViewById(R.id.addButton);
        add.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {

                if(selectedDrug != null)
                {
                    up.addDrug(selectedDrug);
                }

            }
        });

    }



    private void populateMedsList(String searchText)
    {
        List<Drug> drugList = db.getDrugList(searchText);
        // Create list of meds
        if(!drugList.isEmpty()) {
            String[] drugNames = new String[drugList.size()];
            for (int i = 0; i < drugList.size(); i++) {
                drugNames[i] = drugList.get(i).getDrug_name();
            }

            // Build adapter
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.list_items_layout, drugNames);

            // Configure list view
            ListView fdaMedsList = (ListView) findViewById(R.id.fdaMedsList);
            fdaMedsList.setAdapter(adapter);
        }
    }

    private void registerClickCallback()
    {
        final ListView fdaMedsList = (ListView) findViewById(R.id.fdaMedsList);
        fdaMedsList.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> paret, View viewClicked, int position, long id)
            {
                 selectedDrug = (UserDrug)fdaMedsList.getItemAtPosition(position);

                TextView textView = (TextView) viewClicked;
                String message = "You clicked # " + position + ", which is string: " + textView.getText().toString();
                Toast.makeText(SearchActivity.this, message, Toast.LENGTH_LONG).show();
            }
        });
    }
}

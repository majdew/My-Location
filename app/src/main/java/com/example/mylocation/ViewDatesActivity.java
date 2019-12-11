package com.example.mylocation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class ViewDatesActivity extends AppCompatActivity {
    SqliteDatabaseAdapter sqliteDatabaseAdapter;
    ArrayList <String> locationVisitingDates;
    ListView visitingDates;
    TextView numberOfVisitsTextView;
    ArrayAdapter<String> arrayAdapter;
    Intent intent;
    int locationId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_dates);
        visitingDates = findViewById(R.id.list);
        numberOfVisitsTextView = findViewById(R.id.number_of_visits);

        sqliteDatabaseAdapter =new SqliteDatabaseAdapter(this);
        intent = getIntent();
        locationId = intent.getIntExtra("id" , 0);
        locationVisitingDates = sqliteDatabaseAdapter.getAllDates(locationId);


        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1 , locationVisitingDates  );
        visitingDates.setAdapter(arrayAdapter);
        int numberOfVisits = locationVisitingDates.size()+1;
        numberOfVisitsTextView.setText("Number Of visits : " + numberOfVisits);

    }
}

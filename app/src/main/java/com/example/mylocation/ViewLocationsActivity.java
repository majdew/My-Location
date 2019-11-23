package com.example.mylocation;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class ViewLocationsActivity extends AppCompatActivity {
    SqliteDatabaseAdapter sqliteDatabaseAdapter;
    ArrayList <LocationObject> locations;
    LocationObjectAdapter locationObjectAdapter;
    ListView locationsListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_locations);

        locationsListView = findViewById(R.id.locations_list_view);

        sqliteDatabaseAdapter = new SqliteDatabaseAdapter(this);
        sqliteDatabaseAdapter.open();

        locations = (ArrayList<LocationObject>) sqliteDatabaseAdapter.getAllLocations();
        locationObjectAdapter = new LocationObjectAdapter(this , locations);

        locationsListView.setAdapter(locationObjectAdapter);






    }
}

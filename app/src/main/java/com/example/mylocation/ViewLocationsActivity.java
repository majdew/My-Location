package com.example.mylocation;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

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

        sqliteDatabaseAdapter = new SqliteDatabaseAdapter(this);

        locationsListView = findViewById(R.id.locations_list_view);
        refreshListView();


        registerForContextMenu(locationsListView);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.update_delete_context_menu,menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
        int listElementItemPosition = menuInfo.position;
        int locationObjectId = locations.get(listElementItemPosition).getId();
        int idContextItemSelected = item.getItemId();
        switch (idContextItemSelected){
            case R.id.delete: {
                sqliteDatabaseAdapter.deleteLocation(locationObjectId);
                onResume();
            }
            break;
            case R.id.update: {
                updateLocationAlertDialog(locationObjectId);
                locationObjectAdapter.notifyDataSetChanged();
            }
            break;
            case R.id.marker: {
                LocationObject currentLocation = locations.get(listElementItemPosition);
                double latitude = currentLocation.getLatitude();
                double longitude = currentLocation.getLongitude();
                String title = currentLocation.getTitle();
                Intent intent = new Intent(this , MapsActivity.class);
                intent.putExtra("title" ,title);
                intent.putExtra("latitude" , latitude);
                intent.putExtra("longitude" , longitude);
                startActivity(intent);

            }
        }
        return super.onContextItemSelected(item);
    }

    public void updateLocationAlertDialog(final int selectedListItemDatabaseId){
        final AlertDialog.Builder alert = new AlertDialog.Builder(ViewLocationsActivity.this);
        alert.setIcon(R.mipmap.ic_launcher);
        final View view = getLayoutInflater().inflate(R.layout.update_location_layout,null);
        alert.setView(view);
        alert.setTitle("Update Location Dialog");
        alert.setMessage("Enter new location Information: ");
        alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        alert.setPositiveButton("update location", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                EditText titleEditText = view.findViewById(R.id.update_title_edit_text);
                EditText descriptionEditText = view.findViewById(R.id.update_description_edit_text);

                String newLocationTitle = titleEditText.getText().toString();
                String newLocationDescription = descriptionEditText.getText().toString();

                sqliteDatabaseAdapter.updateLocation(selectedListItemDatabaseId , newLocationTitle , newLocationDescription) ;
                onResume();
            }
        });
        AlertDialog dialog = alert.create();
        dialog.show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        refreshListView();
    }

    public void refreshListView(){
        locations = (ArrayList<LocationObject>) sqliteDatabaseAdapter.getAllLocations();
        locationObjectAdapter = new LocationObjectAdapter(this , locations);
        locationsListView.setAdapter(locationObjectAdapter);
        locationObjectAdapter.notifyDataSetChanged();
    }
}

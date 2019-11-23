package com.example.mylocation;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
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
    View locationListItem ;

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

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.update_delete_context_menu,menu);
        locationListItem = v;


    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {

        int id = item.getItemId();

        switch (id){
            case R.id.delete:{
                sqliteDatabaseAdapter.deleteLocation(id);
                locationObjectAdapter.notifyDataSetChanged();
            }


                break;
            case R.id.update: {


            }

                break;
        }

        return super.onContextItemSelected(item);
    }

    public void
    updateLocationAlertDialog(){
        TextView idTextView = (TextView) locationListItem.findViewById(R.id.visting_id_text_view);
        final int locationItemId = Integer.parseInt(idTextView.getText().toString());

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
                EditText titleEditText = (EditText)view.findViewById(R.id.update_title_edit_text);
                EditText descriptionEditText = (EditText)view.findViewById(R.id.update_description_edit_text);

            }
        });
        AlertDialog dialog = alert.create();
        dialog.show();
    }
}

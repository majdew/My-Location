package com.example.mylocation;

import android.content.Context;
import android.location.Location;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class LocationObjectAdapter extends ArrayAdapter <LocationObject> {

    ArrayList <LocationObject> locations ;

    public LocationObjectAdapter(@NonNull Context context,  ArrayList<LocationObject> locations) {
        super(context, 0 , locations);
        this.locations = locations;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LocationObject location = getItem(position);

        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.location_view_layout , parent , false);
        }

        EditText titleEditText = (EditText)convertView. findViewById(R.id.title_edit_text);
        EditText descriptionEditText = (EditText)convertView. findViewById(R.id.description_edit_text);
        EditText dateEditText = (EditText)convertView. findViewById(R.id.date_edit_text);
        EditText latitudeEditText = (EditText)convertView. findViewById(R.id.latitude_edit_text);
        EditText longitudeEditText = (EditText)convertView. findViewById(R.id.longitude_edit_text);

        titleEditText.setText(location.getTitle());
        descriptionEditText.setText(location.getLocationDescription());
        dateEditText.setText(location.getVisitingDate());
        latitudeEditText.setText(location.getLatitude()+"");
        longitudeEditText.setText(location.getLongitude()+"");

        return super.getView(position, convertView, parent);
    }
}

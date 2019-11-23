package com.example.mylocation;

import android.content.Context;
import android.location.Location;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;

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

        TextView titleTextView= (TextView) convertView. findViewById(R.id.title_text_view);
        TextView descriptionTextView = (TextView) convertView. findViewById(R.id.description_text_view);
        TextView dateTextView = (TextView) convertView. findViewById(R.id.date_text_view);
        TextView latitudeTextView = (TextView) convertView. findViewById(R.id.latitude_text_view);
        TextView longitudeTextView = (TextView) convertView. findViewById(R.id.longitude_text_view);

        titleTextView.setText(location.getTitle());
        descriptionTextView.setText(location.getLocationDescription());
        dateTextView.setText(location.getVisitingDate());
        latitudeTextView.setText(location.getLatitude()+"");
        longitudeTextView.setText(location.getLongitude()+"");

        return convertView;
    }
}

package com.example.mylocation;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class LocationObjectAdapter extends ArrayAdapter <LocationObject> {


    public LocationObjectAdapter(@NonNull Context context,  ArrayList<LocationObject> locations) {
        super(context, 0 , locations);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LocationObject location = getItem(position);

        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.location_view_layout , null);
        }

        TextView idTextView=  convertView. findViewById(R.id.id_text_view);
        TextView titleTextView=  convertView. findViewById(R.id.title_text_view);
        TextView descriptionTextView =  convertView. findViewById(R.id.description_text_view);
        TextView dateTextView =  convertView. findViewById(R.id.date_text_view);
        TextView latitudeTextView =  convertView. findViewById(R.id.latitude_text_view);
        TextView longitudeTextView =  convertView. findViewById(R.id.longitude_text_view);
        convertView.setLongClickable(true);
        idTextView.setText(location.getId()+"");
        titleTextView.setText(location.getTitle());
        descriptionTextView.setText(location.getLocationDescription());
        int latestDateIndex = location.getVisitingDate().size() - 1;
        if(latestDateIndex != -1)
            dateTextView.setText(location.getVisitingDate().get(latestDateIndex));
        latitudeTextView.setText(location.getLatitude()+"");
        longitudeTextView.setText(location.getLongitude()+"");

        return convertView;
    }
}

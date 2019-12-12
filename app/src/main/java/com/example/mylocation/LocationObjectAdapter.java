package com.example.mylocation;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class LocationObjectAdapter extends ArrayAdapter <LocationObject> {
    Context context;


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

        TextView titleTextView=  convertView. findViewById(R.id.title_text_view);
        TextView descriptionTextView =  convertView. findViewById(R.id.description_text_view);
        TextView dateTextView =  convertView. findViewById(R.id.date_text_view);
        TextView latitudeTextView =  convertView. findViewById(R.id.latitude_text_view);
        TextView longitudeTextView =  convertView. findViewById(R.id.longitude_text_view);
        ImageView imageView = convertView.findViewById(R.id.imageview);

        convertView.setLongClickable(true);

        titleTextView.setText(location.getTitle());
        descriptionTextView.setText(location.getLocationDescription());
        dateTextView.setText(location.getVisitingDate());

        latitudeTextView.setText(location.getLatitude()+"");
        longitudeTextView.setText(location.getLongitude()+"");

        byte [] imageBytes = location.getLocationImageBytes();
        if(imageBytes.length!=0)
            imageView.setImageBitmap(BitmapConvertorClass.getImage(location.getLocationImageBytes()));

        return convertView;
    }
}

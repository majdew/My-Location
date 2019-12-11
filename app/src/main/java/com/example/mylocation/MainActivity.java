package com.example.mylocation;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import com.google.android.gms.maps.model.LatLng;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    EditText titleEditText , locationDescriptionEditText;
    Button addLocationButton , viewLocationsButton , takeAPictureButton;
    ImageView locationImage;
    LocationManager locationManager;
    double latitude;
    double longitude;
    byte [] locationImageBytes;
    static  int locationId = 1;
    boolean isImageTaken = false;
    SqliteDatabaseAdapter sqliteDatabaseAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Reference the layout component
        titleEditText = findViewById(R.id.location_title_edit_text);
        locationDescriptionEditText = findViewById(R.id.location_description_edit_text);
        addLocationButton = findViewById(R.id.add_location_button);
        viewLocationsButton = findViewById(R.id.view_locations_button);
        takeAPictureButton = findViewById(R.id.take_picture_button);
        locationImage = findViewById(R.id.imageview);

        sqliteDatabaseAdapter = new SqliteDatabaseAdapter(this);


        if (checkLocationPermission()) {
            locationManager = (LocationManager) this.getSystemService(LOCATION_SERVICE);
            LocationListener locationListener = new LocationListener() {
                @Override
                public void onLocationChanged(android.location.Location location) {
                    // Permission already Granted
                    latitude = location.getLatitude();
                    longitude = location.getLongitude();

                }
                @Override
                public void onStatusChanged(String provider, int status, Bundle extras) { }
                @Override
                public void onProviderEnabled(String provider) { }
                @Override
                public void onProviderDisabled(String provider) { }
            };

            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
        }

        // Add location button listener
        addLocationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isImageTaken){
                    String locationTitle = titleEditText.getText().toString();
                    String locationDescription = locationDescriptionEditText.getText().toString();
                    LocationObject location = new LocationObject(latitude ,longitude,locationTitle, locationDescription ,locationImageBytes);
                    if(sqliteDatabaseAdapter.insertLocation(location)) {
                        Toast.makeText(MainActivity.this, "Location added sucssufully", Toast.LENGTH_SHORT).show();
                        locationId++;
                    }
                    isImageTaken = false;
                    float [] results = new float[1];
                    boolean isNearLocation = false;
                    ArrayList <Integer> nearLocationIndicies = new ArrayList<>();
                    if(latitude !=0 && longitude !=0) {
                        for (int i = 0; i < sqliteDatabaseAdapter.getAllLocationsLatLng().size(); i++) {
                            LatLng latLng = sqliteDatabaseAdapter.getAllLocationsLatLng().get(i);
                            if(latLng != null) {
                                Location.distanceBetween(latitude, longitude, latLng.latitude, latLng.longitude, results);
                                if (results[0] < 20) {
                                    isNearLocation =true;
                                    nearLocationIndicies.add(i);

                                }
                            }
                        }
                        boolean isDateInserted = false;
                        if(isNearLocation) {
                            Toast.makeText(MainActivity.this, "You are now close to one of your locations ", Toast.LENGTH_SHORT).show();
                            for (int i = 0; i < nearLocationIndicies.size(); i++) {
                                isDateInserted = (sqliteDatabaseAdapter.insertDate(nearLocationIndicies.get(i)));
                            }
                            if (isDateInserted)
                                Toast.makeText(MainActivity.this, "new date was added ", Toast.LENGTH_SHORT).show();
                        }
                }
                }
                else
                    Toast.makeText(MainActivity.this, "You must take a picture" , Toast.LENGTH_SHORT).show();

            }});

        viewLocationsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this , ViewLocationsActivity.class);
                startActivity(intent);
            }
        });

        takeAPictureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                isImageTaken = true;
                startActivityForResult(intent , 0);
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //Permission Grantnted
                Toast.makeText(this, "Permession Granted You Can add location", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public boolean checkLocationPermission(){
        if (ContextCompat.checkSelfPermission(MainActivity.this , android.Manifest.permission.ACCESS_FINE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(MainActivity.this ,
                        android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            return true;
        }
        else {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
            return  false;
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Bitmap bitmap = (Bitmap) data.getExtras().get("data");
        locationImageBytes = BitmapConvertorClass.getBytes(bitmap);

    }



}

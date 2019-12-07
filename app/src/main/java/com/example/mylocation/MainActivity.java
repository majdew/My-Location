package com.example.mylocation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    EditText titleEditText , locationDescriptionEditText;
    Button addLocationButton , viewLocationsButton;
    LocationManager locationManager;
    double latitude;
    double longitude;
    static  int locationId = 1;
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
                String locationTitle = titleEditText.getText().toString();
                String locationDescription = locationDescriptionEditText.getText().toString();
                LocationObject location = new LocationObject(latitude ,longitude,locationTitle, locationDescription);
                sqliteDatabaseAdapter.insertLocation(location , locationId ++);
            }
        });

        viewLocationsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this , ViewLocationsActivity.class);
                startActivity(intent);
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
}

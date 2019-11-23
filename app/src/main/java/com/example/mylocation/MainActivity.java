package com.example.mylocation;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    EditText titleEditText , locationDescriptionEditText;
    Button addLocationButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Reference the layout component
        titleEditText = findViewById(R.id.location_title_edit_text);
        locationDescriptionEditText = findViewById(R.id.location_description_edit_text);
        addLocationButton = findViewById(R.id.add_location_button);

        // Add location button listener
        addLocationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }
}

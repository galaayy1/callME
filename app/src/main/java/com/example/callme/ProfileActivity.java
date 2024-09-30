package com.example.callme;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class ProfileActivity extends AppCompatActivity {

    private EditText editTextEmergencyNumber;
    private EditText editTextMessage;
    private Button buttonUpdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);  // Link to your activity_profile.xml

        // Initialize the views
        editTextEmergencyNumber = findViewById(R.id.editTextEmergencyNumber);
        editTextMessage = findViewById(R.id.editTextMessage);
        buttonUpdate = findViewById(R.id.buttonUpdate);

        // Set OnClickListener for the update button
        buttonUpdate.setOnClickListener(v -> {
            String emergencyNumber = editTextEmergencyNumber.getText().toString();
            String message = editTextMessage.getText().toString();

            // You can add logic here to save these details, like saving them in SharedPreferences or a database
            // For now, let's just show a Toast
            if (!emergencyNumber.isEmpty() && !message.isEmpty()) {
                Toast.makeText(ProfileActivity.this, "Details updated!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(ProfileActivity.this, "Please fill out all fields!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}

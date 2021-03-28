package com.example.metron;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class addweight extends AppCompatActivity {
    Button save_button;
    EditText height,width;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addweight);

        save_button = findViewById(R.id.save);
        height= findViewById(R.id.edit_height);
        width= findViewById(R.id.edit_width);

        save_button.setOnClickListener(v -> {
            String height_value=height.getText().toString();
            String width_value=width.getText().toString();
            if(!isNumeric(height_value))
            {
                Toast.makeText(getApplicationContext(),"Height in centimeters",Toast.LENGTH_SHORT).show();
            }
            if(!isNumeric(width_value))
            {
                Toast.makeText(getApplicationContext(),"Width give in centimeters",Toast.LENGTH_SHORT).show();
            }
            if(isNumeric(height_value)&&(isNumeric(width_value)))
            {
                finish();
            }
        });
    }
    public static boolean isNumeric(String str) {

        if (str == null || str.length() == 0) {
            return false;
        }

        try {
            Double.parseDouble(str);
            return true;

        } catch (NumberFormatException e) {
            return false;
        }

    }
}
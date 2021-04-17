package com.example.metron;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageButton button = (ImageButton) findViewById (R.id.CameraOpen);

        ImageButton btn = (ImageButton) findViewById (R.id.ReferenceAddition);
        button.setOnClickListener(v -> openActivity2());

        btn.setOnClickListener(v -> openreferencepage());
    }
    public  void openActivity2()
    {
        //For opening camera
        Intent intent = new Intent (this,Page2.class);
        startActivity (intent);

    }

    public void openreferencepage()
    {
        Intent intent= new Intent(this,page3.class);
        startActivity(intent);
    }

}
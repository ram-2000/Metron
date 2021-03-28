package com.example.metron;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class page3 extends AppCompatActivity {
    Button addWeights_button,addObject_button,back_button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page3);

        back_button =(Button) findViewById(R.id.back);
        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        addWeights_button =(Button)findViewById(R.id.addWeights);
        addWeights_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                start_addweight();
            }
        });
    }
    public void start_addweight()
    {
        Intent intent =new Intent(this,addweight.class);
        startActivity(intent);
    }
}
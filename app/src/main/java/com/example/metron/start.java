package com.example.metron;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

public class start extends AppCompatActivity {
    TextView txt,txt1;
    String words="Made 2 Measure";
    int i=0;
    char[] ch = new char[words.length()];
    int n= words.length();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        txt =findViewById(R.id.made2measure);
        final Handler handler = new Handler();
        for (int i = 0; i < n; i++)
        {
            ch[i] = words.charAt(i);
        }
        handler.post(new Runnable() {
            @Override
            public void run() {

                txt.setText(txt.getText().toString()+ch[i]);
                i++;
                if(i < words.length()) {
                    handler.postDelayed(this, 250);
                }
                else {
                    Intent i = new Intent(start.this, MainActivity.class);
                    startActivity(i);
                    finish();

                }
            }
        });
    }
}
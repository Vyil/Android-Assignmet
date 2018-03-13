package com.example.djim.marsrover.controllers;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.djim.marsrover.R;

/**
 * Created by Djim on 13-3-2018.
 */

public class DetailScreen extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstances){
        super.onCreate(savedInstances);
        setContentView(R.layout.detail_screen);

        Bundle extras = getIntent().getExtras();

        ImageView image = findViewById(R.id.detailed_mars_image);
        TextView textView = findViewById(R.id.detailed_mars_text);

        textView.setText("Dummy text");

        this.setTitle("DummyTitle");
    }
}

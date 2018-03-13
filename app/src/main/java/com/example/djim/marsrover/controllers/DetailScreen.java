package com.example.djim.marsrover.controllers;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.djim.marsrover.R;
import com.example.djim.marsrover.api.RoverReceiver;
import com.example.djim.marsrover.domain.RoverCollection;
import com.squareup.picasso.Picasso;

/**
 * Created by Djim on 13-3-2018.
 */

public class DetailScreen extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstances){
        super.onCreate(savedInstances);
        setContentView(R.layout.detail_screen);

        //Hide navbar untill tapped on screen
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);

        //Get extras from intent
        Bundle extras = getIntent().getExtras();
        RoverCollection roverCollection = (RoverCollection) extras.getSerializable("ID");

        ImageView image = findViewById(R.id.detailed_mars_image);
        TextView textView = findViewById(R.id.detailed_mars_text);

        textView.setText(roverCollection.getCameraName());

        Picasso.with(this).load(roverCollection.getImageURL()).into(image);
    }
}

package com.example.djim.marsrover.controllers;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.djim.marsrover.api.RoverReceiver;
import com.example.djim.marsrover.api.onRoverAvailable;
import com.example.djim.marsrover.domain.RoverCollection;
import com.example.djim.marsrover.utilities.MyAdapter;
import com.example.djim.marsrover.R;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements onRoverAvailable {

    private static final String TAG = "MainActivity";

    private RecyclerView recyclerView;
    private RecyclerView.Adapter recyclerAdapter;
    private RecyclerView.LayoutManager recyclerLayoutManager;
    private ArrayList<RoverCollection> roverList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        roverList = new ArrayList<>();
//        for(int i=0; i<10; i++){
//            roverList.add(new RoverCollection(Integer.toString(i),"Testname","non"));
//        }

        String[] params = {"https://api.nasa.gov/mars-photos/api/v1/rovers/curiosity/photos?sol=1000&api_key=cSZD57fGsdMoUQQHYx1GDlE7oitW03FyzMae43H6"};
        RoverReceiver roverReceiver = new RoverReceiver(this);
        roverReceiver.execute(params);

        recyclerView = findViewById(R.id.recycler_view);
        recyclerLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(recyclerLayoutManager);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);

        //Use the adapter
        recyclerAdapter = new MyAdapter(this, roverList);
        recyclerView.setAdapter(recyclerAdapter);


    }

    @Override
    public void onRoverAvailable(RoverCollection rover) {

        roverList.add(rover);
        recyclerAdapter.notifyDataSetChanged();
    }
}

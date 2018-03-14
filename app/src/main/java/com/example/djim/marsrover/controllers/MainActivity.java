package com.example.djim.marsrover.controllers;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.djim.marsrover.api.RoverReceiver;
import com.example.djim.marsrover.api.onRoverAvailable;
import com.example.djim.marsrover.domain.RoverCollection;
import com.example.djim.marsrover.utilities.MyAdapter;
import com.example.djim.marsrover.R;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements onRoverAvailable, AdapterView.OnItemSelectedListener{

    private static final String TAG = "MainActivity";

    private RecyclerView recyclerView;
    private RecyclerView.Adapter recyclerAdapter;
    private RecyclerView.LayoutManager recyclerLayoutManager;
    private ArrayList<RoverCollection> roverList;
    private Spinner spinner;

    private int counter= 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.i(TAG,"onCreate called");

        if(savedInstanceState != null){
            roverList = (ArrayList<RoverCollection>)savedInstanceState.getSerializable("key");
        } else {
            roverList = new ArrayList<>();
        }

        getAllCameras();

        createSpinner();

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
    public void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);
        outState.putSerializable("key", roverList);
        outState.putInt("spinner",spinner.getSelectedItemPosition());
        Log.i(TAG,"onSaveInstanceState called");
    }

    @Override
    public void onRoverAvailable(RoverCollection rover) {
        Log.i(TAG,"onRoverAvailable called");
        roverList.add(rover);
        recyclerAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        roverList = (ArrayList<RoverCollection>) savedInstanceState.getSerializable("key");
        Log.i(TAG,"onRestoreInstanceState called");
    }

    public void createSpinner(){
        Log.i(TAG,"createSpinner called");
        spinner = findViewById(R.id.spinner);


        ArrayList<String> cameras = new ArrayList<>();
        cameras.add("All Cameras");
        cameras.add("FHAZ");
        cameras.add("RHAZ");
        cameras.add("MAST");
        cameras.add("CHEMCAM");
        cameras.add("MAHLI");
        cameras.add("MARDI");
        cameras.add("NAVCAM");

        ArrayAdapter<String> adapter = new ArrayAdapter<>
                (this,R.layout.support_simple_spinner_dropdown_item,cameras);
        spinner.setAdapter(adapter);
        spinner.setSelected(false);
        spinner.setSelection(0,true);
        spinner.setOnItemSelectedListener(this);
    }


//    @Override
//    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//        // Prevent triggering onItemSelected onCreate
//        if (++counter > 1) {
//            // Get Camera item from Spinner Menu
//            item = spinner.getItemAtPosition(position).toString();
//
//            // Get Photos by Camera and Date
//            getSpecificCamera(item);
//        }
//    }


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
        Log.i(TAG,"onItemSelected called");
        if(++counter>1) {
            Log.i(TAG,"Switch cases enabled");
            switch (position) {
                case 0:
                    emptyArray();
                    getAllCameras();
                    break;
                case 1:
                    emptyArray();
                    getSpecificCamera("fhaz");
                    break;
                case 2:
                    emptyArray();
                    getSpecificCamera("rhaz");
                    break;
                case 3:
                    emptyArray();
                    getSpecificCamera("mast");
                    break;
                case 4:
                    emptyArray();
                    getSpecificCamera("chemcam");
                    break;
                case 5:
                    emptyArray();
                    getSpecificCamera("mahli");
                    break;
                case 6:
                    emptyArray();
                    getSpecificCamera("mardi");
                    break;
                case 7:
                    emptyArray();
                    getSpecificCamera("navcam");
                    break;
            }
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    public void emptyArray(){
        Log.i(TAG,"emptyArray called");
        roverList.clear();
        recyclerAdapter.notifyDataSetChanged();
    }

    public void getAllCameras(){
        Log.i(TAG,"getAllCameras called");
        String[] params = {"https://api.nasa.gov/mars-photos/api/v1/rovers/curiosity/photos?sol=1000&api_key=cSZD57fGsdMoUQQHYx1GDlE7oitW03FyzMae43H6"};
        RoverReceiver roverReceiver = new RoverReceiver(this);
        roverReceiver.execute(params);
    }

    public void getSpecificCamera(String camera){
        Log.i(TAG,"getSpecificCamera called");
        String[] params = {"https://api.nasa.gov/mars-photos/api/v1/rovers/curiosity/photos?sol=1000&camera="+camera+"&api_key=cSZD57fGsdMoUQQHYx1GDlE7oitW03FyzMae43H6"};
        RoverReceiver roverReceiver = new RoverReceiver(this);
        roverReceiver.execute(params);
    }
}

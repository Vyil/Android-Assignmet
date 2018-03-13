package com.example.djim.marsrover;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private RecyclerView recyclerView;
    private RecyclerView.Adapter recyclerAdapter;
    private RecyclerView.LayoutManager recyclerLayoutManager;
    private ArrayList<String> testList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        testList = new ArrayList<>();
        for(int i=0; i<10; i++){
            testList.add(Integer.toString(i));
        }

        recyclerView = findViewById(R.id.recycler_view);
        recyclerAdapter = new MyAdapter(this,testList);
        recyclerView.setAdapter(recyclerAdapter);
    }
}

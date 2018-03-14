package com.example.djim.marsrover.utilities;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.djim.marsrover.R;
import com.example.djim.marsrover.controllers.DetailScreen;
import com.example.djim.marsrover.domain.RoverCollection;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Djim on 13-3-2018.
 */

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    private static final String TAG= "MyAdapter";
    private Context context;
    private ArrayList<RoverCollection> dataArray;

    //Viewholder to remove findViewById performance pressure
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        //The view custom_list_row
        private View view;

        //The views inside the custom view
        private ImageView imageView;
        private TextView textView;


        public ViewHolder(View v){
            super(v);
            this.view = v;
            this.view.setOnClickListener(this);

            this.imageView = v.findViewById(R.id.listImage);
            this.textView = v.findViewById(R.id.listTextView);
        }

        @Override
        public void onClick(View view) {
            //Log.i(TAG,"onClick called");
            int position = getAdapterPosition();
            RoverCollection roverCollection = (RoverCollection) dataArray.get(position);

            Intent marsDetail = new Intent(view.getContext().getApplicationContext(),
                    DetailScreen.class);
            marsDetail.putExtra("ID", roverCollection);

            view.getContext().startActivity(marsDetail);
        }
    }

    //Default constructor
    public MyAdapter(Context context, ArrayList<RoverCollection> data){
        this.context = context;
        this.dataArray = data;
    }

    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        //Inflate a new view
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.custom_list_row,parent,false);

        ViewHolder vh = new ViewHolder(view);
        return vh;
    }

    public void onBindViewHolder(ViewHolder holder, int position){

        RoverCollection roverCollection = dataArray.get(position);
        holder.textView.setText("Image ID : " + roverCollection.getId());

        Picasso.with(context).load(roverCollection.getImageURL()).into(holder.imageView);
    }

    @Override
    public int getItemCount(){
        return dataArray.size();
    }
}

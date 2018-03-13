package com.example.djim.marsrover;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Djim on 13-3-2018.
 */

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    private Context context;
    private ArrayList<String> imageNames;

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
            int position = getAdapterPosition();

        }
    }

    //Default constructor
    public MyAdapter(Context context, ArrayList<String> data){
        this.context = context;
        this.imageNames = data;
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

    }

    @Override
    public int getItemCount(){
        return imageNames.size();
    }
}

package com.example.book_my_pg;

import android.content.Context;

import android.content.Intent;
import android.view.LayoutInflater;

import android.view.View;

import android.view.ViewGroup;

import android.widget.TextView;

import androidx.annotation.NonNull;

import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;

import com.firebase.ui.database.FirebaseRecyclerOptions;


public class notifyAdapter extends FirebaseRecyclerAdapter<userDetails, notifyAdapter.personsViewholder> {

    public notifyAdapter(@NonNull FirebaseRecyclerOptions<userDetails> options)
    {
        super(options);
    }


    @Override

    protected void onBindViewHolder(@NonNull personsViewholder holder, int position, @NonNull userDetails model)
    {
        holder.name.setText(model.getPgname());
        holder.add.setText(model.getPgadd());
        holder.city.setText(model.getCity());
        holder.ph.setText (model.getPgph());
    }


    @NonNull
    @Override
    public personsViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.notify, parent, false);
        return new notifyAdapter.personsViewholder(view);
    }

    class personsViewholder extends RecyclerView.ViewHolder {
        TextView name, add,city,ph;
        public personsViewholder(@NonNull View itemView)
        {
            super(itemView);
            name = itemView.findViewById(R.id.pgName);
            add = itemView.findViewById(R.id.add);
            city = itemView.findViewById(R.id.city);
            ph=itemView.findViewById(R.id.phoneno);
        }
    }
}
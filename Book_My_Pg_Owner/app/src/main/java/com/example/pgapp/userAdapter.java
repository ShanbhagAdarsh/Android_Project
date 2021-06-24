package com.example.pgapp;

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


public class userAdapter extends FirebaseRecyclerAdapter<user, userAdapter.personsViewholder> {

    public userAdapter(@NonNull FirebaseRecyclerOptions<user> options)
    {
        super(options);
    }


    @Override

    protected void onBindViewHolder(@NonNull personsViewholder holder, int position, @NonNull user model)
    {
        holder.username.setText(model.getUname());
        holder.usermobileno.setText(model.getPhoneno());
        holder.bookedroom.setText(model.getRoomno());
    }


    @NonNull
    @Override
    public personsViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user, parent, false);
        return new userAdapter.personsViewholder(view);
    }

    class personsViewholder extends RecyclerView.ViewHolder {
        TextView username, usermobileno,bookedroom;
        public personsViewholder(@NonNull View itemView)
        {
            super(itemView);
            username = itemView.findViewById(R.id.bookedusername);
            usermobileno = itemView.findViewById(R.id.bookedusermobileno);
            bookedroom = itemView.findViewById(R.id.bookedroomno);
            itemView.setOnClickListener((new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    String str = bookedroom.getText().toString();

                    Intent intent = new Intent(view.getContext(), DisplayUserActivity.class);

                    intent.putExtra("message_key",str);

                    view.getContext().startActivity(intent);
                }
            }));

        }
    }
}

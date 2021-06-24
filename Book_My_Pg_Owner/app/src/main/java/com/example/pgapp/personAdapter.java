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




public class personAdapter extends FirebaseRecyclerAdapter<RoomProfile, personAdapter.personsViewholder> {



    public personAdapter(@NonNull FirebaseRecyclerOptions<RoomProfile> options)
    {
        super(options);
    }



    @Override

    protected void onBindViewHolder(@NonNull personsViewholder holder, int position, @NonNull RoomProfile model)
    {
        holder.firstname.setText(model.getPgROOMTYPE());
        holder.lastname.setText(model.getPgROOMNO());
        holder.age.setText(model.getPgROOMAMOUNT());

    }


    @NonNull
    @Override
    public personsViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.person, parent, false);
        return new personAdapter.personsViewholder(view);
    }




    class personsViewholder extends RecyclerView.ViewHolder {
        TextView firstname, lastname, age;
        public personsViewholder(@NonNull View itemView)
        {
            super(itemView);
            firstname = itemView.findViewById(R.id.rtyp);
            lastname = itemView.findViewById(R.id.rno);
            age = itemView.findViewById(R.id.amt);
            itemView.setOnClickListener((new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    String str = lastname.getText().toString();

                    Intent intent = new Intent(view.getContext(), Roomdetails.class);

                    intent.putExtra("message_key",str);

                    view.getContext().startActivity(intent);

                }
            }));

        }
    }
}

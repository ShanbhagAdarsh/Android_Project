package com.example.book_my_pg;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.book_my_pg.profile1;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;


public class roomAdapter extends FirebaseRecyclerAdapter<RoomProfile, roomAdapter.roomViewholder> {

    public roomAdapter(@NonNull FirebaseRecyclerOptions<RoomProfile> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull roomViewholder holder, int position, @NonNull RoomProfile model)
    {
        holder.name.setText(model.getpgName());
        holder.num.setText(model.getPgROOMNO());
        holder.type.setText(model.getPgROOMTYPE());
    }

    @NonNull
    @Override
    public roomViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.room, parent, false);
        return new roomAdapter.roomViewholder(view);
    }

    class roomViewholder extends RecyclerView.ViewHolder {
        TextView num, type,name;
        public roomViewholder(@NonNull View itemView)
        {
            super(itemView);
            num = itemView.findViewById(R.id.roomNo);
            type = itemView.findViewById(R.id.roomType);
            name=itemView.findViewById(R.id.pgName);
            itemView.setOnClickListener((new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(view.getContext(), RoomDetails.class);
                    String str1 = name.getText().toString();
                    String str2= num.getText().toString();
                    intent.putExtra("str1",str1);
                    intent.putExtra("str2",str2);
                    intent.putExtras(intent);
                    view.getContext().startActivity(intent);
                }
            }));
        }
    }
}
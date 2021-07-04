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


public class personAdapter extends FirebaseRecyclerAdapter<PGprofile, personAdapter.personsViewholder> {

    public personAdapter(@NonNull FirebaseRecyclerOptions<PGprofile> options)
    {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull personsViewholder holder, int position, @NonNull PGprofile model)
    {
        holder.PGName.setText(model.getPgNAME());
        holder.PGAdd.setText(model.getPgLOCALADDRESS());
        holder.PGCity.setText(model.getPgCITY());
    }

    @NonNull
    @Override
    public personsViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.person, parent, false);
        return new personAdapter.personsViewholder(view);
    }

    class personsViewholder extends RecyclerView.ViewHolder {
        TextView PGName, PGAdd, PGCity;
        public personsViewholder(@NonNull View itemView)
        {
            super(itemView);
            PGName = itemView.findViewById(R.id.pgName);
            PGAdd = itemView.findViewById(R.id.add);
            PGCity = itemView.findViewById(R.id.city);
            itemView.setOnClickListener((new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(view.getContext(), DisplayActivity.class);
                    String str1 = PGCity.getText().toString();
                    String str2= PGName.getText().toString();
                    intent.putExtra("str1",str1);
                    intent.putExtra("str2",str2);
                    intent.putExtras(intent);
                    view.getContext().startActivity(intent);
                }
            }));

        }
    }
}
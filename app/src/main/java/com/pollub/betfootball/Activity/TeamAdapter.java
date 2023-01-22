
package com.pollub.betfootball.Activity;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.pollub.betfootball.Entity.TeamUsers;
import com.pollub.betfootball.R;


// FirebaseRecyclerAdapter is a class provided by
// FirebaseUI. it provides functions to bind, adapt and show
// database contents in a Recycler View

public class TeamAdapter extends FirebaseRecyclerAdapter<

        TeamUsers, TeamAdapter.teamViewholder> {


    public TeamAdapter(

            @NonNull FirebaseRecyclerOptions<TeamUsers> options) {

        super(options);

    }


    // Function to bind the view in Card view(here

    // "person.xml") iwth data in

    // model class(here "person.class")

    @Override

    protected void

    onBindViewHolder(@NonNull teamViewholder holder, int position, @NonNull TeamUsers model) {


        // Add firstname from model class (here

        // "person.class")to appropriate view in Card

        // view (here "person.xml")

        holder.teamName.setText(model.getTeamName());
        holder.teamName.setOnClickListener(new View.OnClickListener() {

            @SuppressLint("RestrictedApi")
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(AuthUI.getApplicationContext(), TeamActivity.class);
                myIntent.putExtra("key", model.getTeamID()); //Optional parameters
                myIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                AuthUI.getApplicationContext().startActivity(myIntent);
            }


        });

    }

    @NonNull

    @Override

    public teamViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view

                = LayoutInflater.from(parent.getContext())

                .inflate(R.layout.item, parent, false);

        return new TeamAdapter.teamViewholder(view);
    }


    // Sub Class to create references of the views in Crad

    // view (here "person.xml")

    class teamViewholder

            extends RecyclerView.ViewHolder {

        Button teamName;

        public teamViewholder(@NonNull View itemView) {

            super(itemView);


            teamName = itemView.findViewById(R.id.teamName);

        }

    }
}

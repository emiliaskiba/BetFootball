
package com.pollub.betfootball.Activity;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.pollub.betfootball.Entity.Team;
import com.pollub.betfootball.R;


// FirebaseRecyclerAdapter is a class provided by
// FirebaseUI. it provides functions to bind, adapt and show
// database contents in a Recycler View

public class TeamAdapter extends FirebaseRecyclerAdapter<

        Team, TeamAdapter.teamViewholder> {



    public TeamAdapter(

            @NonNull FirebaseRecyclerOptions<Team> options)

    {

        super(options);

    }



    // Function to bind the view in Card view(here

    // "person.xml") iwth data in

    // model class(here "person.class")

    @Override

    protected void

    onBindViewHolder(@NonNull teamViewholder holder, int position, @NonNull Team model) {


        // Add firstname from model class (here

        // "person.class")to appropriate view in Card

        // view (here "person.xml")

        holder.teamName.setText(model.getName());


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

        public teamViewholder(@NonNull View itemView)

        {

            super(itemView);


            teamName = itemView.findViewById(R.id.teamName);

        }

    }
}

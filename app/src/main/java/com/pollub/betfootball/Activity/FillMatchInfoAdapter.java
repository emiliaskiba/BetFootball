
package com.pollub.betfootball.Activity;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.pollub.betfootball.Entity.Match;
import com.pollub.betfootball.R;

import java.util.Objects;


// FirebaseRecyclerAdapter is a class provided by
// FirebaseUI. it provides functions to bind, adapt and show
// database contents in a Recycler View

public class FillMatchInfoAdapter extends FirebaseRecyclerAdapter<

        Match, FillMatchInfoAdapter.FillMatchInfoViewholder> {

    public FillMatchInfoAdapter(

            @NonNull FirebaseRecyclerOptions<Match> options) {

        super(options);

    }


    // Function to bind the view in Card view(here

    // "person.xml") iwth data in

    // model class(here "person.class")

    @Override

    protected void

    onBindViewHolder(@NonNull FillMatchInfoViewholder holder, int position, @NonNull Match model) {


        // Add firstname from model class (here

        // "person.class")to appropriate view in Card

        // view (here "person.xml")
        //final String parentKey = null;
        DatabaseReference reference;
        reference = FirebaseDatabase.getInstance().getReference().child("Teams");
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String abc = (String) snapshot.getValue();

                    if (Integer.valueOf(snapshot.getKey()) == Integer.valueOf(model.getTeamOneID())) {
                        holder.teamOne.setText(abc);

                    }
                    ;

                    if (Integer.valueOf(snapshot.getKey()) == Integer.valueOf(model.getTeamTwoID())) {
                        holder.teamTwo.setText(abc);

                    }
                    ;

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
        Query reference1;
        reference1 = FirebaseDatabase.getInstance().getReference().child("Match");
        reference1.addListenerForSingleValueEvent(new ValueEventListener() {
                                                      @Override
                                                      public void onDataChange(DataSnapshot dataSnapshot) {
                                                          for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                                              Match match = snapshot.getValue(Match.class);
                                                              if (Objects.equals(model.getTeamOneID(), match.teamOneID) && Objects.equals(model.getTeamTwoID(), match.teamTwoID)) {
                                                                  final String parentKey = snapshot.getKey();

                                                                  holder.matchDay.setOnClickListener(new View.OnClickListener() {

                                                                      @SuppressLint("RestrictedApi")
                                                                      @Override
                                                                      public void onClick(View v) {
                                                                          Intent myIntent = new Intent(AuthUI.getApplicationContext(), AdminFillInfoMatch.class);
                                                                          myIntent.putExtra("key", parentKey); //Optional parameters
                                                                          myIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                                                          AuthUI.getApplicationContext().startActivity(myIntent);
                                                                      }


                                                                  });
                                                              }
                                                          }
                                                      }

                                                      @Override
                                                      public void onCancelled(DatabaseError databaseError) {
                                                      }
                                                  }
        );



    }

    @NonNull

    @Override

    public FillMatchInfoViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view

                = LayoutInflater.from(parent.getContext())

                .inflate(R.layout.item_fill_match_info, parent, false);

        return new FillMatchInfoAdapter.FillMatchInfoViewholder(view);
    }


    // Sub Class to create references of the views in Crad

    // view (here "person.xml")

    class FillMatchInfoViewholder

            extends RecyclerView.ViewHolder {

        Button matchDay;
        TextView teamOne, teamTwo;

        private final Context context;

        public FillMatchInfoViewholder(@NonNull View itemView) {

            super(itemView);

            matchDay = itemView.findViewById(R.id.matchID);
            teamOne = itemView.findViewById(R.id.teamOne);
            teamTwo = itemView.findViewById(R.id.teamTwo);

            context = itemView.getContext();

           /* matchDay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent myIntent = new Intent(context, AdminFillInfoMatch.class);
                    myIntent.putExtra("key", "1" ); //Optional parameters
                    context.startActivity(myIntent);
                }
            });*/
        }

    }


}

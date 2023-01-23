
package com.pollub.betfootball.Activity;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.pollub.betfootball.Entity.Bet;
import com.pollub.betfootball.Entity.Match;
import com.pollub.betfootball.R;

import java.util.Objects;


// FirebaseRecyclerAdapter is a class provided by
// FirebaseUI. it provides functions to bind, adapt and show
// database contents in a Recycler View

public class ResultsAdapter extends FirebaseRecyclerAdapter<

        Bet, ResultsAdapter.ResultsViewholder> {

    public ResultsAdapter(

            @NonNull FirebaseRecyclerOptions<Bet> options) {

        super(options);

    }


    // Function to bind the view in Card view(here

    // "person.xml") iwth data in

    // model class(here "person.class")

    @Override

    protected void

    onBindViewHolder(@NonNull ResultsViewholder holder, int position, @NonNull Bet model) {


        // Add firstname from model class (here

        // "person.class")to appropriate view in Card

        // view (here "person.xml")
        //final String parentKey = null;

        DatabaseReference reference;
        reference = FirebaseDatabase.getInstance().getReference().child("Match");
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String parentKey = snapshot.getKey();
                    Match match = snapshot.getValue(Match.class);
                    if(Objects.equals(parentKey,model.matchID )){

                        DatabaseReference reference;
                        reference = FirebaseDatabase.getInstance().getReference().child("Teams");
                        reference.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot idataSnapshot) {
                                for (DataSnapshot isnapshot : idataSnapshot.getChildren()) {
                                    String abc = (String) isnapshot.getValue();

                                    if (Integer.valueOf(isnapshot.getKey()).equals(Integer.valueOf(match.teamOneID))) {
                                       holder.matchButton.setText(abc + " - ");

                                    }

                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {
                            }
                        });

                    }

                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        reference = FirebaseDatabase.getInstance().getReference().child("Match");
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String parentKey = snapshot.getKey();
                    Match match = snapshot.getValue(Match.class);
                    if(Objects.equals(parentKey,model.matchID )){

                        DatabaseReference reference;
                        reference = FirebaseDatabase.getInstance().getReference().child("Teams");
                        reference.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot idataSnapshot) {
                                for (DataSnapshot isnapshot : idataSnapshot.getChildren()) {
                                    String abc = (String) isnapshot.getValue();

                                    if (Integer.valueOf(isnapshot.getKey()).equals(Integer.valueOf(match.teamTwoID))) {

                                        String temp = String.valueOf(holder.matchButton.getText());
                                        holder.matchButton.setText(temp + abc);
                                    }

                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {
                            }
                        });

                    }

                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });


        holder.score.setText(String.valueOf(model.score));



    }

    @NonNull

    @Override

    public ResultsViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view

                = LayoutInflater.from(parent.getContext())

                .inflate(R.layout.item_results, parent, false);

        return new ResultsAdapter.ResultsViewholder(view);
    }


    // Sub Class to create references of the views in Crad

    // view (here "person.xml")

    class ResultsViewholder

            extends RecyclerView.ViewHolder {

        TextView score, matchButton;

        //private final Context context;

        public ResultsViewholder(@NonNull View itemView) {

            super(itemView);

            score = itemView.findViewById(R.id.score);
            matchButton = itemView.findViewById(R.id.match);

            //context = itemView.getContext();

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


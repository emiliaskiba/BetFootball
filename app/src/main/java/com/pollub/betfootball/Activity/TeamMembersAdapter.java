
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
import com.pollub.betfootball.Entity.TeamUsers;
import com.pollub.betfootball.Entity.User;
import com.pollub.betfootball.R;

import java.util.Objects;


// FirebaseRecyclerAdapter is a class provided by
// FirebaseUI. it provides functions to bind, adapt and show
// database contents in a Recycler View

public class TeamMembersAdapter extends FirebaseRecyclerAdapter<

        TeamUsers, TeamMembersAdapter.TeamMembersViewholder> {

    public TeamMembersAdapter(

            @NonNull FirebaseRecyclerOptions<TeamUsers> options) {

        super(options);

    }


    // Function to bind the view in Card view(here

    // "person.xml") iwth data in

    // model class(here "person.class")

    @Override

    protected void

    onBindViewHolder(@NonNull TeamMembersViewholder holder, int position, @NonNull TeamUsers model) {


        // Add firstname from model class (here

        // "person.class")to appropriate view in Card

        // view (here "person.xml")
        //final String parentKey = null;


        DatabaseReference reference;
        reference = FirebaseDatabase.getInstance().getReference().child("Users");
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                    if (Objects.equals(snapshot.getKey(), model.userID)) {
                        //System.out.println("!!!!!key" + snapshot.getKey());
                       // System.out.println("!!!!!modeluser" + model.userID);
                        User user = snapshot.getValue(User.class);
                       // System.out.println("!!!!!userfullname" + user.fullName);
                        holder.teamMemberName.setText(user.fullName);
                        holder.scoreMatchday.setText(String.valueOf(user.scoreMatchDay));
                        holder.scoreAllSeason.setText(String.valueOf(user.scoreAllSeason));
                    }
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });



    }

    @NonNull

    @Override

    public TeamMembersViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view

                = LayoutInflater.from(parent.getContext())

                .inflate(R.layout.item_team_members, parent, false);

        return new TeamMembersAdapter.TeamMembersViewholder(view);
    }


    // Sub Class to create references of the views in Crad

    // view (here "person.xml")

    class TeamMembersViewholder

            extends RecyclerView.ViewHolder {

        TextView scoreAllSeason, scoreMatchday, teamMemberName;

        //private final Context context;

        public TeamMembersViewholder(@NonNull View itemView) {

            super(itemView);

            scoreAllSeason = itemView.findViewById(R.id.scoreAllSeason);
            scoreMatchday = itemView.findViewById(R.id.scoreMatchday);
            teamMemberName = itemView.findViewById(R.id.teamMemberName);

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

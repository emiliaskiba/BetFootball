package com.pollub.betfootball.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.pollub.betfootball.Entity.Bet;
import com.pollub.betfootball.Entity.User;
import com.pollub.betfootball.R;

public class Results extends AppCompatActivity {

    private ImageView back;
    private RecyclerView recyclerView;
    ResultsAdapter adapter; // Create Object of the Adapter class
    Query mbase, mbase1;
    private FirebaseUser user;
    private String userID;
    private TextView seasonScore, matchdayScore;
    private DatabaseReference database;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Results.this, HomePage.class));
            }
        });

        seasonScore = findViewById(R.id.seasonScore);
        matchdayScore = findViewById(R.id.matchdayScore);

        user = FirebaseAuth.getInstance().getCurrentUser();
        userID = user.getUid();

        mbase = FirebaseDatabase.getInstance().getReference().child("Bets").orderByChild("userID").equalTo(userID);

        recyclerView = findViewById(R.id.resultsList);
        // To display the Recycler view linearly
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        FirebaseRecyclerOptions<Bet> options = new FirebaseRecyclerOptions.Builder<Bet>().setQuery(mbase, Bet.class).build();
        // the Adapter class itself

        adapter = new ResultsAdapter(options);

        // Connecting Adapter class with the Recycler view*/

        recyclerView.setAdapter(adapter);

        database = FirebaseDatabase.getInstance().getReference().child("Users").child(userID);
        database.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                seasonScore.setText(String.valueOf(user.scoreAllSeason));
                matchdayScore.setText(String.valueOf(user.scoreMatchDay));
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }


    // Function to tell the app to stop getting
    // data from database on stopping of the activity

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }
}
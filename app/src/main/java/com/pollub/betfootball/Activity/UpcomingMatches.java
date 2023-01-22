package com.pollub.betfootball.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.pollub.betfootball.Entity.Match;
import com.pollub.betfootball.R;

public class UpcomingMatches extends AppCompatActivity {

    private RecyclerView recyclerView;
    MatchAdapter adapter; // Create Object of the Adapter class
    Query mbase, mbase1;
    ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upcoming_matches);


        mbase = FirebaseDatabase.getInstance().getReference().child("Match").orderByChild("happened").equalTo(false);

        recyclerView = findViewById(R.id.matchList);
        // To display the Recycler view linearly
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        FirebaseRecyclerOptions<Match> options = new FirebaseRecyclerOptions.Builder<Match>().setQuery(mbase, Match.class).build();
        // the Adapter class itself

        adapter = new MatchAdapter(options);

        // Connecting Adapter class with the Recycler view*/

        recyclerView.setAdapter(adapter);

        back = (ImageView) findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UpcomingMatches.this, HomePage.class));
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
package com.pollub.betfootball.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.pollub.betfootball.Entity.Bet;
import com.pollub.betfootball.Entity.Match;
import com.pollub.betfootball.R;

import java.util.Objects;


public class PlaceBet extends AppCompatActivity implements View.OnClickListener {

    private TextView matchView, teamOne, teamTwo, placed;
    private ImageView back;
    private EditText teamOneScoreEdit, teamTwoScoreEdit;
    private Button placebet;
    private DatabaseReference reference, referenceTeam1, referenceTeam2, referenceBets;
    private String value;
    private Integer teamOneScore;
    private Integer teamTwoScore;
    private FirebaseUser user;
    private String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent intent = getIntent();
        value = intent.getStringExtra("key"); //if it's a string you stored.
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_bet);

        placed = findViewById(R.id.PLACED);
        user = FirebaseAuth.getInstance().getCurrentUser();
        userID = user.getUid();

        referenceBets = FirebaseDatabase.getInstance().getReference("Bets");
        referenceBets.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Bet bet = snapshot.getValue(Bet.class);
                    if (Objects.equals(value, bet.matchID) && Objects.equals(userID, bet.userID)) {
                        teamOne.setVisibility(View.GONE);
                        teamTwo.setVisibility(View.GONE);
                        teamOneScoreEdit.setVisibility(View.GONE);
                        teamTwoScoreEdit.setVisibility(View.GONE);
                        placebet.setVisibility(View.GONE);

                        placed.setText(R.string.placed);
                        placed.setVisibility(View.VISIBLE);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(PlaceBet.this, "Can't load. Make sure your connection is stable", Toast.LENGTH_LONG).show();
            }
        });

        back = findViewById(R.id.back);
        back.setOnClickListener(this);

        placebet = findViewById(R.id.placebet);
        placebet.setOnClickListener(this);

        teamOneScoreEdit = findViewById(R.id.teamOneScoreEdit);
        teamTwoScoreEdit = findViewById(R.id.teamTwoScoreEdit);

        teamTwo = findViewById(R.id.teamTwo);
        teamOne = findViewById(R.id.teamOne);
        matchView = findViewById(R.id.match);




        reference = FirebaseDatabase.getInstance().getReference("Match");
        reference.child(value).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Match match = snapshot.getValue(Match.class);
                Integer teamOneID = match.teamOneID;
                Integer teamTwoID = match.teamTwoID;

                referenceTeam1 = FirebaseDatabase.getInstance().getReference("Teams");
                referenceTeam1.child(String.valueOf(teamOneID)).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String team1 = String.valueOf(snapshot.getValue());

                        teamOne.setText(team1);
                        matchView.setText(team1 + " - ");
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(PlaceBet.this, "Can't load. Make sure your connection is stable", Toast.LENGTH_LONG).show();

                    }
                });



                referenceTeam2 = FirebaseDatabase.getInstance().getReference("Teams");
                referenceTeam2.child(String.valueOf(teamTwoID)).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String team2 = String.valueOf(snapshot.getValue());
                        teamTwo.setText(team2);
                        String temp = matchView.getText().toString();
                        matchView.setText(temp + team2);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(PlaceBet.this, "Can't load. Make sure your connection is stable", Toast.LENGTH_LONG).show();

                    }
                });

            }



            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(PlaceBet.this, "Can't load. Make sure your connection is stable", Toast.LENGTH_LONG).show();
            }
        });



    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                startActivity(new Intent(this, UpcomingMatches.class));
                break;
            case R.id.placebet:
                teamOneScore = Integer.valueOf(teamOneScoreEdit.getText().toString());
                teamTwoScore = Integer.valueOf(teamTwoScoreEdit.getText().toString());
                placeBet(value, teamOneScore, teamTwoScore);
                break;
        }
    }

    //String userID, String matchID, Integer teamOneScore, Integer teamTwoScore


    private void placeBet(String matchID, Integer teamOneScore, Integer teamTwoScore) {

        if (String.valueOf(teamOneScore).length() < 1) {
            teamOneScoreEdit.setError("Score can't be null");
            teamOneScoreEdit.requestFocus();
            return;
        }

        if (String.valueOf(teamOneScore).length() > 3) {
            teamOneScoreEdit.setError("Are you sure this happened?");
            teamOneScoreEdit.requestFocus();
            return;
        }

        if (String.valueOf(teamTwoScore).length() < 1) {
            teamTwoScoreEdit.setError("Score can't be null");
            teamTwoScoreEdit.requestFocus();
            return;
        }

        if (String.valueOf(teamTwoScore).length() > 3) {
            teamTwoScoreEdit.setError("No way! This won't happen...");
            teamTwoScoreEdit.requestFocus();
            return;
        }

        user = FirebaseAuth.getInstance().getCurrentUser();
        userID = user.getUid();

        Bet bet = new Bet(userID, matchID, teamOneScore, teamTwoScore);
        DatabaseReference reference;
        reference = FirebaseDatabase.getInstance().getReference().child("Bets");
        reference.push().setValue(bet);


        Toast.makeText(PlaceBet.this, "Bet Placed!", Toast.LENGTH_LONG).show();
        startActivity(new Intent(this, UpcomingMatches.class));

    }
}
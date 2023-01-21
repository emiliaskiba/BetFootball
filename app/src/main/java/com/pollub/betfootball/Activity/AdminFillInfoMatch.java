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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.pollub.betfootball.Entity.Bet;
import com.pollub.betfootball.Entity.Match;
import com.pollub.betfootball.R;

import java.util.Objects;

public class AdminFillInfoMatch extends AppCompatActivity implements View.OnClickListener {

    private TextView matchView, teamOne, teamTwo;
    private ImageView back;
    private EditText teamOneScoreEdit, teamTwoScoreEdit;
    private Button addAndCalculate;
    private DatabaseReference reference, referenceTeam1, referenceTeam2, referenceBets, referenceChosenMatch;
    private String value;
    private Integer teamOneScore;
    private Integer teamTwoScore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent intent = getIntent();
        value = intent.getStringExtra("key"); //if it's a string you stored.
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_fill_info_match);


        back = findViewById(R.id.back);
        back.setOnClickListener(this);

        addAndCalculate = findViewById(R.id.addAndCalculate);
        addAndCalculate.setOnClickListener(this);

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
                        Toast.makeText(AdminFillInfoMatch.this, "Can't load. Make sure your connection is stable", Toast.LENGTH_LONG).show();

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
                        Toast.makeText(AdminFillInfoMatch.this, "Can't load. Make sure your connection is stable", Toast.LENGTH_LONG).show();

                    }
                });

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(AdminFillInfoMatch.this, "Can't load. Make sure your connection is stable", Toast.LENGTH_LONG).show();
            }
        });


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                startActivity(new Intent(this, AdminFillInfo.class));
                break;
            case R.id.addAndCalculate:
                teamOneScore = Integer.valueOf(teamOneScoreEdit.getText().toString());
                teamTwoScore = Integer.valueOf(teamTwoScoreEdit.getText().toString());
                update(value, teamOneScore, teamTwoScore);
                calculate(value, teamOneScore, teamTwoScore);
                break;
        }
    }

    private void calculate(String matchID, Integer teamOneScore, Integer teamTwoScore) {



        referenceBets = FirebaseDatabase.getInstance().getReference("Bets");

        referenceBets.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                    Integer whoWins = null;
                    if (teamOneScore > teamTwoScore) {
                        whoWins = 1;
                    } else if (teamOneScore < teamTwoScore) {
                        whoWins = 2;
                    } else if (teamOneScore == teamTwoScore) {
                        whoWins = 0;
                    }

                    Bet bet = snapshot.getValue(Bet.class);
                    if (Objects.equals((bet.matchID), matchID)) {
                        String temp = snapshot.getKey();
                        Integer tempScore = 0;
                        referenceBets.child(temp).child("calculated").setValue(true);

                        if (bet.whoWins == whoWins) {
                            tempScore = tempScore + 5;
                        }
                        if (bet.teamOneScore == teamOneScore) {
                            tempScore = tempScore + 5;
                        }
                        if (bet.teamTwoScore == teamTwoScore) {
                            tempScore = tempScore + 5;
                        }
                        referenceBets.child(temp).child("score").setValue(tempScore);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(AdminFillInfoMatch.this, "Can't load. Make sure your connection is stable", Toast.LENGTH_LONG).show();

            }
        });


    }



    private void update(String matchID, Integer teamOneScore, Integer teamTwoScore) {

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
            teamTwoScoreEdit.setError("Are you sure this happened?");
            teamTwoScoreEdit.requestFocus();
            return;
        }


        DatabaseReference reference;
        reference = FirebaseDatabase.getInstance().getReference().child("Match");
        reference.child(matchID).child("teamOneScore").setValue(teamOneScore);
        reference.child(matchID).child("teamTwoScore").setValue(teamTwoScore);
        reference.child(matchID).child("happened").setValue(true);
        if (teamOneScore > teamTwoScore){
            reference.child(matchID).child("whoWins").setValue(1);
        }
        else if (teamOneScore < teamTwoScore){
            reference.child(matchID).child("whoWins").setValue(2);
        }




        Toast.makeText(AdminFillInfoMatch.this, "Match info inserted & score calculated", Toast.LENGTH_LONG).show();
        startActivity(new Intent(this, AdminFillInfo.class));

    }
}
package com.pollub.betfootball.Activity;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
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
import com.pollub.betfootball.R;

import java.util.Objects;

public class UpcomingMatches extends AppCompatActivity implements View.OnClickListener {


    private DatabaseReference reference;
    private FirebaseAuth mAuth;
    private EditText matchIDEdit, teamTwoScoreEdit, teamOneScoreEdit;
    private ProgressBar progressBar;
    private Button confirm;
private ImageView back;

    private String userID, match;
    private FirebaseUser user;
    private DatabaseReference referenceUsers, referenceMatch, referenceBetsInside, referenceBets;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upcoming_matches);

        back = (ImageView) findViewById(R.id.back);
        back.setOnClickListener(this);

        mAuth = FirebaseAuth.getInstance();

        user = FirebaseAuth.getInstance().getCurrentUser();
        referenceUsers = FirebaseDatabase.getInstance().getReference("Users");
        userID = user.getUid();

        referenceMatch = FirebaseDatabase.getInstance().getReference().child("Match");
        referenceBets = FirebaseDatabase.getInstance().getReference().child("Bets");


        final TextView temp = (TextView) findViewById(R.id.temp);


        referenceMatch.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    String key = child.getKey();
                    String value = child.getValue().toString();

                    referenceBets = FirebaseDatabase.getInstance().getReference().child("Bets");
                    referenceBets.addValueEventListener(new ValueEventListener() {


                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                            for (DataSnapshot child : dataSnapshot.getChildren()) {
                                String keyInside = child.getKey();
                                String valueInside = child.getValue().toString();

                                referenceBetsInside = FirebaseDatabase.getInstance().getReference().child("Bets").child(keyInside);
                                referenceBetsInside.addValueEventListener(new ValueEventListener() {


                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                        for (DataSnapshot child : dataSnapshot.getChildren()) {
                                            String keyInside = child.getKey();
                                            String valueInside = child.getValue().toString();
                                            if (Objects.equals(keyInside, "matchID")) {
                                                String betID = keyInside;
                                                temp.setText(match);


                                            }
                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {

                                    }
                                });
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        reference = FirebaseDatabase.getInstance().getReference().child("Bets");
        referenceMatch = FirebaseDatabase.getInstance().getReference().child("Match");
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
          /*  case R.id.confirm:
                placeBet();
                break;*/
            case R.id.back:
                startActivity(new Intent(this, HomePage.class));
                break;
        }
    }

    private void placeBet() {
        Integer matchID = Integer.valueOf(matchIDEdit.getText().toString().trim());
        Integer teamOneScore = Integer.valueOf(teamOneScoreEdit.getText().toString().trim());
        Integer teamTwoScore = Integer.valueOf(teamTwoScoreEdit.getText().toString().trim());

        user = FirebaseAuth.getInstance().getCurrentUser();
        referenceUsers = FirebaseDatabase.getInstance().getReference("Users");
        userID = user.getUid();

        /*if(fullName.isEmpty()){
            editTextFullName.setError("Name is required!");
            editTextFullName.requestFocus();
            return;
        }

        if(email.isEmpty()){
            editTextEmail.setError("Email is required!");
            editTextEmail.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editTextEmail.setError(("Please provide valid Email!"));
            editTextEmail.requestFocus();
            return;
        }
        if(password.isEmpty()){
            editTextPassword.setError("Password is required");
            editTextPassword.requestFocus();
            return;
        }
        if(password.length() < 6){
            editTextPassword.setError("Password length should be 6 characters or more");
            editTextPassword.requestFocus();
            return;
        }*/
        progressBar.setVisibility(View.VISIBLE);

        Bet bet = new Bet(userID, matchID, teamOneScore, teamTwoScore);

        reference.push().setValue(bet);
        Toast.makeText(UpcomingMatches.this, "Data Inserted", Toast.LENGTH_SHORT).show();
        progressBar.setVisibility(View.GONE);

    }
}
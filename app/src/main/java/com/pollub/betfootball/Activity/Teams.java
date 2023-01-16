package com.pollub.betfootball.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.pollub.betfootball.Entity.Bet;
import com.pollub.betfootball.Entity.Team;
import com.pollub.betfootball.Entity.TeamUsers;
import com.pollub.betfootball.R;

import java.util.Objects;

public class Teams extends AppCompatActivity implements View.OnClickListener {
    private Button createTeam, joinTeam;
    private EditText teamCodeEdit;
    private FirebaseUser user;
    private DatabaseReference reference, referenceTeams;
    private String userID;
    private String parentKey = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teams);

        createTeam = (Button) findViewById(R.id.createTeam);
        joinTeam = (Button) findViewById(R.id.joinTeam);
        joinTeam = (Button) findViewById(R.id.joinTeam);
        teamCodeEdit = (EditText) findViewById(R.id.teamCode);

        joinTeam.setOnClickListener(this);
        createTeam.setOnClickListener(this);

        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference().child("TeamUsers");
        referenceTeams = FirebaseDatabase.getInstance().getReference().child("UserTeams");
        userID = user.getUid();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.createTeam:
                startActivity(new Intent(Teams.this, TeamCreate.class));
                break;
            case R.id.joinTeam:
                joinTeam();
                break;
        }
    }

    private void joinTeam() {

        String teamCode = String.valueOf(teamCodeEdit.getText()).trim();
        if (teamCode.isEmpty()) {
            teamCodeEdit.setError("Code is required!");
            teamCodeEdit.requestFocus();
            return;
        }
        if (teamCode.length() != 6) {
            teamCodeEdit.setError("Code must be 6 digits!");
            teamCodeEdit.requestFocus();
            return;
        }

        referenceTeams.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Team team = snapshot.getValue(Team.class);
                    if (Objects.equals(teamCode, team.code)) {
                        parentKey = snapshot.getKey();
                        user = FirebaseAuth.getInstance().getCurrentUser();
                        userID = user.getUid();

                        TeamUsers teamUsers = new TeamUsers(userID, parentKey);
                        reference.push().setValue(teamUsers);
                        Toast.makeText(Teams.this, "Successfully joined team!", Toast.LENGTH_SHORT).show();
                    }
                }
                if (parentKey == null) {
                    Toast.makeText(Teams.this, "Team not found!", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });


    }


}
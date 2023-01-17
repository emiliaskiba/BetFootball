package com.pollub.betfootball.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

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
import com.pollub.betfootball.Entity.Team;
import com.pollub.betfootball.Entity.TeamUsers;
import com.pollub.betfootball.R;

import java.util.Objects;

public class Teams extends AppCompatActivity implements View.OnClickListener {
    private Button createTeam, joinTeam;
    private EditText teamCodeEdit;
    private FirebaseUser user;
    private DatabaseReference reference, referenceTeams, database, referenceTeamUsers;
    private String userID;
    private String parentKey = null;
    private ImageView back;
    private String keys[];

   /* RecyclerView recyclerView;
    TeamAdapter teamAdapter;
    ArrayList<Team> teamList;*/

    private RecyclerView recyclerView;
    TeamAdapter adapter; // Create Object of the Adapter class

    Query mbase, mbase1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teams);

        back = (ImageView) findViewById(R.id.back);
        back.setOnClickListener(this);

        createTeam = (Button) findViewById(R.id.createTeam);
        joinTeam = (Button) findViewById(R.id.joinTeam);
        joinTeam = (Button) findViewById(R.id.joinTeam);
        teamCodeEdit = (EditText) findViewById(R.id.teamCode);

        joinTeam.setOnClickListener(this);
        createTeam.setOnClickListener(this);

        user = FirebaseAuth.getInstance().getCurrentUser();
        userID = user.getUid();
        mbase1 = FirebaseDatabase.getInstance().getReference().child("TeamUsers").orderByChild("userID").equalTo(userID);

        mbase = FirebaseDatabase.getInstance().getReference().child("UserTeams").orderByChild("code").equalTo("abcdef");
        System.out.println("--------------"+ mbase);
        recyclerView = findViewById(R.id.teamList);
        // To display the Recycler view linearly

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // It is a class provide by the FirebaseUI to make a
        // query in the database to fetch appropriate data
        //FirebaseRecyclerOptions<Team> options = new FirebaseRecyclerOptions.Builder<Team>().setQuery(mbase, Team.class).build();
        /*referenceTeamUsers.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                user = FirebaseAuth.getInstance().getCurrentUser();
                userID = user.getUid();
                int i = 0;
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    TeamUsers teamUsers = snapshot.getValue(TeamUsers.class);

                    if (Objects.equals(userID, teamUsers.userID)) {
                        keys[i]=snapshot.getKey();
                        i=i+1;
                         }
                }
                mbase = FirebaseDatabase.getInstance().getReference().child(String.valueOf(keys));
               System.out.println(keys);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
        */
        // Connecting object of required Adapter class to
        FirebaseRecyclerOptions<Team> options = new FirebaseRecyclerOptions.Builder<Team>().setQuery(mbase, Team.class).build();
        // the Adapter class itself

        adapter = new TeamAdapter(options);

        // Connecting Adapter class with the Recycler view*/

        recyclerView.setAdapter(adapter);


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
            case R.id.back:
                startActivity(new Intent(Teams.this, HomePage.class));
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

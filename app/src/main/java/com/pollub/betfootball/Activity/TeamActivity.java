package com.pollub.betfootball.Activity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.pollub.betfootball.Entity.Team;
import com.pollub.betfootball.Entity.TeamUsers;
import com.pollub.betfootball.Entity.User;
import com.pollub.betfootball.R;

import java.util.Objects;

public class TeamActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView back;
    private String value;
    private TextView team_name, team_code, leader;
    private DatabaseReference database, referenceLeader;
    private String teamName, teamCode, leaderID, userLeaderID, leaderName;
    private ClipboardManager myClipboard;
    private ClipData myClip;
    private Button teamCodeCopy;

    private RecyclerView recyclerView;
    TeamMembersAdapter adapter; // Create Object of the Adapter class
    Query mbase, mbase1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent intent = getIntent();
        value = intent.getStringExtra("key"); //if it's a string you stored.
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_activity);

        team_name = findViewById(R.id.team_name);
        team_code = findViewById(R.id.teamCode);
        leader = findViewById(R.id.leader);
        teamCodeCopy = findViewById(R.id.teamCodeCopy);

        database = FirebaseDatabase.getInstance().getReference().child("UserTeams").child(value);
        database.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Team team = dataSnapshot.getValue(Team.class);

                teamName = team.getName();
                teamCode = team.getCode();
                leaderID = team.getLeader();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }

        });

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {

                team_name.setText(teamName);
                team_code.setText(teamCode);
                myClipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);

                teamCodeCopy.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {

                        myClip = ClipData.newPlainText("text", teamCode);
                        myClipboard.setPrimaryClip(myClip);

                        Toast.makeText(TeamActivity.this, "Team Code Copied!", Toast.LENGTH_SHORT).show();
                    }
                });

                referenceLeader = FirebaseDatabase.getInstance().getReference().child("Users");
                referenceLeader.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            User user = snapshot.getValue(User.class);

                            userLeaderID = snapshot.getKey();
                             if (Objects.equals(leaderID, userLeaderID)) {
                                leaderName = user.fullName;

                            }

                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }

                });

                Handler handler2 = new Handler();
                handler2.postDelayed(new Runnable() {
                    public void run() {

                        System.out.println(leaderName);leader.setText(leaderName);
                    }
                }, 500);
            }
        }, 500);


        back = (ImageView) findViewById(R.id.back);
        back.setOnClickListener(this);


        mbase = FirebaseDatabase.getInstance().getReference().child("TeamUsers").orderByChild("teamID").equalTo(value);

        recyclerView = findViewById(R.id.teamUsersList);
        // To display the Recycler view linearly
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        FirebaseRecyclerOptions<TeamUsers> options = new FirebaseRecyclerOptions.Builder<TeamUsers>().setQuery(mbase, TeamUsers.class).build();
        // the Adapter class itself

        adapter = new TeamMembersAdapter(options);

        // Connecting Adapter class with the Recycler view*/

        recyclerView.setAdapter(adapter);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                startActivity(new Intent(this, Teams.class));
                break;
        }
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
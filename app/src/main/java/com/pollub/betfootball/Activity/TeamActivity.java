package com.pollub.betfootball.Activity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
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
    private String value, matchdayLeaderUser, seasonLeaderUser;
    private Integer matchdayLeaderUserScore = 0, seasonLeaderUserScore = 0;
    private TextView team_name, team_code, leader, matchdayLeader, seasonLeader;
    private DatabaseReference database, referenceLeader;
    private String teamName, teamCode, leaderID, userLeaderID, leaderName;
    private ClipboardManager myClipboard;
    private ClipData myClip;
    private Button teamCodeCopy, setPrizeButton;
    private Spinner spinner1;
    private static final String[] paths = {"Chocolate","Beer", "£50"};
    private String prizeChosen;
    private TextView prize;

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
        matchdayLeader = findViewById(R.id.matchdayLeader);
        seasonLeader = findViewById(R.id.seasonLeader);
        teamCodeCopy = findViewById(R.id.teamCodeCopy);
        prize = findViewById(R.id.prize);
        setPrizeButton = findViewById(R.id.button2);
        setPrizeButton.setOnClickListener(this);

        //spinner

        spinner1 = (Spinner)findViewById(R.id.spinner1);

        ArrayAdapter<String> adapterPrize = new ArrayAdapter<String>(TeamActivity.this, android.R.layout.simple_spinner_item,paths);
        adapterPrize.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(adapterPrize);
        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
                switch (item) {

                    case "Chocolate":
                        prizeChosen = "Chocolate";
                        break;
                    case "Beer":
                        prizeChosen = "Beer";
                        break;
                    case "£50":
                        prizeChosen = "£50";
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                prizeChosen = "Chocolate";
            }
        });

        //spinner end


        database = FirebaseDatabase.getInstance().getReference().child("UserTeams").child(value);
        database.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Team team = dataSnapshot.getValue(Team.class);

                teamName = team.getName();
                teamCode = team.getCode();
                leaderID = team.getLeader();
                prize.setText(team.prize);

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

                        leader.setText(leaderName);
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

        Query referencematchdayleader = FirebaseDatabase.getInstance().getReference().child("TeamUsers").orderByChild("teamID").equalTo(value);


        referencematchdayleader.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    TeamUsers teamUser = snapshot.getValue(TeamUsers.class);
                    String tempID = teamUser.userID;


                    DatabaseReference referencematchdayleader1 = FirebaseDatabase.getInstance().getReference("Users");
                    referencematchdayleader1.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot udataSnapshot) {
                            for (DataSnapshot usnapshot : udataSnapshot.getChildren()) {
                                User user1 = usnapshot.getValue(User.class);

                                if (Objects.equals(String.valueOf(usnapshot.getKey()), String.valueOf(tempID))) {

                                    Integer tempMatchdayScore = user1.scoreMatchDay;
                                    Integer tempSeasonScore = user1.scoreAllSeason;

                                    if (Integer.valueOf(matchdayLeaderUserScore) < Integer.valueOf(tempMatchdayScore)) {

                                        matchdayLeaderUserScore = tempMatchdayScore;
                                        matchdayLeaderUser = user1.fullName;
                                        matchdayLeader.setText(matchdayLeaderUser);

                                    }
                                    if (Integer.valueOf(seasonLeaderUserScore) < Integer.valueOf(tempSeasonScore)) {
                                        seasonLeaderUserScore = tempSeasonScore;
                                        seasonLeaderUser = user1.fullName;
                                        seasonLeader.setText(seasonLeaderUser);
                                    }

                                }
                            }

                        }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {
                            }

                        });
                            }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }

        });



    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                startActivity(new Intent(this, Teams.class));
                break;
            case R.id.button2:
                setPrizeChosen();
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

    private void setPrizeChosen(){
        DatabaseReference reference;
        reference = FirebaseDatabase.getInstance().getReference().child("UserTeams");
        reference.child(value).child("prize").setValue(prizeChosen);
        prize.setText(prizeChosen);
    }
}
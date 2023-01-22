package com.pollub.betfootball.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
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
    private String userID, teamName;
    private String parentKey = null;
    private ImageView back;
    private String keys[];
    private Boolean zmiennaPomocnicza = false;

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
        mbase = FirebaseDatabase.getInstance().getReference().child("TeamUsers").orderByChild("userID").equalTo(userID);


        recyclerView = findViewById(R.id.teamList);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        FirebaseRecyclerOptions<TeamUsers> options = new FirebaseRecyclerOptions.Builder<TeamUsers>().setQuery(mbase, TeamUsers.class).build();

        adapter = new TeamAdapter(options);
        recyclerView.setAdapter(adapter);

        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference().child("TeamUsers");
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
        referenceTeams = FirebaseDatabase.getInstance().getReference().child("UserTeams");
        referenceTeams.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Team team = snapshot.getValue(Team.class);
                    if (Objects.equals(teamCode, team.code)) {

                        teamName = team.name;
                        parentKey = snapshot.getKey();
                        System.out.println("------------- zmiennej wartosc na zewnatrz " + parentKey);
                        user = FirebaseAuth.getInstance().getCurrentUser();
                        userID = user.getUid();

                        //koniec

                    }
                }

            }


            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });



        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
                                public void run() {
                                    referenceTeamUsers = FirebaseDatabase.getInstance().getReference("TeamUsers");
                                    referenceTeamUsers.addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                                TeamUsers teamUser = snapshot.getValue(TeamUsers.class);
                                                if (Objects.equals(parentKey, teamUser.teamID) && Objects.equals(userID, teamUser.userID)) {
                                                    zmiennaPomocnicza = true;
                                                }
                                            }
                                        }
                                        @Override
                                        public void onCancelled(@NonNull DatabaseError error) {
                                            Toast.makeText(Teams.this, "Can't load. Make sure your connection is stable", Toast.LENGTH_LONG).show();
                                        }
                                    });
                                }
                            }
                , 1000);

        Handler handler2 = new Handler();
        handler2.postDelayed(new Runnable() {
                                 public void run() {
                                     if (parentKey == null) {
                                         Toast.makeText(Teams.this, "Team not found!", Toast.LENGTH_LONG).show();
                                     } else {
                                         if (zmiennaPomocnicza == false) {

                                             TeamUsers teamUsers = new TeamUsers(userID, parentKey, teamName);
                                             reference.push().setValue(teamUsers);
                                             Toast.makeText(Teams.this, "Successfully joined team!", Toast.LENGTH_SHORT).show();
                                         } else {
                                             Toast.makeText(Teams.this, "You already joined this team!", Toast.LENGTH_SHORT).show();
                                             zmiennaPomocnicza = false;
                                         }

                                     }
                                 }
                             }

                , 3000);





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

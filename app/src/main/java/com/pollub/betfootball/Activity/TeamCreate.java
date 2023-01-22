package com.pollub.betfootball.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.pollub.betfootball.Entity.Team;
import com.pollub.betfootball.Entity.TeamUsers;
import com.pollub.betfootball.R;

public class TeamCreate extends AppCompatActivity implements View.OnClickListener {

    private TextView temp;
    private FirebaseAuth mAuth;
    private EditText teamNameEdit;
    private FirebaseUser user;
    private DatabaseReference reference, referenceInsert;
    private String userID, code, teamID;
    private Button createTeam;
    private ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_create);

        mAuth = FirebaseAuth.getInstance();

        back = (ImageView) findViewById(R.id.back);
        back.setOnClickListener(this);

        teamNameEdit = (EditText) findViewById(R.id.teamNameEdit);
        user = FirebaseAuth.getInstance().getCurrentUser();
        userID = user.getUid();
        reference = FirebaseDatabase.getInstance().getReference().child("UserTeams");
        createTeam = (Button) findViewById(R.id.createTeam);
        createTeam.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.createTeam:
                createTeam();
                startActivity(new Intent(this, Teams.class));
                break;
            case R.id.back:
                startActivity(new Intent(this, Teams.class));
                break;
        }
    }


    // function to generate a random string of length n
    static String getAlphaNumericString(int n) {

        // choose a Character random from this String
        String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
                + "0123456789"
                + "abcdefghijklmnopqrstuvxyz";

        // create StringBuffer size of AlphaNumericString
        StringBuilder sb = new StringBuilder(n);

        for (int i = 0; i < n; i++) {

            // generate a random number between
            // 0 to AlphaNumericString variable length
            int index
                    = (int) (AlphaNumericString.length()
                    * Math.random());

            // add Character one by one in end of sb
            sb.append(AlphaNumericString
                    .charAt(index));
        }

        return sb.toString();
    }


    private void createTeam() {

        String teamName = teamNameEdit.getText().toString().trim();

        Team team = new Team(getAlphaNumericString(6), teamName, userID);
       // reference.push().setValue(team);
// read the index key
        String teamID = reference.push().getKey();
        reference.child(teamID).setValue(team);

        /*referenceInsert = FirebaseDatabase.getInstance().getReference("UserTeams");
        referenceInsert.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Team team = snapshot.getValue(Team.class);
                    if (Objects.equals(team.name, teamName ) && Objects.equals(team.leader, userID )) {
                        teamID = dataSnapshot.getKey();
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(TeamCreate.this, "Can't load. Make sure your connection is stable", Toast.LENGTH_LONG).show();
            }
        });*/

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
        DatabaseReference referenceTeamUser = FirebaseDatabase.getInstance().getReference().child("TeamUsers");
        TeamUsers teamusers = new TeamUsers(userID, teamID, teamName);
        referenceTeamUser.push().setValue(teamusers);

        Toast.makeText(TeamCreate.this, "Team " + teamName + " created", Toast.LENGTH_SHORT).show();
            }
        }, 500);


    }

}

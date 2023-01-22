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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.pollub.betfootball.Entity.Team;
import com.pollub.betfootball.R;

public class TeamActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView back;
    private String value;
    private TextView team_name, team_code;
    private DatabaseReference database;
    private String teamName, teamCode;
    private ClipboardManager myClipboard;
    private ClipData myClip;
    private Button teamCodeCopy;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent intent = getIntent();
        value = intent.getStringExtra("key"); //if it's a string you stored.
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_activity);

        team_name = findViewById(R.id.team_name);
        team_code = findViewById(R.id.teamCode);
        teamCodeCopy = findViewById(R.id.teamCodeCopy);

            database = FirebaseDatabase.getInstance().getReference().child("UserTeams").child(value);
            database.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    Team team = dataSnapshot.getValue(Team.class);

                            teamName = team.getName();
                            teamCode = team.getCode();

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

                }
            }, 1000);


        back = (ImageView) findViewById(R.id.back);
        back.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                startActivity(new Intent(this, Teams.class));
                break;
        }
    }
}
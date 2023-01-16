package com.pollub.betfootball.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.pollub.betfootball.Entity.User;
import com.pollub.betfootball.R;

public class HomePage extends AppCompatActivity implements View.OnClickListener {

    private Button settings, profile, matches, teams;
    private FirebaseUser user;
    private DatabaseReference reference;
    private String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        settings = (Button) findViewById(R.id.settings);
        settings.setOnClickListener(this);

        matches = (Button) findViewById(R.id.matches);
        matches.setOnClickListener(this);

        profile = (Button) findViewById(R.id.profile);
        profile.setOnClickListener(this);

        teams = (Button) findViewById(R.id.teams);
        teams.setOnClickListener(this);

        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users");
        userID = user.getUid();

        final TextView nameTextView = (TextView) findViewById(R.id.name);


        reference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User userProfile = snapshot.getValue(User.class);
                if (userProfile != null){
                    String name = userProfile.fullName;
                    nameTextView.setText(name);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(HomePage.this,"Something wrong happened!", Toast.LENGTH_LONG).show();
            }
        });

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.settings:
                startActivity(new Intent(this, Settings.class));
                break;
            case R.id.profile:
                startActivity(new Intent(this, Profile.class));
                break;
            case R.id.matches:
                startActivity(new Intent(this, UpcomingMatches.class));
                break;
            case R.id.teams:
                startActivity(new Intent(this, Teams.class));
                break;
        }
    }


}
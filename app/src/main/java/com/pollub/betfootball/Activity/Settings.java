package com.pollub.betfootball.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.pollub.betfootball.R;

public class Settings extends AppCompatActivity implements View.OnClickListener {
    private Button logout;
    private FirebaseUser user;
    private DatabaseReference reference;
    private String userID;
    private ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users");
        userID = user.getUid();


        back = (ImageView) findViewById(R.id.back);
        back.setOnClickListener(this);

        logout = (Button) findViewById(R.id.logout);

        logout.setOnClickListener(this);




    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.logout:
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(Settings.this, MainActivity.class));
                break;
            case R.id.back:
                startActivity(new Intent(this, HomePage.class));
                break;
        }
    }
}
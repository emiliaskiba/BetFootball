package com.pollub.betfootball.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;
import com.pollub.betfootball.R;

public class TeamActivity extends AppCompatActivity implements View.OnClickListener {
private ImageView back;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_activity);

        back = (ImageView) findViewById(R.id.back);
        back.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back:
                startActivity(new Intent(this, Teams.class));
                break;
        }
    }
}
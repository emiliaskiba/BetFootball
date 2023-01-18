package com.pollub.betfootball.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.pollub.betfootball.R;

public class Admin extends AppCompatActivity implements View.OnClickListener {

    private Button addMatch, fillMatchInfo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        addMatch = (Button) findViewById(R.id.addMatch);
        fillMatchInfo = (Button) findViewById(R.id.fillMatchInfo);
        addMatch.setOnClickListener(this);
        fillMatchInfo.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.addMatch:
                startActivity(new Intent(this, AdminAddMatch.class));
                break;
            case R.id.fillMatchInfo:
                startActivity(new Intent(this, AdminFillInfo.class));
                break;
        }
    }
}
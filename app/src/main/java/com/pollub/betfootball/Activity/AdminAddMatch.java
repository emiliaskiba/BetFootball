package com.pollub.betfootball.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.pollub.betfootball.Entity.Match;
import com.pollub.betfootball.R;

public class AdminAddMatch extends AppCompatActivity implements View.OnClickListener  {
    private DatabaseReference reference;
    private FirebaseAuth mAuth;
    private EditText matchDayValue, teamTwoID, teamOneID;
    private ProgressBar progressBar;
    private Button addMatch;
    private ImageView back;


    private String userID;
    private FirebaseUser user;
    private DatabaseReference referenceMatch;

    private Spinner spinner1, spinner2;
    private static final String[] paths = {"Arsenal", "Aston Villa", "Bournemouth", "Brentford", "Brighton","Chelsea","Crystal Palace","Everton","Fulham" ,"Leeds","Leicester","Liverpool","Manchester City", "Manchester United","Newcastle","Nottingham Forest", "Southampton","Tottenham Hotspur","West Ham","Wolverhampton"};
    private Integer team1chosen, team2chosen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_add_match);

        back = (ImageView) findViewById(R.id.back);
        back.setOnClickListener(this);

       // teamOneID = (EditText) findViewById(R.id.teamOneID);
       // teamTwoID = (EditText) findViewById(R.id.teamTwoID);
        matchDayValue = (EditText) findViewById(R.id.matchDay);

        mAuth = FirebaseAuth.getInstance();
        referenceMatch = FirebaseDatabase.getInstance().getReference().child("Match");

        addMatch = (Button) findViewById(R.id.addMatch);
        addMatch.setOnClickListener(this);

       // Spinner dropdown = findViewById(R.id.spinner1);
//create a list of items for the spinner.
       // String[] items = new String[]{"Arsenal", "Aston Villa", "Bournemouth", "Brentford", "Brighton","Chelsea","Crystal Palace","Everton","Fulham" ,"Leeds","Leicester","Liverpool","Manchester City", "Manchester United","Newcastle","Nottingham Forest", "Southampton"};
//create an adapter to describe how the items are displayed, adapters are used in several places in android.
//There are multiple variations of this, but this is the basic variant.
       // ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
//set the spinners adapter to the previously created one.
        //dropdown.setAdapter(adapter);
        //dropdown.setOnItemSelectedListener(this);
        spinner1 = (Spinner)findViewById(R.id.spinner1);
        spinner2 = (Spinner)findViewById(R.id.spinner2);
        ArrayAdapter<String>adapter = new ArrayAdapter<String>(AdminAddMatch.this, android.R.layout.simple_spinner_item,paths);
        ArrayAdapter<String>adapter2 = new ArrayAdapter<String>(AdminAddMatch.this, android.R.layout.simple_spinner_item,paths);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(adapter);
        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
                switch (item) {

                    case "Arsenal":
                        team1chosen = 1;
                        break;
                    case "Aston Villa":
                        team1chosen = 2;
                        break;
                    case "Bournemouth":
                        team1chosen = 3;
                        break;
                    case "Brentford":
                        team1chosen = 4;
                        break;
                    case "Brighton":
                        team1chosen = 5;
                        break;
                    case "Chelsea":
                        team1chosen = 6;
                        break;
                    case "Crystal Palace":
                        team1chosen = 7;
                        break;
                    case "Everton":
                        team1chosen = 8;
                        break;
                    case "Fulham":
                        team1chosen = 9;
                        break;
                    case "Leeds":
                        team1chosen = 10;
                        break;
                    case "Leicester":
                        team1chosen = 11;
                        break;
                    case "Liverpool":
                        team1chosen = 12;
                        break;
                    case "Manchester City":
                        team1chosen = 13;
                        break;
                    case "Manchester United":
                        team1chosen = 14;
                        break;
                    case "Newcastle":
                        team1chosen = 15;
                        break;
                    case "Nottingham Forest":
                        team1chosen = 16;
                        break;
                    case "Southampton":
                        team1chosen = 17;
                        break;
                    case "Tottenham Hotspur":
                        team1chosen = 18;
                        break;
                    case "West Ham":
                        team1chosen = 19;
                        break;
                    case "Wolverhampton":
                        team1chosen = 20;
                        break;

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinner2.setAdapter(adapter2);
        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
                switch (item) {
                    case "Arsenal":
                        team2chosen = 1;
                        break;
                    case "Aston Villa":
                        team2chosen = 2;
                        break;
                    case "Bournemouth":
                        team2chosen = 3;
                        break;
                    case "Brentford":
                        team2chosen = 4;
                        break;
                    case "Brighton":
                        team2chosen = 5;
                        break;
                    case "Chelsea":
                        team2chosen = 6;
                        break;
                    case "Crystal Palace":
                        team2chosen = 7;
                        break;
                    case "Everton":
                        team2chosen = 8;
                        break;
                    case "Fulham":
                        team2chosen = 9;
                        break;
                    case "Leeds":
                        team2chosen = 10;
                        break;
                    case "Leicester":
                        team2chosen = 11;
                        break;
                    case "Liverpool":
                        team2chosen = 12;
                        break;
                    case "Manchester City":
                        team2chosen = 13;
                        break;
                    case "Manchester United":
                        team2chosen = 14;
                        break;
                    case "Newcastle":
                        team2chosen = 15;
                        break;
                    case "Nottingham Forest":
                        team2chosen = 16;
                        break;
                    case "Southampton":
                        team2chosen = 17;
                        break;
                    case "Tottenham Hotspur":
                        team2chosen = 18;
                        break;
                    case "West Ham":
                        team2chosen = 19;
                        break;
                    case "Wolverhampton":
                        team2chosen = 20;
                        break;

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }



    private void addMatch() {

        //Integer teamOne = Integer.valueOf(teamOneID.getText().toString().trim());
        //Integer teamTwo = Integer.valueOf(teamTwoID.getText().toString().trim());
        Integer matchDay = Integer.valueOf(matchDayValue.getText().toString().trim());


        /*if(fullName.isEmpty()){
            editTextFullName.setError("Name is required!");
            editTextFullName.requestFocus();
            return;
        }

        if(email.isEmpty()){
            editTextEmail.setError("Email is required!");
            editTextEmail.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editTextEmail.setError(("Please provide valid Email!"));
            editTextEmail.requestFocus();
            return;
        }
        if(password.isEmpty()){
            editTextPassword.setError("Password is required");
            editTextPassword.requestFocus();
            return;
        }
        if(password.length() < 6){
            editTextPassword.setError("Password length should be 6 characters or more");
            editTextPassword.requestFocus();
            return;
        }*/
        // progressBar.setVisibility(View.VISIBLE);

        Match match = new Match(matchDay, team1chosen, team2chosen);

        referenceMatch.push().setValue(match).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(AdminAddMatch.this, "Match added!", Toast.LENGTH_SHORT).show();
                    //progressBar.setVisibility(View.GONE);

                }else{
                    Toast.makeText(AdminAddMatch.this, "Failed to add match! Try again!", Toast.LENGTH_LONG).show();
                    //progressBar.setVisibility(View.GONE);
                }
                //Toast.makeText(AdminAddMatch.this, "Match added", Toast.LENGTH_SHORT).show();
            }
        });

        //progressBar.setVisibility(View.GONE);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.addMatch:
                addMatch();
                break;
            case R.id.back:
                startActivity(new Intent(this, Admin.class));
                break;
        }
    }


}
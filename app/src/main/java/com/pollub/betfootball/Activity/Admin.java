package com.pollub.betfootball.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.pollub.betfootball.Entity.User;
import com.pollub.betfootball.R;

public class Admin extends AppCompatActivity implements View.OnClickListener {

    private Button addMatch, fillMatchInfo, changeMatchday;
    private ImageView back;
    private EditText editTextNumber;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        addMatch = (Button) findViewById(R.id.addMatch);
        fillMatchInfo = (Button) findViewById(R.id.fillMatchInfo);
        back = (ImageView) findViewById(R.id.back);
        changeMatchday = findViewById(R.id.changeMatchday);
        editTextNumber = findViewById(R.id.editTextNumber);

        changeMatchday.setOnClickListener(this);
        addMatch.setOnClickListener(this);
        fillMatchInfo.setOnClickListener(this);
        back.setOnClickListener(this);


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
            case R.id.back:
                startActivity(new Intent(this, HomePage.class));
                break;
            case R.id.changeMatchday:
                changeMatchday();
                break;
        }
    }

    private void changeMatchday(){
        String value = editTextNumber.getText().toString().trim();
        if(value.isEmpty()){
            editTextNumber.setError("Field can't be blank!");
            editTextNumber.requestFocus();
            return;
        }
        if(Integer.valueOf(value) > 38 || Integer.valueOf(value) < 1 ){
            editTextNumber.setError("Number of matchday please!");
            editTextNumber.requestFocus();
            return;
        }

        FirebaseDatabase.getInstance().getReference("Users").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    User user = snapshot.getValue(User.class);
                    String parentKey = snapshot.getKey();
                    DatabaseReference reference;
                    reference = FirebaseDatabase.getInstance().getReference().child("Users");
                    reference.child(parentKey).child("scoreMatchDay").setValue(0);




                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        FirebaseDatabase.getInstance().getReference().child("Matchday").setValue(Integer.valueOf(value));
        Toast.makeText(Admin.this, "Matchday changed", Toast.LENGTH_SHORT).show();
    }
}
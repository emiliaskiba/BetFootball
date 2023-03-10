package com.pollub.betfootball.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.pollub.betfootball.Entity.User;
import com.pollub.betfootball.R;

public class Profile extends AppCompatActivity implements View.OnClickListener {

    private FirebaseUser user;
    private DatabaseReference reference;
    private String userID;
    private ImageView back;
    private EditText editFullName;
    private Button change;
    private String newFullName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users");
        userID = user.getUid();

        back = (ImageView) findViewById(R.id.back);
        back.setOnClickListener(this);

       // final TextView nameTextView = (TextView) findViewById(R.id.name);
        final TextView fullNameTextView = (TextView) findViewById(R.id.fullName);
        final TextView emailTextView = (TextView) findViewById(R.id.email);

        change = findViewById(R.id.change);
        change.setOnClickListener(this);


        reference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User userProfile = snapshot.getValue(User.class);
                if (userProfile != null) {
                    String fullName = userProfile.fullName;
                    String email = userProfile.email;
                    fullNameTextView.setText(fullName);
                   // nameTextView.setText(fullName);
                    emailTextView.setText(email);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Profile.this, "Something wrong happened!", Toast.LENGTH_LONG).show();
            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                startActivity(new Intent(this, HomePage.class));
                break;
            case R.id.change:
                editFullName = (EditText) findViewById(R.id.editFullName);
                newFullName = editFullName.getText().toString().trim();

                if(newFullName.length() < 2){
                    editFullName.setError("Nickname too short! It should be longer than 2");
                    editFullName.requestFocus();
                    break;
                }
                if(newFullName.length() > 15){
                    editFullName.setError("Nickname too long! It should be shorter than 15");
                    editFullName.requestFocus();
                    break;
                }
                User.updateFullName(userID, newFullName);
                Toast.makeText(Profile.this, "Nickname changed!", Toast.LENGTH_LONG).show();
                finish();
                startActivity(getIntent());
                break;
        }
    }
}
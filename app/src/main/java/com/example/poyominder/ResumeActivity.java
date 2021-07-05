package com.example.poyominder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class ResumeActivity extends AppCompatActivity implements View.OnClickListener {

    private Button back_to_main_resume, button_home_resume, button_add_medicine_resume;
    private FirebaseFirestore firebaseFirestore;
    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private DatabaseReference reference;
    private String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resume);

        back_to_main_resume = (Button) findViewById(R.id.back_to_main_resume);
        back_to_main_resume.setOnClickListener(this);

        button_add_medicine_resume = (Button) findViewById(R.id.button_add_medicine_resume);
        button_add_medicine_resume.setOnClickListener(this);

        button_home_resume = (Button) findViewById(R.id.button_home_resume);
        button_home_resume.setOnClickListener(this);


        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users");
        userID = user.getUid();

        final TextView usernameTextView = (TextView) findViewById(R.id.data_username);

        FirebaseFirestore.getInstance().collection("users").document(userID).get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        User user = documentSnapshot.toObject(User.class);
                        if (user != null) {
                            usernameTextView.setText(user.username);
                        }
                    }
                });


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_to_main_resume:
            case R.id.button_home_resume:
                startActivity(new Intent(this, ProfileActivity.class));
                break;
            case R.id.button_add_medicine_resume:
                startActivity(new Intent(this, AddMedicine.class));
                break;
        }
    }
}
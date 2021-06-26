package com.example.poyominder;

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

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener {

    private FirebaseUser user;
    private DatabaseReference reference;
    private String userID;

    private Button logout_button, button_add_medicine;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        button_add_medicine = (Button) findViewById(R.id.button_add_medicine);
        button_add_medicine.setOnClickListener(this);

        logout_button = (Button) findViewById(R.id.logout_button);
        logout_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(ProfileActivity.this, MainActivity.class));
                Toast.makeText(ProfileActivity.this, "Déconnexion réussi, à bientôt ! @Poyo'Minder", Toast.LENGTH_LONG).show();
            }
        });

        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users");
        userID = user.getUid();

        final TextView usernameTextView = (TextView) findViewById(R.id.data_username);
        final TextView emailTextView = (TextView) findViewById(R.id.data_email);

        reference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User userProfile = snapshot.getValue(User.class);

                if (userProfile != null) {
                    String username = userProfile.username;
                    String email = userProfile.email;

                    usernameTextView.setText(username);
                    emailTextView.setText(email);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ProfileActivity.this, "Erreur de chargement! Réessayer.", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_add_medicine:
                startActivity(new Intent(this, AddMedicine.class));
        }
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}


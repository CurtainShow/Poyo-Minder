package com.example.poyominder;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.model.Document;

import java.util.ArrayList;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener {

    private FirebaseFirestore firebaseFirestore;
    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private DatabaseReference reference;
    private String userID;

    private RecyclerView recyclerMatin;
    private RecyclerView recyclerMidi;
    private RecyclerView recyclerSoir;

    private ArrayList<Medicine> medicineList = new ArrayList<>();
    private ArrayList<Medicine> medicineListMidday = new ArrayList<>();
    private ArrayList<Medicine> medicineListEvening = new ArrayList<>();

    private Button logout_button, button_add_medicine;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        button_add_medicine = (Button) findViewById(R.id.button_add_medicine);
        button_add_medicine.setOnClickListener(this);

        recyclerMatin = findViewById(R.id.recycleur_view_matin);
        recyclerMidi = findViewById(R.id.recycleur_view_midi);
        recyclerSoir = findViewById(R.id.recycleur_view_soir);

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

        // Added Firestore recycleur view
        firebaseFirestore = FirebaseFirestore.getInstance();

        mAuth = FirebaseAuth.getInstance();

        Query query = firebaseFirestore.collection("users").document(userID).collection("planning").whereArrayContains("prescription", "morning");
        MedicineAdapter medAdapterMorning = new MedicineAdapter(this, medicineList);

        recyclerMatin.setHasFixedSize(true);
        recyclerMatin.setLayoutManager(new LinearLayoutManager(this));
        recyclerMatin.setAdapter(medAdapterMorning);

        query.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                DocumentSnapshot document = null;
                for (Integer i = 0; i < queryDocumentSnapshots.size(); i++) {
                    document = queryDocumentSnapshots.getDocuments().get(i);
                    Medicine medoc = document.toObject(Medicine.class);
                    if (medoc != null) {
                        medicineList.add(medoc);
                        medAdapterMorning.notifyDataSetChanged();
                    }
                }
            }
        });

        // Recycleur Midi

        Query queryMidday = firebaseFirestore.collection("users").document(userID).collection("planning").whereArrayContains("prescription", "midday");
        MedicineAdapter medAdapterMidday = new MedicineAdapter(this, medicineListMidday);

        recyclerMidi.setHasFixedSize(true);
        recyclerMidi.setLayoutManager(new LinearLayoutManager(this));
        recyclerMidi.setAdapter(medAdapterMidday);

        queryMidday.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                DocumentSnapshot document = null;
                for (Integer i = 0; i < queryDocumentSnapshots.size(); i++) {
                    document = queryDocumentSnapshots.getDocuments().get(i);
                    Medicine medoc = document.toObject(Medicine.class);
                    if (medoc != null) {
                        medicineListMidday.add(medoc);
                        medAdapterMidday.notifyDataSetChanged();
                    }
                }
            }
        });

        // Recycleur Soir

        Query queryEvening = firebaseFirestore.collection("users").document(userID).collection("planning").whereArrayContains("prescription", "evening");
        MedicineAdapter medAdapterEvening = new MedicineAdapter(this, medicineListEvening);

        recyclerSoir.setHasFixedSize(true);
        recyclerSoir.setLayoutManager(new LinearLayoutManager(this));
        recyclerSoir.setAdapter(medAdapterEvening);

        queryEvening.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                DocumentSnapshot document = null;
                for (Integer i = 0; i < queryDocumentSnapshots.size(); i++) {
                    document = queryDocumentSnapshots.getDocuments().get(i);
                    Medicine medoc = document.toObject(Medicine.class);
                    if (medoc != null) {
                        medicineListEvening.add(medoc);
                        medAdapterEvening.notifyDataSetChanged();
                    }
                }
            }
        });

        // End FireStore Recycleur view

        final TextView usernameTextView = (TextView) findViewById(R.id.data_username);
        //final TextView emailTextView = (TextView) findViewById(R.id.data_email);

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
            case R.id.button_add_medicine:
                startActivity(new Intent(this, AddMedicine.class));
        }
    }
}


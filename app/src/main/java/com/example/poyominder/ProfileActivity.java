package com.example.poyominder;

import android.content.Intent;
import android.media.Image;
import android.net.Uri;
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

    private Button logout_button, button_add_medicine, covid_stats_button, vaccination_button;
    private ImageView emptyViewEvening;
    private ImageView emptyViewMidday;
    private ImageView emptyViewMorning;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        button_add_medicine = (Button) findViewById(R.id.button_add_medicine);
        button_add_medicine.setOnClickListener(this);

        covid_stats_button = (Button) findViewById(R.id.covid_stats_button);
        covid_stats_button.setOnClickListener(this);

        vaccination_button = (Button) findViewById(R.id.vaccination_button);
        vaccination_button.setOnClickListener(this);

        emptyViewEvening = (ImageView) findViewById(R.id.image_empty_recyclerview_soir);
        emptyViewMidday = (ImageView) findViewById(R.id.image_empty_recyclerview_midi);
        emptyViewMorning = (ImageView) findViewById(R.id.image_empty_recyclerview_matin);

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
        MedicineAdapter medAdapterMorning = new MedicineAdapter(this, medicineList, "morning");

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
                    if (medAdapterMorning.getItemCount() == 0) {
                        recyclerSoir.setVisibility(View.GONE);
                        emptyViewMorning.setVisibility(View.VISIBLE);
                    }
                    else {
                        recyclerSoir.setVisibility(View.VISIBLE);
                        emptyViewMorning.setVisibility(View.GONE);
                    }
                }
            }
        });

        // Recycleur Midi

        Query queryMidday = firebaseFirestore.collection("users").document(userID).collection("planning").whereArrayContains("prescription", "midday");
        MedicineAdapter medAdapterMidday = new MedicineAdapter(this, medicineListMidday, "midday");

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
                    if (medAdapterMidday.getItemCount() == 0) {
                        recyclerSoir.setVisibility(View.GONE);
                        emptyViewMidday.setVisibility(View.VISIBLE);
                    }
                    else {
                        recyclerSoir.setVisibility(View.VISIBLE);
                        emptyViewMidday.setVisibility(View.GONE);
                    }
                }
            }
        });

        // Recycleur Soir

        Query queryEvening = firebaseFirestore.collection("users").document(userID).collection("planning").whereArrayContains("prescription", "evening");
        MedicineAdapter medAdapterEvening = new MedicineAdapter(this, medicineListEvening, "evening");

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
                    if (medAdapterEvening.getItemCount() == 0) {
                        recyclerSoir.setVisibility(View.GONE);
                        emptyViewEvening.setVisibility(View.VISIBLE);
                    }
                    else {
                        recyclerSoir.setVisibility(View.VISIBLE);
                        emptyViewEvening.setVisibility(View.GONE);
                    }
                }
            }
        });

        // End FireStore Recycleur view

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
            case R.id.button_add_medicine:
                startActivity(new Intent(this, AddMedicine.class));
                break;
            case R.id.covid_stats_button:
                Intent viewIntent = new Intent("android.intent.action.VIEW", Uri.parse("https://www.gouvernement.fr/info-coronavirus/carte-et-donnees"));
                startActivity(viewIntent);
                break;
            case R.id.vaccination_button:
                Intent viewIntentVaccin = new Intent("android.intent.action.VIEW", Uri.parse("https://www.sante.fr/cf/centres-vaccination-covid.html"));
                startActivity(viewIntentVaccin);
                break;
        }
    }
}


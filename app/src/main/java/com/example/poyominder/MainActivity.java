package com.example.poyominder;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    GoogleApiClient mGoogleApiClient;
    private TextView register, forgot_password;
    private EditText editTextEmail, editTextPassword;
    private Button login_button;
    private ProgressBar progressBar_login;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        register = (TextView) findViewById(R.id.register);
        register.setOnClickListener(this);

        login_button = (Button) findViewById(R.id.login);
        login_button.setOnClickListener(this);

        editTextEmail = (EditText) findViewById(R.id.email);
        editTextPassword = (EditText) findViewById(R.id.password);

        forgot_password = (TextView) findViewById(R.id.forgot_password);
        forgot_password.setOnClickListener(this);

        progressBar_login = (ProgressBar) findViewById(R.id.progressBar_login);

        // Modification du 22/06

        //GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
        //        .requestEmail()
        //        .build();

        //mGoogleApiClient = new GoogleApiClient.Builder(this)
        //        .enableAutoManage(this, (GoogleApiClient.OnConnectionFailedListener) this)
        //        .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
        //        .build();


        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.register:
                startActivity(new Intent(this, RegisterUser.class));

            case R.id.login:
                userLogin();
                break;

            case R.id.forgot_password:
                startActivity(new Intent(this, ForgotPassword.class));
                break;
        }
    }

    private void userLogin() {
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        if (email.isEmpty()) {
            editTextEmail.setError("Votre email ne peux pas être vide !");
            editTextEmail.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editTextEmail.setError("Veuillez renseignez un email valide !");
            editTextEmail.requestFocus();
            return;
        }

        if (password.isEmpty()) {
            editTextPassword.setError("Votre mot de passe ne peux pas être vide !");
            editTextPassword.requestFocus();
            return;
        }

        if (password.length() < 6) {
            editTextPassword.setError("Votre mot de passe ne peux pas être inférieur à 6 caractères!");
            editTextPassword.requestFocus();
            return;
        }

        progressBar_login.setVisibility(View.VISIBLE);

        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    if (user.isEmailVerified()) {
                        updateUser(user.getUid());
                        //Redirect to profile
                    } else {
                        user.sendEmailVerification();
                        Toast.makeText(MainActivity.this, "Vérifier votre email avant de vous connecté!", Toast.LENGTH_LONG).show();
                        progressBar_login.setVisibility(View.INVISIBLE);
                    }
                } else {
                    Toast.makeText(MainActivity.this, "Echec de connexion, vérifier vos identifiants !", Toast.LENGTH_LONG).show();
                    progressBar_login.setVisibility(View.INVISIBLE);
                }
            }
        });
    }

    public void updateUser(String id) {
        FirebaseFirestore.getInstance().collection("users").document(id).collection("planning").get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        for (Integer i = 0; i < queryDocumentSnapshots.size(); i++) {
                            DocumentSnapshot document = queryDocumentSnapshots.getDocuments().get(i);
                            Medicine medoc = document.toObject(Medicine.class);
                            if (Timestamp.now().toDate().compareTo(medoc.until.toDate()) > 0) {
                                FirebaseFirestore.getInstance().collection("users").document(id).collection("planning").document(medoc.id).delete();
                            } else if (Timestamp.now().toDate().getDay() != medoc.lastUpdated.toDate().getDay() || Timestamp.now().toDate().getMonth() != medoc.lastUpdated.toDate().getMonth()) {
                                for (Integer k = 0; k < medoc.hasPrisSonMedoc.size(); k++) {
                                    //TODO ajouter if pour checker si pas pris checker le last updated avec le nombre de jours
                                    medoc.hasPrisSonMedoc.set(k, false);
                                }
                                FirebaseFirestore.getInstance().collection("users").document(id).collection("planning").document(medoc.id).update("hasPrisSonMedoc", medoc.hasPrisSonMedoc);
                                FirebaseFirestore.getInstance().collection("users").document(id).collection("planning").document(medoc.id).update("lastUpdated", Timestamp.now());
                            }
                            if (i == queryDocumentSnapshots.size() - 1) {
                                startActivity(new Intent(MainActivity.this, ProfileActivity.class));
                            }
                        }
                        if (queryDocumentSnapshots.size() == 0) {
                            startActivity(new Intent(MainActivity.this, ProfileActivity.class));
                        }
                    }
                });
    }
//}


}


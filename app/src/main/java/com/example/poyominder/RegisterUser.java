package com.example.poyominder;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.Task;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class RegisterUser extends AppCompatActivity implements View.OnClickListener {

    private String userID;

    private TextView return_login, inscription;
    private EditText editTextEmail, editTextUsername, editTextPassword, editTextConfirm_password;
    private Button register_button;
    private ProgressBar progressBar_register;
    private TabLayout tabLayout;
    private ImageView imageInscription;

    float v=0;

    private FirebaseAuth mAuth;
    private FirebaseFirestore fStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        //Initialisation des variables
        return_login = (TextView) findViewById(R.id.return_login);
        return_login.setOnClickListener(this);

        register_button = (Button) findViewById(R.id.register_button);
        register_button.setOnClickListener(this);

        inscription = (TextView) findViewById(R.id.inscription);

        imageInscription = (ImageView) findViewById(R.id.imageInscription);

        editTextEmail = (EditText) findViewById(R.id.email);
        editTextUsername = (EditText) findViewById(R.id.username);
        editTextPassword = (EditText) findViewById(R.id.password);
        editTextConfirm_password = (EditText) findViewById(R.id.confirm_password);

        progressBar_register = (ProgressBar) findViewById(R.id.progressBar_register);


        tabLayout = findViewById(R.id.tab_layout);

        tabLayout.setTranslationY(300);
        inscription.setTranslationY(300);
        editTextEmail.setTranslationY(300);
        editTextUsername.setTranslationY(300);
        editTextPassword.setTranslationY(300);
        editTextConfirm_password.setTranslationY(300);
        register_button.setTranslationY(300);
        return_login.setTranslationY(300);
        imageInscription.setTranslationY(300);

        tabLayout.setAlpha(v);
        inscription.setAlpha(v);
        editTextEmail.setAlpha(v);
        editTextUsername.setAlpha(v);
        editTextPassword.setAlpha(v);
        editTextConfirm_password.setAlpha(v);
        register_button.setAlpha(v);
        return_login.setAlpha(v);
        imageInscription.setAlpha(v);

        imageInscription.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(50).start();
        tabLayout.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(100).start();
        inscription.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(250).start();
        editTextEmail.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(400).start();
        editTextUsername.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(550).start();
        editTextPassword.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(700).start();
        editTextConfirm_password.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(850).start();
        register_button.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(1000).start();
        return_login.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(1150).start();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.return_login:
                startActivity(new Intent(this, MainActivity.class));
                break;

            case R.id.register_button:
                registerUser();
                break;
        }
    }

    private void registerUser() {
        String email = editTextEmail.getText().toString().trim();
        String username = editTextUsername.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        String confirm_password = editTextConfirm_password.getText().toString().trim();

        if (username.isEmpty()) {
            editTextUsername.setError("Votre nom d'utilisateur ne peux pas être vide !");
            editTextUsername.requestFocus();
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

        if (confirm_password.isEmpty()) {
            editTextConfirm_password.setError("Votre confirmation de mot de passe ne peux pas être vide !");
            editTextConfirm_password.requestFocus();
            return;
        }

        if (!confirm_password.matches(password)) {
            editTextConfirm_password.setError("Votre confirmation de mot de passe doit être similaire a votre mot de passe !");
            editTextConfirm_password.requestFocus();
            return;
        }

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

        progressBar_register.setVisibility(View.VISIBLE);

        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener((Task<AuthResult> task) -> {

            if (task.isSuccessful()) {

                User user = new User(username, email, password);

                Toast.makeText(RegisterUser.this, "Compte crée avec succès !", Toast.LENGTH_LONG).show();
                userID = mAuth.getCurrentUser().getUid();
                DocumentReference documentReference = fStore.collection("users").document(userID);
                Map<String, Object> map = new HashMap<>();
                map.put("username", user.username);
                map.put("email", user.email);
                map.put("id", userID);
                FirebaseFirestore.getInstance().collection("users").document(FirebaseAuth.getInstance().getCurrentUser().getUid()).set(map);
                progressBar_register.setVisibility(View.INVISIBLE);
                startActivity(new Intent(this, MainActivity.class));
            } else {
                System.out.println("Non");
                Toast.makeText(RegisterUser.this, "Echec lors de la création du compte !", Toast.LENGTH_LONG).show();
                progressBar_register.setVisibility(View.INVISIBLE);
            }
        });

        /*mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        //@org.jetbrains.annotations.NotNull after Non NUll

                        if (task.isSuccessful()) {
                            User user = new User(username, email, password);

                            HashMap map = new HashMap();
                            map.put("username", user.username);
                            map.put("email", user.email);
                            map.put("id", FirebaseAuth.getInstance().getCurrentUser().getUid());

                            FirebaseFirestore.getInstance().collection("users").document(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .set(map).addOnCompleteListener(new OnCompleteListener<Void>() {

                            //FirebaseDatabase.getInstance().getReference("Users")
                            //        .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                            //        .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {

                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        System.out.println("OUI");
                                        Toast.makeText(RegisterUser.this, "Compte crée avec succès !", Toast.LENGTH_LONG).show();
                                        progressBar_register.setVisibility(View.INVISIBLE);
                                    }else{
                                        System.out.println("Non");
                                        Toast.makeText(RegisterUser.this, "Echec lors de la création du compte !", Toast.LENGTH_LONG).show();
                                        progressBar_register.setVisibility(View.INVISIBLE);
                                    }
                                }
                            });

                        }else{
                            Toast.makeText(RegisterUser.this, "Echec lors de la création du compte !", Toast.LENGTH_LONG).show();
                            progressBar_register.setVisibility(View.INVISIBLE);
                        }
                    }
        });*/
    }
}
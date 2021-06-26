package com.example.poyominder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Pattern;

public class RegisterUser extends AppCompatActivity implements View.OnClickListener{

    private TextView return_login;
    private EditText editTextEmail, editTextUsername, editTextPassword, editTextConfirm_password;
    private Button register_button;
    private ProgressBar progressBar_register;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        //Initialisation des variables
        return_login = (TextView) findViewById(R.id.return_login);
        return_login.setOnClickListener(this);

        register_button = (Button) findViewById(R.id.register_button);
        register_button.setOnClickListener(this);

        editTextEmail = (EditText) findViewById(R.id.email);
        editTextUsername = (EditText) findViewById(R.id.username);
        editTextPassword = (EditText) findViewById(R.id.password);
        editTextConfirm_password = (EditText) findViewById(R.id.confirm_password);

        progressBar_register = (ProgressBar) findViewById(R.id.progressBar_register);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
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
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        //@org.jetbrains.annotations.NotNull after Non NUll

                        if (task.isSuccessful()) {
                            User user = new User(username, email, password);

                            FirebaseDatabase.getInstance().getReference("Users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(RegisterUser.this, "Compte crée avec succès !", Toast.LENGTH_LONG).show();
                                        progressBar_register.setVisibility(View.INVISIBLE);
                                    }else{
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
                });
    }
}
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
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPassword extends AppCompatActivity {

    private EditText emailEditText;
    private Button resetPasswordButton;
    private ProgressBar progressBarResetPassword;

    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        emailEditText = (EditText) findViewById(R.id.reset_email_entry);
        resetPasswordButton = (Button) findViewById(R.id.reset_password_button);
        progressBarResetPassword = (ProgressBar) findViewById(R.id.progressBar_reset_password);

        auth = FirebaseAuth.getInstance();

        resetPasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetPassword();
            }
        });
    }

    private void resetPassword() {
        String email = emailEditText.getText().toString().trim();

        if (email.isEmpty()) {
            emailEditText.setError("Votre email ne peux pas être vide !");
            emailEditText.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailEditText.setError("Veuillez renseignez un email valide !");
            emailEditText.requestFocus();
            return;
        }

        progressBarResetPassword.setVisibility(View.VISIBLE);
        auth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(ForgotPassword.this, "Email de réinitialisation envoyé !", Toast.LENGTH_LONG).show();
                    progressBarResetPassword.setVisibility(View.INVISIBLE);
                    startActivity(new Intent(ForgotPassword.this, MainActivity.class));
                }else{
                    Toast.makeText(ForgotPassword.this, "Erreur lors de l'envois du mail, réessayez !", Toast.LENGTH_LONG).show();
                    progressBarResetPassword.setVisibility(View.INVISIBLE);
                }
            }
        });

    }
}
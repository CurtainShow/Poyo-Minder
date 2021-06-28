package com.example.poyominder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    private TextView register, forgot_password;
    private EditText editTextEmail, editTextPassword;
    private Button login_button, google_button_signin;
    private ProgressBar progressBar_login;

    GoogleApiClient mGoogleApiClient;

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

        google_button_signin = (Button) findViewById(R.id.button_google_signin);
        google_button_signin.setOnClickListener(this);

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
        switch (v.getId()){
            case R.id.register:
                startActivity(new Intent(this, RegisterUser.class));

            case R.id.login:
                userLogin();
                break;

            case R.id.forgot_password:
                startActivity(new Intent(this, ForgotPassword.class));
                break;

            //case R.id.button_google_signin:
            //    loginGoogle();
            //    break;
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
                    if (user.isEmailVerified()){
                        //Redirect to profile
                        startActivity(new Intent(MainActivity.this, ProfileActivity.class));
                    }else{
                        user.sendEmailVerification();
                        Toast.makeText(MainActivity.this, "Vérifier votre email avant de vous connecté!", Toast.LENGTH_LONG).show();
                    }
                }else{
                    Toast.makeText(MainActivity.this, "Echec de connexion, vérifier vos identifiants !",Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    //private void loginGoogle() {
    //    Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
    //    startActivity(new Intent(MainActivity.this, ProfileActivity.class));
    //}

}
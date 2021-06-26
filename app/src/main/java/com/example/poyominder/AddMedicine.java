package com.example.poyominder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class AddMedicine extends AppCompatActivity implements View.OnClickListener {

    private Button back_main;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_medicine);

        back_main = (Button) findViewById(R.id.back_to_main);
        back_main.setOnClickListener(this);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_to_main:
                startActivity(new Intent(this, ProfileActivity.class));
        }
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
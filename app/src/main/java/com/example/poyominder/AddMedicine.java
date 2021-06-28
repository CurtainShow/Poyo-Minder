package com.example.poyominder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Spinner;

public class AddMedicine extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_medicine);

        Button back_main = findViewById(R.id.back_to_main);
        back_main.setOnClickListener(this);

        Spinner spinner = findViewById(R.id.spinner_medicament);
        spinner.setOnItemSelectedListener(this);

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.types_medicament, android.R.layout.simple_spinner_item);

        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);
    }

    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
        // An item was selected. You can retrieve the selected item using
        // parent.getItemAtPosition(pos)
    }

    public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback
    }

    public void onCheckboxClicked(View view) {
        // Is the view now checked?
        boolean checked = ((CheckBox) view).isChecked();

        // Check which checkbox was clicked
        switch (view.getId()) {
            case R.id.checkbox_matin:
                if (checked) {
                    // Put some meat on the sandwich
                } else
                    // Remove the meat
                    break;
            case R.id.checkbox_midi:
                if (checked) {
                    // Cheese me
                } else
                    // I'm lactose intolerant
                    break;
            case R.id.checkbox_soir:
                if (checked) {
                    // yeet
                } else
                    // poponais
                    break;
                // TODO: Veggie sandwich
        }
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
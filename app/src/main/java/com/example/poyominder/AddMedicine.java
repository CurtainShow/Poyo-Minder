package com.example.poyominder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.widget.EditText;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Spinner;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;


public class AddMedicine extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    private EditText editTextNomMedicament;
    private EditText editTextRappelMedicament;
    private CheckBox checkBoxMatin, checkBoxMidi, checkBoxSoir;
    private Spinner spinnerTypeMedicament;


    String typeMedicament;

    boolean matinMedicament;
    boolean midiMedicament;
    boolean soirMedicament;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_medicine);

        Button back_main = findViewById(R.id.back_to_main);
        back_main.setOnClickListener(this);

        Button addMedicine = findViewById(R.id.button_add_medicine);
        addMedicine.setOnClickListener(this);

        // récupérer le nom
        editTextNomMedicament = (EditText) findViewById(R.id.name_medicament);

        // récupérer le type ?
        Spinner spinner = findViewById(R.id.spinner_medicament);
        spinner.setOnItemSelectedListener(this);

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.types_medicament, android.R.layout.simple_spinner_item);

        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);

        spinnerTypeMedicament = spinner;

        checkBoxMatin = (CheckBox) findViewById(R.id.checkbox_matin);

        checkBoxMidi = (CheckBox) findViewById(R.id.checkbox_midi);

        checkBoxSoir = (CheckBox) findViewById(R.id.checkbox_soir);

        matinMedicament = checkBoxMatin.isSelected();
        midiMedicament = checkBoxMidi.isSelected();
        soirMedicament = checkBoxSoir.isSelected();

        editTextRappelMedicament = (EditText) findViewById(R.id.rappel_input);


    }

    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
        // An item was selected. You can retrieve the selected item using
        // parent.getItemAtPosition(pos)
        typeMedicament = parent.getItemAtPosition(pos).toString();
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
                    matinMedicament = true;
                } else
                    matinMedicament = false;
                    break;
            case R.id.checkbox_midi:
                if (checked) {
                    boolean midiMedicament = true;
                } else
                    midiMedicament = false;
                    break;
            case R.id.checkbox_soir:
                if (checked) {
                    soirMedicament = true;
                } else
                    soirMedicament = false;
                    break;
                // TODO: Veggie sandwich
        }
    }


    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_to_main:
                startActivity(new Intent(this, ProfileActivity.class));
            case R.id.button_add_medicine:
                onComplete();
                startActivity(new Intent(this, ProfileActivity.class));
        }
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    public void onComplete() {
            String nomMedicament = editTextNomMedicament.getText().toString().trim();
            String rappelMedicament = editTextRappelMedicament.getText().toString().trim();


            Medicine medicine = new Medicine(nomMedicament, typeMedicament, matinMedicament, midiMedicament, soirMedicament, rappelMedicament);



    }


}
package com.example.poyominder;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class AddMedicine extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    String typeMedicament;
    boolean matinMedicament;
    boolean midiMedicament;
    boolean soirMedicament;
    private EditText editTextNomMedicament;
    private EditText editTextRappelMedicament;
    private CheckBox checkBoxMatin, checkBoxMidi, checkBoxSoir;
    private Spinner spinnerTypeMedicament;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_medicine);

        Button back_main = findViewById(R.id.back_to_main);
        back_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AddMedicine.this, ProfileActivity.class));
            }
        });

        Button addMedicine = findViewById(R.id.button_add_medicine);
        addMedicine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onComplete();
                startActivity(new Intent(AddMedicine.this, ProfileActivity.class));
            }
        });

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
                matinMedicament = checked;
                break;
            case R.id.checkbox_midi:
                midiMedicament = checked;
                break;
            case R.id.checkbox_soir:
                soirMedicament = checked;
                break;
        }
    }

    public void onComplete() {
        String nomMedicament = editTextNomMedicament.getText().toString().trim();
        String descMedoc = editTextRappelMedicament.getText().toString().trim();

        ArrayList<String> prise = new ArrayList<>();
        ArrayList<Boolean> hasPrisSonMedoc = new ArrayList<>();


        DocumentReference medocRef = FirebaseFirestore.getInstance().collection("users").document(FirebaseAuth.getInstance().getUid()).collection("planning").document();
        Medicine medicine = new Medicine(nomMedicament, typeMedicament, descMedoc, hasPrisSonMedoc, prise, medocRef.getId(), Timestamp.now(), Timestamp.now());
        Map<String, Object> map = new HashMap<>();
        map.put("id", medicine.id);
        map.put("type", medicine.type);
        map.put("name", medicine.name);

        if (checkBoxMatin.isChecked()) {
            prise.add("morning");
            hasPrisSonMedoc.add(false);
        }
        if (checkBoxMidi.isChecked()) {
            prise.add("midday");
            hasPrisSonMedoc.add(false);
        }
        if (checkBoxSoir.isChecked()) {
            prise.add("evening");
            hasPrisSonMedoc.add(false);
        }

        map.put("prescription", prise);
        map.put("hasPrisSonMedoc", hasPrisSonMedoc);
        map.put("description", medicine.description);
        map.put("until", medicine.until);
        map.put("lastUpdated", medicine.lastUpdated);

        medocRef.set(map);

        System.out.println(nomMedicament);
        System.out.println(typeMedicament);
        System.out.println(matinMedicament);
        System.out.println(midiMedicament);
        System.out.println(soirMedicament);
        System.out.println(descMedoc);
    }


}
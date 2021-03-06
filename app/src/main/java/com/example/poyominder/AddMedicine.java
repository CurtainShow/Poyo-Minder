package com.example.poyominder;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.datatransport.runtime.time.TimeModule_EventClockFactory;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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

    private DatePickerDialog datePickerDialog;
    private Button dateUntilButton;
    private Date dateSelected;

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
                Toast.makeText(AddMedicine.this, "Votre m??dicament a bien ??t?? cr??er !", Toast.LENGTH_LONG).show();
            }
        });

        // r??cup??rer le nom
        editTextNomMedicament = (EditText) findViewById(R.id.name_medicament);

        // r??cup??rer le type ?
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

        initDatePicker();
        dateUntilButton = findViewById(R.id.date_Picker_Untill_Button);
        dateUntilButton.setText(getTodayDate());
    }

    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
        // An item was selected. You can retrieve the selected item using
        // parent.getItemAtPosition(pos)
        typeMedicament = parent.getItemAtPosition(pos).toString();
    }

    public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback


    }

    private void initDatePicker() {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month + 1;
                String date = makeDateString(dayOfMonth, month, year);
                try {
                    dateSelected = new SimpleDateFormat("dd/MM/yyyy").parse(dayOfMonth + "/" + month + "/" + year);
                    System.out.println("date chang??e");
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                dateUntilButton.setText(date);
            }
        };

        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        int style = AlertDialog.THEME_HOLO_LIGHT;

        datePickerDialog = new DatePickerDialog(this, style, dateSetListener, year, month, day);
        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);

    }

    private String makeDateString(int dayOfMonth, int month, int year) {
        return dayOfMonth + " " + getMonthFormat(month) + " " + year;
    }

    private String getMonthFormat(int month) {
        if (month == 1)
            return "Janvier";
        else if (month == 2)
            return "F??vrier";
        else if (month == 3)
            return "Mars";
        else if (month == 4)
            return "Avril";
        else if (month == 5)
            return "Mai";
        else if (month == 6)
            return "Juin";
        else if (month == 7)
            return "Juillet";
        else if (month == 8)
            return "Ao??t";
        else if (month == 9)
            return "Septembre";
        else if (month == 10)
            return "Octobre";
        else if (month == 11)
            return "Novembre";
        else if (month == 12)
            return "D??cembre";

        return "JANVIER";
    }

    public void openDatePicker(View view) {
        datePickerDialog.show();
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

    private String getTodayDate() {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        month = month + 1;
        int day = cal.get(Calendar.DAY_OF_MONTH);
        return makeDateString(day, month, year);
    }

    public void onComplete() {
        String nomMedicament = editTextNomMedicament.getText().toString().trim();
        String descMedoc = editTextRappelMedicament.getText().toString().trim();
        System.out.println();
        Date date = new Date();
        dateSelected.setHours(21);
        dateSelected.setMinutes(59);
        Timestamp until = new Timestamp(dateSelected);

        ArrayList<String> prise = new ArrayList<>();
        ArrayList<Boolean> hasPrisSonMedoc = new ArrayList<>();


        DocumentReference medocRef = FirebaseFirestore.getInstance().collection("users").document(FirebaseAuth.getInstance().getUid()).collection("planning").document();
        Medicine medicine = new Medicine(nomMedicament, typeMedicament, descMedoc, hasPrisSonMedoc, prise, medocRef.getId(), until, Timestamp.now());
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
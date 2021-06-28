package com.example.poyominder;

public class Medicine {

    public String nomMedicament;
    public String typeMedicament;
    public boolean matinMedicament;
    public boolean midiMedicament;
    public boolean soirMedicament;
    public String rappelMedicament;

    public Medicine(){

    }

    public Medicine(String nomMedicament, String typeMedicament, boolean matinMedicament, boolean midiMedicament, boolean soirMedicament, String rappelMedicament){

        this.nomMedicament = nomMedicament;
        this.typeMedicament = typeMedicament;
        this.rappelMedicament = rappelMedicament;
        this.matinMedicament = matinMedicament;
        this.midiMedicament = midiMedicament;
        this.soirMedicament = soirMedicament;

    }
}

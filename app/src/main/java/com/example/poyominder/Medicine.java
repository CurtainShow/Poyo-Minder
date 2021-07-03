package com.example.poyominder;

import com.google.firebase.Timestamp;

import java.util.ArrayList;

public class Medicine {

    public String name;
    public String type;
    public ArrayList<Boolean> hasPrisSonMedoc;
    public String id;
    public ArrayList<String> prescription;
    public Timestamp until;
    public String description;
    public Timestamp lastUpdated;

    public Medicine() {

    }

    public Medicine(String name, String type, String description, ArrayList<Boolean> hasPrisSonMedoc, ArrayList<String> prescription, String id, Timestamp until, Timestamp lastUpdated) {

        this.name = name;
        this.type = type;
        this.hasPrisSonMedoc = hasPrisSonMedoc;
        this.description = description;
        this.id = id;
        this.prescription = prescription;
        this.until = until;
        this.lastUpdated = lastUpdated;
    }
}

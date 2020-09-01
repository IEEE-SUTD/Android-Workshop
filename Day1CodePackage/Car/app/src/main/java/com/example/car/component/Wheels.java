package com.example.car.component;

import android.util.Log;

// Consists of rims & tyres
public class Wheels {

    private static final String TAG = "MainCar";

    private Rims rims;
    private Tyres tyres;

    public Wheels(Rims rims, Tyres tyres) {
        this.rims = rims;
        this.tyres = tyres;
    }

    public static void moving() {
        Tyres.inflate();
        Log.d(TAG, "moving...");
    }

    public static void stopped() {
        Log.d(TAG, "stopped...");
    }

}

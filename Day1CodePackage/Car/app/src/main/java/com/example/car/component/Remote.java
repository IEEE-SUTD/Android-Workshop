package com.example.car.component;

import android.util.Log;

public class Remote {

    private static final String TAG = "MainCar";

    public static void unlock() {
        Log.d(TAG, "Car unlocked");
    }

    public static void lock() {
        Log.d(TAG, "Car locked");
    }
}

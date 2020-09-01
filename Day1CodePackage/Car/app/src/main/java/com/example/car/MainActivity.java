package com.example.car;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.car.constant.EngineStatus;

public class MainActivity extends AppCompatActivity {

    private EngineStatus status = EngineStatus.OFF;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        CarComponent.setStatus(status);

        switch (status) {
            case ON:
                CarComponent.moving();
                break;
            case OFF:
                CarComponent.stopped();
                break;
            default:
                break;
        }

//        if (CarComponent.getStatus() == EngineStatus.OFF) {
//            CarComponent.moving();
//        } else {
//            CarComponent.stopped();
//        }
    }
}

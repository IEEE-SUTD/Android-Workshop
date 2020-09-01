package com.example.car.component;

import com.example.car.CarEngine;
import com.example.car.constant.EngineStatus;

// Building the Engine from CarEngine Model
public class Engine implements CarEngine {

    private EngineStatus status; //On or Off

    public Engine() {}

    @Override
    public EngineStatus getStatus() {
        return status;
    }

    @Override
    public void setStatus(EngineStatus status) {
        this.status = status;
    }
}

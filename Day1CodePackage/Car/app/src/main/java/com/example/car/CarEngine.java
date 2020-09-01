package com.example.car;

import com.example.car.constant.EngineStatus;

// inherited CarEngine Model from other suppliers
public interface CarEngine {

    EngineStatus getStatus();

    void setStatus(EngineStatus status);
}

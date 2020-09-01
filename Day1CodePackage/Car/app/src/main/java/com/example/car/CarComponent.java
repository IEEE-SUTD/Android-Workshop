package com.example.car;

import com.example.car.component.Engine;
import com.example.car.component.Remote;
import com.example.car.component.Wheels;
import com.example.car.constant.EngineStatus;

// Contains engine, wheels
public interface CarComponent {

    Engine engine = new Engine();

    static void setStatus(EngineStatus status) {
        engine.setStatus(status);
    }

    static EngineStatus getStatus() {
        return engine.getStatus();
    }

    static void moving() {
        Remote.unlock();
        Wheels.moving();
    }

    static void stopped() {
        Wheels.stopped();
        Remote.lock();
    }
}
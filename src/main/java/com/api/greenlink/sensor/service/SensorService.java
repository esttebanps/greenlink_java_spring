package com.api.greenlink.sensor.service;

import com.api.greenlink.sensor.dto.SensorInput;
import com.api.greenlink.sensor.dto.SensorResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public interface SensorService {
    SensorResponse addSensor(SensorInput sensorInput);
    Map<String, Object> getAllSensors(int page, int size);
    SensorResponse findById(Long id);
    SensorResponse updateSensor(Long id, SensorInput sensorInput);
    void deleteSensor(Long id);
}

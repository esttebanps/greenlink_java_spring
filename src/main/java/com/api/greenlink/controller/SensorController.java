package com.api.greenlink.controller;

import com.api.greenlink.dto.SensorInput;
import com.api.greenlink.dto.SensorResponse;
import com.api.greenlink.entity.Sensor;
import com.api.greenlink.service.SensorService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/es")
@AllArgsConstructor
public class SensorController {

    private final SensorService sensorService;

    @PostMapping("/sensors")
    public ResponseEntity<SensorResponse> postSensor(@RequestBody SensorInput sensorInput) {
        SensorResponse newSensor = sensorService.addSensor(sensorInput);
        return ResponseEntity.status(HttpStatus.CREATED).body(newSensor);
    }

    @GetMapping("/sensors")
    public ResponseEntity<List<SensorResponse>> getAllSensors() {
        return ResponseEntity.ok(sensorService.getAllSensors());
    }

    @GetMapping("/sensors/{id}")
    public ResponseEntity<SensorResponse> getSensorById(@PathVariable Long id) {
        return ResponseEntity.ok(sensorService.findById(id));
    }

    @PutMapping("/sensors/{id}")
    public ResponseEntity<SensorResponse> updateSensor(@PathVariable Long id, @RequestBody SensorInput sensorInput) {
        return ResponseEntity.status(HttpStatus.CREATED).body(sensorService.updateSensor(id, sensorInput));
    }

    @DeleteMapping("/sensors/{id}")
    public ResponseEntity<SensorResponse> deleteSensor(@PathVariable Long id) {
        sensorService.deleteSensor(id);
        return ResponseEntity.noContent().build();
    }
}

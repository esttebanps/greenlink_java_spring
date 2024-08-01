package com.api.greenlink.sensor.controller;

import com.api.greenlink.sensor.dto.SensorInput;
import com.api.greenlink.sensor.dto.SensorResponse;
import com.api.greenlink.sensor.service.SensorService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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
    public ResponseEntity<Map<String, Object>> getAllSensors(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size)
    {
        return ResponseEntity.ok(sensorService.getAllSensors(page, size));
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

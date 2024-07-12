package com.api.greenlink.controller;

import com.api.greenlink.dto.SensorInput;
import com.api.greenlink.entity.Sensor;
import com.api.greenlink.service.SensorService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/es")
@AllArgsConstructor
public class SensorController {

    private final SensorService sensorService;

    @PostMapping("/sensors")
    public ResponseEntity<Sensor> postSensor(@RequestBody SensorInput sensorInput) throws HttpMessageNotReadableException {

        Sensor newSensor = sensorService.addSensor(sensorInput);

        return ResponseEntity.ok(newSensor);

    }
}

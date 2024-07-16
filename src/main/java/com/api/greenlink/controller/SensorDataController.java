package com.api.greenlink.controller;

import com.api.greenlink.dto.DataInput;
import com.api.greenlink.dto.DataResponse;
import com.api.greenlink.entity.SensorData;
import com.api.greenlink.service.SensorDataService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/es")
@AllArgsConstructor
public class SensorDataController {

    private final SensorDataService registrationService;

    @PostMapping("/sensors/{id}/data")
    public ResponseEntity<SensorData> addSensorRegistration(@RequestBody DataInput dataInput, @PathVariable Long id){
        return ResponseEntity.status(HttpStatus.CREATED).body(registrationService.addSensorRegistration(dataInput, id));
    }

    @GetMapping("/sensors/{id}/data")
    public ResponseEntity<List<DataResponse>> getAllSensorRegistrations(@PathVariable Long id){
        return ResponseEntity.ok(registrationService.getRegistrationsById(id));
    }
}

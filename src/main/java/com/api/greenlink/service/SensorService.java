package com.api.greenlink.service;

import com.api.greenlink.config.error.NotFoundException;
import com.api.greenlink.dto.SensorInput;
import com.api.greenlink.dto.SensorResponse;
import com.api.greenlink.entity.Sensor;
import com.api.greenlink.entity.enums.TypeSensor;
import com.api.greenlink.repository.SensorRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class SensorService {

    private final SensorRepository sensorRepository;

    public Sensor addSensor(SensorInput sensorInput) {
        Sensor newSensor = new Sensor();

        newSensor.setName_sensor(sensorInput.getName());
        newSensor.setSensor_type(TypeSensor.valueOf(sensorInput.getType()));
        newSensor.setModel_sensor(sensorInput.getModel());

        return sensorRepository.save(newSensor);
    }

    public List<SensorResponse> getAllSensors() {
        List<Sensor> sensors = sensorRepository.findAll().stream().toList();
        List<SensorResponse> responses = new ArrayList<>();


        if (sensors.isEmpty()) {
            throw new NotFoundException();
        }

        for (Sensor sensor : sensors) {
            SensorResponse response = new SensorResponse();
            response.setName(sensor.getName_sensor());
            response.setType(String.valueOf(sensor.getSensor_type()));
            response.setModel(sensor.getModel_sensor());
            response.setCreated_at(sensor.getCreated_at());
            responses.add(response);
        }

        return responses;
    }

    public SensorResponse findById(Long id) {
        SensorResponse response = new SensorResponse();
        Optional<Sensor> sensor = sensorRepository.findById(id);

        if (sensor.isEmpty()) {
            throw new NotFoundException();
        }

        response.setName(sensor.get().getName_sensor());
        response.setType(String.valueOf(sensor.get().getSensor_type()));
        response.setModel(sensor.get().getModel_sensor());
        response.setCreated_at(sensor.get().getCreated_at());
        return response;
    }

    public SensorResponse updateSensor(Long id, SensorInput sensorInput) {
        SensorResponse response = new SensorResponse();
        Optional<Sensor> sensor = sensorRepository.findById(id);

        if (sensor.isEmpty()) {
            throw new NotFoundException();
        }

        Sensor updatedSensor = sensor.get();
        updatedSensor.setName_sensor(sensorInput.getName());
        updatedSensor.setSensor_type(TypeSensor.valueOf(sensorInput.getType()));
        updatedSensor.setModel_sensor(sensorInput.getModel());

        Sensor savedSensor = sensorRepository.save(updatedSensor);

        response.setName(savedSensor.getName_sensor());
        response.setType(String.valueOf(savedSensor.getSensor_type()));
        response.setModel(savedSensor.getModel_sensor());
        response.setCreated_at(savedSensor.getCreated_at());

        return response;
    }
}

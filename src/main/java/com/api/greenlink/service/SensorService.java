package com.api.greenlink.service;

import com.api.greenlink.config.error.NotFoundException;
import com.api.greenlink.dto.SensorInput;
import com.api.greenlink.dto.SensorResponse;
import com.api.greenlink.entity.Sensor;
import com.api.greenlink.entity.enums.TypeSensor;
import com.api.greenlink.repository.SensorRepository;
import com.api.greenlink.util.SensorMapper;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.api.greenlink.util.SensorMapper.toSensor;
import static com.api.greenlink.util.SensorMapper.toSensorResponse;

@Service
@AllArgsConstructor
public class SensorService {

    private final SensorRepository sensorRepository;

    public SensorResponse addSensor(SensorInput sensorInput) {
        Sensor newSensor = toSensor(sensorInput);
        sensorRepository.save(newSensor);
        return toSensorResponse(newSensor);
    }

    public List<SensorResponse> getAllSensors() {
        List<SensorResponse> sensors = sensorRepository
                .findAll()
                .stream()
                .map(SensorMapper::toSensorResponse)
                .toList();

        if (sensors.isEmpty()) {
            throw new NotFoundException();
        }

        return sensors;
    }

    public SensorResponse findById(Long id) {
        Optional<Sensor> sensor = sensorRepository.findById(id);

        if (sensor.isEmpty()) {
            throw new NotFoundException();
        }

        return toSensorResponse(sensor.get());
    }

    public SensorResponse updateSensor(Long id, SensorInput sensorInput) {

        Optional<Sensor> sensor = sensorRepository.findById(id);

        if (sensor.isEmpty()) {
            throw new NotFoundException();
        }

        Sensor updatedSensor = sensor.get();
        updatedSensor.setName_sensor(sensorInput.getName());
        updatedSensor.setSensor_type(TypeSensor.valueOf(sensorInput.getType()));
        updatedSensor.setModel_sensor(sensorInput.getModel());

        Sensor savedSensor = sensorRepository.save(updatedSensor);
        return toSensorResponse(savedSensor);
    }

    public void deleteSensor(Long id) {
        Optional<Sensor> sensor = sensorRepository.findById(id);

        if (sensor.isEmpty()) {
            throw new NotFoundException();
        }

        sensorRepository.deleteById(id);
    }
}

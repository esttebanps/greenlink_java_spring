package com.api.greenlink.sensor.service.impl;

import com.api.greenlink.exceptions.exception.NotFoundException;
import com.api.greenlink.sensor.dto.SensorInput;
import com.api.greenlink.sensor.dto.SensorResponse;
import com.api.greenlink.sensor.entity.Sensor;
import com.api.greenlink.sensor.enums.TypeSensor;
import com.api.greenlink.sensor.repository.SensorRepository;
import com.api.greenlink.sensor.mapper.SensorMapper;
import com.api.greenlink.sensor.service.SensorService;
import com.api.greenlink.util.ResponseConvert;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.api.greenlink.sensor.mapper.SensorMapper.toSensor;
import static com.api.greenlink.sensor.mapper.SensorMapper.toSensorResponse;

@Service
@AllArgsConstructor
public class SensorServiceImpl implements SensorService {

    private final SensorRepository sensorRepository;
    private final ResponseConvert convert;

    public SensorResponse addSensor(SensorInput sensorInput) {
        Sensor newSensor = toSensor(sensorInput);
        sensorRepository.save(newSensor);
        return toSensorResponse(newSensor);
    }

    public Map<String, Object> getAllSensors(int page, int size) {
        PageRequest pageable = PageRequest.of(page, size);
        Page<SensorResponse> sensors = sensorRepository
                .findAll(pageable)
                .map(SensorMapper::toSensorResponse);

        if (sensors.isEmpty()) {
            throw new NotFoundException("sensors not found");
        }

        return convert.toResponse(page, size, sensors);
    }

    public SensorResponse findById(Long id) {
        Optional<Sensor> sensor = sensorRepository.findById(id);

        if (sensor.isEmpty()) {
            throw new NotFoundException("sensor not found");
        }

        return toSensorResponse(sensor.get());
    }

    public SensorResponse updateSensor(Long id, SensorInput sensorInput) {

        Optional<Sensor> sensor = sensorRepository.findById(id);

        if (sensor.isEmpty()) {
            throw new NotFoundException("sensor not found");
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
            throw new NotFoundException("sensor not found");
        }

        sensorRepository.deleteById(id);
    }
}

package com.api.greenlink.service;

import com.api.greenlink.dto.SensorInput;
import com.api.greenlink.entity.Sensor;
import com.api.greenlink.entity.enums.TypeSensor;
import com.api.greenlink.repository.SensorRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

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
}

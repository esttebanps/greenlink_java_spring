package com.api.greenlink.util;

import com.api.greenlink.dto.SensorInput;
import com.api.greenlink.dto.SensorResponse;
import com.api.greenlink.entity.Sensor;
import com.api.greenlink.entity.enums.TypeSensor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class SensorMapper {
    public static SensorResponse toSensorResponse(Sensor sensor){
        SensorResponse sensorResponse = new SensorResponse();
        sensorResponse.setName(sensor.getName_sensor());
        sensorResponse.setType(String.valueOf(sensor.getSensor_type()));
        sensorResponse.setModel(sensor.getModel_sensor());
        sensorResponse.setCreated_at(sensor.getCreated_at());
        return sensorResponse;
    }

    public static Sensor toSensor(SensorInput sensorInput){
        Sensor sensor = new Sensor();
        sensor.setName_sensor(sensorInput.getName());
        sensor.setSensor_type(TypeSensor.valueOf(sensorInput.getType()));
        sensor.setModel_sensor(sensorInput.getModel());
        return sensor;
    }
}

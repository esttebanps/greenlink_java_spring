package com.api.greenlink.service;

import com.api.greenlink.config.error.NotFoundException;
import com.api.greenlink.dto.DataInput;
import com.api.greenlink.dto.DataResponse;
import com.api.greenlink.entity.Sensor;
import com.api.greenlink.entity.SensorData;
import com.api.greenlink.repository.SensorDataRepository;
import com.api.greenlink.repository.SensorRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class SensorDataService {

    private final SensorRepository sensorRepository;
    private final SensorDataRepository dataRepository;

    public SensorData addSensorRegistration(DataInput dataInput, Long id) {

        Optional<Sensor> sensor = sensorRepository.findById(id);
        if (sensor.isPresent()) {
            SensorData registration = new SensorData();
            registration.setValue(dataInput.getValue());
            registration.setSensor(sensor.get());
            return dataRepository.save(registration);
        }

        throw new NotFoundException("Sensor no encontrado");
    }

    public List<DataResponse> getRegistrationsById(Long id) {
        List<SensorData> registrations = dataRepository.findAllBySensorId(id);
        List<DataResponse> responses = new ArrayList<>();

        if (!registrations.isEmpty()) {
            for (SensorData sensorData : registrations) {
                DataResponse response = new DataResponse();
                response.setValue(sensorData.getValue());
                response.setCreated_at(sensorData.getCreated_at());
                response.setSensor_name(sensorData.getSensor().getName_sensor());
                response.setSensor_type(String.valueOf(sensorData.getSensor().getSensor_type()));
                responses.add(response);
            }
            return responses;
        }

        throw new NotFoundException("No hay datos registrados para este sensor");
    }
}

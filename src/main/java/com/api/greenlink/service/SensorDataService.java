package com.api.greenlink.service;

import com.api.greenlink.config.error.NotFoundException;
import com.api.greenlink.dto.data.DataInput;
import com.api.greenlink.dto.data.DataResponse;
import com.api.greenlink.entity.Sensor;
import com.api.greenlink.entity.SensorData;
import com.api.greenlink.repository.SensorDataRepository;
import com.api.greenlink.repository.SensorRepository;
import com.api.greenlink.util.SensorDataMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.api.greenlink.util.SensorDataMapper.toDataResponse;

@Service
@AllArgsConstructor
public class SensorDataService {

    private final SensorRepository sensorRepository;
    private final SensorDataRepository dataRepository;

    public DataResponse addSensorRegistration(DataInput dataInput, Long id) {
        Optional<Sensor> sensor = sensorRepository.findById(id);
        if (sensor.isPresent()) {
            SensorData sensorData = new SensorData();
            sensorData.setValue(dataInput.getValue());
            sensorData.setSensor(sensor.get());
            dataRepository.save(sensorData);
            return toDataResponse(sensorData);
        }
        throw new NotFoundException("Sensor no encontrado");
    }

    public List<DataResponse> getRegistrationsById(Long id) {
        List<DataResponse> registrations = dataRepository
                .findAllBySensorId(id)
                .stream()
                .map(SensorDataMapper::toDataResponse)
                .toList();

        if (registrations.isEmpty()) {
            throw new NotFoundException("No hay datos registrados para este sensor");
        }

        return registrations;
    }
}

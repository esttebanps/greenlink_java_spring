package com.api.greenlink.data.service.impl;

import com.api.greenlink.data.repository.DataRepository;
import com.api.greenlink.data.service.DataService;
import com.api.greenlink.exceptions.exception.NotFoundException;
import com.api.greenlink.data.dto.DataInput;
import com.api.greenlink.data.dto.DataResponse;
import com.api.greenlink.sensor.entity.Sensor;
import com.api.greenlink.data.entity.SensorData;
import com.api.greenlink.sensor.repository.SensorRepository;
import com.api.greenlink.data.mapper.DataMapper;
import com.api.greenlink.util.ResponseConvert;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@AllArgsConstructor
public class DataServiceImpl implements DataService {

    private final SensorRepository sensorRepository;
    private final DataRepository dataRepository;
    private final ResponseConvert convert;

    public DataResponse addSensorRegistration(DataInput dataInput, Long id) {
        Optional<Sensor> sensor = sensorRepository.findById(id);
        if (sensor.isPresent()) {
            SensorData sensorData = new SensorData();
            sensorData.setValue(dataInput.getValue());
            sensorData.setSensor(sensor.get());
            dataRepository.save(sensorData);
            return DataMapper.toDataResponse(sensorData);
        }
        throw new NotFoundException("Sensor no encontrado");
    }

    public Map<String, Object> getRegistrationsById(Long id, int page, int size) {
        PageRequest pageable = PageRequest.of(page, size);

        Page<DataResponse> registrations = dataRepository
                .findAllBySensorId(id, pageable)
                .map(DataMapper::toDataResponse);

        if (registrations.isEmpty()) {
            throw new NotFoundException("No hay datos registrados para este sensor");
        }

        return convert.toResponse(page, size, registrations);
    }
}

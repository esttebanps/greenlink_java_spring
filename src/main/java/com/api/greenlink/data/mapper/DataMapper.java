package com.api.greenlink.data.mapper;

import com.api.greenlink.data.dto.DataResponse;
import com.api.greenlink.data.entity.SensorData;
import org.springframework.stereotype.Component;

@Component
public class DataMapper {
    public static DataResponse toDataResponse(SensorData sensorData){
        DataResponse dataResponse = new DataResponse();
        dataResponse.setSensor_name(sensorData.getSensor().getName_sensor());
        dataResponse.setSensor_type(sensorData.getSensor().getModel_sensor());
        dataResponse.setValue(sensorData.getValue());
        dataResponse.setCreated_at(sensorData.getCreated_at());
        return dataResponse;
    }

}

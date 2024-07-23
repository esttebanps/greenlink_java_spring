package com.api.greenlink.util;

import com.api.greenlink.dto.DataInput;
import com.api.greenlink.dto.DataResponse;
import com.api.greenlink.dto.SensorInput;
import com.api.greenlink.entity.SensorData;
import org.springframework.stereotype.Component;

@Component
public class SensorDataMapper {
    public static DataResponse toDataResponse(SensorData sensorData){
        DataResponse dataResponse = new DataResponse();
        dataResponse.setSensor_name(sensorData.getSensor().getName_sensor());
        dataResponse.setSensor_type(sensorData.getSensor().getModel_sensor());
        dataResponse.setValue(sensorData.getValue());
        dataResponse.setCreated_at(sensorData.getCreated_at());
        return dataResponse;
    }

}

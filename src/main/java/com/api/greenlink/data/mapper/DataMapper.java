package com.api.greenlink.data.mapper;

import com.api.greenlink.data.dto.DataResponse;
import com.api.greenlink.data.entity.FlowerpotData;
import org.springframework.stereotype.Component;

@Component
public class DataMapper {
    public static DataResponse toDataResponse(FlowerpotData sensorData){
        DataResponse dataResponse = new DataResponse();
        dataResponse.setTemperature(sensorData.getTemperature());
        dataResponse.setHumidity(sensorData.getHumidity());
        dataResponse.setSun(sensorData.getSun());
        dataResponse.setCreateDate(sensorData.getCreateDate());
        return dataResponse;
    }

}

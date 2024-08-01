package com.api.greenlink.data.service;

import com.api.greenlink.data.dto.DataInput;
import com.api.greenlink.data.dto.DataResponse;

import java.util.List;
import java.util.Map;

public interface DataService {
    DataResponse addSensorRegistration(DataInput dataInput, Long id);
    Map<String, Object> getRegistrationsById(Long id, int page, int size);

}

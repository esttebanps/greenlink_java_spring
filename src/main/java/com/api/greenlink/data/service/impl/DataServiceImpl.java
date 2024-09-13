package com.api.greenlink.data.service.impl;

import com.api.greenlink.auth.repository.AuthRepository;
import com.api.greenlink.auth.security.authorization.AuthorizationManager;
import com.api.greenlink.data.repository.DataRepository;
import com.api.greenlink.data.service.DataService;
import com.api.greenlink.exceptions.exception.BadRequestException;
import com.api.greenlink.exceptions.exception.NotFoundException;
import com.api.greenlink.data.dto.DataInput;
import com.api.greenlink.data.dto.DataResponse;
import com.api.greenlink.flowerpot.entity.Flowerpot;
import com.api.greenlink.flowerpot.repository.FlowerpotRepository;
import com.api.greenlink.data.entity.FlowerpotData;
import com.api.greenlink.data.mapper.DataMapper;
import com.api.greenlink.user.entity.UserGreenlink;
import com.api.greenlink.util.ResponseConvert;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
@AllArgsConstructor
public class DataServiceImpl implements DataService {

    private final FlowerpotRepository flowerpotRepository;
    private final DataRepository dataRepository;
    private final AuthRepository authRepository;
    private final ResponseConvert convert;

    public DataResponse addDataFlowerpot(DataInput dataInput, Long id) {
        Optional<Flowerpot> flowerpot = flowerpotRepository.findById(id);
        UserGreenlink user = getAuthenticatedUser();

        if (flowerpot.isEmpty()) {
            throw new NotFoundException("flowerpot no encontrado");
        }

        if (!flowerpot.get().getUser().getId().equals(user.getId())){
            throw new BadRequestException("No tiene permisos para acceder a este flowerpot");
        }

        FlowerpotData flowerpotData = new FlowerpotData();
        flowerpotData.setTemperature(dataInput.getTemperature());
        flowerpotData.setHumidity(dataInput.getHumidity());
        flowerpotData.setSun(dataInput.getSun());
        flowerpotData.setFlowerpot(flowerpot.get());
        dataRepository.save(flowerpotData);
        return DataMapper.toDataResponse(flowerpotData);
    }

    public Map<String, Object> getDataById(Long id, int page, int size) {
        PageRequest pageable = PageRequest.of(page, size);
        Optional<Flowerpot> flowerpot = flowerpotRepository.findById(id);
        UserGreenlink user = getAuthenticatedUser();

        if (flowerpot.isEmpty()) {
            throw new NotFoundException("Flowerpot no encontrado");
        }

        if (!flowerpot.get().getUser().getId().equals(user.getId())){
            throw new BadRequestException("No tiene permisos para acceder a este flowerpot");
        }

        Page<DataResponse> registrations = dataRepository
                .findAllByFlowerpotId(id, pageable)
                .map(DataMapper::toDataResponse);

        if (registrations.isEmpty()) {
            throw new NotFoundException("No hay datos registrados para este sensor");
        }

        return convert.toResponse(page, size, registrations);
    }

    private UserGreenlink getAuthenticatedUser() {
        String username = AuthorizationManager.getUserAuthentication();
        return authRepository.findByUsername(username)
                .map(user -> (UserGreenlink) user)
                .orElseThrow(() -> new NotFoundException("User not found"));
    }
}

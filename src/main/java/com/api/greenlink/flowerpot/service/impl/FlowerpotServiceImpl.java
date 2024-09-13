package com.api.greenlink.flowerpot.service.impl;

import com.api.greenlink.auth.repository.AuthRepository;
import com.api.greenlink.auth.security.authorization.AuthorizationManager;
import com.api.greenlink.exceptions.exception.BadRequestException;
import com.api.greenlink.exceptions.exception.NotFoundException;
import com.api.greenlink.flowerpot.dto.FlowerpotDto;
import com.api.greenlink.flowerpot.entity.Flowerpot;
import com.api.greenlink.flowerpot.mapper.MapperFlowerpot;
import com.api.greenlink.flowerpot.repository.FlowerpotRepository;
import com.api.greenlink.flowerpot.service.FlowerpotService;
import com.api.greenlink.user.entity.UserGreenlink;
import com.api.greenlink.util.ResponseConvert;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;

@Service
@AllArgsConstructor
public class FlowerpotServiceImpl implements FlowerpotService {

    private final FlowerpotRepository flowerpotRepository;
    private final AuthRepository authRepository;
    private final ResponseConvert convert;
    private static final Logger logger = LoggerFactory.getLogger(FlowerpotService.class);

    @Override
    @Transactional
    public FlowerpotDto addFlowerpot(FlowerpotDto flowerpotDto) {
        UserGreenlink user = getAuthenticatedUser();
        Flowerpot flowerpot = createFlowerpotFromDto(flowerpotDto, user);
        flowerpotRepository.save(flowerpot);
        return flowerpotDto;
    }

    @Override
    public Map<String, Object> getFlowerpots(int page, int size) {
        PageRequest pageable = PageRequest.of(page, size);
        UserGreenlink user = getAuthenticatedUser();
        Page<FlowerpotDto> result = flowerpotRepository.findAllByUser(user, pageable)
                .map(MapperFlowerpot::toFlowerpotDto);

        return convert.toResponse(page, size, result);
    }

    @Override
    public FlowerpotDto findById(Long id) {
        UserGreenlink user = getAuthenticatedUser();
        Flowerpot flowerpot = getFlowerpotByIdAndUser(id, user);
        return MapperFlowerpot.toFlowerpotDto(flowerpot);
    }

    @Override
    public FlowerpotDto updateFlowerpot(Long id, FlowerpotDto flowerpotDto) {
        UserGreenlink user = getAuthenticatedUser();
        Flowerpot flowerpot = getFlowerpotByIdAndUser(id, user);
        updateFlowerpotFromDto(flowerpot, flowerpotDto);
        flowerpotRepository.save(flowerpot);
        return MapperFlowerpot.toFlowerpotDto(flowerpot);
    }

    @Override
    public void deleteFlowerpot(Long id) {
        UserGreenlink user = getAuthenticatedUser();
        getFlowerpotByIdAndUser(id, user);
        flowerpotRepository.deleteById(id);
    }

    private UserGreenlink getAuthenticatedUser() {
        String username = AuthorizationManager.getUserAuthentication();
        return authRepository.findByUsername(username)
                .map(user -> (UserGreenlink) user)
                .orElseThrow(() -> new NotFoundException("User not found"));
    }

    private Flowerpot getFlowerpotByIdAndUser(Long id, UserGreenlink user) {
        Flowerpot flowerpot = flowerpotRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Flowerpot not found"));
        if (!flowerpot.getUser().getId().equals(user.getId())) {
            throw new BadRequestException("Unauthorized to access this flowerpot");
        }
        return flowerpot;
    }

    private Flowerpot createFlowerpotFromDto(FlowerpotDto flowerpotDto, UserGreenlink user) {
        Flowerpot flowerpot = MapperFlowerpot.toFlowerpot(flowerpotDto);
        flowerpot.setUser(user);
        return flowerpot;
    }

    private void updateFlowerpotFromDto(Flowerpot flowerpot, FlowerpotDto flowerpotDto) {
        flowerpot.setName(flowerpotDto.getName());
        flowerpot.setMacAddress(flowerpotDto.getMacAddress());
        flowerpot.setRedSsid(flowerpotDto.getRedSsid());
        flowerpot.setRedPassword(flowerpotDto.getRedPassword());
    }
}

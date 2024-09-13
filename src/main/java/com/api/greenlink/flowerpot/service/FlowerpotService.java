package com.api.greenlink.flowerpot.service;

import com.api.greenlink.flowerpot.dto.FlowerpotDto;
import com.api.greenlink.flowerpot.entity.Flowerpot;

import java.util.List;
import java.util.Map;

public interface FlowerpotService {

    public FlowerpotDto addFlowerpot(FlowerpotDto flowerpotDto);

    public Map<String, Object> getFlowerpots(int page, int size);

    public FlowerpotDto findById(Long id);

    public FlowerpotDto updateFlowerpot(Long id, FlowerpotDto flowerpotDto);

    public void deleteFlowerpot(Long id);
}

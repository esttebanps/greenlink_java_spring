package com.api.greenlink.flowerpot.mapper;

import com.api.greenlink.flowerpot.dto.FlowerpotDto;
import com.api.greenlink.flowerpot.entity.Flowerpot;
import com.api.greenlink.user.entity.UserGreenlink;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class MapperFlowerpot {

    public static Flowerpot toFlowerpot(FlowerpotDto flowerpotDto){
        Flowerpot flowerpot = new Flowerpot();

        flowerpot.setName(flowerpotDto.getName());
        flowerpot.setMacAddress(flowerpotDto.getMacAddress());
        flowerpot.setRedSsid(flowerpotDto.getRedSsid());
        flowerpot.setRedPassword(flowerpotDto.getRedPassword());

        return flowerpot;
    }

    public static FlowerpotDto toFlowerpotDto(Flowerpot flowerpot){
        FlowerpotDto flowerpotDto = new FlowerpotDto();

        flowerpotDto.setName(flowerpot.getName());
        flowerpotDto.setMacAddress(flowerpot.getMacAddress());
        flowerpotDto.setRedSsid(flowerpot.getRedSsid());
        flowerpotDto.setRedPassword(flowerpot.getRedPassword());

        return flowerpotDto;
    }
}

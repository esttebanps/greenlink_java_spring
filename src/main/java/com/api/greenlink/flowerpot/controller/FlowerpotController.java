package com.api.greenlink.flowerpot.controller;

import com.api.greenlink.flowerpot.dto.FlowerpotDto;
import com.api.greenlink.flowerpot.entity.Flowerpot;
import com.api.greenlink.flowerpot.service.FlowerpotService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@AllArgsConstructor
@RequestMapping("/v1/es")
public class FlowerpotController {

    private final FlowerpotService flowerpotService;

    @PostMapping("/flowerpot")
    public ResponseEntity<FlowerpotDto> addflowerpot(@RequestBody FlowerpotDto flowerpotDto) {
        FlowerpotDto result = flowerpotService.addFlowerpot(flowerpotDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @GetMapping("/flowerpot")
    public ResponseEntity<Map<String, Object>> getflowerpots(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size
    ) {
        Map<String, Object> result = flowerpotService.getFlowerpots(page, size);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/flowerpot/{id}")
    public ResponseEntity<FlowerpotDto> getFlowerpotById(@PathVariable Long id) {
        FlowerpotDto result = flowerpotService.findById(id);
        return ResponseEntity.ok(result);
    }

    @PutMapping("/flowerpot/{id}")
    public ResponseEntity<FlowerpotDto> updateFlowerpot(@PathVariable Long id, @RequestBody FlowerpotDto flowerpotDto) {
        FlowerpotDto result = flowerpotService.updateFlowerpot(id, flowerpotDto);
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/flowerpot/{id}")
    public ResponseEntity<Void> deleteFlowerpot(@PathVariable Long id) {
        flowerpotService.deleteFlowerpot(id);
        return ResponseEntity.noContent().build();
    }

}

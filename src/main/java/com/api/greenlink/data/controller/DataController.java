package com.api.greenlink.data.controller;

import com.api.greenlink.data.dto.DataInput;
import com.api.greenlink.data.dto.DataResponse;
import com.api.greenlink.data.service.DataService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/v1/es")
@AllArgsConstructor
public class DataController {

    private final DataService registrationService;

    @PostMapping("/flowerpot/{id}/data")
    public ResponseEntity<DataResponse> addSensorRegistration(@RequestBody DataInput dataInput, @PathVariable Long id){
        DataResponse response = registrationService.addDataFlowerpot(dataInput, id);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/flowerpot/{id}/data")
    public ResponseEntity<Map<String, Object>> getAllSensorRegistrations(
            @PathVariable Long id,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size
    ){
        return ResponseEntity.ok(registrationService.getDataById(id, page, size));
    }
}

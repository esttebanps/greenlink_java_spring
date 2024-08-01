package com.api.greenlink.util.impl;

import com.api.greenlink.util.ResponseConvert;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class ConvertResponse implements ResponseConvert {
    @Override
    public Map<String, Object> toResponse(int page, int size, Page pageContent) {

        Map<String, Object> response = new HashMap<>();
        response.put("page_number", page+1);
        response.put("size", size);
        response.put("total_elements", pageContent.getTotalElements());
        response.put("total_pages", pageContent.getTotalPages());
        response.put("data", pageContent.getContent());


        return response;
    }
}

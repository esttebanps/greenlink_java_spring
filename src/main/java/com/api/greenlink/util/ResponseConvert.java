package com.api.greenlink.util;

import org.springframework.data.domain.Page;

import java.util.Map;

public interface ResponseConvert {
    Map<String, Object> toResponse(int page, int size, Page pageContent);
}

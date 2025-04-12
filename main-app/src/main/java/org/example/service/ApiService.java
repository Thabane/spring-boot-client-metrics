package org.example.service;

import org.example.dto.RequestDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.Map;

@Service
public class ApiService {

    private final RestTemplate restTemplate;

    @Value("${external.api.url}")
    private String externalApiUrl;

    @Autowired
    public ApiService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    // Original method (maintains backward compatibility)
    public String makePostCall(RequestDto request) {
        return makePostCall(request, Collections.emptyMap());
    }

    // New overloaded method with headers
    public String makePostCall(RequestDto request, Map<String, String> headers) {
        // Create headers
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);

        // Add custom headers
        headers.forEach(httpHeaders::add);

        // Create HTTP entity
        HttpEntity<RequestDto> requestEntity = new HttpEntity<>(request, httpHeaders);

        // Make the call
        ResponseEntity<String> response = restTemplate.exchange(
                externalApiUrl,
                HttpMethod.POST,
                requestEntity,
                String.class);

        return response.getBody();
    }
}
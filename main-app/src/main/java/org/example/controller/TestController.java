package org.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import org.example.dto.RequestDto;
import org.example.service.ApiService;

@RestController
public class TestController {

    private final ApiService apiService;

    @Autowired
    public TestController(ApiService apiService) {
        this.apiService = apiService;
    }

    @PostMapping("/test-api")
    public String testApi(@RequestBody RequestDto request) {
        return apiService.makePostCall(request);
    }
}
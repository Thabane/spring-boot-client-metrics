package org.example.schedule;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import org.example.dto.RequestDto;
import org.example.service.ApiService;
import java.util.*;

@Component
public class ApiScheduler {
    private static final Logger LOG = LoggerFactory.getLogger(ApiScheduler.class);
    private final ApiService apiService;
    private final Random random = new Random();
    private final List<String> headerValues = Arrays.asList(
            "success", "created", "error",
            "unauthorized", "conflict", "server-error"
    );

    public ApiScheduler(ApiService apiService) {
        this.apiService = apiService;
    }

    @Scheduled(fixedRate = 2000) // Runs every 2 seconds
    public void triggerApiCall() {
        LOG.info("running call...");
        // Get random header value
        String randomHeader = headerValues.get(random.nextInt(headerValues.size()));

        // Create sample request
        RequestDto request = new RequestDto(
                "ScheduledUser-" + System.currentTimeMillis() % 1000,
                "user" + (System.currentTimeMillis() % 1000) + "@example.com"
        );

        Map<String, String> headers = new HashMap<>();

        headers.put("X-Response-Type", randomHeader);

        // Make the call
        apiService.makePostCall(request, headers);

        // Log the invocation
        LOG.info("Made API call with header: %-12s | User: %s%n",
                randomHeader, request.getName());
    }
}
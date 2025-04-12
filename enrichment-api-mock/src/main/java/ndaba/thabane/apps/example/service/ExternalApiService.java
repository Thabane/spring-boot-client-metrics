package ndaba.thabane.apps.example.service;

import ndaba.thabane.apps.example.dto.RequestDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.Random;

@Service
public class ExternalApiService {
    private final RestTemplate restTemplate;
    private final ResponseService responseService;
    private final Random random = new Random();

    @Value("${external.api.url}")
    private String externalApiUrl;

    public ExternalApiService(RestTemplate restTemplate, ResponseService responseService) {
        this.restTemplate = restTemplate;
        this.responseService = responseService;
    }

    public ResponseEntity<String> mockExternalCall(RequestDto request, String responseType) {
        try {
            // Simulate processing delay (50-300ms)
            Thread.sleep(50 + random.nextInt(250));

            ResponseService.ResponseTemplate template = responseService.getResponseTemplate(responseType);

            // Replace placeholders
            String responseBody = template.getContent()
                    .replace("${name}", request.getName())
                    .replace("${email}", request.getEmail())
                    .replace("${random.uuid}", java.util.UUID.randomUUID().toString())
                    .replace("${current.timestamp}", java.time.Instant.now().toString());

            return ResponseEntity.status(template.getStatus()).body(responseBody);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("{\"error\":\"Template not found\"}");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("{\"error\":\"Processing failed: " + e.getMessage() + "\"}");
        }
    }
}
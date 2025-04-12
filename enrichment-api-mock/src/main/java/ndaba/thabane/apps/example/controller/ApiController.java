package ndaba.thabane.apps.example.controller;

import ndaba.thabane.apps.example.dto.RequestDto;
import ndaba.thabane.apps.example.service.ExternalApiService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class ApiController {
    private final ExternalApiService apiService;

    public ApiController(ExternalApiService apiService) {
        this.apiService = apiService;
    }

    @PostMapping("/process")
    public ResponseEntity<String> processRequest(
            @RequestBody RequestDto request,
            @RequestHeader(name = "X-Response-Type", defaultValue = "success") String responseType) {
        return apiService.mockExternalCall(request, responseType);
    }
}
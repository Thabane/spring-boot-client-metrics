package ndaba.thabane.apps.example.service;

import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@Service
public class ResponseService {
    private final ResourceLoader resourceLoader;
    private final Map<String, HttpStatus> responseStatusMapping;

    public ResponseService(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
        this.responseStatusMapping = new HashMap<>();
        initializeStatusMapping();
    }

    private void initializeStatusMapping() {
        responseStatusMapping.put("success", HttpStatus.OK);
        responseStatusMapping.put("created", HttpStatus.CREATED);
        responseStatusMapping.put("accepted", HttpStatus.ACCEPTED);
        responseStatusMapping.put("error", HttpStatus.BAD_REQUEST);
        responseStatusMapping.put("unauthorized", HttpStatus.UNAUTHORIZED);
        responseStatusMapping.put("not-found", HttpStatus.NOT_FOUND);
        responseStatusMapping.put("conflict", HttpStatus.CONFLICT);
        responseStatusMapping.put("server-error", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public ResponseTemplate getResponseTemplate(String templateName) throws IOException {
        Resource resource = resourceLoader.getResource("classpath:templates/" + templateName + ".json");
        try (Reader reader = new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8)) {
            String content = FileCopyUtils.copyToString(reader);
            HttpStatus status = responseStatusMapping.getOrDefault(templateName, HttpStatus.OK);
            return new ResponseTemplate(content, status);
        }
    }

    public static class ResponseTemplate {
        private final String content;
        private final HttpStatus status;

        public ResponseTemplate(String content, HttpStatus status) {
            this.content = content;
            this.status = status;
        }

        public String getContent() {
            return content;
        }

        public HttpStatus getStatus() {
            return status;
        }
    }
}
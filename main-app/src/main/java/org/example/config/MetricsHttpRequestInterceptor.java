package org.example.config;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import java.io.IOException;

public class MetricsHttpRequestInterceptor implements ClientHttpRequestInterceptor {

    private final MeterRegistry meterRegistry;

    public MetricsHttpRequestInterceptor(MeterRegistry meterRegistry) {
        this.meterRegistry = meterRegistry;
    }

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body,
                                        ClientHttpRequestExecution execution) throws IOException {
        final String requestName = request.getURI().getHost() + request.getURI().getPath();

        Timer.Sample sample = Timer.start(meterRegistry);
        String status = "SUCCESS";

        try {
            ClientHttpResponse response = execution.execute(request, body);
            status = Integer.toString(response.getStatusCode().value());
            return response;
        } catch (IOException e) {
            status = "IO_ERROR";
            throw e;
        } catch (Exception e) {
            status = "CLIENT_ERROR";
            throw e;
        } finally {
            sample.stop(meterRegistry.timer("http.client.requests",
                    "uri", requestName,
                    "method", request.getMethod().name(),
                    "status", status));

            meterRegistry.counter("http.client.requests.total",
                    "uri", requestName,
                    "method", request.getMethod().name(),
                    "status", status).increment();

            meterRegistry.summary("http.client.request.size",
                            "uri", requestName,
                            "method", request.getMethod().name())
                    .record(body.length);
        }
    }
}


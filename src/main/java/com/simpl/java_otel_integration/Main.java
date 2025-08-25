package com.simpl.java_otel_integration;

import com.simpl.java_otel_integration.client.implemantation.otel.OTelClient;
import com.simpl.java_otel_integration.client.interfaces.Counter;
import com.simpl.java_otel_integration.client.interfaces.Gauge;
import com.simpl.java_otel_integration.client.interfaces.Histogram;
import com.simpl.java_otel_integration.config.OtelConfig;

import java.util.HashMap;
import java.util.Map;
//for testing
public class Main {
    public static void main(String[] args) {
        System.out.println("OpenTelemetry Integration Demo");

        try {
            // Create configuration
            OtelConfig config = new OtelConfig(
                    "demo-service",
                    "1.0.0",
                    "local",
                    "http://localhost:4318/v1/metrics", // OTEL collector endpoint
                    10, // 10 second intervals
                    true // enable debug
            );

            // Create OTel client
            try (OTelClient client = new OTelClient(config)) {

                // Demo counter
                Counter requestCounter = client.createCounter("demo_requests_total", "requests");
                Map<String, String> labels = new HashMap<>();
                labels.put("method", "GET");
                labels.put("status", "200");
                requestCounter.inc(labels);
                requestCounter.inc(labels);

                // Demo gauge
                Gauge memoryGauge = client.createGauge("demo_memory_usage", "bytes");
                memoryGauge.set(1024.0, Map.of("type", "heap"));

                // Demo histogram
                Histogram responseTimeHistogram = client.createHistogram(
                        "demo_response_time",
                        "ms",
                        new double[]{10, 50, 100, 500, 1000}
                );
                responseTimeHistogram.record(150.0, Map.of("endpoint", "/api/users"));

                System.out.println("Metrics recorded successfully!");

                // Force flush to ensure metrics are sent
                client.forceFlush();
                System.out.println("Metrics flushed to collector");

            } // Auto-close will handle cleanup

        } catch (Exception e) {
            System.err.println("Error running demo: " + e.getMessage());
            e.printStackTrace();
        }
    }
}

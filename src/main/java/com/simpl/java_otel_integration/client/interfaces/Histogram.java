package com.simpl.java_otel_integration.client.interfaces;

import java.util.Map;

public interface Histogram {
    void record(double value, Map<String, String> labels);
}

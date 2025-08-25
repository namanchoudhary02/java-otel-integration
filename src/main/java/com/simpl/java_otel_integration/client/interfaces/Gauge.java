package com.simpl.java_otel_integration.client.interfaces;

import java.util.Map;

public interface Gauge {
    void set(double value, Map<String, String> labels);
}

package com.simpl.java_otel_integration.client.interfaces;

import java.util.Map;

public interface Counter {
    void inc(Map<String, String> labels);
    void add(long delta, Map<String, String> labels);
}

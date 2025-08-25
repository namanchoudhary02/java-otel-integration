package com.simpl.java_otel_integration.client.interfaces;

public interface OTelClient extends AutoCloseable {
    Counter createCounter(String name, String unit) throws Exception;
    Gauge createGauge(String name, String unit) throws Exception;
    Histogram createHistogram(String name, String unit, double[] buckets) throws Exception;
    void forceFlush() throws Exception;
}

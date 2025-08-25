package com.simpl.java_otel_integration.config;

public class OtelConfig {
    private String serviceName;
    private String serviceVersion;
    private String environment;
    private String otelEndpoint;
    private int sendInterval;
    private boolean enableDebug;

    /**
     * Create a new Config with default values
     */
    public OtelConfig() {
        // Default values
        this.sendInterval = 30; // Default to 30 seconds
        this.enableDebug = false;
        this.environment = "prod"; // Default to production
    }

    /**
     * Create a new Config with specified values
     *
     * @param serviceName    The name of the service
     * @param serviceVersion The version of the service
     * @param environment    The deployment environment (e.g., "prod", "staging", "local")
     * @param otelEndpoint   The OpenTelemetry collector endpoint URL
     * @param sendInterval   How often to send metrics (in seconds)
     * @param enableDebug    Whether to enable debug logging
     */
    public OtelConfig(String serviceName, String serviceVersion, String environment,
                  String otelEndpoint, int sendInterval, boolean enableDebug) {
        this.serviceName = serviceName;
        this.serviceVersion = serviceVersion;
        this.environment = environment;
        this.otelEndpoint = otelEndpoint;
        this.sendInterval = sendInterval;
        this.enableDebug = enableDebug;
    }

    // Getters and setters
    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getServiceVersion() {
        return serviceVersion;
    }

    public void setServiceVersion(String serviceVersion) {
        this.serviceVersion = serviceVersion;
    }

    public String getEnvironment() {
        return environment;
    }

    public void setEnvironment(String environment) {
        this.environment = environment;
    }

    public String getOtelEndpoint() {
        return otelEndpoint;
    }

    public void setOtelEndpoint(String otelEndpoint) {
        this.otelEndpoint = otelEndpoint;
    }

    public int getSendInterval() {
        return sendInterval;
    }

    public void setSendInterval(int sendInterval) {
        this.sendInterval = sendInterval;
    }

    public boolean isEnableDebug() {
        return enableDebug;
    }

    public void setEnableDebug(boolean enableDebug) {
        this.enableDebug = enableDebug;
    }
}

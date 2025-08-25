package com.simpl.java_otel_integration.client.implemantation.otel;

import com.simpl.java_otel_integration.config.OtelConfig;
import io.opentelemetry.api.GlobalOpenTelemetry;
import io.opentelemetry.api.common.Attributes;
import io.opentelemetry.api.metrics.DoubleHistogram;
import io.opentelemetry.api.metrics.LongCounter;
import io.opentelemetry.api.metrics.Meter;
import io.opentelemetry.exporter.otlp.http.metrics.OtlpHttpMetricExporter;
import io.opentelemetry.exporter.otlp.http.metrics.OtlpHttpMetricExporterBuilder;
import io.opentelemetry.sdk.OpenTelemetrySdk;
import io.opentelemetry.sdk.metrics.SdkMeterProvider;
import io.opentelemetry.sdk.metrics.export.PeriodicMetricReader;
import io.opentelemetry.sdk.resources.Resource;
import io.opentelemetry.context.Context;
import io.opentelemetry.api.metrics.ObservableDoubleGauge;
import io.opentelemetry.semconv.resource.attributes.ResourceAttributes;

import java.time.Duration;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

public class OTelClient implements com.simpl.java_otel_integration.client.interfaces.OTelClient {
    private final OtelConfig config;
    private final SdkMeterProvider meterProvider;
    private final Meter meter;
    private final OtlpHttpMetricExporter exporter;
    private final Context context;
    private final Resource resource;

    public OTelClient(OtelConfig config) throws Exception {
        this.config = config;

        // Create resource
        this.resource = Resource.getDefault()
                .merge(Resource.create(Attributes.builder()
                        .put(ResourceAttributes.SERVICE_NAME, config.getServiceName())
                        .put(ResourceAttributes.SERVICE_VERSION, config.getServiceVersion())
                        .put(ResourceAttributes.DEPLOYMENT_ENVIRONMENT, config.getEnvironment())
                        .build()));

        System.out.println("Resource service name: " + resource.getAttribute(ResourceAttributes.SERVICE_NAME));

        // Configure exporter
        OtlpHttpMetricExporterBuilder exporterBuilder = OtlpHttpMetricExporter.builder()
                .setEndpoint(config.getOtelEndpoint())
                .setTimeout(30, TimeUnit.SECONDS);

        this.exporter = exporterBuilder.build();

        // Create meter provider
        this.meterProvider = SdkMeterProvider.builder()
                .registerMetricReader(
                    PeriodicMetricReader.builder(exporter)
                        .setInterval(Duration.ofSeconds(5))
                        .build())
                .setResource(resource)
                .build();

        // Initialize OpenTelemetry SDK
        OpenTelemetrySdk.builder()
                .setMeterProvider(meterProvider)
                .buildAndRegisterGlobal();

        this.meter = GlobalOpenTelemetry.get().getMeter(config.getServiceName());
        this.context = Context.current();

        System.out.println("OpenTelemetry initialized with OTLP endpoint: " + config.getOtelEndpoint());
    }

    @Override
    public com.simpl.java_otel_integration.client.interfaces.Counter createCounter(String name, String unit) {
        LongCounter counter = meter.counterBuilder(name)
                .setUnit(unit)
                .build();
        return new Counter(context, counter);
    }

    @Override
    public com.simpl.java_otel_integration.client.interfaces.Gauge createGauge(String name, String unit) {
        AtomicReference<Double> value = new AtomicReference<>(0.0);
        AtomicReference<Attributes> lastAttributes = new AtomicReference<>(Attributes.empty());

        ObservableDoubleGauge gauge = meter.gaugeBuilder(name)
                .setUnit(unit)
                .buildWithCallback(measurement -> {
                    measurement.record(value.get(), lastAttributes.get());
                });

        return new Gauge(context, gauge, value, lastAttributes);
    }

    @Override
    public com.simpl.java_otel_integration.client.interfaces.Histogram createHistogram(String name, String unit, double[] buckets) {
        DoubleHistogram histogram = meter.histogramBuilder(name)
                .setUnit(unit)
                .build();
        return new Histogram(context,histogram);
    }

    @Override
    public void forceFlush() throws Exception {
        if (meterProvider != null) {
            meterProvider.forceFlush().join(5, TimeUnit.SECONDS);
        }
    }

    @Override
    public void close() throws Exception {
        if (meterProvider != null) {
            meterProvider.close();
        }
        if (exporter != null) {
            exporter.close();
        }
        System.out.println("OTelClient closed");
    }
}

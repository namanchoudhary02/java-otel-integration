package com.simpl.java_otel_integration.client.implemantation.otel;

import com.simpl.java_otel_integration.util.AttributeConverter;
import io.opentelemetry.api.common.Attributes;
import io.opentelemetry.api.metrics.DoubleHistogram;
import io.opentelemetry.context.Context;

import java.util.Map;

public class Histogram implements com.simpl.java_otel_integration.client.interfaces.Histogram {
    private final Context context;
    private final DoubleHistogram otelHistogram;

    /**
     * Creates a new DefaultHistogram instance
     *
     * @param context       The OpenTelemetry context
     * @param otelHistogram The underlying OpenTelemetry histogram
     */
    public Histogram(Context context, DoubleHistogram otelHistogram) {
        this.context = context;
        this.otelHistogram = otelHistogram;
    }

    @Override
    public void record(double value, Map<String, String> labels) {
        Attributes attributes = AttributeConverter.convertLabelsToAttributes(labels);
        otelHistogram.record(value, attributes, context);
    }
}

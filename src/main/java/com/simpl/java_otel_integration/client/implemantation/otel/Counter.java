package com.simpl.java_otel_integration.client.implemantation.otel;

import com.simpl.java_otel_integration.util.AttributeConverter;
import io.opentelemetry.api.common.Attributes;
import io.opentelemetry.api.metrics.LongCounter;
import io.opentelemetry.context.Context;

import java.util.Map;

public class Counter implements com.simpl.java_otel_integration.client.interfaces.Counter {
    private final Context context;
    private final LongCounter otelCounter;

    /**
     * Creates a new DefaultCounter instance
     *
     * @param context     The OpenTelemetry context
     * @param otelCounter The underlying OpenTelemetry counter
     */
    public Counter(Context context, LongCounter otelCounter) {
        this.context = context;
        this.otelCounter = otelCounter;
    }

    @Override
    public void inc(Map<String, String> labels) {
        // Increment is just adding 1
        this.add(1, labels);
    }

    @Override
    public void add(long delta, Map<String, String> labels) {
        Attributes attributes = AttributeConverter.convertLabelsToAttributes(labels);
        otelCounter.add(delta, attributes, context);
    }
}

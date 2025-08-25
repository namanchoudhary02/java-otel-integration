package com.simpl.java_otel_integration.client.implemantation.otel;

import com.simpl.java_otel_integration.util.AttributeConverter;
import io.opentelemetry.api.common.Attributes;
import io.opentelemetry.api.metrics.ObservableDoubleGauge;
import io.opentelemetry.context.Context;

import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

public class Gauge implements com.simpl.java_otel_integration.client.interfaces.Gauge {
    private final Context context;
    private final ObservableDoubleGauge observableGauge;
    private final AtomicReference<Double> currentValue;
    private final AtomicReference<Attributes> currentAttributes;

    /**
     * Creates a new Gauge instance
     *
     * @param context           The OpenTelemetry context
     * @param observableGauge   The underlying OpenTelemetry observable gauge
     * @param currentValue      Reference to store current value
     * @param currentAttributes Reference to store current attributes
     */
    public Gauge(Context context, ObservableDoubleGauge observableGauge,
                 AtomicReference<Double> currentValue,
                 AtomicReference<Attributes> currentAttributes) {
        this.context = context;
        this.observableGauge = observableGauge;
        this.currentValue = currentValue;
        this.currentAttributes = currentAttributes;
    }

    @Override
    public void set(double value, Map<String, String> labels) {
        Attributes attributes = AttributeConverter.convertLabelsToAttributes(labels);
        this.currentValue.set(value);
        this.currentAttributes.set(attributes);
    }
}

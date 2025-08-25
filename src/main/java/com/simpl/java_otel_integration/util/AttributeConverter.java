package com.simpl.java_otel_integration.util;

import io.opentelemetry.api.common.Attributes;
import io.opentelemetry.api.common.AttributesBuilder;

import java.util.Map;

public class AttributeConverter {
    /**
     * Converts a map of labels to OpenTelemetry Attributes
     *
     * @param labels Map of string key-value pairs
     * @return OpenTelemetry Attributes
     */
    public static Attributes convertLabelsToAttributes(Map<String, String> labels) {
        if (labels == null || labels.isEmpty()) {
            return Attributes.empty();
        }

        AttributesBuilder builder = Attributes.builder();
        labels.forEach(builder::put);
        return builder.build();
    }
}

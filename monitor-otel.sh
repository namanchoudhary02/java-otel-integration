#!/bin/bash

echo "=== Real-time OpenTelemetry Collector Monitoring ==="
echo "This will show live logs from the OTel collector"
echo "Press Ctrl+C to stop monitoring"
echo ""

podman logs -f otel-collector | grep --line-buffered -E "(metric|batch|debug|otlp|received)"

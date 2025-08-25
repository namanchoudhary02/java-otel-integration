#!/bin/bash

echo "=== Checking OpenTelemetry Collector Logs ==="
echo "Looking for metric data in collector logs..."
podman logs otel-collector --tail=50 | grep -E "(metric|batch|debug)" || echo "No recent metric logs found"

echo ""
echo "=== Checking Collector Container Status ==="
podman ps | grep otel-collector

echo ""
echo "=== Checking Prometheus Metrics Endpoint ==="
echo "Fetching metrics from collector's Prometheus endpoint..."
curl -s http://localhost:8889/metrics | head -20

echo ""
echo "=== Checking if your demo metrics are present ==="
echo "Looking for your demo metrics..."
curl -s http://localhost:8889/metrics | grep -E "(demo_requests_total|demo_memory_usage|demo_response_time)" || echo "Demo metrics not found"
plugins {
    id("java")
    id("application")
}

group = "com.simpl"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    // OpenTelemetry core
    implementation("io.opentelemetry:opentelemetry-api:1.32.0")
    implementation("io.opentelemetry:opentelemetry-sdk:1.32.0")
    implementation("io.opentelemetry:opentelemetry-exporter-otlp:1.32.0")
    implementation("io.opentelemetry:opentelemetry-semconv:1.21.0-alpha")

    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.test {
    useJUnitPlatform()
}

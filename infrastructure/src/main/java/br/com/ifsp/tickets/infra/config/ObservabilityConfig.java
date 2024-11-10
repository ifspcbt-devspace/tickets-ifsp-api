package br.com.ifsp.tickets.infra.config;

import io.micrometer.core.instrument.Tag;
import io.micrometer.core.instrument.config.MeterFilter;
import io.opentelemetry.api.OpenTelemetry;
import io.opentelemetry.api.common.Attributes;
import io.opentelemetry.context.propagation.ContextPropagators;
import io.opentelemetry.exporter.otlp.logs.OtlpGrpcLogRecordExporter;
import io.opentelemetry.instrumentation.logback.appender.v1_0.OpenTelemetryAppender;
import io.opentelemetry.sdk.OpenTelemetrySdk;
import io.opentelemetry.sdk.logs.LogRecordProcessor;
import io.opentelemetry.sdk.logs.SdkLoggerProvider;
import io.opentelemetry.sdk.logs.SdkLoggerProviderBuilder;
import io.opentelemetry.sdk.logs.export.BatchLogRecordProcessor;
import io.opentelemetry.sdk.resources.Resource;
import io.opentelemetry.sdk.trace.SdkTracerProvider;
import io.opentelemetry.semconv.ResourceAttributes;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import java.time.ZoneId;
import java.util.List;
import java.util.UUID;

@Configuration
public class ObservabilityConfig {

    @Bean
    public MeterFilter commonTagsMeterFilter() {
        return MeterFilter.commonTags(
                List.of(
                        Tag.of("instance.uuid", UUID.randomUUID().toString()),
                        Tag.of("zone.id", ZoneId.systemDefault().toString())
                )
        );
    }

    @Bean
    public OpenTelemetry openTelemetry(SdkLoggerProvider sdkLoggerProvider, SdkTracerProvider sdkTracerProvider, ContextPropagators contextPropagators) {
        final OpenTelemetrySdk openTelemetrySdk = OpenTelemetrySdk.builder()
                .setLoggerProvider(sdkLoggerProvider)
                .setTracerProvider(sdkTracerProvider)
                .setPropagators(contextPropagators)
                .build();
        OpenTelemetryAppender.install(openTelemetrySdk);
        return openTelemetrySdk;
    }

    @Bean
    public SdkLoggerProvider otelSdkLoggerProvider(Environment environment, ObjectProvider<LogRecordProcessor> logRecordProcessors) {
        final String applicationName = environment.getProperty("spring.application.name", "tickets-api");
        final Resource springResource = Resource.create(Attributes.of(ResourceAttributes.SERVICE_NAME, applicationName));
        final SdkLoggerProviderBuilder builder = SdkLoggerProvider.builder()
                .setResource(Resource.getDefault().merge(springResource));
        logRecordProcessors.orderedStream().forEach(builder::addLogRecordProcessor);
        return builder.build();
    }

    @Bean
    public LogRecordProcessor otelLogRecordProcessor() {
        return BatchLogRecordProcessor
                .builder(
                        OtlpGrpcLogRecordExporter.builder()
                                .setEndpoint("http://localhost:4317")
                                .build())
                .build();
    }

}

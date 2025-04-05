package br.com.ifsp.tickets.infra.config;

import io.micrometer.core.instrument.Tag;
import io.micrometer.core.instrument.config.MeterFilter;
import io.opentelemetry.api.OpenTelemetry;
import io.opentelemetry.context.propagation.ContextPropagators;
import io.opentelemetry.sdk.OpenTelemetrySdk;
import io.opentelemetry.sdk.logs.SdkLoggerProvider;
import io.opentelemetry.sdk.trace.SdkTracerProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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
        return OpenTelemetrySdk.builder()
                .setLoggerProvider(sdkLoggerProvider)
                .setTracerProvider(sdkTracerProvider)
                .setPropagators(contextPropagators)
                .build();
    }
}

package io.github.aquerr.rentacar.application.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.zalando.logbook.Logbook;
import org.zalando.logbook.core.BodyFilters;
import org.zalando.logbook.core.DefaultCorrelationId;
import org.zalando.logbook.core.DefaultHttpLogWriter;
import org.zalando.logbook.core.DefaultSink;
import org.zalando.logbook.core.QueryFilters;
import org.zalando.logbook.json.JsonBodyFilters;
import org.zalando.logbook.json.JsonHttpLogFormatter;

import static org.zalando.logbook.core.HeaderFilters.authorization;

@Configuration
public class LogBookConfig {

    @Bean
    public Logbook logbook() {
        return Logbook.builder()
                .correlationId(new DefaultCorrelationId())
                .queryFilter(QueryFilters.replaceQuery("password", "<secret>"))
                .bodyFilter(JsonBodyFilters.replacePrimitiveJsonProperty((text) -> text.equalsIgnoreCase("password"), "<secret>"))
                .headerFilter(authorization())
                .sink(new DefaultSink(
                        new JsonHttpLogFormatter(),
                        new DefaultHttpLogWriter()
                ))
                .build();
    }
}

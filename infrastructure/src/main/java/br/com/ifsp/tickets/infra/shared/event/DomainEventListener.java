package br.com.ifsp.tickets.infra.shared.event;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor(onConstructor_ = @__(@Autowired))
public class DomainEventListener implements ApplicationListener<InfraAppEvent> {

    private final ObjectMapper objectMapper;

    @Override
    public void onApplicationEvent(@NotNull InfraAppEvent event) {
        final String eventJson;
        try {
            eventJson = this.objectMapper.writeValueAsString(event);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        final String message = "domain event received: " + eventJson;
        switch (event.type()) {
            case INFO -> log.info(message);
            case WARNING -> log.warn(message);
            case ERROR, FATAL -> log.error(message);
            case DEBUG -> log.debug(message);
        }
    }

}

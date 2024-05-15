package br.com.ifsp.tickets.app.event.create;

import br.com.ifsp.tickets.domain.event.EventConfigKey;
import br.com.ifsp.tickets.domain.user.User;

import java.util.Date;
import java.util.HashMap;

public record CreateEventInput(
        User user,
        String name,
        String description,
        Date initialDate,
        Date endDate,
        HashMap<EventConfigKey, String> configuration
) {

    public static CreateEventInput with(User user, String name, String description, Date initialDate, Date endDate, HashMap<String, String> config) {
        final HashMap<EventConfigKey, String> configuration = new HashMap<>();
        config.forEach((key, value) -> configuration.put(EventConfigKey.valueOf(key), value));
        return new CreateEventInput(user, name, description, initialDate, endDate, configuration);
    }

}

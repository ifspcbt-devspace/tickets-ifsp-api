package br.com.ifsp.tickets.app.administrative.event.create;

import br.com.ifsp.tickets.domain.administrative.event.vo.EventConfigKey;
import br.com.ifsp.tickets.domain.administrative.user.User;

import java.time.LocalDate;
import java.util.HashMap;

public record CreateEventInput(
        User user,
        String companyId,
        String name,
        String description,
        LocalDate initialDate,
        LocalDate endDate,
        HashMap<EventConfigKey, String> configuration
) {

    public static CreateEventInput with(User user, String companyId, String name, String description, LocalDate initialDate, LocalDate endDate, HashMap<String, String> config) {
        final HashMap<EventConfigKey, String> configuration = new HashMap<>();
        config.forEach((key, value) -> configuration.put(EventConfigKey.valueOf(key), value));
        return new CreateEventInput(user, companyId, name, description, initialDate, endDate, configuration);
    }

}

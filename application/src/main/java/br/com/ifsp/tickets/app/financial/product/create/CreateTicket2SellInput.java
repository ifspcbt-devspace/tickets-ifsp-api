package br.com.ifsp.tickets.app.financial.product.create;

import br.com.ifsp.tickets.domain.administrative.user.User;

import java.math.BigDecimal;

public record CreateTicket2SellInput(
        User user,
        String eventId,
        String name,
        String description,
        BigDecimal price,
        int stock,
        int entries
) {

    public static CreateTicket2SellInput of(User user, String eventId, String name, String description, BigDecimal price, int stock, int entries) {
        return new CreateTicket2SellInput(user, eventId, name, description, price, stock, entries);
    }

}

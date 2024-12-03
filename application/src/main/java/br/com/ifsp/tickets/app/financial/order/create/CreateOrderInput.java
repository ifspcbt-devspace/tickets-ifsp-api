package br.com.ifsp.tickets.app.financial.order.create;

import br.com.ifsp.tickets.domain.administrative.user.User;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public record CreateOrderInput(
        User authenticatedUser,
        String name,
        String email,
        String phone,
        String document,
        LocalDate birthDate,
        List<OrderItemInput> items
) {

    public static CreateOrderInput of(User authenticatedUser, String name, String email, String phone, String document, LocalDate birthDate, List<OrderItemInput> items) {
        return new CreateOrderInput(authenticatedUser, name, email, phone, document, birthDate, items);
    }

    public record OrderItemInput(
            UUID ticketSaleId,
            int quantity
    ) {
        public static OrderItemInput of(UUID ticketSaleId, int quantity) {
            return new OrderItemInput(ticketSaleId, quantity);
        }
    }
}

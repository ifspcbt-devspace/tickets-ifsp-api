package br.com.ifsp.tickets.app.financial.order.retrieve;

import br.com.ifsp.tickets.domain.administrative.user.User;
import br.com.ifsp.tickets.domain.administrative.user.UserID;
import br.com.ifsp.tickets.domain.financial.order.Order;
import br.com.ifsp.tickets.domain.financial.order.OrderStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public record OrderOutput(
        Long id,
        String customerID,
        String name,
        String document,
        String email,
        String phone,
        LocalDate birthDate,
        String paymentUrl,
        OrderStatus status,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        List<OrderItemOutput> items
) {

    public static OrderOutput from(Order order) {
        return new OrderOutput(
                order.getId().getValue(),
                order.getCustomer().map(User::getId).map(UserID::toString).orElse(null),
                order.getName(),
                order.getDocument().getValue(),
                order.getEmail().getValue(),
                order.getPhoneNumber().getValue(),
                order.getBirthDate(),
                order.getPaymentUrl(),
                order.getStatus(),
                order.getCreatedAt(),
                order.getUpdatedAt(),
                order.getItems().stream().map(OrderItemOutput::from).toList()
        );
    }

}

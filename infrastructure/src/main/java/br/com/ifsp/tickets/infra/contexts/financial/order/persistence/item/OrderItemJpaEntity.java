package br.com.ifsp.tickets.infra.contexts.financial.order.persistence.item;

import br.com.ifsp.tickets.domain.financial.order.OrderID;
import br.com.ifsp.tickets.domain.financial.order.item.OrderItem;
import br.com.ifsp.tickets.domain.financial.order.item.OrderItemID;
import br.com.ifsp.tickets.infra.contexts.financial.product.persistence.TicketSaleJpaEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Entity
@Table(name = "order_items")
@NoArgsConstructor
@Getter
public class OrderItemJpaEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true, updatable = false)
    private Long id;
    @Column(name = "order_id", nullable = false)
    private Long orderId;
    @OneToOne(cascade = {CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name = "ticket_id", nullable = false)
    private TicketSaleJpaEntity ticket;
    @Column(name = "quantity", nullable = false)
    private int quantity;

    public OrderItemJpaEntity(Long id, Long orderId, TicketSaleJpaEntity ticket, int quantity) {
        this.id = id;
        this.orderId = orderId;
        this.ticket = ticket;
        this.quantity = quantity;
    }

    public static OrderItemJpaEntity from(OrderItem orderItem) {
        return new OrderItemJpaEntity(
                orderItem.getId().getValue(),
                orderItem.getOrderID().getValue(),
                TicketSaleJpaEntity.from(orderItem.getTicket()),
                orderItem.getQuantity()
        );
    }

    public OrderItem toAggregate() {
        return new OrderItem(
                OrderItemID.with(this.getId()),
                OrderID.with(this.getOrderId()),
                this.getTicket().toAggregate(),
                this.getQuantity()
        );
    }

}

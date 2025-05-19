package br.com.ifsp.tickets.infra.contexts.financial.order.persistence;

import br.com.ifsp.tickets.domain.administrative.user.vo.EmailAddress;
import br.com.ifsp.tickets.domain.administrative.user.vo.PhoneNumber;
import br.com.ifsp.tickets.domain.administrative.user.vo.RG;
import br.com.ifsp.tickets.domain.financial.order.Order;
import br.com.ifsp.tickets.domain.financial.order.OrderID;
import br.com.ifsp.tickets.domain.financial.order.OrderStatus;
import br.com.ifsp.tickets.infra.contexts.administrative.user.persistence.UserJpaEntity;
import br.com.ifsp.tickets.infra.contexts.financial.order.persistence.item.OrderItemJpaEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
@Getter
@NoArgsConstructor
public class OrderJpaEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true, updatable = false)
    private Long id;
    @OneToOne(cascade = {CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name = "customer_id", nullable = false)
    private UserJpaEntity customer;
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "email", nullable = false)
    private String email;
    @Column(name = "phone_number", nullable = false)
    private String phoneNumber;
    @Column(name = "document", nullable = false)
    private String document;
    @Column(name = "birth_date", nullable = false)
    private LocalDate birthDate;
    @Column(name = "payment_url", nullable = false)
    private String paymentUrl;
    @Column(name = "status", nullable = false)
    private OrderStatus status;
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "order_id")
    private List<OrderItemJpaEntity> items;

    public OrderJpaEntity(Long id, UserJpaEntity customer, String name, String email, String phoneNumber, String document, LocalDate birthDate, String paymentUrl, OrderStatus status, LocalDateTime createdAt, LocalDateTime updatedAt, List<OrderItemJpaEntity> items) {
        this.id = id;
        this.customer = customer;
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.document = document;
        this.birthDate = birthDate;
        this.paymentUrl = paymentUrl;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.items = items;
    }

    public static OrderJpaEntity from(Order order) {
        return new OrderJpaEntity(
                order.getId().getValue(),
                order.getCustomer().map(UserJpaEntity::from).orElse(null),
                order.getName(),
                order.getEmail().getValue(),
                order.getPhoneNumber().getValue(),
                order.getDocument().getValue(),
                order.getBirthDate(),
                order.getPaymentUrl(),
                order.getStatus(),
                order.getCreatedAt(),
                order.getUpdatedAt(),
                new ArrayList<>(order.getItems().stream().map(OrderItemJpaEntity::from).toList())
        );
    }

    public Order toAggregate() {
        return new Order(
                OrderID.with(this.getId()),
                this.getCustomer().toAggregate(),
                this.getName(),
                new EmailAddress(this.getEmail()),
                new PhoneNumber(this.getPhoneNumber()),
                new RG(this.getDocument()),
                this.getBirthDate(),
                this.getItems().stream().map(OrderItemJpaEntity::toAggregate).toList(),
                this.getPaymentUrl(),
                this.getStatus(),
                this.getCreatedAt(),
                this.getUpdatedAt()
        );
    }

}

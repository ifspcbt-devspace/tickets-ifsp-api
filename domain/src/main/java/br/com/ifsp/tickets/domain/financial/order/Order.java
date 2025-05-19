package br.com.ifsp.tickets.domain.financial.order;

import br.com.ifsp.tickets.domain.administrative.user.User;
import br.com.ifsp.tickets.domain.administrative.user.vo.Document;
import br.com.ifsp.tickets.domain.administrative.user.vo.EmailAddress;
import br.com.ifsp.tickets.domain.administrative.user.vo.PhoneNumber;
import br.com.ifsp.tickets.domain.financial.order.item.OrderItem;
import br.com.ifsp.tickets.domain.shared.AggregateRoot;
import br.com.ifsp.tickets.domain.shared.validation.IValidationHandler;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Getter
public class Order extends AggregateRoot<OrderID> {

    private final User customer;
    private final String name;
    private final EmailAddress email;
    private final PhoneNumber phoneNumber;
    private final Document document;
    private final LocalDate birthDate;
    private final List<OrderItem> items;
    private final LocalDateTime createdAt;
    private String paymentUrl;
    private OrderStatus status;
    private LocalDateTime updatedAt;

    public Order(OrderID orderID, User customer, String name, EmailAddress email, PhoneNumber phoneNumber, Document document, LocalDate birthDate, List<OrderItem> items, String paymentUrl, OrderStatus status, LocalDateTime createdAt, LocalDateTime updatedAt) {
        super(orderID);
        this.customer = customer;
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.document = document;
        this.birthDate = birthDate;
        this.items = items;
        this.paymentUrl = paymentUrl;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static Order with(OrderID orderID, User customer, String name, EmailAddress email, PhoneNumber phoneNumber, Document document, LocalDate birthDate, List<OrderItem> items, String paymentUrl, OrderStatus status, LocalDateTime createdAt, LocalDateTime updatedAt) {
        return new Order(orderID, customer, name, email, phoneNumber, document, birthDate, items, paymentUrl, status, createdAt, updatedAt);
    }

    public static Order newOrderTicket(User customer, List<OrderItem> items, String name, EmailAddress email, PhoneNumber phoneNumber, Document document, LocalDate birthDate) {
        return new Order(new OrderID(null), customer, name, email, phoneNumber, document, birthDate, items, null, OrderStatus.RESERVED, LocalDateTime.now(), LocalDateTime.now());
    }

    public void generatePaymentUrl(IPaymentURLGenerator paymentURLGenerator) {
        if (this.paymentUrl != null) return;

        this.paymentUrl = paymentURLGenerator.generateURL(this);
        this.status = OrderStatus.PENDING;
    }

    public void addItems(List<OrderItem> items) {
        if (this.status != OrderStatus.RESERVED) {
            throw new IllegalStateException("Order already generated payment URL");
        }
        this.items.addAll(items);
    }

    public Optional<User> getCustomer() {
        return Optional.ofNullable(customer);
    }

    public void approve() {
        this.status = OrderStatus.APPROVED;
        this.updatedAt = LocalDateTime.now();
    }

    public void fail() {
        this.status = OrderStatus.FAILED;
        this.updatedAt = LocalDateTime.now();
    }

    public void cancel() {
        if (this.status == OrderStatus.CANCELED || this.status == OrderStatus.FORCED_CANCELED) {
            throw new IllegalStateException("Order is already canceled");
        }

        if (this.status != OrderStatus.PENDING) {
            throw new IllegalStateException("Order must be pending to be canceled");
        }

        this.status = OrderStatus.CANCELED;
        this.updatedAt = LocalDateTime.now();
    }

    @Override
    public void validate(IValidationHandler handler) {
        new OrderValidator(handler, this).validate();
    }
}

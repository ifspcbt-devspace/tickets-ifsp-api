package br.com.ifsp.tickets.app.financial.payment.handle;

import br.com.ifsp.tickets.domain.financial.order.IOrderGateway;
import br.com.ifsp.tickets.domain.financial.order.Order;
import br.com.ifsp.tickets.domain.financial.order.OrderID;
import br.com.ifsp.tickets.domain.financial.payment.IPaymentGateway;
import br.com.ifsp.tickets.domain.financial.payment.Payment;
import br.com.ifsp.tickets.domain.financial.payment.PaymentStatus;
import br.com.ifsp.tickets.domain.shared.IDomainEventPublisher;
import br.com.ifsp.tickets.domain.shared.event.OrderPaymentFailed;
import br.com.ifsp.tickets.domain.shared.exceptions.NotFoundException;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

public class HandlePaymentUseCase implements IHandlePaymentUseCase {

    private final IPaymentGateway paymentGateway;
    private final IOrderGateway orderGateway;
    private final IDomainEventPublisher eventPublisher;
    private final PaymentSuccessHandler paymentSuccessHandler;

    public HandlePaymentUseCase(IPaymentGateway paymentGateway, IOrderGateway orderGateway, IDomainEventPublisher eventPublisher, PaymentSuccessHandler paymentSuccessHandler) {
        this.paymentGateway = paymentGateway;
        this.orderGateway = orderGateway;
        this.eventPublisher = eventPublisher;
        this.paymentSuccessHandler = paymentSuccessHandler;
    }

    @Override
    public HandlePaymentOutput execute(HandlePaymentInput anIn) {
        Payment payment;
        final Optional<Payment> paymentOptional = this.paymentGateway.findByExternalId(anIn.externalId());
        if (paymentOptional.isPresent()) payment = paymentOptional.get();
        else {
            final OrderID orderID = OrderID.with(anIn.orderId());
            final LocalDateTime createdAt = anIn.createdAt();
            final String currency = anIn.currency();
            final LocalDateTime updatedAt = anIn.updatedAt();
            final BigDecimal amount = anIn.amount();
            final String externalId = anIn.externalId();
            final String paymentType = anIn.paymentType();
            final PaymentStatus status = anIn.status();
            final LocalDateTime approvalDate = anIn.approvalDate();

            if (!this.orderGateway.exists(orderID))
                throw NotFoundException.with(Order.class, orderID);

            payment = Payment.newPayment(
                    externalId,
                    status,
                    orderID,
                    currency,
                    amount,
                    paymentType,
                    createdAt,
                    updatedAt,
                    approvalDate
            );
        }

        final OrderID orderId = payment.getOrderId();

        Order order = this.orderGateway.findById(orderId).orElseThrow(() -> NotFoundException.with(Order.class, orderId));

        switch (payment.getStatus()) {
            case APPROVED -> this.paymentSuccessHandler.handle(order);
            case REJECTED -> {
                order.fail();
                order = this.orderGateway.update(order);
                order.registerEvent(new OrderPaymentFailed(order));
                order.publishDomainEvents(this.eventPublisher);
            }
        }

        payment = paymentOptional.isPresent() ? this.paymentGateway.update(payment) : this.paymentGateway.create(payment);

        return HandlePaymentOutput.from(payment);
    }


}

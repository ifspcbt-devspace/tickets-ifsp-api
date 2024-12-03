package br.com.ifsp.tickets.app.financial.payment.create;

import br.com.ifsp.tickets.domain.financial.order.IOrderGateway;
import br.com.ifsp.tickets.domain.financial.order.Order;
import br.com.ifsp.tickets.domain.financial.order.OrderID;
import br.com.ifsp.tickets.domain.financial.payment.IPaymentGateway;
import br.com.ifsp.tickets.domain.financial.payment.Payment;
import br.com.ifsp.tickets.domain.financial.payment.PaymentStatus;
import br.com.ifsp.tickets.domain.shared.exceptions.NotFoundException;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class CreatePaymentUseCase implements ICreatePaymentUseCase {

    private final IPaymentGateway paymentGateway;
    private final IOrderGateway orderGateway;

    public CreatePaymentUseCase(IPaymentGateway paymentGateway, IOrderGateway orderGateway) {
        this.paymentGateway = paymentGateway;
        this.orderGateway = orderGateway;
    }

    @Override
    public CreatePaymentOutput execute(CreatePaymentInput anIn) {
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

        final Payment payment = Payment.newPayment(
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

        return CreatePaymentOutput.from(paymentGateway.create(payment));
    }
}

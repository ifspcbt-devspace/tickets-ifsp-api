package br.com.ifsp.tickets.app.payment.retrieve;

import com.mercadopago.client.payment.PaymentClient;

public class GetPaymentUseCase implements IGetPaymentUseCase{

    @Override
    public PaymentOutput execute(String anIn) {
        PaymentOutput output = null;
        try {
            PaymentClient client = new PaymentClient();

            output = PaymentOutput.from(client.get(Long.parseLong(anIn)));
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        return output;
    }
}

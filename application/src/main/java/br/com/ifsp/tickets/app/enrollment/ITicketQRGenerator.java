package br.com.ifsp.tickets.app.enrollment;

import br.com.ifsp.tickets.domain.ticket.TicketID;

public interface ITicketQRGenerator {

    byte[] generateQRCodeToBase64(TicketID ticketID);
}

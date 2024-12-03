package br.com.ifsp.tickets.app.administrative.enrollment;

import br.com.ifsp.tickets.domain.administrative.ticket.TicketID;

public interface ITicketQRGenerator {

    byte[] generateQRCodeToBase64(TicketID ticketID);
}

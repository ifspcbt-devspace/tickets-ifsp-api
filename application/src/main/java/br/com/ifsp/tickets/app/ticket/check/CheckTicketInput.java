package br.com.ifsp.tickets.app.ticket.check;

public record CheckTicketInput(
        String id
) {
    public static CheckTicketInput of(String id) {
        return new CheckTicketInput(id);
    }
}

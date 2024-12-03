package br.com.ifsp.tickets.domain.financial.product;

import br.com.ifsp.tickets.domain.shared.validation.IValidationHandler;
import br.com.ifsp.tickets.domain.shared.validation.Validator;

public class TicketSaleValidator extends Validator {

    private final TicketSale ticketSale;

    public TicketSaleValidator(IValidationHandler anHandler, TicketSale ticketSale) {
        super(anHandler);
        this.ticketSale = ticketSale;
    }

    @Override
    public void validate() {
        this.validateName();
        this.validateDescription();
        this.validatePrice();
        this.validateEntries();
        this.validateEventID();
    }

    private void validateName() {
        if (this.ticketSale.getName() == null || this.ticketSale.getName().isBlank()) {
            this.error("Name is required");
        }

        if (this.ticketSale.getName().length() > 40) {
            this.error("Name must have a maximum of 40 characters");
        }

        if (this.ticketSale.getName().length() < 3) {
            this.error("Name must have a minimum of 3 characters");
        }
    }

    private void validateDescription() {
        if (this.ticketSale.getDescription() == null || this.ticketSale.getDescription().isBlank()) {
            this.error("Description is required");
        }

        if (this.ticketSale.getDescription().length() > 255) {
            this.error("Description must have a maximum of 255 characters");
        }
    }

    private void validatePrice() {
        if (this.ticketSale.getPrice() == null || this.ticketSale.getPrice().doubleValue() <= 0) {
            this.error("Price must be greater than 0");
        }
    }

    private void validateEntries() {
        if (this.ticketSale.getEntries() <= 0) {
            this.error("Entries must be greater than 0");
        }
    }

    private void validateEventID() {
        if (this.ticketSale.getEventID() == null) {
            this.error("EventID is required");
        }
    }
}

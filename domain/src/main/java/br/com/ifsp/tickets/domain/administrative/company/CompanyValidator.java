package br.com.ifsp.tickets.domain.administrative.company;

import br.com.ifsp.tickets.domain.shared.validation.IValidationHandler;
import br.com.ifsp.tickets.domain.shared.validation.Validator;

public class CompanyValidator extends Validator {

    private final Company company;

    public CompanyValidator(IValidationHandler aHandler, Company company) {
        super(aHandler);
        this.company = company;
    }

    @Override
    public void validate() {
        this.validateName();
        this.validateDescription();
        this.validateCNPJ();
        this.validateOwnerID();
        this.validateAddress();
    }

    private void validateName() {
        if (this.company.getName() == null || this.company.getName().isEmpty())
            error("Name is required");
        if (this.company.getName() != null && this.company.getName().length() < 3)
            error("Name must have at least 3 characters");
        if (this.company.getName() != null && this.company.getName().length() > 100)
            error("Name must have at most 100 characters");
    }

    private void validateDescription() {
        if (this.company.getDescription() != null && this.company.getDescription().length() > 255)
            error("Description must have at most 255 characters");
    }

    private void validateCNPJ() {
        if (this.company.getCnpj() == null)
            error("CNPJ is required");
    }

    private void validateOwnerID() {
        if (this.company.getOwnerID() == null || this.company.getOwnerID().getValue() == null)
            error("Owner ID is required");
    }

    private void validateAddress() {
        if (this.company.getAddress() == null)
            error("Address is required");
    }

}

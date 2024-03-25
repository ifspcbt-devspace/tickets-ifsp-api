package br.com.ifsp.tickets.domain.company;

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
        this.validateCNPJ();
        this.validateOwnerID();
        this.validateAddress();
    }

    private void validateName() {
        if (this.company.getName() == null || this.company.getName().isEmpty()) {
            error("Name is required");
        }
    }

    private void validateCNPJ() {
        if (this.company.getCnpj() == null) {
            error("CNPJ is required");
        }
    }

    private void validateOwnerID() {
        if (this.company.getOwnerID() == null || this.company.getOwnerID().getValue() == null) {
            error("Owner ID is required");
        }
    }

    private void validateAddress() {
        if (this.company.getAddress() == null) {
            error("Address is required");
        }
    }

}

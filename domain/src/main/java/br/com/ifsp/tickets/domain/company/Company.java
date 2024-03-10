package br.com.ifsp.tickets.domain.company;

import br.com.ifsp.tickets.domain.company.vo.Address;
import br.com.ifsp.tickets.domain.company.vo.CNPJ;
import br.com.ifsp.tickets.domain.shared.Entity;
import br.com.ifsp.tickets.domain.shared.validation.ValidationHandler;
import br.com.ifsp.tickets.domain.user.UserID;

public class Company extends Entity<CompanyID> {

    private String name;
    private CNPJ cnpj;
    private UserID ownerID;
    private Address address;

    protected Company(CompanyID companyID) {
        super(companyID);
    }


    @Override
    public void validate(ValidationHandler handler) {

    }
}

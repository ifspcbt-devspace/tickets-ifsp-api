package br.com.ifsp.tickets.domain.company;

import br.com.ifsp.tickets.domain.shared.vo.Address;
import br.com.ifsp.tickets.domain.company.vo.CNPJ;
import br.com.ifsp.tickets.domain.shared.Entity;
import br.com.ifsp.tickets.domain.shared.exceptions.IllegalEntityIdException;
import br.com.ifsp.tickets.domain.shared.validation.IValidationHandler;
import br.com.ifsp.tickets.domain.user.User;
import br.com.ifsp.tickets.domain.user.UserID;
import lombok.Getter;

import java.util.Optional;

@Getter
public class Company extends Entity<CompanyID> {

    private String name;
    private CNPJ cnpj;
    private UserID ownerID;
    private Address address;

    public Company(CompanyID companyID, String name, CNPJ cnpj, UserID ownerID, Address address) {
        super(companyID);
        this.name = name;
        this.cnpj = cnpj;
        this.ownerID = ownerID;
        this.address = address;
    }

    public static Company with(CompanyID companyID, String name, CNPJ cnpj, UserID ownerID, Address address) {
        return new Company(companyID, name, cnpj, ownerID, address);
    }

    public static Company newCompany(String name, CNPJ cnpj, UserID ownerID, Address address) {
        return new Company(CompanyID.unique(), name, cnpj, ownerID, address);
    }

    public boolean isOwner(User user) {
        return user.isCompanyManager() && this.ownerID.equals(user.getId());
    }

    public void updateCompanyInfo(String name, CNPJ cnpj, Address address) {
        this.name = name;
        this.cnpj = cnpj;
        this.address = address;
    }

    public void changeOwner(UserID ownerID) {
        if (ownerID == null || ownerID.getValue() == null) {
            throw new IllegalEntityIdException(User.class, Optional.ofNullable(ownerID).map(UserID::getValue).orElse(null));
        }
        this.ownerID = ownerID;
    }

    @Override
    public void validate(IValidationHandler handler) {
        new CompanyValidator(handler, this).validate();
    }
}

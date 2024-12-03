package br.com.ifsp.tickets.domain.administrative.company;

import br.com.ifsp.tickets.domain.administrative.company.vo.CNPJ;
import br.com.ifsp.tickets.domain.shared.AggregateRoot;
import br.com.ifsp.tickets.domain.shared.exceptions.IllegalEntityIdException;
import br.com.ifsp.tickets.domain.shared.validation.IValidationHandler;
import br.com.ifsp.tickets.domain.shared.vo.Address;
import br.com.ifsp.tickets.domain.administrative.user.User;
import br.com.ifsp.tickets.domain.administrative.user.UserID;
import lombok.Getter;

import java.util.Optional;

@Getter
public class Company extends AggregateRoot<CompanyID> {

    private String name;
    private String description;
    private CNPJ cnpj;
    private UserID ownerID;
    private Address address;

    public Company(CompanyID companyID, String name, String description, CNPJ cnpj, UserID ownerID, Address address) {
        super(companyID);
        this.name = name;
        this.description = description;
        this.cnpj = cnpj;
        this.ownerID = ownerID;
        this.address = address;
    }

    public static Company with(CompanyID companyID, String name, String description, CNPJ cnpj, UserID ownerID, Address address) {
        return new Company(companyID, name, description, cnpj, ownerID, address);
    }

    public static Company newCompany(String name, String description, CNPJ cnpj, UserID ownerID, Address address) {
        return new Company(CompanyID.unique(), name, description, cnpj, ownerID, address);
    }

    public boolean isOwner(User user) {
        return user.isCompanyManager() && this.ownerID.equals(user.getId());
    }

    public void updateCompanyInfo(String name, String description, CNPJ cnpj) {
        this.name = name;
        this.description = description;
        this.cnpj = cnpj;
    }

    public void changeAddress(Address address) {
        this.address = address;
    }

    public void changeOwner(UserID ownerID) {
        if (ownerID == null || ownerID.getValue() == null)
            throw new IllegalEntityIdException(User.class, Optional.ofNullable(ownerID).map(UserID::getValue).orElse(null));
        this.ownerID = ownerID;
    }

    @Override
    public void validate(IValidationHandler handler) {
        new CompanyValidator(handler, this).validate();
    }
}

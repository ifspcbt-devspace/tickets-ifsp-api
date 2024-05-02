package br.com.ifsp.tickets.domain.user;

import br.com.ifsp.tickets.domain.company.Company;
import br.com.ifsp.tickets.domain.company.CompanyID;
import br.com.ifsp.tickets.domain.shared.Entity;
import br.com.ifsp.tickets.domain.shared.exceptions.IllegalEntityIdException;
import br.com.ifsp.tickets.domain.shared.validation.IValidationHandler;
import br.com.ifsp.tickets.domain.user.vo.CPF;
import br.com.ifsp.tickets.domain.user.vo.EmailAddress;
import br.com.ifsp.tickets.domain.user.vo.PhoneNumber;
import br.com.ifsp.tickets.domain.user.vo.role.Role;
import br.com.ifsp.tickets.domain.user.vo.role.RoleType;
import lombok.Getter;

import java.time.LocalDate;
import java.util.Optional;

@Getter
public class User extends Entity<UserID> {

    private final Role role;
    private String name;
    private EmailAddress email;
    private PhoneNumber phoneNumber;
    private String username;
    private String password;
    private CPF cpf;
    private LocalDate birthDate;
    private LocalDate passwordDate;
    private boolean active;
    private CompanyID companyID;

    protected User(UserID userID, String name, Role role, EmailAddress email, PhoneNumber phoneNumber, String username, String password, CPF cpf, LocalDate birthDate, LocalDate passwordDate, boolean active, CompanyID companyID) {
        super(userID);
        this.name = name;
        this.role = role;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.username = username;
        this.password = password;
        this.cpf = cpf;
        this.birthDate = birthDate;
        this.passwordDate = passwordDate;
        this.active = active;
        this.companyID = companyID == null ? new CompanyID(null) : companyID;
    }

    public static User with(UserID userID, String name, Role role, EmailAddress email, PhoneNumber phoneNumber, String username, String password, CPF cpf, LocalDate birthDate, LocalDate passwordDate, boolean active, CompanyID companyID) {
        return new User(userID, name, role, email, phoneNumber, username, password, cpf, birthDate, passwordDate, active, companyID);
    }

    public static User create(String name, Role role, PhoneNumber phoneNumber, String username, String password, CPF cpf, LocalDate birthDate) {
        return new User(UserID.unique(), name, role, new EmailAddress(null), phoneNumber, username, password, cpf, birthDate, LocalDate.now(), false, null);
    }

    public void updateProfile(String name, CPF cpf, LocalDate birthDate) {
        this.name = name;
        this.cpf = cpf;
        this.birthDate = birthDate;
    }

    public void joinCompany(CompanyID companyID) {
        if (companyID == null || companyID.getValue() == null) {
            throw new IllegalEntityIdException(Company.class, Optional.ofNullable(companyID).map(CompanyID::getValue).orElse(null));
        }
        this.companyID = companyID;
    }

    public void leaveCompany() {
        this.companyID = new CompanyID(null);
    }

    public void changeEmail(EmailAddress email) {
        this.email = email;
    }

    public void changePhoneNumber(String phoneNumber) {
        this.phoneNumber = new PhoneNumber(phoneNumber);
    }

    public void changeUsername(String username) {
        this.username = username;
    }

    public boolean isCompanyManager() {
        return this.role.getRoleType() == RoleType.COMPANY_MANAGER;
    }

    public void disable() {
        this.active = false;
    }

    public void enable() {
        this.active = true;
    }

    public void changePassword(String password) {
        this.password = password;
        this.passwordDate = LocalDate.now();
    }

    @Override
    public void validate(IValidationHandler handler) {
        new UserValidator(handler, this).validate();
    }
}

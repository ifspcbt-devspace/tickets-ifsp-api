package br.com.ifsp.tickets.domain.user;

import br.com.ifsp.tickets.domain.company.Company;
import br.com.ifsp.tickets.domain.company.CompanyID;
import br.com.ifsp.tickets.domain.shared.Entity;
import br.com.ifsp.tickets.domain.shared.exceptions.IllegalEntityIdException;
import br.com.ifsp.tickets.domain.shared.validation.IValidationHandler;
import br.com.ifsp.tickets.domain.user.vo.CPF;
import br.com.ifsp.tickets.domain.user.vo.Email;
import br.com.ifsp.tickets.domain.user.vo.PhoneNumber;
import br.com.ifsp.tickets.domain.user.vo.role.Role;
import br.com.ifsp.tickets.domain.user.vo.role.RoleType;
import lombok.Getter;

import java.util.Date;
import java.util.Optional;

@Getter
public class User extends Entity<UserID> {

    private final Role role;
    private final RegistrationSource registrationSource;
    private String name;
    private Email email;
    private PhoneNumber phoneNumber;
    private String username;
    private String password;
    private CPF cpf;
    private Date birthDate;
    private Date passwordDate;
    private boolean active;
    private CompanyID companyID;

    protected User(UserID userID, RegistrationSource registrationSource, String name, Role role, Email email, PhoneNumber phoneNumber, String username, String password, CPF cpf, Date birthDate, Date passwordDate, boolean active, CompanyID companyID) {
        super(userID);
        this.registrationSource = registrationSource;
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

    public static User with(UserID userID, RegistrationSource registrationSource, String name, Role role, Email email, PhoneNumber phoneNumber, String username, String password, CPF cpf, Date birthDate, Date passwordDate, boolean active, CompanyID companyID) {
        return new User(userID, registrationSource, name, role, email, phoneNumber, username, password, cpf, birthDate, passwordDate, active, companyID);
    }

    public static User newUser(String name, RegistrationSource registrationSource, Role role, String email, String phoneNumber, String username, String password, CPF cpf, Date birthDate) {
        return new User(UserID.unique(), registrationSource, name, role, new Email(email), new PhoneNumber(phoneNumber), username, password, cpf, birthDate, new Date(), false, null);
    }

    public void updateProfile(String name, CPF cpf, Date birthDate) {
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

    public void changeEmail(String email) {
        this.email = new Email(email);
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

    public boolean login(String password, IPasswordEncoder passwordEncoder) {
        return passwordEncoder.matches(password, this.password);
    }

    public void block() {
        this.active = false;
    }

    public void unblock() {
        this.active = true;
    }

    public void changePassword(String password) {
        this.password = password;
        this.passwordDate = new Date();
    }

    @Override
    public void validate(IValidationHandler handler) {
        new UserValidator(handler, this).validate();
    }
}

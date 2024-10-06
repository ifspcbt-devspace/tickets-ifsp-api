package br.com.ifsp.tickets.domain.user;

import br.com.ifsp.tickets.domain.company.Company;
import br.com.ifsp.tickets.domain.company.CompanyID;
import br.com.ifsp.tickets.domain.shared.AggregateRoot;
import br.com.ifsp.tickets.domain.shared.exceptions.IllegalEntityIdException;
import br.com.ifsp.tickets.domain.shared.validation.IValidationHandler;
import br.com.ifsp.tickets.domain.user.vo.Document;
import br.com.ifsp.tickets.domain.user.vo.EmailAddress;
import br.com.ifsp.tickets.domain.user.vo.PhoneNumber;
import br.com.ifsp.tickets.domain.user.vo.role.PermissionType;
import br.com.ifsp.tickets.domain.user.vo.role.Role;
import br.com.ifsp.tickets.domain.user.vo.role.RoleType;
import lombok.Getter;

import java.time.LocalDate;
import java.util.Optional;

@Getter
public class User extends AggregateRoot<UserID> {

    private final Role role;
    private String name;
    private String bio;
    private EmailAddress email;
    private PhoneNumber phoneNumber;
    private String username;
    private String password;
    private Document document;
    private LocalDate birthDate;
    private LocalDate passwordDate;
    private boolean active;
    private CompanyID companyID;

    protected User(UserID userID, String name, Role role, String bio, EmailAddress email, PhoneNumber phoneNumber, String username, String password, Document document, LocalDate birthDate, LocalDate passwordDate, boolean active, CompanyID companyID) {
        super(userID);
        this.name = name;
        this.role = role;
        this.bio = bio;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.username = username;
        this.password = password;
        this.document = document;
        this.birthDate = birthDate;
        this.passwordDate = passwordDate;
        this.active = active;
        this.companyID = companyID == null ? new CompanyID(null) : companyID;
    }

    public static User with(UserID userID, String name, Role role, String bio, EmailAddress email, PhoneNumber phoneNumber, String username, String password, Document document, LocalDate birthDate, LocalDate passwordDate, boolean active, CompanyID companyID) {
        return new User(userID, name, role, bio, email, phoneNumber, username, password, document, birthDate, passwordDate, active, companyID);
    }

    public static User create(String name, Role role, PhoneNumber phoneNumber, String username, String password, Document document, LocalDate birthDate) {
        return new User(UserID.unique(), name, role, null, new EmailAddress(null), phoneNumber, username, password, document, birthDate, LocalDate.now(), true, null);
    }

    public void updateProfile(String name, String bio, Document document, LocalDate birthDate) {
        this.name = name;
        this.bio = bio;
        this.document = document;
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

    public boolean canManageCompany() {
        return this.role.getPermissions().contains(PermissionType.MANAGE_COMPANY);
    }

    public boolean canManageAnyCompany() {
        return this.role.getPermissions().contains(PermissionType.MANAGE_ANY_COMPANY);
    }

    public boolean canManageAnyEvent() {
        return this.role.getPermissions().contains(PermissionType.MANAGE_ANY_EVENT);
    }

    public boolean canManageEvents() {
        return this.role.getPermissions().contains(PermissionType.MANAGE_COMPANY_EVENTS);
    }

    public boolean canManageAnyTicket() {
        return this.role.getPermissions().contains(PermissionType.MANAGE_ANY_TICKET);
    }

    public boolean canManageTickets() {
        return this.role.getPermissions().contains(PermissionType.MANAGE_COMPANY_TICKETS);
    }

    public boolean hasCompany() {
        return this.companyID != null && this.companyID.getValue() != null;
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

package br.com.ifsp.tickets.infra.user.persistence;

import br.com.ifsp.tickets.domain.company.CompanyID;
import br.com.ifsp.tickets.domain.user.User;
import br.com.ifsp.tickets.domain.user.UserID;
import br.com.ifsp.tickets.domain.user.vo.CPF;
import br.com.ifsp.tickets.domain.user.vo.EmailAddress;
import br.com.ifsp.tickets.domain.user.vo.PhoneNumber;
import br.com.ifsp.tickets.domain.user.vo.role.Role;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Collection;
import java.util.UUID;

@Entity
@Table(name = "users")
@NoArgsConstructor
@Getter
public class UserJpaEntity implements UserDetails, Serializable {

    @Id
    @Column(name = "id", nullable = false, unique = true)
    private UUID id;
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "email", unique = true)
    private String email;
    @Column(name = "phone")
    private String phoneNumber;
    @Column(name = "username", nullable = false, unique = true)
    private String username;
    @Column(name = "password")
    private String password;
    @Column(name = "cpf", nullable = false, unique = true)
    private String cpf;
    @Column(name = "birth_date")
    private LocalDate birthDate;
    @Column(name = "password_date")
    private LocalDate passwordDate;
    @Column(name = "active", nullable = false)
    private boolean active;
    @Column(name = "company_id")
    private UUID companyID;
    @Column(name = "role_id", nullable = false)
    private Integer roleId;

    public UserJpaEntity(UUID id, String name, String email, String phoneNumber, String username, String password, String cpf, LocalDate birthDate, LocalDate passwordDate, boolean active, UUID companyID, Integer roleId) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.username = username;
        this.password = password;
        this.cpf = cpf;
        this.birthDate = birthDate;
        this.passwordDate = passwordDate;
        this.active = active;
        this.companyID = companyID;
        this.roleId = roleId;
    }

    public static UserJpaEntity from(User user) {
        return new UserJpaEntity(
                user.getId().getValue(),
                user.getName(),
                user.getEmail().getValue(),
                user.getPhoneNumber().getValue(),
                user.getUsername(),
                user.getPassword(),
                user.getCpf().getValue(),
                user.getBirthDate(),
                user.getPasswordDate(),
                user.isActive(),
                user.getCompanyID().getValue(),
                user.getRole().getRoleType().getCode());
    }

    public User toAggregate() {
        return User.with(
                UserID.with(this.id),
                this.name,
                this.getRole(),
                new EmailAddress(this.email),
                new PhoneNumber(this.phoneNumber),
                this.username,
                this.password,
                new CPF(this.cpf),
                this.birthDate,
                this.passwordDate,
                this.active,
                CompanyID.with(this.companyID)
        );
    }

    public Role getRole() {
        return Role.fromCode(this.roleId);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.getRole().getPermissions().stream().map(permission -> new SimpleGrantedAuthority(permission.name())).toList();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return this.active;
    }
}

package br.com.ifsp.tickets.infra.contexts.company.persistence;

import br.com.ifsp.tickets.domain.company.Company;
import br.com.ifsp.tickets.domain.company.CompanyID;
import br.com.ifsp.tickets.domain.company.vo.CNPJ;
import br.com.ifsp.tickets.domain.user.UserID;
import br.com.ifsp.tickets.infra.shared.address.AddressJpaEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;

@Entity
@Table(name = "companies")
@NoArgsConstructor
@Getter
public class CompanyJpaEntity implements Serializable {

    @Id
    @Column(name = "id", nullable = false, unique = true, updatable = false)
    private UUID id;
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "description")
    private String description;
    @Column(name = "cnpj", nullable = false, unique = true)
    private String cnpj;
    @Column(name = "owner_id", nullable = false)
    private UUID ownerID;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id", referencedColumnName = "id")
    private AddressJpaEntity address;

    public CompanyJpaEntity(UUID id, String name, String description, String cnpj, UUID ownerID, AddressJpaEntity address) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.cnpj = cnpj;
        this.ownerID = ownerID;
        this.address = address;
    }

    public static CompanyJpaEntity from(Company company) {
        return new CompanyJpaEntity(
                company.getId().getValue(),
                company.getName(),
                company.getDescription(),
                company.getCnpj().getValue(),
                company.getOwnerID().getValue(),
                AddressJpaEntity.from(company.getId(), company.getAddress())
        );
    }

    public Company toAggregate() {
        return Company.with(
                CompanyID.with(this.id),
                this.name,
                this.description,
                new CNPJ(this.cnpj),
                UserID.with(this.ownerID),
                this.address.toAggregate()
        );
    }

}

package br.com.ifsp.tickets.infra.shared.address;

import br.com.ifsp.tickets.domain.shared.Identifier;
import br.com.ifsp.tickets.domain.shared.vo.Address;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;

@Entity
@Table(name = "addresses")
@NoArgsConstructor
@Getter
public class AddressJpaEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false, updatable = false)
    private UUID id;
    @Column(name = "street", nullable = false)
    private String street;
    @Column(name = "number", nullable = false)
    private String number;
    @Column(name = "complement")
    private String complement;
    @Column(name = "neighborhood", nullable = false)
    private String neighborhood;
    @Column(name = "city", nullable = false)
    private String city;
    @Column(name = "state", nullable = false)
    private String state;
    @Column(name = "country", nullable = false)
    private String country;
    @Column(name = "zip_code", nullable = false)
    private String zipCode;

    public AddressJpaEntity(UUID id, String street, String number, String complement, String neighborhood, String city, String state, String country, String zipCode) {
        this.id = id;
        this.street = street;
        this.number = number;
        this.complement = complement;
        this.neighborhood = neighborhood;
        this.city = city;
        this.state = state;
        this.country = country;
        this.zipCode = zipCode;
    }

    public static AddressJpaEntity from(Identifier<UUID> id, Address address) {
        return new AddressJpaEntity(
                id.getValue(),
                address.getStreet(),
                address.getNumber(),
                address.getComplement(),
                address.getNeighborhood(),
                address.getCity(),
                address.getState(),
                address.getCountry(),
                address.getZipCode()
        );
    }

    public Address toAggregate() {
        return Address.with(
                this.street,
                this.complement,
                this.number,
                this.neighborhood,
                this.city,
                this.state,
                this.country,
                this.zipCode
        );
    }
}

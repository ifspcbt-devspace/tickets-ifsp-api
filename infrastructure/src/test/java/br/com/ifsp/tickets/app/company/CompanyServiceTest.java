package br.com.ifsp.tickets.app.company;

import br.com.ifsp.tickets.app.company.create.CreateCompanyInput;
import br.com.ifsp.tickets.app.company.create.CreateCompanyOutput;
import br.com.ifsp.tickets.app.company.delete.DeleteCompanyInput;
import br.com.ifsp.tickets.app.company.retrieve.search.SearchCompanyOutput;
import br.com.ifsp.tickets.app.company.update.UpdateCompanyInput;
import br.com.ifsp.tickets.app.company.update.UpdateCompanyOutput;
import br.com.ifsp.tickets.domain.company.Company;
import br.com.ifsp.tickets.domain.company.CompanyID;
import br.com.ifsp.tickets.domain.company.ICompanyGateway;
import br.com.ifsp.tickets.domain.shared.exceptions.IllegalResourceAccessException;
import br.com.ifsp.tickets.domain.shared.search.AdvancedSearchQuery;
import br.com.ifsp.tickets.domain.shared.search.Pagination;
import br.com.ifsp.tickets.domain.shared.search.SearchFilter;
import br.com.ifsp.tickets.domain.shared.search.SortSearch;
import br.com.ifsp.tickets.domain.user.IUserGateway;
import br.com.ifsp.tickets.domain.user.User;
import br.com.ifsp.tickets.domain.user.UserID;
import br.com.ifsp.tickets.domain.user.vo.EmailAddress;
import br.com.ifsp.tickets.domain.user.vo.PhoneNumber;
import br.com.ifsp.tickets.domain.user.vo.RG;
import br.com.ifsp.tickets.domain.user.vo.role.Role;
import br.com.ifsp.tickets.infra.config.WebServerConfig;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ContextConfiguration(classes = {WebServerConfig.class})
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ActiveProfiles("test")
public class CompanyServiceTest {

    @Autowired
    IUserGateway userGateway;
    @Autowired
    ICompanyGateway companyGateway;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    CompanyService companyService;


    @Order(1)
    @DisplayName("Should register a new company manager")
    @Test
    public void companyManager() {
        User companyManager = User.with(
                UserID.unique(),
                "Leonardo",
                Role.COMPANY_MANAGER,
                "admin",
                new EmailAddress("l.6042silva@gmail.com"),
                new PhoneNumber("11999999999"),
                "c_manager",
                passwordEncoder.encode("c_manager"),
                new RG("130142281"),
                LocalDate.of(1999, 4, 2),
                null,
                true,
                null
        );

        userGateway.create(companyManager);
    }

    @Order(2)
    @DisplayName("Should register a new customer")
    @Test
    public void customer() {
        User customer = User.with(
                UserID.unique(),
                "Mateus",
                Role.CUSTOMER,
                "admin",
                new EmailAddress("oproprioleonardo@gmail.com"),
                new PhoneNumber("(16) 3496-1354"),
                "cliente",
                passwordEncoder.encode("cliente"),
                new RG("147836785"),
                LocalDate.of(1999, 4, 2),
                null,
                true,
                null
        );

        userGateway.create(customer);
    }

    @Order(3)
    @DisplayName("Should create a new company")
    @Test
    public void newCompany() {
        User companyManager = this.userGateway.findByUsername("c_manager").orElse(null);
        assertThat(companyManager).isNotNull();

        CreateCompanyInput input = CreateCompanyInput.of(
                companyManager,
                companyManager.getId().getValue().toString(),
                "Company Test",
                "desc",
                "67555344000138",
                "Rua Vinte e Quatro",
                "",
                "2324",
                "Moacir Brotas",
                "Colatina",
                "Espírito Santo",
                "29701-503"
        );
        CreateCompanyOutput output = companyService.create(input);

        assertThat(output).isNotNull();
        assertThat(output.id()).isNotNull();

        Company company = this.companyGateway.findById(CompanyID.with(output.id())).orElse(null);
        assertThat(company).isNotNull();

        companyManager = this.userGateway.findById(companyManager.getId()).orElse(null);
        assertThat(companyManager).isNotNull();
        assertThat(companyManager.getCompanyID().equals(company.getId())).isTrue();
    }

    @Order(4)
    @DisplayName("Should not create a new company")
    @Test
    public void newCompanyFail() {
        User customer = this.userGateway.findByUsername("cliente").orElse(null);
        assertThat(customer).isNotNull();

        CreateCompanyInput input = CreateCompanyInput.of(
                customer,
                customer.getId().getValue().toString(),
                "Company Testee",
                "desc",
                "44489691000104",
                "Rua Vinte e Quatro",
                "",
                "2324",
                "Moacir Brotas",
                "Colatina",
                "Espírito Santo",
                "29701-503"
        );

        CreateCompanyOutput output = null;
        try {
            output = companyService.create(input);
        } catch (IllegalResourceAccessException e) {
            assertThat(e).isNotNull();
        }
        assertThat(output).isNull();
    }

    @Order(5)
    @DisplayName("Should update a company")
    @Test
    public void updateCompany() {
        User user = this.userGateway.findByUsername("c_manager").orElse(null);
        assertThat(user).isNotNull();

        Company company = this.companyGateway.findById(user.getCompanyID()).orElse(null);
        assertThat(company).isNotNull();

        UpdateCompanyInput input = UpdateCompanyInput.of(
                company.getId().getValue().toString(),
                "Company Test Updated",
                "desc updated",
                "44489691000104",
                user
        );

        UpdateCompanyOutput output = companyService.update(input);
        assertThat(output).isNotNull();
        assertThat(output.id()).isNotNull();

        company = this.companyGateway.findById(CompanyID.with(output.id())).orElse(null);
        assertThat(company).isNotNull();

        assertThat(company.getName()).isEqualTo("Company Test Updated");
        assertThat(company.getDescription()).isEqualTo("desc updated");
        assertThat(company.getCnpj().getValue()).isEqualTo("44489691000104");
    }

    @Order(6)
    @DisplayName("Should search a company")
    @Test
    public void searchCompany() {
        final AdvancedSearchQuery asq = AdvancedSearchQuery.of(
                0,
                10,
                List.of(SortSearch.of("id", "asc")),
                List.of(SearchFilter.of("name", "test", "ic", "any"))
        );

        Pagination<SearchCompanyOutput> search = companyService.search(asq);
        assertThat(search).isNotNull();
        assertThat(search.total()).isGreaterThan(0);
        assertThat(search.items()).isNotEmpty();
    }

    @Order(7)
    @DisplayName("Should delete a company")
    @Test
    public void deleteCompany() {
        User user = this.userGateway.findByUsername("c_manager").orElse(null);
        assertThat(user).isNotNull();

        Company company = this.companyGateway.findById(user.getCompanyID()).orElse(null);
        assertThat(company).isNotNull();

        companyService.delete(DeleteCompanyInput.of(company.getId().getValue().toString(), user));

        company = this.companyGateway.findById(user.getCompanyID()).orElse(null);
        assertThat(company).isNull();
    }


}

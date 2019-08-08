package com.epam.courses.paycom.dao;

import com.epam.courses.paycom.model.Company;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertAll;
import com.epam.courses.paycom.stub.CompanyStub;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@RunWith(SpringRunner.class)
@PropertySource("classpath:application.properties")
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
@SpringBootTest(classes = TestConfig.class)
@Transactional
@Rollback
public class CompanyRepositoryTest {

    private static final long TOTAL_COUNT = 3;

    @Autowired
    CompanyRepository companyRepository;

    @Test
    public void findAll() {
        List<Company> companies = companyRepository.findAll();
        assertNotNull(companies);
        assertEquals(companies.size(), TOTAL_COUNT);
    }

    @Test
    public void findById() {
        Company company = companyRepository.findById(1).get();
        assertNotNull(company);
        assertAll(
                () -> assertEquals(company.getCompanyId().intValue(), 1),
                () -> assertEquals(company.getCompanyAccount(), "BY27BLBB34325630287478004008"),
                () -> assertEquals(company.getCompanyName(), "Prestizh"));
    }

    @Test
    public void findByAccount() {
        Company company = companyRepository.findByAccount("BY27BLBB34325630287478004008").get();
        assertNotNull(company);
        assertAll(
                () -> assertEquals(company.getCompanyId().intValue(), 1),
                () -> assertEquals(company.getCompanyName(), "Prestizh"));
    }

    @Test
    public void addCompany() {
        List<Company> companiesBeforeInsert = companyRepository.findAll();
        Company company = new Company();
        company.setCompanyAccount("account");
        company.setCompanyName("name");
        companyRepository.save(company);
        List<Company> companiesAfterInsert = companyRepository.findAll();
        assertEquals(1, companiesAfterInsert.size() - companiesBeforeInsert.size());
    }

    @Test
    public void findAllStubs() {
        List<CompanyStub> stub = companyRepository.findAllStubs();
        assertNotNull(stub);
        assertTrue(stub.size() > 1);}

    @Test
    public void delete() {
        List<Company> companiesBeforeDelete = companyRepository.findAll();
        Company company = companyRepository.findById(3).get();
        companyRepository.deleteById(company.getCompanyId());
        List<Company> companiesAfterDelete = companyRepository.findAll();
        assertEquals(1, companiesBeforeDelete.size() - companiesAfterDelete.size());
    }
}
package com.epam.courses.paycom.dao;

import com.epam.courses.paycom.model.Company;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertAll;
import com.epam.courses.paycom.stub.CompanyStub;
import org.hibernate.exception.ConstraintViolationException;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import java.util.List;

@RunWith(SpringRunner.class)
@PropertySource("classpath:application.properties")
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest
@Sql({"/data-script.sql"})
public class CompanyRepositoryTest {

    @Autowired
    CompanyRepository companyRepository;

    @Test
    public void findAll() {
        List<Company> companies = companyRepository.findAll();
        assertNotNull(companies);
        assertEquals(companies.size(), 2);
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
        company.setCompanyId(4);
        company.setCompanyAccount("aaa");
        company.setCompanyName("bbb");
        companyRepository.save(company);

        List<Company> companiesAfterInsert = companyRepository.findAll();
        assertEquals(1, companiesAfterInsert.size() - companiesBeforeInsert.size());
    }

    @Test
    public void addDublicateCompany() {
        Company company2 = new Company();
        company2.setCompanyId(4);
        company2.setCompanyAccount("aaa");
        company2.setCompanyName("bbb");
        companyRepository.save(company2);
        company2.setCompanyId(5);

        Assertions.assertThrows(org.springframework.dao.DataIntegrityViolationException.class, () -> {
            companyRepository.save(company2);
        });

            }

    @Test
    public void findAllStubs() {
        List<CompanyStub> stub = companyRepository.findAllStubs();
        assertNotNull(stub);
        assertTrue(stub.size() > 1);}

}
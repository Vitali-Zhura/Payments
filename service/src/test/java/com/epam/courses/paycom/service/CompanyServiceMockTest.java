package com.epam.courses.paycom.service;

import com.epam.courses.paycom.dao.CompanyRepository;
import com.epam.courses.paycom.model.Company;
import com.epam.courses.paycom.stub.CompanyStub;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.*;


public class CompanyServiceMockTest {

    private static final int ONE = 1;
    private static Company FIRST_COMPANY;
    private static CompanyStub FIRST_COMPANYSTUB;

    private CompanyRepository repository;
    private CompanyService service;
    private static List<Company> list;

    @BeforeAll
    static void init() {
        FIRST_COMPANY = create(ONE);
        FIRST_COMPANYSTUB = createStub(ONE);
        list = new ArrayList<Company>();
        list.add(create(ONE));
    }

    @BeforeEach
    void setup() {
        repository = Mockito.mock(CompanyRepository.class);
        service = new CompanyServiceImpl(repository);
    }
    @Test
    public void findAll() {

        Mockito.when(repository.findAll()).thenReturn(list);
        List<Company> result = service.findAll();
        assertNotNull(result);
        assertEquals(ONE, result.size());
        Mockito.verify(repository, Mockito.times(ONE)).findAll();
        Mockito.verifyNoMoreInteractions(repository);
    }

    @Test
    public void findAllStubs() {
        List <CompanyStub> listStub = new ArrayList<CompanyStub>();
        listStub.add(createStub(ONE));

        Mockito.when(repository.findAllStubs()).thenReturn(listStub);
        List<CompanyStub> result = service.findAllStubs();
        assertNotNull(result);
        assertEquals(ONE, result.size());

        Mockito.verify(repository, Mockito.times(ONE)).findAllStubs();
        Mockito.verifyNoMoreInteractions(repository);
    }

    @Test
    public void findById() {

        Mockito.when(repository.findById(anyInt())).thenReturn(Optional.of(FIRST_COMPANY));

        Company result = service.findById(ONE);
        assertNotNull(result);
        assertEquals(result.getCompanyId(), FIRST_COMPANY.getCompanyId());
        Mockito.verify(repository, Mockito.times(ONE)).findById(anyInt());
        Mockito.verifyNoMoreInteractions(repository);
    }

    @Test
    public void findByAccount() {

        Mockito.when(repository.findByAccount(anyString())).thenReturn(Optional.of(FIRST_COMPANY));
        Company result = service.findByAccount(anyString());
        assertNotNull(result);

        Mockito.verify(repository, Mockito.times(ONE)).findByAccount(anyString());
        Mockito.verifyNoMoreInteractions(repository);
    }

    @Test
    public void add() {

        Mockito.when(repository.save(any())).thenReturn(FIRST_COMPANY);

        service.add(any());
        Mockito.verify(repository, Mockito.times(ONE)).save(any());
        Mockito.verifyNoMoreInteractions(repository);
    }

    @Test
    public void delete() {

        repository.deleteById(anyInt());
        Mockito.verify(repository, Mockito.times(ONE)).deleteById(anyInt());
        Mockito.verifyNoMoreInteractions(repository);
    }

    private static Company create(int index) {
        Company company = new Company();
        company.setCompanyId(index);
        company.setCompanyAccount("account" + index);
        company.setCompanyName("name" + index);
        return company;
    }

    private static CompanyStub createStub(int index) {
        CompanyStub companyStub = new CompanyStub();
        companyStub.setId(index);
        companyStub.setName("name" + index);
        companyStub.setCounts(5 + index);
        companyStub.setAmounts(200 + index);
        return companyStub;
    }
}

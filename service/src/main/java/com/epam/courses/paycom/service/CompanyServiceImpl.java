package com.epam.courses.paycom.service;

import com.epam.courses.paycom.dao.CompanyRepository;
import com.epam.courses.paycom.model.Company;
import com.epam.courses.paycom.stub.CompanyStub;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Transactional
public class CompanyServiceImpl implements CompanyService {
    private static final Logger LOGGER = LoggerFactory.getLogger(CompanyServiceImpl.class);

    private CompanyRepository repository;

    public CompanyServiceImpl(CompanyRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Company> findAll() {
        LOGGER.debug("Find all companies");
        return repository.findAll();
    }

    @Override
    public void add(Company company) {
        repository.save(company);
    }

    @Override
    public Company findById(Integer id) {
        LOGGER.debug("findById({})", id);
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Failed to get company from DB"));
    }

    @Override
    public Company findByAccount(String companyAccount) {
        LOGGER.debug("findByAccount({})", companyAccount);
        return repository.findByAccount(companyAccount)
                .orElseThrow(() -> new RuntimeException("Failed to get company from DB"));
    }

    @Override
    public void update(Company company) {
        LOGGER.debug("update({})", company);
        repository.save(company);
    }

    @Override
    public void delete (int id) {
        LOGGER.debug("delete({})", id);
        repository.deleteById(id);
    }

    @Override
    public List<CompanyStub> findAllStubs() {
        LOGGER.debug("Find all stubs");
        return repository.findAllStubs();
    }
}

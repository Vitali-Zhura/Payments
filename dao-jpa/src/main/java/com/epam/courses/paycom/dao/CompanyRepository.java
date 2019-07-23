package com.epam.courses.paycom.dao;

import com.epam.courses.paycom.model.Company;

import com.epam.courses.paycom.stub.CompanyStub;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Integer> {

    List<Company> findAll();

    Optional<Company> findById(Integer companyId);

    @Query(value = "from Company c where companyAccount= :companyAccount")
    Optional<Company> findByAccount(@Param("companyAccount") String companyAccount);

    Company save(Company company);

    void deleteById(Integer companyId);

    @Query(nativeQuery=true)
    List<CompanyStub> findAllStubs();
}


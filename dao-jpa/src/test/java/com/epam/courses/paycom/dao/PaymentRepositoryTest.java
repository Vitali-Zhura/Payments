package com.epam.courses.paycom.dao;

import com.epam.courses.paycom.model.Company;
import com.epam.courses.paycom.model.Payment;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertAll;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import java.sql.Date;
import java.util.List;

@RunWith(SpringRunner.class)
@PropertySource("classpath:application.properties")
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest
@Sql({"/data-script.sql"})
public class PaymentRepositoryTest {

    @Autowired
    PaymentRepository paymentRepository;

    @Test
    public void findAll() {
        List<Payment> payments = paymentRepository.findAll();
        assertNotNull(payments);
        assertEquals(payments.size(), 3);
    }

    @Test
    public void findById() {

        Payment payment = paymentRepository.findById(1).get();
        assertNotNull(payment);
        assertAll(
                () -> assertEquals(payment.getPaymentId().intValue(), 1),
                () -> assertEquals(payment.getCompanyAccount().toString(), createCompany().toString()),
                () -> assertEquals(payment.getPayerName(), "Ivanov"),
                () -> assertEquals(payment.getPaymentSum().intValue(), 230),
                () -> assertEquals(payment.getPaymentDate().toString(), "2019-03-10 12:12:30.0"));
    }

    @Test
    public void addPayment() {
        List<Payment> paymentsBeforeInsert = paymentRepository.findAll();
        Payment payment = new Payment();
        payment.setCompanyAccount(createCompany());
        payment.setPaymentId(7);
        payment.setPayerName("aaa");
        payment.setPaymentSum(100);
        paymentRepository.save(payment);

        List<Payment> paymentsAfterInsert = paymentRepository.findAll();
        assertEquals(1, paymentsAfterInsert.size() - paymentsBeforeInsert.size());
    }

    @Test
    public void findByDate() {

        Date beginDate = java.sql.Date.valueOf("2019-03-10");
        Date endDate = java.sql.Date.valueOf("2019-03-11");
        List<Payment> payments = paymentRepository.findByDate(beginDate, endDate);
        assertNotNull(payments);
        assertEquals(payments.size(), 3);
    }

    @Test
    public void cancel() {

        List<Payment> paymentsBeforeDelete = paymentRepository.findAll();
        Payment payment =  paymentRepository.findById(1).get();
        paymentRepository.deleteById(payment.getPaymentId());
        List<Payment> paymentsAfterDelete = paymentRepository.findAll();
        assertEquals(1, paymentsBeforeDelete.size() - paymentsAfterDelete.size());
    }

    Company createCompany() {

        Company company = new Company();
        company.setCompanyId(1);
        company.setCompanyAccount("BY27BLBB34325630287478004008");
        company.setCompanyName("Prestizh");
        return company;
    }
}
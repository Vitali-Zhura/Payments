package com.epam.courses.paycom.service;

import com.epam.courses.paycom.dao.PaymentRepository;
import com.epam.courses.paycom.model.Company;
import com.epam.courses.paycom.model.Payment;

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

public class PaymentServiceMockTest {

    private static final int ONE = 1;
    private static Payment FIRST_PAYMENT;

    private PaymentRepository repository;
    private PaymentService service;
    private static List<Payment> list;

    @BeforeAll
    static void init() {
        FIRST_PAYMENT = create(ONE);
        list = new ArrayList<Payment>();
        list.add(create(ONE));
    }

    @BeforeEach
    void setup() {
        repository = Mockito.mock(PaymentRepository.class);
        service = new PaymentServiceImpl(repository);
    }

    @Test
    public void findAll() {

        Mockito.when(repository.findAll()).thenReturn(list);
        List<Payment> result = service.findAll();
        assertNotNull(result);
        assertEquals(ONE, result.size());

        Mockito.verify(repository, Mockito.times(ONE)).findAll();
        Mockito.verifyNoMoreInteractions(repository);
    }

    @Test
    public void findById() {

        Mockito.when(repository.findById(anyInt())).thenReturn(Optional.of(FIRST_PAYMENT));

        Payment result = service.findById(ONE);
        assertNotNull(result);
        assertEquals(result.getPaymentId(), FIRST_PAYMENT.getPaymentId());
        Mockito.verify(repository, Mockito.times(ONE)).findById(anyInt());
        Mockito.verifyNoMoreInteractions(repository);
    }

    @Test
    public void findByDate() {

        Mockito.when(repository.findByDate(any(), any())).thenReturn(list);
        List <Payment> result = service.findByDate(any(), any());
        assertNotNull(result);
        Mockito.verify(repository, Mockito.times(ONE)).findByDate(any(), any());
        Mockito.verifyNoMoreInteractions(repository);
    }

    @Test
    public void add() {

        Mockito.when(repository.save(any())).thenReturn(FIRST_PAYMENT);
        service.add(any());
        Mockito.verify(repository, Mockito.times(ONE)).save(any());
        Mockito.verifyNoMoreInteractions(repository);
    }

    @Test
    public void cancel() {

        repository.deleteById(anyInt());
        Mockito.verify(repository, Mockito.times(ONE)).deleteById(anyInt());
        Mockito.verifyNoMoreInteractions(repository);
    }


    private static Payment create(int index) {
        Payment payment = new Payment();
        payment.setPaymentId(index);
        payment.setPayerName("name" + index);
        payment.setPaymentSum(100 + index);
        payment.setCompanyAccount(createCompany(1));
        payment.setPaymentDate(java.sql.Timestamp.valueOf("2019-03-10 12:12:30"));
        return payment;
    }

    private static Company createCompany(int index) {
        Company company = new Company();
        company.setCompanyId(index);
        company.setCompanyAccount("account" + index);
        company.setCompanyName("name" + index);
        return company;
    }
}

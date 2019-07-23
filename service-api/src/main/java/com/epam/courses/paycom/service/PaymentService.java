package com.epam.courses.paycom.service;

import com.epam.courses.paycom.model.Payment;

import java.sql.Date;
import java.util.List;

public interface PaymentService {

    List<Payment> findAll();

    void add(Payment payment);

    Payment findById(Integer id);

    List<Payment> findByDate(Date beginDate, Date endDate);

    void cancel(int id);
}
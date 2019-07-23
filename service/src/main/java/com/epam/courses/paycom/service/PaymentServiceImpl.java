package com.epam.courses.paycom.service;

import com.epam.courses.paycom.dao.PaymentRepository;
import com.epam.courses.paycom.model.Payment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.List;

@Transactional
public class PaymentServiceImpl implements PaymentService{

    private static final Logger LOGGER = LoggerFactory.getLogger (PaymentServiceImpl.class);

    private PaymentRepository repository;

    public PaymentServiceImpl(PaymentRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Payment> findAll() {
        LOGGER.debug("Find all payments");
        return repository.findAll();
    }

    @Override
    public void add(Payment payment) {
        repository.save(payment);
    }

    @Override
    public Payment findById(Integer id) {
        LOGGER.debug("findById({})", id);
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Failed to get payment from DB"));
    }

    @Override
    public List<Payment> findByDate(Date beginDate, Date endDate) {
        LOGGER.debug("findByDate({})", beginDate, endDate);
        return repository.findByDate(beginDate, endDate);
    }

    @Override
    public void cancel (int id) {
        LOGGER.debug("delete({})", id);
        repository.deleteById(id);
    }
}

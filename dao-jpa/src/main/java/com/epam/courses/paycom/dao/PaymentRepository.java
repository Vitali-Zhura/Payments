package com.epam.courses.paycom.dao;

import com.epam.courses.paycom.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Integer> {

    List<Payment> findAll();

    Optional<Payment> findById(Integer paymentId);

    @Query(value = "from Payment p where paymentDate BETWEEN :beginDate AND concat(:endDate, ' ', '23:59:59')")
    List<Payment> findByDate(@Param("beginDate")Date beginDate,@Param("endDate")Date endDate);

    Payment save(Payment payment);

    void deleteById(Integer paymentId);
}

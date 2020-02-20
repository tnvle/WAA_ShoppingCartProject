package com.online.store.waa3l.repository;

import com.online.store.waa3l.domain.Payment;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface PaymentRepository extends CrudRepository<Payment, Integer> {
}

package com.ms_payments.repository;

import com.ms_payments.model.entity.PaymentsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentsRepository extends JpaRepository<PaymentsEntity, Integer> {
}

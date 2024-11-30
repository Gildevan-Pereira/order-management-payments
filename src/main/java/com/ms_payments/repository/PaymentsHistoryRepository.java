package com.ms_payments.repository;

import com.ms_payments.model.document.PaymentsHistoryDocument;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PaymentsHistoryRepository extends MongoRepository<PaymentsHistoryDocument, String> {
}

package com.ms_payments.service;

import com.ms_payments.repository.PaymentsHistoryRepository;
import com.ms_payments.repository.PaymentsRepository;
import com.ms_payments.model.dto.request.PaymentRequestDto;
import com.ms_payments.model.entity.PaymentsEntity;
import com.ms_payments.model.mapper.PaymentsEntityMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentOrderService {

    private final PaymentsRepository paymentsRepository;
    private final PaymentsHistoryRepository paymentsHistoryRepository;

    @Transactional
    public void savePayment(PaymentRequestDto dto) {
        PaymentsEntity paymentsEntity = PaymentsEntityMapper.fromRequest(dto);

        var entitySaved = paymentsRepository.save(paymentsEntity);
        log.info("PaymentOrderService.savePayment - Payment saved successful | data: {}", entitySaved);

        var document = PaymentsEntityMapper.toDocumentFromEntity(entitySaved);

        paymentsHistoryRepository.save(document);
        log.info("PaymentOrderService.savePayment - Payment history saved successful | data: {}", document);

    }
}

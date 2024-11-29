package com.ms_payments.service;

import com.ms_payments.PaymentsRepository;
import com.ms_payments.model.dto.request.PaymentRequestDto;
import com.ms_payments.model.entity.PaymentsEntity;
import com.ms_payments.model.mapper.PaymentsEntityMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentOrderService {

    private final PaymentsRepository paymentsRepository;

    public void savePayment(PaymentRequestDto dto) {

        PaymentsEntity paymentsEntity = PaymentsEntityMapper.fromRequest(dto);

        paymentsRepository.save(paymentsEntity);

    }
}

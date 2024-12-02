package com.ms_payments.model.mapper;

import com.ms_payments.model.document.PaymentsHistoryDocument;
import com.ms_payments.model.dto.request.PaymentRequestDto;
import com.ms_payments.model.entity.PaymentsEntity;
import com.ms_payments.model.enums.PaymentStatusEnum;

public class PaymentsEntityMapper {

    public static PaymentsEntity fromRequest(PaymentRequestDto dto) {

//        TODO: Corrigir implementação do ojeto de cartão
        return PaymentsEntity.builder()
                .orderId(dto.getOrderId())
                .orderDate(dto.getOrderDate())
                .clientCpf(dto.getClientCpf())
//                .cardNumber(dto.getCardNumber())
                .amount(dto.getAmount())
                .status(PaymentStatusEnum.valueOf(dto.getStatus()))
                .build();
    }

    public static PaymentsHistoryDocument toDocumentFromEntity(PaymentsEntity entity) {
        return PaymentsHistoryDocument.builder()
                .orderId(entity.getOrderId())
                .orderDate(entity.getOrderDate())
                .paymentRegisterDate(entity.getCreatedAt())
                .clientCpf(entity.getClientCpf())
                .cardNumber(entity.getCardNumber())
                .amount(entity.getAmount())
                .status(entity.getStatus())
                .build();
    }
}

package com.ms_payments.model.mapper;

import com.ms_payments.model.dto.request.CreatePaymentRequestDto;
import com.ms_payments.model.dto.request.PaymentReceiptDto;
import com.ms_payments.model.dto.request.PaymentRequestDto;
import com.ms_payments.model.dto.request.UpdateOrderRequestDto;
import com.ms_payments.model.entity.PaymentsEntity;
import com.ms_payments.model.enums.PaymentStatusEnum;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class PaymentsEntityMapper {

    public static PaymentsEntity fromRequestToEntity(PaymentRequestDto dto) {

        return PaymentsEntity.builder()
                .orderId(dto.getOrderId())
                .orderDate(dto.getOrderDate())
                .clientCpf(dto.getClientCpf())
                .cardHolderName(dto.getCardDto().getHolderName())
                .cardNumber(dto.getCardDto().getNumber())
                .cardBrand(dto.getCardDto().getBrand())
                .cardCvv(dto.getCardDto().getCvv())
                .cardType(dto.getCardDto().getType())
                .cardExpirationDate(dto.getCardDto().getExpirationDate())
                .amount(dto.getAmount())
                .status(PaymentStatusEnum.valueOf(dto.getStatus()))
                .build();
    }

    public static CreatePaymentRequestDto fromRequestToCreatePayment(PaymentRequestDto dto) {

        return CreatePaymentRequestDto.builder()
                .orderId(dto.getOrderId())
                .clientCpf(dto.getClientCpf())
                .card(dto.getCardDto())
                .amount(dto.getAmount())
                .build();
    }

    public static UpdateOrderRequestDto fromEntityToUpdateOrderRequest(PaymentsEntity entity) {

        return UpdateOrderRequestDto.builder()
                .orderId(entity.getOrderId())
                .status(entity.getStatus().name())
                .attemptedPaymentAt(entity.getCreatedAt())
//                TODO: implementar URL do arquivo do S3
                .receiptUrl(entity.getReceiptUrl())
                .build();
    }

    public static PaymentReceiptDto fromEntityToPaymentSlipPublisher(PaymentsEntity entity) {

//        TODO: Verificar a necessidade das informações em null
        return PaymentReceiptDto.builder()
                .paymentId(entity.getId())
                .orderId(entity.getOrderId())
                .payerName(entity.getCardHolderName())
                .beneficiaryName(null)
                .transactionAmount(entity.getAmount())
                .transactionDateTime(entity.getCreatedAt())
                .paymentMethod(entity.getCardType())
                .paymentDescription(null)
                .financialInstitution(null)
                .build();
    }
}

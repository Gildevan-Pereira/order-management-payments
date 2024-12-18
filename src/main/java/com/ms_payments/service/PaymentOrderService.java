package com.ms_payments.service;

import com.ms_payments.client.PaymentGatewayClient;
import com.ms_payments.events.PaymentSlipPublisher;
import com.ms_payments.events.UpdateOrderPublisher;
import com.ms_payments.exception.BusinessException;
import com.ms_payments.featureflag.RedisFeatureFlagService;
import com.ms_payments.messages.MessageEnum;
import com.ms_payments.model.dto.request.CreatePaymentRequestDto;
import com.ms_payments.model.dto.request.PaymentRequestDto;
import com.ms_payments.model.dto.response.ProcessedPaymentResponseDto;
import com.ms_payments.model.entity.PaymentsEntity;
import com.ms_payments.model.enums.PaymentStatusEnum;
import com.ms_payments.model.mapper.PaymentsEntityMapper;
import com.ms_payments.repository.PaymentsRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentOrderService {

    private final PaymentsRepository paymentsRepository;
    private final PaymentGatewayClient paymentGatewayClient;
    private final UpdateOrderPublisher updateOrderPublisher;
    private final PaymentSlipPublisher paymentSlipPublisher;
    private final RedisFeatureFlagService featureFlagService;


    @Transactional
    public void savePayment(PaymentRequestDto dto) {
        PaymentsEntity paymentsEntity = PaymentsEntityMapper.fromRequestToEntity(dto);
        paymentsEntity.setStatus(PaymentStatusEnum.CREATED);

        var entitySaved = paymentsRepository.save(paymentsEntity);
        log.info("PaymentOrderService.savePayment - Payment saved successful | data: {}", entitySaved);

        var processPaymentDto = PaymentsEntityMapper.fromRequestToCreatePayment(dto);

        var paymentResponse = processPayment(processPaymentDto);

        if (paymentResponse.getCode() == 10) {
            throw new BusinessException(MessageEnum.GENERIC_ERROR, MessageEnum.GENERIC_ERROR.getCode(), HttpStatus.UNAUTHORIZED);
        }

        entitySaved.setStatus(PaymentStatusEnum.fromCode(paymentResponse.getCode()));

        var entitySaved2 = paymentsRepository.save(entitySaved);
        log.info("PaymentOrderService.savePayment - Payment saved successful | data: {}", entitySaved2);


        var updateOrderDto = PaymentsEntityMapper.fromEntityToUpdateOrderRequest(entitySaved2);
        var paymentSlipDto = PaymentsEntityMapper.fromEntityToPaymentSlipPublisher(entitySaved2);

        updateOrderPublisher.send(updateOrderDto);

        boolean enabled = featureFlagService.isFeatureEnabled("sendToS3");

        if (enabled) {
            paymentSlipPublisher.send(paymentSlipDto);
        }
    }

    public ProcessedPaymentResponseDto processPayment(CreatePaymentRequestDto dto) {
        var response = paymentGatewayClient.processPayment(dto);
        if (response.getStatusCode().value() == 500) {
            throw new BusinessException(MessageEnum.CARD_IS_NOT_VALID, MessageEnum.CARD_IS_NOT_VALID.getCode(), HttpStatus.UNAUTHORIZED);
        }
        return response.getBody();
    }
}

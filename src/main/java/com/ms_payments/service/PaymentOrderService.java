package com.ms_payments.service;

import com.ms_payments.exception.BusinessException;
import com.ms_payments.messages.MessageEnum;
import com.ms_payments.model.dto.request.FetchPaymentRequestDto;
import com.ms_payments.model.dto.request.PaymentRequestDto;
import com.ms_payments.model.dto.response.ProcessedPaymentResponseDto;
import com.ms_payments.model.entity.PaymentsEntity;
import com.ms_payments.model.mapper.PaymentsEntityMapper;
import com.ms_payments.repository.PaymentsHistoryRepository;
import com.ms_payments.repository.PaymentsRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentOrderService {

    private static final String API_URL = "https://payment-gateway-api.com";
    private static final String CLIENT_ID = "CLIENT_ID";
    private static final String CLIENT_SECRET = "CLIENT_SECRET";

    private final PaymentsRepository paymentsRepository;
    private final PaymentsHistoryRepository paymentsHistoryRepository;
    private final RestTemplate restTemplate;

    @Transactional
    public void savePayment(PaymentRequestDto dto) {
        PaymentsEntity paymentsEntity = PaymentsEntityMapper.fromRequest(dto);

        var fetchPaymentDto = FetchPaymentRequestDto.builder()
                                        .orderId(dto.getOrderId())
                                        .clientCpf(dto.getClientCpf())
                                        .cardNumber(dto.getCardNumber())
                                        .amount(dto.getAmount())
                                        .build();

        var paymentResponse = fetchPayments(fetchPaymentDto);

//      TODO: Corrigir essa lógica quando o gateway for implementado
        if (!paymentResponse.getCode().equals("1")) {
            throw new BusinessException(MessageEnum.GENERIC_ERROR, MessageEnum.GENERIC_ERROR.getCode(), HttpStatus.UNAUTHORIZED);
        }

        var entitySaved = paymentsRepository.save(paymentsEntity);
        log.info("PaymentOrderService.savePayment - Payment saved successful | data: {}", entitySaved);

        var document = PaymentsEntityMapper.toDocumentFromEntity(entitySaved);

        paymentsHistoryRepository.save(document);
        log.info("PaymentOrderService.savePayment - Payment history saved successful | data: {}", document);


    }

//    TODO: Corrigir lógica para o gateway
    public ProcessedPaymentResponseDto fetchPayments(FetchPaymentRequestDto dto) {

        String url = API_URL + "/v1/oauth2/token";
        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth(CLIENT_ID, CLIENT_SECRET);
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        HttpEntity<String> request = new HttpEntity<>("grant_type=client_credentials", headers);

        ResponseEntity<ProcessedPaymentResponseDto> response = restTemplate.exchange(url, HttpMethod.POST, request, ProcessedPaymentResponseDto.class);

        if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
            return response.getBody();
        }

        throw new BusinessException(MessageEnum.GENERIC_ERROR, MessageEnum.GENERIC_ERROR.getCode(), HttpStatus.UNAUTHORIZED);
    }
}

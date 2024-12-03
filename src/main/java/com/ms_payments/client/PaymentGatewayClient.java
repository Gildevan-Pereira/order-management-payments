package com.ms_payments.client;

import com.ms_payments.model.dto.request.CreatePaymentRequestDto;
import com.ms_payments.model.dto.response.ProcessedPaymentResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "paymentGateway", url = "http://localhost:3000/api")
public interface PaymentGatewayClient {

    @PostMapping("/payment")
    ResponseEntity<ProcessedPaymentResponseDto> processPayment(@RequestBody CreatePaymentRequestDto paymentRequest);
}

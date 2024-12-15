package com.ms_payments.client;

import com.ms_payments.model.dto.request.CreatePaymentRequestDto;
import com.ms_payments.model.dto.response.ProcessedPaymentResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(value = "paymentGateway", url = "http://localhost:3000/api")
public interface PaymentGatewayClient {

    @RequestMapping(method = RequestMethod.POST, value = "/payments")
    ResponseEntity<ProcessedPaymentResponseDto> processPayment(@RequestBody CreatePaymentRequestDto paymentRequest);
}

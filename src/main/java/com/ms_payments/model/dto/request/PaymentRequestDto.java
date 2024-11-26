package com.ms_payments.model.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentRequestDto {

    private Integer orderId;

    private String orderDate;

    private String clientCpf;

    private String cardNumber;

    private BigDecimal amount;

    private String status;

}

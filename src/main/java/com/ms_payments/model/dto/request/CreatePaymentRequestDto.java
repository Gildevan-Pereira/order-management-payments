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
public class CreatePaymentRequestDto {

    private Integer orderId;

    private String clientCpf;

    private BigDecimal amount;

    private CardDto card;

}

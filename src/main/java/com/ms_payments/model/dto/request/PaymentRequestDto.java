package com.ms_payments.model.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentRequestDto {

    private Integer orderId;

    private LocalDateTime orderDate;

    private String clientCpf;

    private BigDecimal amount;

    private String status;

    private CardDto cardDto;

}

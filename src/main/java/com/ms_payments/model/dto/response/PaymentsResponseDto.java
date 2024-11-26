package com.ms_payments.model.dto.response;

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
public class PaymentsResponseDto {

    private Integer id;

    private Integer orderId;

    private LocalDateTime orderDate;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private LocalDateTime removedAt;

    private String clientCpf;

    private String cardNumber;

    private BigDecimal amount;

    private String status;

    private String receiptUrl;
}

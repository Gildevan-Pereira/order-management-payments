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
public class UpdateOrderRequestDto {

    private Integer orderId;
    private String status;
    private LocalDateTime attemptedPaymentAt;
    private String receiptUrl;

}

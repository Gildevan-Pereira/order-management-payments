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
public class PaymentReceiptDto {

    private Integer paymentId;
    private Integer orderId;
    private String payerName;
    private String beneficiaryName;
    private BigDecimal transactionAmount;
    private LocalDateTime transactionDateTime;
    private String paymentMethod;
    private String paymentDescription;
    private String financialInstitution;

}

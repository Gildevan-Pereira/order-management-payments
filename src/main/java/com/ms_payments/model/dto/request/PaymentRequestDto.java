package com.ms_payments.model.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentRequestDto implements Serializable {

    private Integer orderId;

    private LocalDateTime orderDate;

    private String clientCpf;

    private String cardNumber;

    private BigDecimal amount;

    private String status;

}

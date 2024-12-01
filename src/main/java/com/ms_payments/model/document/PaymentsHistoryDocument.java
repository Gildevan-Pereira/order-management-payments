package com.ms_payments.model.document;

import com.ms_payments.model.enums.OrderStatusEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.cglib.core.Local;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@EntityListeners(AuditingEntityListener.class)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "payment_history")
public class PaymentsHistoryDocument {

    @Id
    private String id;

    private Integer orderId;

    private LocalDateTime orderDate;

    @CreatedDate
    private LocalDateTime createdAt;

    private LocalDateTime paymentRegisterDate;

    private String clientCpf;

    private String cardNumber;

    private BigDecimal amount;

    private OrderStatusEnum status;

    private String receiptUrl;
}

package com.ms_payments.model.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CardDto {

    private String holderName;

    private String number;

    private String brand;

    private String cvv;

    private String type;

    private String expirationDate;

}

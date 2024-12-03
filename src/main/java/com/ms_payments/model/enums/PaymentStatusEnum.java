package com.ms_payments.model.enums;

import com.ms_payments.exception.BusinessException;
import com.ms_payments.messages.MessageEnum;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.Objects;

public enum PaymentStatusEnum {

    CREATED(1),
    REJECTED(2),
    PROCESSED(3);

    private final Integer code;

    PaymentStatusEnum(Integer code) {
        this.code = code;
    }

    public static PaymentStatusEnum fromName(String name) {
        for (PaymentStatusEnum statusEnum : PaymentStatusEnum.values()){
            if (statusEnum.name().equals(name)) {
                return statusEnum;
            }
        }
        throw new BusinessException(MessageEnum.INVALID_STATUS, List.of(PaymentStatusEnum.values()).toString(), HttpStatus.BAD_REQUEST);
    }

    public static PaymentStatusEnum fromCode(Integer code) {
        for (PaymentStatusEnum statusEnum : PaymentStatusEnum.values()) {
            if (Objects.equals(statusEnum.code, code)) {
                return statusEnum;
            }
        }
        throw new BusinessException(MessageEnum.INVALID_STATUS, List.of(PaymentStatusEnum.values()).toString(), HttpStatus.BAD_REQUEST);
    }

}
package com.ms_payments.exception;

import com.ms_payments.messages.MessageEnum;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class InternalException extends RuntimeException {

    private String code;
    private HttpStatus httpStatus;

    public InternalException(MessageEnum message, HttpStatus httpStatus) {
        super(message.getMessage());
        this.code = message.getCode();
        this.httpStatus = httpStatus;
    }
}
